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

/**
 * <p>
 * Provides interfaces and classes for reading configuration information
 * for other components and the application.
 * </p>
 *
 * <p>
 * Multiple sources of configuration may be supported. However, for any one
 * test automation project, configuration must be read from a single source
 * only. The exact configuration source used for a particular project depends
 * upon how the project is structured. See documentation for implementations of
 * {@link com.qualitrix.infinitum.config.ConfigurationService} to understand
 * how to use a particular source for your project.
 * </p>
 */
package com.qualitrix.infinitum.config;
