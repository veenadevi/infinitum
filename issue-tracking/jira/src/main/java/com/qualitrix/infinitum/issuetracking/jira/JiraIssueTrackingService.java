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

import com.qualitrix.infinitum.config.ConfigurationService;
import com.qualitrix.infinitum.config.ConfigurationServiceLocator;
import com.qualitrix.infinitum.issuetracking.IssueTracker;
import com.qualitrix.infinitum.issuetracking.IssueTrackingService;
import com.qualitrix.infinitum.util.StringUtil;

import java.util.stream.Stream;

/**
 * <p>
 * Tracks test failures as bugs in an Atlassian Jira instance. The following
 * configuration parameters must be available in the application configuration.
 * </p>
 *
 * <ol>
 *     <li><b>{@code infinitum.issuetracking.provider}</b> must be set to
 *     <b>{@code jira}</b> to indicate that issues must be tracked in Jira;</li>
 *     <li><b>{@code infinitum.issuetracking.jira.url}</b> must be set to the
 *     URL of the Jira project to which issues must be logged - e.g.
 *     {@code https://mycompany.atlassian.net};</li>
 *     <li><b>{@code infinitum.issuetracking.jira.project}</b> must be set to
 *     the name of the Jira project to which issues must be logged;</li>
 *     <li><b>{@code infinitum.issuetracking.jira.username}</b> must be set to
 *     the username to use for authenticating with Jira. This user must have
 *     permissions to use the Jira API for querying and creating Jira issues.
 *     Consult the official Jira documentation for details on creating a user
 *     with required permissions to use the Jira API;</li>
 *     <li><b>{@code infinitum.issuetracking.jira.token}</b> must be set to
 *     an API token associated with the Jira user.</li>
 * </ol>
 *
 * <p>
 * Jira issue tracking is available only if all the settings described above
 * have been configured correctly.
 * </p>
 *
 * @see ConfigurationService
 */
public class JiraIssueTrackingService implements IssueTrackingService {
    private static final String CONFIGURATION_PARAMETER_PROJECT = "infinitum.issuetracking.jira.project";

    private static final String CONFIGURATION_PARAMETER_TOKEN = "infinitum.issuetracking.jira.token";

    private static final String CONFIGURATION_PARAMETER_URL = "infinitum.issuetracking.jira.url";

    private static final String CONFIGURATION_PARAMETER_USERNAME = "infinitum.issuetracking.jira.username";

    private static final String PROVIDER = "JIRA";

    private final boolean enabled;

    private final String project;

    private final String token;

    private final String url;

    private final String username;

    /**
     * Creates a service that reads Jira settings from the application
     * configuration and uses those settings for tracking issues in Jira.
     */
    public JiraIssueTrackingService() {
        final ConfigurationService configuration = ConfigurationServiceLocator.getInstance()
                                                                              .getConfigurationService();

        enabled = PROVIDER.equalsIgnoreCase(configuration.getString(CONFIGURATION_PARAMETER_PROVIDER));

        project = configuration.getString(CONFIGURATION_PARAMETER_PROJECT);
        token = configuration.getString(CONFIGURATION_PARAMETER_TOKEN);
        url = configuration.getString(CONFIGURATION_PARAMETER_URL);
        username = configuration.getString(CONFIGURATION_PARAMETER_USERNAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IssueTracker getIssueTracker() {
        return new JiraIssueTracker(url, project, username, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return isEnabled()
            && Stream.of(project, token, url, getUsername())
                     .allMatch(StringUtil::isNotBlank);
    }

    /**
     * Gets the username to use for authenticating with Jira.
     *
     * @return The username to use for authenticating with Jira.
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets whether Jira issue tracking is enabled through configuration.
     *
     * @return {@code true} if Jira issue tracking is enabled, {@code false}
     * otherwise.
     */
    boolean isEnabled() {
        return enabled;
    }
}
