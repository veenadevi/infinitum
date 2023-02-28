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

package com.qualitrix.infinitum.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

/**
 * Provides methods for working with classes.
 */
public final class ClassUtil {
    /**
     * Deliberately hidden to prevent direct instantiation.
     */
    private ClassUtil() {
    }

    /**
     * Creates a dummy JDK proxy for a specified type.
     *
     * @param type The type for which proxy is required.
     * @param <T> The type for which proxy is required.
     *
     * @return A proxy instance of the specified type.
     */
    public static <T> T createDummyProxy(final Class<T> type) {
        try {
            return Optional.of(type)
                           .map(target -> createProxy(type, DummyInvocationHandler.INSTANCE))
                           .orElse(null);
        }
        catch (final Throwable t) {
            return null;
        }
    }

    /**
     * Creates a JDK proxy for a specified type.
     *
     * @param type The type for which proxy is required.
     * @param invocationHandler An invocation handler for handling methods
     * invoked on the created proxy.
     * @param <T> The type for which proxy is required.
     *
     * @return A proxy instance of the specified type.
     */
    static <T> T createProxy(final Class<T> type
        , final InvocationHandler invocationHandler) {
        return type.cast(Proxy.newProxyInstance(ClassUtil.class.getClassLoader()
            , new Class[] { type }
            , invocationHandler));
    }

    /**
     * Dummy implementation of {@link InvocationHandler}.
     */
    private static final class DummyInvocationHandler
        implements InvocationHandler {
        private static final DummyInvocationHandler INSTANCE = new DummyInvocationHandler();

        /**
         * Does nothing.
         * <p>
         * {@inheritDoc}
         */
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            switch (method.getName()) {
                case "equals":
                    return false;

                case "hashCode":
                    return 0;

                default:
                    return null;
            }
        }
    }
}
