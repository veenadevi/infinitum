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

package com.qualitrix.infinitum.device.driver;

import com.qualitrix.infinitum.UnitTest;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Unit tests for a {@link DeviceDriverService}.
 */
abstract class DeviceDriverServiceTest implements UnitTest {
    static final ImmutableCapabilities CAPABILITIES = new ImmutableCapabilities();

    private static final Function<Command, Response> ECHO = command -> {
        final Response response = new Response();
        response.setValue(((Capabilities) command.getParameters()
                                                 .get("desiredCapabilities"))
                              .asMap()
                              .entrySet()
                              .stream()
                              .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())));
        response.setSessionId(UUID.randomUUID().toString());

        return response;
    };

    private static final Function<Command, Response> NULL_VALUE_RESPONDER = cmd -> {
        final Response response = new Response();
        response.setValue(null);
        response.setSessionId(cmd.getSessionId() != null ? cmd.getSessionId().toString() : null);

        return response;
    };

    /**
     * Gets an executor that mocks the execution of commands on a Selenium
     * server.
     *
     * @return A command executor.
     */
    HttpCommandExecutor getMockCommandExecutor() {
        final HttpCommandExecutor executor = mock(HttpCommandExecutor.class);

        try {
            when(executor.execute(any()))
                .thenAnswer(invocation -> ECHO.apply(invocation.getArgument(0)))
                .thenAnswer(invocation -> NULL_VALUE_RESPONDER.apply(invocation.getArgument(0)));

            return executor;
        }
        catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
