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

import com.poiji.exception.PoijiExcelType;

/**
 * <p>
 * Reads structured data from a Microsoft Excel 2000 workbook (Excel files
 * ending in {@code .xlsx}). Consider the following spreadsheet in an Excel
 * 2000 workbook:
 * </p>
 *
 * <table>
 *     <caption>Balance Sheet</caption>
 *     <thead>
 *         <tr>
 *             <th>Year</th>
 *             <th>Month</th>
 *             <th>Revenue</th>
 *             <th>Expenses</th>
 *         </tr>
 *     </thead>
 *     <tbody>
 *         <tr>
 *             <td>2001</td>
 *             <td>January</td>
 *             <td align="right">1,096,100</td>
 *             <td align="right">700,495</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>February</td>
 *             <td align="right">1,107,699</td>
 *             <td align="right">787,025</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>March</td>
 *             <td align="right">1,111,413</td>
 *             <td align="right">756,127</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>April</td>
 *             <td align="right">1,142,805</td>
 *             <td align="right">789,401</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>May</td>
 *             <td align="right">1,062,120</td>
 *             <td align="right">696,636</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>June</td>
 *             <td align="right">1,049,379</td>
 *             <td align="right">614,289</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>July</td>
 *             <td align="right">1,188,511</td>
 *             <td align="right">783,190</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>August</td>
 *             <td align="right">1,192,911</td>
 *             <td align="right">760,190</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>September</td>
 *             <td align="right">1,113,860</td>
 *             <td align="right">610,055</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>October</td>
 *             <td align="right">1,088,810</td>
 *             <td align="right">621,875</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>November</td>
 *             <td align="right">1,077,840</td>
 *             <td align="right">625,026</td>
 *         </tr>
 *         <tr>
 *             <td>2001</td>
 *             <td>December</td>
 *             <td align="right">1,179,334</td>
 *             <td align="right">649,819</td>
 *         </tr>
 *     </tbody>
 * </table>
 *
 * <p>
 * The Excel data shown above can be read as objects of the following class.
 * </p>
 *
 * <pre><code>
 * import com.poiji.annotation.ExcelCellName;
 *
 * public class BalanceSheet {
 *     &#64;ExcelCellName("Year")
 *     private String year;
 *
 *     &#64;ExcelCellName("Month")
 *     private String month;
 *
 *     &#64;ExcelCellName("Revenue")
 *     private BigDecimal revenue;
 *
 *     &#64;ExcelCellName("Expenses")
 *     private BigDecimal expenses;
 * }
 * </code></pre>
 *
 * <p>
 * By default, data are read from the first worksheet in the Excel workbook.
 * If data need to be read from a different worksheet in the workbook, the
 * target Java class can be simply decorated as shown below:
 * </p>
 *
 * <pre><code>
 * import com.poiji.annotation.ExcelSheet;
 *
 * &#64;ExcelSheet("Sheet9")
 * public class BalanceSheet {
 * }
 * </code></pre>
 */
public class XLSXDataReader extends ExcelDataReader {
    static final XLSXDataReader INSTANCE = new XLSXDataReader();

    /**
     * Creates a reader for reading data from a Microsoft Excel 97 workbook.
     */
    private XLSXDataReader() {
        super(PoijiExcelType.XLSX);
    }
}
