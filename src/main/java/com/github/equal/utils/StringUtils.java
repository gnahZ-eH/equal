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

package com.github.equal.utils;

public class StringUtils {

    private StringUtils() { }

    public static final String XLS = "XLS";
    public static final String XLSX = "XLSX";
    public static final String CSV = "CSV";

    public static final String BLINK_STRING = "";
    public static final String NEW_LINE = "\n";
    public static final String COMMA = ",";

    public static final String DATE_PATTERN = "yyyy/MM/dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";

    public static final String DEFAULT = "Default";
    public static final String UNTITLED = "Untitled";

    public static final Character BOM_HEAD = '\ufeff';

    public static boolean isNotEmpty(String value) {
        return null != value && !value.isEmpty();
    }

    public static boolean isEmpty(String value) {
        return null == value || value.isEmpty();
    }
}