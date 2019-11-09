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

package com.github.equal.processor.adapter;

import com.github.equal.exception.EqualException;
import com.github.equal.exception.ExceptionUtils;
import com.github.equal.utils.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeAdapter implements Adapter<String, LocalTime> {

    private DateTimeFormatter dateTimeFormatter;

    public TimeAdapter(String timePattern) {
        if (StringUtils.isNotEmpty(timePattern)) {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        } else {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }
    }

    @Override
    public LocalTime fromString(String str) throws EqualException {
        try {
            if (str == null) {
                return null;
            }
            return LocalTime.parse(str, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new EqualException(ExceptionUtils.ADAPT_TIME_ERROR, e.getMessage());
        }
    }

    @Override
    public String toString(LocalTime localTime) throws EqualException {
        if (localTime == null) {
            return null;
        }
        return localTime.format(dateTimeFormatter);
    }
}
