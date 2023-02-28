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

package com.qualitrix.infinitum.notification;

/**
 * Contract for sending notification messages to external systems.
 */
public interface Notifier {
    /**
     * Adds a recipient to whom notification messages should be sent. This is
     * usually dependent on the notification mechanism used. For example, if
     * email notifications are used, the recipient would be an email address,
     * and so on.
     *
     * @param recipient The recipient to whom notification messages should be
     * sent.
     *
     * @return This notifier.
     */
    default Notifier addRecipient(String recipient) {
        return addRecipients(recipient);
    }

    /**
     * <p>
     * Adds recipients to whom notification messages should be sent. This is
     * usually dependent on the notification mechanism used. For example, if
     * email notifications are used, the recipient would be an email address,
     * and so on.
     * </p>
     *
     * <p>
     * Calling this method multiple times keeps accumulating recipients.
     * </p>
     *
     * @param recipients The recipients to whom notification messages should be
     * sent.
     *
     * @return This notifier.
     */
    Notifier addRecipients(String... recipients);

    /**
     * Removes all recipients to whom notification messages should be sent.
     *
     * @return This notifier.
     */
    Notifier clearRecipients();

    /**
     * Sends a notification message to the intended recipient(s). The message is
     * sent only if at least one recipient has been specified.
     *
     * @param message The messages to send.
     *
     * @return {@code true} if the message is sent successfully without any
     * error, {@code false} otherwise. Note that success in sending a message
     * does not guarantee that the message has been or will be delivered to
     * the intended recipients - it is simply an indication that the application
     * managed to send it without encountering any error.
     */
    boolean notify(String message);

    /**
     * Sends a notification message to the intended recipient(s) using a
     * specified format for the message and an additional contextual value. The
     * format must be supported by {@link String#format(String, Object...)}.
     * For example, <code>notify("Tests completed in %d seconds.", 101)</code>
     * sends the notification message {@code Tests completed in 101 seconds.}.
     *
     * @param format The message format - e.g. <code>Total time taken: %s</code>.
     * @param context Context to include in the message - e.g. {@code 101}.
     *
     * @return {@code true} if the message is sent successfully without any
     * error, {@code false} otherwise. Note that success in sending a message
     * does not guarantee that the message has been or will be delivered to
     * the intended recipients - it is simply an indication that the application
     * managed to send it without encountering any error.
     */
    default boolean notify(final String format, final Object context) {
        return notify(String.format(format, context));
    }

    /**
     * Sends a notification message to the intended recipient(s) using a
     * specified format for the message and two additional contextual values.
     * For example,
     * <code>notify("Tests completed for %d features across %d modules.", 100, 9)</code>
     * sends the notification message
     * {@code Tests completed for 100 across 9 modules.}.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     *
     * @return {@code true} if the message is sent successfully without any
     * error, {@code false} otherwise. Note that success in sending a message
     * does not guarantee that the message has been or will be delivered to
     * the intended recipients - it is simply an indication that the application
     * managed to send it without encountering any error.
     */
    default boolean notify(final String format, final Object arg1, final Object arg2) {
        return notify(String.format(format, arg1, arg2));
    }

    /**
     * Sends a notification message to the intended recipient(s) using a
     * specified format for the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     *
     * @return {@code true} if the message is sent successfully without any
     * error, {@code false} otherwise. Note that success in sending a message
     * does not guarantee that the message has been or will be delivered to
     * the intended recipients - it is simply an indication that the application
     * managed to send it without encountering any error.
     */
    default boolean notify(final String format, final Object... args) {
        return notify(String.format(format, args));
    }
}
