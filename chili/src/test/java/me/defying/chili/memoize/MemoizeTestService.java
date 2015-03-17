/*
 * The MIT License (MIT)
 * Copyright (c) 2015 Rafael Marmelo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.defying.chili.memoize;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.defying.chili.Memoize;

/**
 * Test service for {@code Memoize} annotation.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeTestService {

    @Memoize
    public Wrap simple(String x) {
        return new Wrap(x);
    }

    @Memoize(size = 3)
    public Wrap keep3(String x) {
        return new Wrap(x);
    }

    @Memoize(time = 1, unit = TimeUnit.SECONDS)
    public Wrap keep1Sec(String x) {
        return new Wrap(x);
    }

    @Memoize(size = 3, time = 1, unit = TimeUnit.SECONDS)
    public Wrap keep3for1Sec(String x) {
        return new Wrap(x);
    }

    @Memoize(statistics = true)
    public Wrap statistics(String x) {
        return new Wrap(x);
    }

    @Memoize
    public Wrap exception(String x) throws Exception {
        throw new IOException();
    }

    @Memoize
    public Wrap noArgs() {
        return new Wrap(null);
    }

    @Memoize
    public Wrap twoArgs(String x, String y) {
        return new Wrap(x + y);
    }

    @Memoize
    public Wrap varArgs(String... x) {
        return new Wrap(x);
    }

    @Memoize
    public Wrap mixedVarArgs(String x, String... y) {
        return new Wrap(x);
    }

    @Memoize
    public Wrap primitiveVarArgs(int... x) {
        return new Wrap(x);
    }

    @Memoize
    public Wrap nullResult(String x) {
        return null;
    }

    @Memoize
    public Wrap array(String[] x) {
        return new Wrap(x);
    }
}
