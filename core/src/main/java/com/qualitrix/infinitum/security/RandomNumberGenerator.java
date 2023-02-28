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

package com.qualitrix.infinitum.security;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>
 * Generates cryptographically-secure random numbers. A cryptographically-secure
 * random number is unique and cannot be guessed without knowing the exact
 * internal details of the process used for generating the random number.
 * Cryptographically-secure random numbers can be used for cryptographic
 * purposes like keys for encryption algorithms, salt for hashing algorithms,
 * etc.
 * </p>
 *
 * <p>
 * Having a common generator ensures that random numbers can be generated
 * securely throughout the application without having to repeat code and
 * having to ensure that the individual numbers generated are indeed secure.
 * </p>
 */
public final class RandomNumberGenerator {
    private static final Object LOCK = new Object();

    private static Random RANDOM;

    /**
     * Deliberately hidden to prevent direct instantiation.
     */
    private RandomNumberGenerator() {
    }

    /**
     * Generates a random {@link BigInteger}, uniformly distributed over the
     * range <i>0</i> to <i>(2<sup>{@code bits}</sup> - 1)</i>, both inclusive.
     *
     * @param bits The maximum bit length of the {@link BigInteger} to
     * generate.
     *
     * @return A {@link BigInteger} with the specified number of randomly
     * generated bits.
     *
     * @throws IllegalArgumentException if {@code bits} is negative.
     */
    public static BigInteger nextBigInteger(final int bits) {
        return new BigInteger(bits, getRandom());
    }

    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code boolean} value from this random number generator's
     * sequence. The general contract of {@code nextBoolean} is that one
     * {@code boolean} value is pseudorandomly generated and returned.  The
     * values {@code true} and {@code false} are produced with
     * (approximately) equal probability.
     *
     * @return the next pseudorandom, uniformly distributed
     * {@code boolean} value from this random number generator's
     * sequence
     *
     * @see java.util.Random#nextBoolean()
     */
    public static boolean nextBoolean() {
        return getRandom().nextBoolean();
    }

    /**
     * Generates a specified number of random bytes and places them in a
     * user-provided byte array.
     *
     * @param bytes The array to be filled in with random bytes. The array
     * length determines the number of random bytes to generate.
     */
    public static void nextBytes(final byte[] bytes) {
        getRandom().nextBytes(bytes);
    }

    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code double} value between {@code 0.0} and
     * {@code 1.0} from this random number generator's sequence.
     *
     * @return The next pseudorandom, uniformly distributed {@code double}
     * value between {@code 0.0} and {@code 1.0} from this
     * random number generator's sequence.
     *
     * @see java.util.Random#nextDouble()
     */
    public static double nextDouble() {
        return getRandom().nextDouble();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code 1.0} from this random
     * number generator's sequence.
     *
     * @return The next pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code 1.0} from this
     * random number generator's sequence.
     *
     * @see java.util.Random#nextFloat()
     */
    public static float nextFloat() {
        return getRandom().nextFloat();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code int}
     * value from this random number generator's sequence. The general
     * contract of {@code nextInt} is that one {@code int} value is
     * pseudorandomly generated and returned. All 2<sup>32</sup> possible
     * {@code int} values are produced with (approximately) equal probability.
     *
     * @return The next pseudorandom, uniformly distributed {@code int}
     * value from this random number generator's sequence.
     *
     * @see java.util.Random#nextInt()
     */
    public static int nextInt() {
        return getRandom().nextInt();
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextInt} is that one {@code int} value in the specified range
     * is pseudorandomly generated and returned.  All {@code bound} possible
     * {@code int} values are produced with (approximately) equal probability.
     *
     * @param bound The upper bound (exclusive).  Must be positive.
     *
     * @return The next pseudorandom, uniformly distributed {@code int}
     * value between zero (inclusive) and {@code bound} (exclusive)
     * from this random number generator's sequence.
     *
     * @see java.util.Random#nextInt(int)
     */
    public static int nextInt(final int bound) {
        return getRandom().nextInt(bound);
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence. The general
     * contract of {@code nextLong} is that one {@code long} value is
     * pseudorandomly generated and returned.
     *
     * @return The next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence.
     *
     * @see java.util.Random#nextLong()
     */
    public static long nextLong() {
        return getRandom().nextLong();
    }

    /**
     * Gets a {@link Random} instance that can be used to generate random
     * numbers.
     *
     * @return A {@link Random} instance that can be used to generate random
     * numbers.
     */
    private static Random getRandom() {
        if (RANDOM == null) {
            synchronized (LOCK) {
                // Attempt to create a random number generator with strong
                // cryptographic strength and evenly distributed spectrum of return
                // values.
                RANDOM = new SecureRandom();
            }
        }

        return RANDOM;
    }
}
