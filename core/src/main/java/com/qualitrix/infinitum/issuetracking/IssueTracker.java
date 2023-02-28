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

package com.qualitrix.infinitum.issuetracking;

/**
 * Contract for tracking test failures in an issue tracking system.
 */
public interface IssueTracker extends AutoCloseable {
    /**
     * Releases any resources held for working with the issue tracking system.
     *
     * @throws Exception if resources cannot be released due to some reason.
     */
    @Override
    default void close() throws Exception {
    }

    /**
     * Reports an issue in the issue tracking system.
     *
     * @param summary The issue summary/title to report.
     * @param description The issue description/overview/details to report.
     * @param severity The severity to use while reporting the issue. This is
     * dependent on the actual issue tracking system used.
     *
     * @return {@code false} if {@code summary}, {@code description} or
     * {@code severity} is blank, if an issue with the same summary already
     * exists in the issue tracking system and is still open (not closed) or
     * if an error is encountered when reporting the issue, {@code true} if
     * the issue is reported successfully.
     */
    boolean reportIssue(String summary, String description, String severity);
}
