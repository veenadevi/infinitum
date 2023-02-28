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

package com.qualitrix.infinitum.data.excel;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import com.qualitrix.infinitum.data.DataFormat;
import com.qualitrix.infinitum.data.DataReader;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Integration tests for {@link ExcelDataReader}.
 */
abstract class ExcelDataReaderTest {
    /**
     * Tests that the format supported by the reader is known.
     */
    @Test
    public void testGetSupportedFormats() {
        final DataReader subject = getDataReader();

        assertNotNull(subject.getSupportedFormats());
        assertFalse(subject.getSupportedFormats().isEmpty());
    }

    /**
     * Tests that data can be read from the first worksheet in a workbook.
     */
    @Test
    public void testReadFromFirstWorksheet() {
        final DataReader subject = getDataReader();

        final DataFormat format = subject.getSupportedFormats().get(0);

        final String fileName = String.format("%s.%s"
            , BalanceSheet.class.getSimpleName()
            , format.toString().toLowerCase());

        final List<BalanceSheet> records = subject.read(fileName, BalanceSheet.class);

        assertNotNull(records);
        assertFalse(records.isEmpty());

        records.forEach(record -> {
            assertNotNull(record);
            assertNotNull(record.getExpenses());
            assertNotNull(record.getMonth());
            assertNotNull(record.getRevenue());
            assertNotNull(record.getYear());
        });
    }

    /**
     * Tests that data can be read from a file by specifying its fully-qualified
     * filesystem path.
     */
    @Test
    public void testReadWithFullyQualifiedPath() {
        final DataReader subject = getDataReader();

        // Get the default format supported by this reader.
        final DataFormat format = subject.getSupportedFormats().get(0);

        // Prepare a filename that can be read with this reader.
        final String fileName = String.format("%s.%s"
            , BalanceSheet.class.getSimpleName()
            , format.toString().toLowerCase());

        // Find the fully-qualified path of the file on the filesystem.
        final URL url = getClass().getClassLoader().getResource(fileName);
        final String path = url.getFile();

        // Read data from the file.
        final List<BalanceSheet> records = subject.read(path
            , BalanceSheet.class);

        assertNotNull(records);
        assertFalse(records.isEmpty());

        records.forEach(record -> {
            assertNotNull(record);
            assertNotNull(record.getExpenses());
            assertNotNull(record.getMonth());
            assertNotNull(record.getRevenue());
            assertNotNull(record.getYear());
        });
    }

    /**
     * Tests that data can be read from a named worksheet in a workbook.
     */
    @Test
    public void testReadFromNamedWorksheet() {
        final DataReader subject = getDataReader();

        final DataFormat format = subject.getSupportedFormats().get(0);

        final String fileName = String.format("%s.%s"
            , Student.class.getSimpleName()
            , format.toString().toLowerCase());

        final List<Student> records = subject.read(fileName, Student.class);

        assertNotNull(records);
        assertFalse(records.isEmpty());

        records.forEach(record -> {
            assertNotNull(record);
            assertNotNull(record.getName());
            assertNotEquals(0, record.getAge());
            assertNotEquals(0, record.getHeight());
            assertNotEquals(0, record.getWeight());
        });
    }

    /**
     * Tests that data cannot be read from a non-existent file.
     */
    @Test
    public void testReadWithNonExistentFile() {
        final DataReader subject = getDataReader();

        final DataFormat format = subject.getSupportedFormats().get(0);

        final String fileName = String.format("%s.%s"
            , getClass().getSimpleName()
            , format.toString().toLowerCase());

        final List<BalanceSheet> records = subject.read(fileName, BalanceSheet.class);

        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    /**
     * Gets a reader for running tests.
     *
     * @return An {@link ExcelDataReader}.
     */
    abstract ExcelDataReader getDataReader();

    /**
     * Represents a balance sheet.
     */
    static class BalanceSheet {
        @ExcelCellName("Year")
        private String year;

        @ExcelCellName("Month")
        private String month;

        @ExcelCellName("Revenue")
        private BigDecimal revenue;

        @ExcelCellName("Expenses")
        private BigDecimal expenses;

        /**
         * Gets total expenses incurred during a calendar month.
         *
         * @return Total expenses incurred during a calendar month.
         */
        public BigDecimal getExpenses() {
            return expenses;
        }

        /**
         * Gets the calendar month for which the balance sheet has been
         * prepared.
         *
         * @return The calendar month for which the balance sheet has been
         * prepared.
         */
        public String getMonth() {
            return month;
        }

        /**
         * Gets total revenue generated during a calendar month.
         *
         * @return Total revenue generated during a calendar month.
         */
        public BigDecimal getRevenue() {
            return revenue;
        }

        /**
         * Gets the Gregorian calendar year for which the balance sheet has been
         * prepared.
         *
         * @return The Gregorian calendar year for which the balance sheet has
         * been prepared.
         */
        public String getYear() {
            return year;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return getClass().getSimpleName()
                + "("
                + "year=" + getYear()
                + ", month=" + getMonth()
                + ", revenue=" + getRevenue()
                + ", expenses=" + getExpenses()
                + ")";
        }
    }

    /**
     * Represents a student.
     */
    @ExcelSheet("Students")
    static class Student {
        @ExcelCellName("Name")
        private String name;

        @ExcelCellName("Age")
        private int age;

        @ExcelCellName("Height")
        private int height;

        @ExcelCellName("Weight")
        private int weight;

        /**
         * Gets the student's age.
         *
         * @return The student's age.
         */
        public int getAge() {
            return age;
        }

        /**
         * Gets the student's height in centimetres.
         *
         * @return The student's height in centimetres.
         */
        public int getHeight() {
            return height;
        }

        /**
         * Gets the student's full name.
         *
         * @return The student's full name.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the student's weight in kilograms.
         *
         * @return The student's weight in kilograms.
         */
        public int getWeight() {
            return weight;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return getClass().getSimpleName()
                + "("
                + "name=" + getName()
                + ", age=" + getAge()
                + ", height=" + getHeight()
                + ", weight=" + getWeight()
                + ")";
        }
    }
}
