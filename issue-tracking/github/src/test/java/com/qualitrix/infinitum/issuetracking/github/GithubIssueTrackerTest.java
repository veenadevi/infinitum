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
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueBuilder;
import org.kohsuke.github.GHIssueSearchBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedSearchIterable;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link GithubIssueTracker}.
 */
public class GithubIssueTrackerTest implements UnitTest {
    private GithubIssueTracker subject;

    /**
     * Cleans up mock dependency created for running test.
     *
     * @throws Exception if an error occurs during cleanup.
     */
    @AfterMethod
    public void cleanup() throws Exception {
        subject.close();
    }

    /**
     * Creates a mock dependency for running test.
     */
    @BeforeMethod
    public void setup() {
        subject = spy(new GithubIssueTracker("Qualitrix/Infinitum", "invalid"));
        when(subject.reportIssue(any(), any(), any())).thenCallRealMethod();
    }

    /**
     * Tests that an issue cannot be created if body has not been
     * specified.
     */
    @Test
    public void testReportIssueWithBlankBody() {
        assertFalse(subject.reportIssue(getString(), "", "1"));
    }

    /**
     * Tests that an issue cannot be created if severity has not been
     * specified.
     */
    @Test
    public void testReportIssueWithBlankSeverity() {
        assertFalse(subject.reportIssue(getString(), getString(), ""));
    }

    /**
     * Tests that an issue cannot be created if title has not been specified.
     */
    @Test
    public void testReportIssueWithBlankTitle() {
        assertFalse(subject.reportIssue("", getString(), "1"));
    }

    /**
     * Tests that when an issue with a given title already exists in Github and
     * is in open state, a new issue with the same title is not created again.
     */
    @Test
    public void testReportIssueWithDuplicateTitle() throws Exception {
        final PagedSearchIterable<GHIssue> searchResult = mock(PagedSearchIterable.class);
        when(searchResult.getTotalCount()).thenReturn(1);

        final GHIssueSearchBuilder search = mock(GHIssueSearchBuilder.class);
        when(search.isOpen()).thenReturn(search);
        when(search.q(any())).thenReturn(search);
        when(search.list()).thenReturn(searchResult);

        final GitHub client = mock(GitHub.class);
        when(client.searchIssues()).thenReturn(search);

        when(subject.getClient()).thenReturn(client);

        assertFalse(subject.reportIssue(getString(), getString(), "1"));
    }

    /**
     * Tests that when an error is encountered while creating an issue, the
     * same is communicated to the caller.
     */
    @Test
    public void testReportIssueWithError() throws Exception {
        doThrow(new RuntimeException()).when(subject).createIssue(any(), any(), any());

        assertFalse(subject.reportIssue(getString(), getString(), "1"));
    }

    /**
     * Tests that when an issue with a given title already does not exist in
     * Github, a new issue with the given title is created automatically.
     */
    @Test
    public void testReportIssueWithUniqueTitle() throws Exception {
        final PagedSearchIterable<GHIssue> searchResult = mock(PagedSearchIterable.class);
        when(searchResult.getTotalCount()).thenReturn(0);

        final GHIssueSearchBuilder search = mock(GHIssueSearchBuilder.class);
        when(search.isOpen()).thenReturn(search);
        when(search.q(any())).thenReturn(search);
        when(search.list()).thenReturn(searchResult);

        final GHIssue issue = mock(GHIssue.class);
        when(issue.getTitle()).thenReturn(getString());

        final GHIssueBuilder issueBuilder = mock(GHIssueBuilder.class);
        when(issueBuilder.body(any())).thenReturn(issueBuilder);
        when(issueBuilder.label(any())).thenReturn(issueBuilder);
        when(issueBuilder.create()).thenReturn(issue);

        final GHRepository repository = mock(GHRepository.class);
        when(repository.createIssue(issue.getTitle())).thenReturn(issueBuilder);

        final GitHub client = mock(GitHub.class);
        when(client.getRepository(any())).thenReturn(repository);
        when(client.searchIssues()).thenReturn(search);

        when(subject.getClient()).thenReturn(client);

        assertTrue(subject.reportIssue(issue.getTitle(), getString(), "1"));
    }
}
