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

package com.qualitrix.infinitum;

import com.qualitrix.infinitum.util.ClasspathUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

/**
 * <p>
 * Contract for a standalone component that offers a set of operations usable
 * across different kinds of applications in different business domains.
 * </p>
 *
 * <p>
 * The set of operations on a service forms a feature. Each service can have
 * multiple implementations, allowing each feature to be offered in different
 * ways depending on the needs of specific applications.
 * </p>
 */
public interface Service {
    /**
     * <p>
     * Attempts to cast this service to a particular type. Can be used to cast
     * to a custom type that derives from {@link Service} for type-safe access
     * to custom services.
     * </p>
     *
     * <p>
     * As an example, consider that we have the following interfaces and
     * classes:
     * </p>
     *
     * <pre>{@code
     *     interface FooService extends Service {
     *         void foo();
     *     }
     *
     *     class SimpleFooService implements FooService {
     *         public void foo() {}
     *     }
     *
     *     interface BarService extends FooService {
     *         void bar();
     *     }
     *
     *     class SimpleBarService extends SimpleFooService implements BarService {
     *         public void bar() {}
     *     }
     * }</pre>
     *
     * <p>
     * Let us say an instance of {@code BarService} has been created as
     * {@code Service service = new SimpleBarService()}. The actual type of the
     * {@code service} variable is {@code SimpleBarService}, but its declared
     * type is {@code Service}. For this reason, the {@code foo()} and
     * {@code bar()} methods cannot be called on the {@code service} variable
     * directly - in order to call those methods, we need to cast the
     * {@code service} variable to {@code BarService}. We could do something
     * like {@code BarService barService = (BarService) service}, but this may
     * throw {@link ClassCastException} if the types are incompatible.
     * </p>
     *
     * <p>
     * This is where {@code cast(Class)} is useful - we can perform the
     * desired cast as
     * {@code BarService barService = service.cast(BarService.class)} without
     * worrying about the possible exception. If the cast is valid, this will
     * return a {@code BarService} - if not, it will return {@code null}. This
     * allows the code to be written as follows:
     * </p>
     *
     * <pre>{@code
     *     public void testSomething() {
     *         final Service service = ServiceLocator.getService();
     *
     *         Optional.ofNullable(service)
     *                 .map(source -> source.cast(BarService.class))
     *                 .ifPresent(target -> target.bar());
     *     }
     * }</pre>
     *
     * @param type The type to cast to.
     * @param <T> The type to cast to.
     *
     * @return This instance type-casted into the specified type if this
     * instance is compatible with the specified type, {@code null} otherwise.
     * For example, {@code Service.as(String.class)} will return
     * {@code null} because {@link Service} is not compatible with the type
     * {@link String}.
     */
    default <T extends Service> T cast(final Class<T> type) {
        return Optional.ofNullable(type)
                       .filter(target -> target.isAssignableFrom(getClass()))
                       .map(target -> target.cast(this))
                       .orElse(null);
    }

    /**
     * Gets a logical priority in which this service should be picked up. The
     * logical priority allows implementations of the same service to be
     * ordered sequentially, such that clients of the service can make an
     * informed decision about the most suitable implementation to use.
     *
     * @return A logical priority for this service.
     */
    default int getPriority() {
        return Integer.MAX_VALUE;
    }

    /**
     * <p>
     * Gets an input stream for reading content of a file whose path has been
     * provided. If the provided path is a fully-qualified filesystem path,
     * a {@link FileInputStream} is returned. Otherwise, the file is searched
     * on the runtime classpath and a resource-specific {@link InputStream} is
     * returned.
     * </p>
     *
     * <p>
     * It is the caller's responsibility to close the returned input stream
     * once data have been read from the stream in order to ensure that system
     * resources are freed correctly.
     * </p>
     *
     * @param path Path to the resource to read, e.g. {@code config.yml},
     * {@code reporting/integration/external/config.xml}, etc.
     *
     * @return An {@link InputStream} for reading the resource if it is found
     * on the runtime application classpath, {@code null} otherwise.
     *
     * @throws FileNotFoundException if the specified file name is a
     * fully-qualified filesystem path but no file exists at the specified
     * location.
     */
    default InputStream getResourceStream(final String path) throws FileNotFoundException {
        return ClasspathUtil.getResourceStream(path);
    }

    /**
     * <p>
     * Gets whether this service is currently available, meaning its operations
     * can be invoked by a client. A service may be unavailable because it may
     * have a dependency that is not met at runtime. For example, a service may
     * rely on communicating with an external application and may be unavailable
     * because a network connection is not available.
     * </p>
     *
     * <p>
     * Service availability is critical for determining whether a service
     * implementation can be used by a client, because each service can have
     * multiple implementations, each of which may get discovered at runtime.
     * </p>
     *
     * @return {@code true} if this service is ready to accept invocations from
     * a client, {@code false} otherwise.
     */
    boolean isAvailable();
}
