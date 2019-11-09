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

package com.github.equal.processor.inserter;

import com.github.equal.exception.EqualException;
import com.github.equal.enums.FileType;
import com.github.equal.exception.ExceptionUtils;
import com.github.equal.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class Inserter {

    private Collection<?> data;
    private FileType fileType;
    private int rowStartIndex = 1;
    private File sourceFile;
    private int tableIndex = 0;
    private String tableName = "Default";

    private Charset charset = StandardCharsets.UTF_8;

    public Inserter(FileType fileType) {
        this.fileType = fileType;
    }

    public static Inserter insert() {
        return new Inserter(FileType.XLSX);
    }

    public static Inserter insert(FileType fileType) {
        return new Inserter(fileType);
    }

    public Inserter values(Collection<?> data) {
        this.data = data;
        return this;
    }

    public void fromRow(int rowStartIndex) {
        if (rowStartIndex < 1) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_START_INDEX_IS_LESS_THAN_1));
        }
        this.rowStartIndex = rowStartIndex;
    }

    public Inserter into(File sourceFile) {
        assertNullSourceFile(sourceFile);
        this.sourceFile = sourceFile;
        return this;
    }

    public Inserter into(File sourceFile, int tableIndex) {
        assertNullSourceFile(sourceFile);
        assertTableIndexLessThanZero(tableIndex);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        return this;
    }

    public Inserter into(File sourceFile, String tableName) {
        assertNullSourceFile(sourceFile);
        assertNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableName = tableName;
        return this;
    }

    public Inserter into(File sourceFile, int tableIndex, String tableName) {
        assertNullSourceFile(sourceFile);
        assertTableIndexLessThanZero(tableIndex);
        assertNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        this.tableName = tableName;
        return this;
    }

    private void assertNullSourceFile(File sourceFile) {
        if (sourceFile == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
    }

    private void assertTableIndexLessThanZero(int tableIndex) {
        if (tableIndex < 0) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_INDEX_IS_LESS_THAN_0));
        }
    }

    private void assertNullTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_NAME_IS_NULL));
        }
    }

    public void flush() throws EqualException {
        if (data == null || data.isEmpty()) {
            throw new EqualException(ExceptionUtils.INSERT_DATA_IS_NULL);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.sourceFile);
        } catch (FileNotFoundException e) {
            throw new EqualException(e);
        }
        new FileInserter(fileOutputStream).insertTable(this);
    }
}