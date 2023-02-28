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

package com.qualitrix.infinitum.issuetracking.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.qualitrix.infinitum.UnitTest;
import io.atlassian.util.concurrent.Promise;
import io.atlassian.util.concurrent.Promises;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link JiraIssueTracker}.
 */
public class JiraIssueTrackerTest implements UnitTest {
    private IssueRestClient issueClient;

    private SearchRestClient searchClient;

    private JiraIssueTracker subject;

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
        issueClient = mock(IssueRestClient.class);

        final MetadataRestClient metadataClient = mock(MetadataRestClient.class);
        final IssueType bugIssueType = mock(IssueType.class);
        when(bugIssueType.getName()).thenReturn(JiraIssueTracker.ISSUE_TYPE_BUG);

        final Promise<Iterable<IssueType>> promise = Promises.promise(Collections.singleton(bugIssueType));

        when(metadataClient.getIssueTypes()).thenReturn(promise);

        searchClient = mock(SearchRestClient.class);

        final JiraRestClient jiraClient = mock(JiraRestClient.class);
        when(jiraClient.getIssueClient()).thenReturn(issueClient);
        when(jiraClient.getMetadataClient()).thenReturn(metadataClient);
        when(jiraClient.getSearchClient()).thenReturn(searchClient);

        subject = spy(new JiraIssueTracker("https://qualitrix.atlassian.net"
            , "Infinitum"
            , "qualitrix"
            , "invalid"));
        when(subject.getRestClient()).thenReturn(jiraClient);
        when(subject.reportIssue(any(), any(), any())).thenCallRealMethod();
    }

    /**
     * Tests that an issue cannot be created if summary has not been
     * specified.
     */
    @Test
    public void testReportIssueWithBlankSummary() {
        assertFalse(subject.reportIssue("", getString(), "1"));
    }

    /**
     * Tests that an issue cannot be created if description has not been
     * specified.
     */
    @Test
    public void testReportIssueWithBlankDescription() {
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
     * Tests that when an issue with a given summary already exists in Jira and
     * is in open state, a new issue with the same summary is not created
     * again.
     */
    @Test
    public void testReportIssueWithDuplicateSummary() {
        final Issue issue = mock(Issue.class);
        when(issue.getKey()).thenReturn(getString());

        final SearchResult searchResult = mock(SearchResult.class);
        when(searchResult.getTotal()).thenReturn(1);
        when(searchResult.getIssues()).thenReturn(Collections.singleton(issue));

        final Promise<SearchResult> promise = Promises.promise(searchResult);

        when(searchClient.searchJql(any())).thenReturn(promise);

        assertFalse(subject.reportIssue(getString(), getString(), "1"));
    }

    /**
     * Tests that when an error is encountered while creating an issue, the
     * same is communicated to the caller.
     */
    @Test
    public void testReportIssueWithError() {
        final SearchResult searchResult = mock(SearchResult.class);
        when(searchResult.getTotal()).thenReturn(0);

        final Promise<SearchResult> promise = Promises.promise(searchResult);

        when(searchClient.searchJql(any())).thenReturn(promise);

        final BasicIssue issue = mock(BasicIssue.class);
        when(issue.getKey()).thenReturn(getString());

        doThrow(new RuntimeException()).when(subject).createIssue(any(), any(), any());

        assertFalse(subject.reportIssue(getString(), getString(), "1"));
    }

    /**
     * Tests that when an issue with a given summary already does not exist in
     * Jira, a new issue with the given summary is created automatically.
     */
    @Test
    public void testReportIssueWithUniqueSummary() {
        final SearchResult searchResult = mock(SearchResult.class);
        when(searchResult.getTotal()).thenReturn(0);

        final Promise<SearchResult> promise = Promises.promise(searchResult);

        when(searchClient.searchJql(any())).thenReturn(promise);

        final BasicIssue issue = mock(BasicIssue.class);
        when(issue.getKey()).thenReturn(getString());

        final Promise<BasicIssue> response = Promises.promise(issue);

        when(issueClient.createIssue(any())).thenReturn(response);

        doCallRealMethod().when(subject).createIssue(any(), any(), any());

        assertTrue(subject.reportIssue(getString(), getString(), "1"));
    }
}
