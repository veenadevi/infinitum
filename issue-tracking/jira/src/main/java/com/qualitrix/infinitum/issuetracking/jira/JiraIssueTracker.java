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
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.qualitrix.infinitum.issuetracking.IssueTracker;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import com.qualitrix.infinitum.util.StringUtil;

import java.net.URI;
import java.util.stream.StreamSupport;

/**
 * Tracks test failures in Atlassian Jira as bugs.
 */
class JiraIssueTracker implements IssueTracker {
    static final String ISSUE_TYPE_BUG = "BUG";

    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(JiraIssueTracker.class);

    private static final String OPEN_ISSUE_QUERY_FORMAT = "text ~ \"%s\"";

    private final JiraRestClient jiraClient;

    private final String project;

    /**
     * Creates a tracker for reporting Jira issues.
     *
     * @param url URL to the Jira instance to which issues must be reported,
     * e.g. {@code https://mycompany.atlassian.net}.
     * @param project The name of the Jira project under which issues should be
     * reported.
     * @param username A username having access to the project and permissions
     * to create new issues within the project.
     * @param token API token associated with the username.
     */
    JiraIssueTracker(final String url, final String project, final String username, final String token) {
        jiraClient = new AsynchronousJiraRestClientFactory()
            .createWithBasicHttpAuthentication(URI.create(url), username, token);

        this.project = project;
    }

    /**
     * Closes the connection with Jira.
     *
     * @throws Exception if the connection cannot be closed due to some
     * reason.
     */
    @Override
    public void close() throws Exception {
        getRestClient().close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean reportIssue(final String summary, final String description, final String severity) {
        // Ensure that issue summary has been specified.
        if (StringUtil.isBlank(summary)) {
            return false;
        }

        // Ensure that issue description has been specified.
        if (StringUtil.isBlank(description)) {
            return false;
        }

        // Ensure that issue severity has been specified.
        if (StringUtil.isBlank(severity)) {
            return false;
        }

        // Ensure that no open issue exists with the specified summary.
        if (isAnyIssueOpen(summary)) {
            return false;
        }

        // Create a new issue with the given summary, description and severity.
        try {
            createIssue(summary, description, severity);

            return true;
        }
        catch (final Exception e) {
            LOGGER.error(e
                , String.format("Unable to create Jira issue with summary %s due to the following error."
                    , summary));

            return false;
        }
    }

    /**
     * Creates a new Jira issue with given summary, description and severity.
     *
     * @param summary The issue summary.
     * @param description The issue description.
     * @param severity The issue severity.
     */
    void createIssue(final String summary, final String description, final String severity) {
        // Build an issue with the specified data and create it using the
        // Jira Issues Rest API.
        final BasicIssue issue = getIssueClient().createIssue(buildIssue(summary
                                                     , description
                                                     , severity))
                                                 .claim();

        LOGGER.debug(String.format("Jira issue %s created with summary %s."
            , issue.getKey()
            , summary));
    }

    /**
     * Gets a client for invoking the Jira Rest APIs.
     *
     * @return A {@link JiraRestClient}.
     */
    JiraRestClient getRestClient() {
        return jiraClient;
    }

    /**
     * Creates a new Jira issue of type Bug, with given summary, description
     * and severity.
     *
     * @param summary The issue summary.
     * @param description The issue description.
     * @param severity The issue severity.
     *
     * @return An {@link IssueInput}.
     */
    private IssueInput buildIssue(final String summary, final String description, final String severity) {
        return new IssueInputBuilder().setProjectKey(project)
                                      .setIssueType(getBugIssueType())
                                      .setSummary(summary)
                                      .setDescription(description)
                                      .setPriorityId(Long.parseLong(severity))
                                      .build();
    }

    /**
     * Gets the Jira issue type that represents a bug.
     *
     * @return A Jira {@link IssueType}.
     */
    private IssueType getBugIssueType() {
        return StreamSupport.stream(getMetadataClient().getIssueTypes()
                                                       .claim()
                                                       .spliterator(), false)
                            .filter(type -> ISSUE_TYPE_BUG.equalsIgnoreCase(type.getName()))
                            .findFirst()
                            .orElse(null);
    }

    /**
     * Gets a client for invoking the Jira Issues Rest API for creating an
     * issue.
     *
     * @return An {@link IssueRestClient}.
     */
    private IssueRestClient getIssueClient() {
        return getRestClient().getIssueClient();
    }

    /**
     * Gets a client for invoking the Jira Metadata Rest API for loading
     * global Jira metadata.
     *
     * @return A {@link MetadataRestClient}.
     */
    private MetadataRestClient getMetadataClient() {
        return getRestClient().getMetadataClient();
    }

    /**
     * Gets a client for invoking the Jira Search Rest API to search issues
     * matching specific criteria.
     *
     * @return A {@link SearchRestClient}.
     */
    private SearchRestClient getSearchClient() {
        return getRestClient().getSearchClient();
    }

    /**
     * Gets a Jira Query Language (JQL) query for finding open issues that
     * match a given summary.
     *
     * @param summary The issue summary to find.
     *
     * @return A JQL query with the given summary.
     */
    private String getOpenIssueSearchQuery(final String summary) {
        return String.format(OPEN_ISSUE_QUERY_FORMAT, summary);
    }

    /**
     * Gets whether there is an existing open issue with the specified summary.
     *
     * @param summary The issue summary to check.
     *
     * @return {@code true} if there is one or more existing open issue with
     * the specified summary, {@code false} if there is no issue with the
     * given summary, or if there is one or more issue with the summary but
     * all of them are closed.
     */
    private boolean isAnyIssueOpen(final String summary) {
        // Search for open issues having the specified summary.
        final SearchResult searchResult = searchOpenIssuesWithSummary(summary);

        if (searchResult.getTotal() == 0) {
            return false;
        }

        for (final Issue issue : searchResult.getIssues()) {
            LOGGER.debug(String.format("Jira issue %s with summary %s already exists."
                , issue.getKey()
                , summary));
        }

        return true;
    }

    /**
     * Searches for open issues having a specified summary.
     *
     * @param summary The issue summary to search.
     *
     * @return A {@link SearchResult} containing open issues with the specified
     * summary if any exist.
     */
    private SearchResult searchOpenIssuesWithSummary(final String summary) {
        return getSearchClient().searchJql(getOpenIssueSearchQuery(summary))
                                .claim();
    }
}
