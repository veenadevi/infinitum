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

import com.qualitrix.infinitum.issuetracking.IssueTracker;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import com.qualitrix.infinitum.util.StringUtil;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedSearchIterable;

/**
 * Tracks test failures as Github issues.
 */
class GithubIssueTracker implements IssueTracker {
    static final String ISSUE_TYPE = "bug";

    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(GithubIssueTracker.class);

    private static final String OPEN_ISSUE_QUERY_FORMAT = "type:%s %s";

    private final String repository;

    private final String token;

    /**
     * Creates a tracker for reporting Github issues.
     *
     * @param repository The name of the Github repository to which issues must
     * be logged.
     * @param token OAuth token associated with the username.
     */
    GithubIssueTracker(final String repository, final String token) {
        this.repository = repository;
        this.token = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean reportIssue(final String title, final String body, final String severity) {
        // Ensure that issue title has been specified.
        if (StringUtil.isBlank(title)) {
            return false;
        }

        // Ensure that issue body has been specified.
        if (StringUtil.isBlank(body)) {
            return false;
        }

        // Ensure that issue severity has been specified.
        if (StringUtil.isBlank(severity)) {
            return false;
        }

        try {
            // Ensure that no open issue exists with the specified title.
            if (isAnyIssueOpen(title)) {
                return false;
            }

            // Create a new issue with the given title, body and severity.
            createIssue(title, body, severity);

            return true;
        }
        catch (final Exception e) {
            e.printStackTrace();
            LOGGER.error(e
                , String.format("Unable to create Github issue with title [%s] due to the following error."
                    , title));

            return false;
        }
    }

    /**
     * Creates a new Github issue with given title, body and severity.
     *
     * @param title The issue title.
     * @param body The issue body/description.
     * @param severity The issue severity.
     *
     * @throws Exception if an error occurs while attempting to create an issue
     * in the Github issue tracker.
     */
    void createIssue(final String title, final String body, final String severity) throws Exception {
        final GHIssue issue = getClient().getRepository(repository)
                                         .createIssue(title)
                                         .body(body)
                                         .label(ISSUE_TYPE)
                                         .label(severity)
                                         .create();

        LOGGER.debug(String.format("Github issue [%d] created with title [%s]."
            , issue.getNumber()
            , title));
    }

    /**
     * Gets a Github API client.
     *
     * @return A {@link GitHub}.
     *
     * @throws Exception if the client cannot be obtained.
     */
    GitHub getClient() throws Exception {
        return new GitHubBuilder().withOAuthToken(token)
                                  .build();
    }

    /**
     * Gets whether there is an existing open issue with the specified title
     * in the issue tracking system.
     *
     * @param title The issue title to check.
     *
     * @return {@code true} if there is one or more existing open issue with
     * the specified title, {@code false} if there is no issue with the given
     * title, or if there is one or more issue with the title but all of them
     * are closed.
     *
     * @throws Exception if an error occurs when checking with the issue
     * tracking system.
     */
    private boolean isAnyIssueOpen(final String title) throws Exception {
        // Search for open issues having the specified title.
        return searchOpenIssuesWithTitle(title).getTotalCount() != 0;
    }

    /**
     * Searches for open issues having a specified title.
     *
     * @param title The issue title to search.
     *
     * @return A page of open issues with the specified title if any exist.
     *
     * @throws Exception if an error occurs while attempting to query Github.
     */
    private PagedSearchIterable<GHIssue> searchOpenIssuesWithTitle(final String title) throws Exception {
        return getClient().searchIssues()
                          .isOpen()
                          .q(String.format(OPEN_ISSUE_QUERY_FORMAT, ISSUE_TYPE, title))
                          .list();
    }
}
