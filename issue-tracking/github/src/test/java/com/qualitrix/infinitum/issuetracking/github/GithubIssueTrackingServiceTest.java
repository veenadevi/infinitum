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

package com.qualitrix.infinitum.issuetracking.github;

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link GithubIssueTrackingService}.
 */
public class GithubIssueTrackingServiceTest implements UnitTest {
    /**
     * Tests that an issue tracker is available.
     */
    @Test
    public void testGetIssueTracker() {
        assertNotNull(new GithubIssueTrackingService().getIssueTracker());
    }

    /**
     * Tests that Github issue tracking is available.
     */
    @Test
    public void testIsAvailable() {
        assertTrue(new GithubIssueTrackingService().isAvailable());
    }

    /**
     * Tests that Github issue tracking is considered unavailable if not enabled
     * through configuration.
     */
    @Test
    public void testIsAvailableWithDisabled() {
        final GithubIssueTrackingService subject = mock(GithubIssueTrackingService.class);
        when(subject.isEnabled()).thenReturn(false);
        when(subject.isAvailable()).thenCallRealMethod();

        assertFalse(subject.isAvailable());
    }

    /**
     * Tests that Github issue tracking is considered unavailable if repository
     * name is not available.
     */
    @Test
    public void testIsAvailableWithoutRepository() {
        final GithubIssueTrackingService subject = mock(GithubIssueTrackingService.class);
        when(subject.isEnabled()).thenReturn(true);
        when(subject.isAvailable()).thenCallRealMethod();

        assertFalse(subject.isAvailable());
    }
}
