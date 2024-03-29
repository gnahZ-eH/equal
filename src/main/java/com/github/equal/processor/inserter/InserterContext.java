/*
 * MIT License
 *
 * Copyright (c) [2019] [He Zhang]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished
 *  to do so, subject to the following conditions:
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

import com.github.equal.enums.ExceptionType;
import com.github.equal.enums.FileType;
import com.github.equal.exception.InserterException;

public class InserterContext {

    private InserterContext() { }

    public static void insertIntoFile(Inserter<?> inserter) throws InserterException {
        FileType fileType = inserter.getFileType();
        switch (fileType) {
            case XLSX:
                new XLSXInserter(inserter).insertIntoFile();
                break;
            case XLS:
                new XLSInserter(inserter).insertIntoFile();
                break;
            case CSV:
                new CSVInserter(inserter).insertIntoFile();
                break;
            default:
                throw new InserterException(ExceptionType.UNSUPPORTED_FILE_TYPE);
        }
    }
}
