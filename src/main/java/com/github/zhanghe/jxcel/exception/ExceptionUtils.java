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

package com.github.zhanghe.jxcel.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionUtils {

    public static final int SOURCE_FILE_IS_NULL = 100;
    public static final int ROW_START_INDEX_IS_LESS_THAN_2 = 101;
    public static final int ROW_END_INDEX_IS_LESS_THAN_0 = 102;
    public static final int ROW_END_INDEX_IS_LESS_THAN_ROW_START_INDEX = 103;
    public static final int TABLE_INDEX_IS_LESS_THAN_0 = 104;
    public static final int TABLE_NAME_IS_NULL = 105;
    public static final int CLAZZ_IS_NULL = 106;


    public static final Map<Integer, String> exceptionMap = new HashMap<Integer, String>();

    static {
        exceptionMap.put(SOURCE_FILE_IS_NULL, "Source file is null");
        exceptionMap.put(ROW_START_INDEX_IS_LESS_THAN_2, "Start row index is less than 2");
        exceptionMap.put(ROW_END_INDEX_IS_LESS_THAN_0, "Row end index is less than 0");
        exceptionMap.put(ROW_END_INDEX_IS_LESS_THAN_ROW_START_INDEX, "Row end index is less than row start index");
        exceptionMap.put(TABLE_INDEX_IS_LESS_THAN_0, "Table index is less than 0");
        exceptionMap.put(TABLE_NAME_IS_NULL, "Table name is null");
        exceptionMap.put(CLAZZ_IS_NULL, "Clazz is null");
    }
}
