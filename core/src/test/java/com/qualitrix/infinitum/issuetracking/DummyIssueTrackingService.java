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

import com.qualitrix.infinitum.util.StringUtil;

/**
 * A dummy (fake) issue tracking service.
 */
public class DummyIssueTrackingService implements IssueTrackingService {
    /**
     * {@inheritDoc}
     */
    @Override
    public IssueTracker getIssueTracker() {
        return new DummyIssueTracker();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return true;
    }

    /**
     * A dummy (fake) issue tracker that accepts requests for raising an issue
     * but does nothing.
     */
    private static class DummyIssueTracker implements IssueTracker {
        /**
         * {@inheritDoc}
         *
         * @return {@code false} if {@code summary}, {@code description} or
         * {@code severity} is blank, {@code true} otherwise.
         */
        @Override
        public boolean reportIssue(final String summary
            , final String description
            , final String severity) {
            return StringUtil.isNotBlank(description)
                && StringUtil.isNotBlank(severity)
                && StringUtil.isNotBlank(summary);
        }
    }
}
