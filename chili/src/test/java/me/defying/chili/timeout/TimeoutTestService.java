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
package me.defying.chili.timeout;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.defying.chili.Timeout;

/**
 * Test service for {@code Timeout} annotation.
 * 
 * @author Rafael Marmelo
 * @since 1.1
 */
public class TimeoutTestService {

    public void noTimeout() throws InterruptedException {
        Thread.sleep(100);
    }

    @Timeout
    public void emptyTimeout() throws InterruptedException {
        Thread.sleep(100);
    }

    @Timeout(time = 100)
    public void wait100Timeout(long sleep) throws InterruptedException {
        Thread.sleep(sleep);
    }

    @Timeout(time = 1, unit = TimeUnit.SECONDS)
    public void wait200Timeout(long sleep) throws InterruptedException {
        Thread.sleep(sleep);
    }

    @Timeout(time = 200)
    public void exceptionTimeout() throws Exception {
        throw new IOException();
    }
}
