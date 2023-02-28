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

package com.qualitrix.infinitum.inject;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.qualitrix.infinitum.config.ConfigurationService;
import com.qualitrix.infinitum.config.ConfigurationServiceLocator;
import com.qualitrix.infinitum.issuetracking.IssueTrackingService;
import com.qualitrix.infinitum.issuetracking.IssueTrackingServiceLocator;
import com.qualitrix.infinitum.logging.LoggingService;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import com.qualitrix.infinitum.notification.NotificationService;
import com.qualitrix.infinitum.notification.NotificationServiceLocator;
import com.qualitrix.infinitum.notification.Notifier;
import com.qualitrix.infinitum.reporting.ReportingService;
import com.qualitrix.infinitum.reporting.ReportingServiceLocator;

import java.util.Optional;

/**
 * Allows injecting core services into test classes.
 */
public class InjectorModule implements Module {
    /**
     * Registers implementations of core services.
     *
     * @param binder A {@link Binder}.
     */
    @Override
    public void configure(final Binder binder) {
        binder.bind(ConfigurationService.class)
              .toInstance(ConfigurationServiceLocator.getInstance().getConfigurationService());

        Optional.ofNullable(IssueTrackingServiceLocator.getInstance().getIssueTrackingService())
                .ifPresent(issueTrackingService -> binder.bind(IssueTrackingService.class)
                                                         .toInstance(issueTrackingService));

        binder.bind(LoggingService.class)
              .toInstance(LoggingServiceLocator.getInstance().getLoggingService());

        binder.bind(NotificationService.class)
              .toInstance(NotificationServiceLocator.getInstance().getNotificationService());

        binder.bind(Notifier.class)
              .toInstance(NotificationServiceLocator.getInstance()
                                                    .getNotificationService()
                                                    .getNotifier());

        binder.bind(ReportingService.class)
              .toInstance(ReportingServiceLocator.getInstance().getReportingService());
    }
}
