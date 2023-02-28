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

import com.qualitrix.infinitum.annotation.TrackIssueOnFailure;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Listens for test failures so that an issue can be raised for failing tests
 * if required.
 */
public class IssueTrackingListener implements ITestListener {
    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(IssueTrackingListener.class);

    private static final IssueTrackingService SERVICE = IssueTrackingServiceLocator.getInstance()
                                                                                   .getIssueTrackingService();

    /**
     * Checks whether an issue needs to be raised when a test fails and raises
     * an issue in an issue tracking system associated with the project.
     *
     * @param result Test result which can be examined to determine if an issue
     * needs to be tracked for the failed test.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);

        if (result.getMethod().getSuccessPercentage() < 0) {
            trackIssue(result);
        }
    }

    /**
     * Checks whether an issue needs to be raised when a test fails and raises
     * an issue in an issue tracking system associated with the project.
     *
     * @param result Test result which can be examined to determine if an issue
     * needs to be tracked for the failed test.
     */
    @Override
    public void onTestFailure(final ITestResult result) {
        ITestListener.super.onTestFailure(result);

        trackIssue(result);
    }

    /**
     * Gets a service for raising issues on test failure.
     *
     * @return An {@link IssueTrackingService}.
     */
    IssueTrackingService getService() {
        return SERVICE;
    }

    /**
     * Tracks an issue on test failure, if issue tracking is enabled for the
     * failed test method.
     *
     * @param result Test result which can be examined to determine if an issue
     * needs to be tracked for the failed test.
     */
    private void trackIssue(final ITestResult result) {
        // Check if automatic issue tracking is enabled.
        if (getService() != null) {
            // Attempt to load metadata for tracking an issue from the
            // failed test.
            final TrackIssueOnFailure metadata = result.getMethod()
                                                       .getConstructorOrMethod()
                                                       .getMethod()
                                                       .getAnnotation(TrackIssueOnFailure.class);

            if (metadata != null) {
                LOGGER.debug(String.format("Attempting to raise an issue for failed test [%s]."
                    , result.getMethod().getMethodName()));

                try (final IssueTracker tracker = getService().getIssueTracker()) {
                    // If issue metadata is found for the failed test, raise
                    // an issue with the available information.
                    tracker.reportIssue(metadata.summary()
                        , metadata.description()
                        , metadata.severity());
                }
                catch (final Exception e) {
                    LOGGER.error(e, String.format("Unable to raise issue for failed test [%s] due to the following error."
                        , result.getMethod().getMethodName()));
                }
            }
        }
    }
}
