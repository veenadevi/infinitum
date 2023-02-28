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
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.Reader;
import java.util.List;

/**
 * <p>
 * Reads structured data from tab-separated values (TSV) format. <b>TSV data
 * must have column headers that match Java property names.</b> For instance,
 * the following is a valid TSV file with {@code year}, {@code month},
 * {@code revenue} and {@code expenses} as column headers and remaining lines
 * containing data:
 * </p>
 *
 * <pre><code>
 * year    month        revenue    expenses
 * "2001"  "January"    1096100    700495
 * "2001"  "February"   1107699    787025
 * "2001"  "March"      1111413    756127
 * "2001"  "April"      1142805    789401
 * "2001"  "May"        1062120    696636
 * "2001"  "June"       1049379    614289
 * "2001"  "July"       1188511    783190
 * "2001"  "August"     1192911    760190
 * "2001"  "September"  1113860    610055
 * "2001"  "October"    1088810    621875
 * "2001"  "November"   1077840    625026
 * "2001"  "December"   1179334    649819
 * </code></pre>
 *
 * <p>
 * The TSV data shown above can be read as objects of the following class.
 * Note how the column headers in the TSV data above match the field names in
 * the Java class.
 * </p>
 *
 * <p>
 * <b>Note:</b> column header names must not be enclosed in double-quotes.
 * </p>
 *
 * <pre><code>
 * import com.univocity.parsers.annotations.Parsed;
 *
 * public class BalanceSheet {
 *     &#64;Parsed
 *     private String year;
 *
 *     &#64;Parsed
 *     private String month;
 *
 *     &#64;Parsed
 *     private BigDecimal revenue;
 *
 *     &#64;Parsed
 *     private BigDecimal expenses;
 * }
 * </code></pre>
 *
 * <p>
 * In case the column headers do not match the Java property names exactly
 * (Java property names are case-sensitive), the Java fields must be decorated
 * to map them to the correct headers. Consider the following TSV data:
 * </p>
 *
 * <pre><code>
 * Name        Age        Height (cm)        Weight (kg)
 * "Adam"      22         68                 61
 * "Bob"       21         65                 58
 * "Charlie"   23         72                 78
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
 *     &#64;Parsed(field = "Name")
 *     private String name;
 *
 *     &#64;Parsed(field = "Age")
 *     private int age;
 *
 *     &#64;Parsed(field = "Height (cm)")
 *     private int height;
 *
 *     &#64;Parsed(field = "Weight (kg)")
 *     private int weight;
 * }
 * </code></pre>
 *
 * <p>
 * It is recommended to prepare TSV data in such a way that text values are
 * enclosed in double-quotes ({@code "}). This will ensure that the text data
 * will get read correctly if data itself contains tab spaces.
 * </p>
 */
class TabDelimitedDataReader extends DelimitedDataReader {
    static final DataFormat FORMAT = DelimitedDataFormat.TSV;

    static final TabDelimitedDataReader INSTANCE = new TabDelimitedDataReader();

    /**
     * Creates a reader for reading data stored as tab-separated values.
     */
    private TabDelimitedDataReader() {
        super(FORMAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    <T> List<T> read(Reader reader, BeanListProcessor<T> processor) {
        final TsvParserSettings parserSettings = new TsvParserSettings();
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setProcessor(processor);

        new TsvParser(parserSettings).parse(reader);

        return processor.getBeans();
    }
}
