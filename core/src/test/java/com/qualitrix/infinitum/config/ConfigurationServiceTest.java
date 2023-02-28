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

package com.qualitrix.infinitum.config;

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.BeforeMethod;

/**
 * Unit tests for reading configuration.
 */
abstract class ConfigurationServiceTest implements UnitTest {
    /**
     * Clears system properties to ensure that the tests work predictably.
     */
    @BeforeMethod
    public void setup() {
        System.clearProperty(ConfigurationNameProvider.SYSTEM_PROPERTY_NAME);
    }

    /**
     * Injects a custom configuration name into the system properties and
     * returns the injected name.
     */
    String injectCustomConfigurationName() {
        // Generate a random configuration name.
        final String configurationName = getString();

        // Configure the system properties to use the randomly-generated
        // configuration name.
        System.setProperty(ConfigurationNameProvider.SYSTEM_PROPERTY_NAME, configurationName);

        return configurationName;
    }
}
