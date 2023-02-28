/*
 * Copyright (c) 2021-2022 Qualitrix Technologies Pvt Ltd.  All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to use the
 * Software without restriction, subject to the following conditions:
 *
 * THE SOFTWARE MUST HAVE BEEN PROVIDED BY THE ORIGINAL AUTHORS OR AN AUTHORIZED
 * SIGNATORY THEREOF. THE PERSON TO WHOM THE SOFTWARE HAS BEEN PROVIDED MAY USE IT
 * FOR THE PURPOSE FOR WHICH IT HAS BEEN PROVIDED, AND EXTEND IT TO MEET THEIR
 * NEEDS. HOWEVER, THE PERSON TO WHOM THE SOFTWARE HAS BEEN PROVIDED MAY NOT SELL,
 * MODIFY, DISTRIBUTE, PUBLISH, MERGE, LICENSE OR SUBLICENSE IT TO ANYONE ELSE.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.qualitrix.infinitum.data.delimited;

import com.qualitrix.infinitum.data.DataFormat;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

import java.io.Reader;
import java.util.List;

/**
 * <p>
 * Reads structured data from fixed-width column format. <b>Data must have
 * column headers that match Java property names.</b> For instance, the
 * following is a valid file in fixed-width column format with {@code year},
 * {@code month}, {@code revenue} and {@code expenses} as column headers and
 * remaining lines containing data:
 * </p>
 *
 * <pre><code>
 * year      month          revenue     expenses
 * 2001      January        1096100     700495
 * 2001      February       1107699     787025
 * 2001      March          1111413     756127
 * 2001      April          1142805     789401
 * 2001      May            1062120     696636
 * 2001      June           1049379     614289
 * 2001      July           1188511     783190
 * 2001      August         1192911     760190
 * 2001      September      1113860     610055
 * 2001      October        1088810     621875
 * 2001      November       1077840     625026
 * 2001      December       1179334     649819
 * </code></pre>
 *
 * <p>
 * The data shown above can be read as objects of the following class. Note how
 * the column headers in the data above match the field names in the Java class.
 * </p>
 *
 * <pre><code>
 * import com.univocity.parsers.annotations.FixedWidth;
 * import com.univocity.parsers.annotations.Parsed;
 *
 * public class BalanceSheet {
 *     &#64;FixedWidth(value = 10)
 *     &#64;Parsed
 *     private String year;
 *
 *     &#64;FixedWidth(value = 15)
 *     &#64;Parsed
 *     private String month;
 *
 *     &#64;FixedWidth(value = 12)
 *     &#64;Parsed
 *     private BigDecimal revenue;
 *
 *     &#64;FixedWidth(value = 8)
 *     &#64;Parsed
 *     private BigDecimal expenses;
 * }
 * </code></pre>
 *
 * <p>
 * In case the column headers do not match the Java property names exactly
 * (Java property names are case-sensitive), the Java fields must be decorated
 * to map them to the correct headers. Consider the following data:
 * </p>
 *
 * <pre><code>
 * Name         Age     Height (cm)     Weight (kg)
 * Adam         22      68              61
 * Bob          21      65              58
 * Charlie      23      72              78
 * </code></pre>
 *
 * <p>
 * Column headers for the data above cannot be mapped directly to Java fields
 * because the header names contain spaces and brackets which are not allowed
 * in field names. The data above can be read as objects by decorating fields
 * with the column names as shown for the Java class below.
 * </p>
 *
 * <pre><code>
 * import com.univocity.parsers.annotations.Parsed;
 *
 * public class Student {
 *     &#64;FixedWidth(value = 13)
 *     &#64;Parsed(field = "Name")
 *     private String name;
 *
 *     &#64;FixedWidth(value = 8)
 *     &#64;Parsed(field = "Age")
 *     private int age;
 *
 *     &#64;FixedWidth(value = 16)
 *     &#64;Parsed(field = "Height (cm)")
 *     private int height;
 *
 *     &#64;FixedWidth(value = 11)
 *     &#64;Parsed(field = "Weight (kg)")
 *     private int weight;
 * }
 * </code></pre>
 *
 * <p>
 * It is recommended to prepare data in such a way that the column headers
 * and all text values are enclosed in double-quotes ({@code "}).
 * </p>
 */
class FixedWidthTextDataReader extends DelimitedDataReader {
    static final DataFormat FORMAT = DelimitedDataFormat.TXT;

    static final FixedWidthTextDataReader INSTANCE = new FixedWidthTextDataReader();

    /**
     * Creates a reader for reading data stored as fixed-width columns.
     */
    private FixedWidthTextDataReader() {
        super(FORMAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    <T> List<T> read(final Reader reader, final BeanListProcessor<T> processor) {
        final FixedWidthParserSettings parserSettings = new FixedWidthParserSettings();
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.setIgnoreLeadingWhitespaces(true);
        parserSettings.setIgnoreTrailingWhitespaces(true);
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setProcessor(processor);
        parserSettings.setRecordEndsOnNewline(true);

        new FixedWidthParser(parserSettings).parse(reader);

        return processor.getBeans();
    }
}
