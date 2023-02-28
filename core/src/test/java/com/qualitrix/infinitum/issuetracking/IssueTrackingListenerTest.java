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

import com.qualitrix.infinitum.UnitTest;
import com.qualitrix.infinitum.annotation.TrackIssueOnFailure;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;

import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

/**
 * Unit tests for {@link IssueTrackingListener}.
 */
@Listeners(IssueTrackingListener.class)
public class IssueTrackingListenerTest implements UnitTest {
    /**
     * Tests that an issue is raised automatically when a test for which issue
     * tracking has been enabled, fails.
     */
    @Test(successPercentage = -1)
    @TrackIssueOnFailure(summary = "As a User I am unable to sign in"
        , description = "A user with a valid username and password and active user account is unable to sign in."
        , severity = "Critical")
    public void testOnTestFailedButWithinSuccessPercentageWithAnnotation() {
        fail();
    }

    /**
     * Tests that an issue is not raised automatically when tracking has not been
     * enabled for the failing test.
     */
    @Test(successPercentage = 0)
    public void testOnTestFailedButWithinSuccessPercentageWithoutAnnotation() {
        fail();
    }

    /**
     * Tests that an issue cannot be raised when an exception is encountered.
     */
    @Test
    @TrackIssueOnFailure(summary = "Issue summary"
        , description = "Issue description"
        , severity = "Issue severity")
    public void testOnTestFailureWithException() {
        final IssueTracker tracker = mock(IssueTracker.class);
        doThrow(new RuntimeException()).when(tracker).reportIssue(any(), any(), any());

        final IssueTrackingService service = mock(IssueTrackingService.class);
        when(service.getIssueTracker()).thenReturn(tracker);

        final IssueTrackingListener subject = mock(IssueTrackingListener.class);
        when(subject.getService()).thenReturn(service);
        doCallRealMethod().when(subject).onTestFailure(any());

        final Method method = new Object() {
        }.getClass().getEnclosingMethod();

        final ConstructorOrMethod constructorOrMethod = new ConstructorOrMethod(method);

        final ITestNGMethod testMethod = mock(ITestNGMethod.class);
        when(testMethod.getConstructorOrMethod()).thenReturn(constructorOrMethod);

        final ITestResult testResult = mock(ITestResult.class);
        when(testResult.getMethod()).thenReturn(testMethod);

        subject.onTestFailure(testResult);
    }

    /**
     * Tests that an issue cannot be raised when no issue tracker is available.
     */
    @Test
    public void testOnTestFailureWithoutIssueTracker() {
        final IssueTrackingListener subject = mock(IssueTrackingListener.class);
        when(subject.getService()).thenReturn(null);
        doCallRealMethod().when(subject).onTestFailure(any());

        subject.onTestFailure(mock(ITestResult.class));
    }

    /**
     * Tests that an issue cannot be raised when issue tracking is not enabled
     * for the failing test method.
     */
    @Test
    public void testOnTestFailureWithoutIssueTracking() {
        final Method method = new Object() {
        }.getClass().getEnclosingMethod();

        final ConstructorOrMethod constructorOrMethod = new ConstructorOrMethod(method);

        final ITestNGMethod testMethod = mock(ITestNGMethod.class);
        when(testMethod.getConstructorOrMethod()).thenReturn(constructorOrMethod);

        final ITestResult testResult = mock(ITestResult.class);
        when(testResult.getMethod()).thenReturn(testMethod);

        new IssueTrackingListener().onTestFailure(testResult);
    }
}
