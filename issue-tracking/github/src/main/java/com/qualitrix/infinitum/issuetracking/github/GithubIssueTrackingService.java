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

import com.qualitrix.infinitum.config.ConfigurationService;
import com.qualitrix.infinitum.config.ConfigurationServiceLocator;
import com.qualitrix.infinitum.issuetracking.IssueTracker;
import com.qualitrix.infinitum.issuetracking.IssueTrackingService;
import com.qualitrix.infinitum.util.StringUtil;

import java.util.stream.Stream;

/**
 * <p>
 * Tracks test failures as Github issues. The following configuration
 * parameters must be available in the application configuration.
 * </p>
 *
 * <ol>
 *     <li><b>{@code infinitum.issuetracking.provider}</b> must be set to
 *     <b>{@code github}</b> to indicate that issues must be tracked in
 *     Github;</li>
 *     <li><b>{@code infinitum.issuetracking.github.repository}</b> must be set
 *     to the name of the Github repository to which issues must be logged -
 *     e.g. {@code supplier-portal};</li>
 *     <li><b>{@code infinitum.issuetracking.github.token}</b> must be set to
 *     an OAuth token associated with the Github user.</li>
 * </ol>
 *
 * <p>
 * Github issue tracking is available only if all the settings described above
 * have been configured correctly.
 * </p>
 *
 * @see ConfigurationService
 */
public class GithubIssueTrackingService implements IssueTrackingService {
    private static final String CONFIGURATION_PARAMETER_REPOSITORY = "infinitum.issuetracking.github.repository";

    private static final String CONFIGURATION_PARAMETER_TOKEN = "infinitum.issuetracking.github.token";

    private static final String PROVIDER = "GITHUB";

    private final boolean enabled;

    private final String repository;

    private final String token;

    /**
     * Creates a service that reads Github settings from the application
     * configuration and uses those settings for tracking issues in Github.
     */
    public GithubIssueTrackingService() {
        final ConfigurationService configuration = ConfigurationServiceLocator.getInstance()
                                                                              .getConfigurationService();

        enabled = PROVIDER.equalsIgnoreCase(configuration.getString(CONFIGURATION_PARAMETER_PROVIDER));
        repository = configuration.getString(CONFIGURATION_PARAMETER_REPOSITORY);
        token = configuration.getString(CONFIGURATION_PARAMETER_TOKEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IssueTracker getIssueTracker() {
        return new GithubIssueTracker(repository, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return isEnabled()
            && Stream.of(getRepository(), token)
                     .allMatch(StringUtil::isNotBlank);
    }

    /**
     * Gets the name of the Github repository in which to create issues.
     *
     * @return The name of the Github repository in which to create issues.
     */
    String getRepository() {
        return repository;
    }

    /**
     * Gets whether Github issue tracking is enabled through configuration.
     *
     * @return {@code true} if Github issue tracking is enabled, {@code false}
     * otherwise.
     */
    boolean isEnabled() {
        return enabled;
    }
}
