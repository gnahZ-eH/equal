/*
 * MIT License
 *
 * Copyright (c) [2019] [He Zhang]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.equal.processor.selector;

import com.github.equal.annotation.Column;
import com.github.equal.enums.ExceptionType;
import com.github.equal.exception.SelectorException;
import com.github.equal.processor.adapter.Adapter;
import com.github.equal.utils.DateUtils;
import com.github.equal.utils.FileUtils;
import com.github.equal.utils.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

public class XLSSelector extends FileSelector {

    private final Workbook workbook;

    public XLSSelector(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public <T> Stream<T> selectFromFile(Selector<T> selector) throws SelectorException {
        Stream.Builder<T> builder = Stream.builder();
        Class<T> clazz = selector.getClazz();

        init(clazz.getDeclaredFields());
        Sheet table = getTable(selector);

        if (table == null) {
            throw new SelectorException(ExceptionType.DID_NOT_FIND_THE_TABLE);
        }

        int rowStartIndex = selector.getRowStartIndex();
        int numberOfRows = selector.getNumberOfRows();
        int rows = table.getLastRowNum() + 1;

        for (int i = 0; i < rows; i++) {
            if (numberOfRows == 0) break;
            if (i < rowStartIndex - 1) continue;

            Row row = table.getRow(i);
            if (row == null) {
                builder.add(null);
            } else {
                T obj;

                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new SelectorException(e);
                } finally {
                    FileUtils.closeIO(workbook);
                }

                for (Field field : fieldIndexes.values()) {
                    setField(field, row, obj);
                }
                builder.add(obj);
            }
            numberOfRows--;
        }

        // if numberRows > rows, use null
        if (numberOfRows > 0) {
            while (0 != numberOfRows--) {
                builder.add(null);
            }
        }

        FileUtils.closeIO(workbook);
        return builder.build();
    }

    @Override
    public Object getCellValue(Cell cell, Field field) {
        Adapter<String, ?> adapter = fieldAdapters.get(field);
        if (adapter == null) {
            return cell.getStringCellValue();
        }
        if (cell.getCellType() != CellType.NUMERIC) {
            return adapter.fromString(cell.getStringCellValue());
        }
        if (DateUtils.isDateOrTime(field.getType())) {
            Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
            if (field.getType().equals(LocalDate.class)) {
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (field.getType().equals(LocalTime.class)) {
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            } else if (field.getType().equals(LocalDateTime.class)) {
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        }
        return adapter.fromString(cell.getNumericCellValue() + StringUtils.BLINK_STRING);
    }

    private Sheet getTable(Selector<?> selector) {
        return StringUtils.isNotEmpty(selector.getTableName()) ?
                workbook.getSheet(selector.getTableName()) :
                workbook.getSheetAt(selector.getTableIndex());
    }

    private void setField(Field field, Row row, Object obj) {
        Column column = field.getAnnotation(Column.class);
        Cell cell = row.getCell(column.index());
        if (cell == null) {
            return;
        }
        Object value = getCellValue(cell, field);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            throw new SelectorException(e);
        }
    }
}
