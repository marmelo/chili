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
package me.defying.chili;

import java.io.IOException;

import com.google.inject.Inject;
import org.junit.Test;

import me.defying.chili.timeout.TimeoutException;
import me.defying.chili.timeout.TimeoutTestService;
import me.defying.chili.util.ChiliTest;

/**
 * Test class for {@code Timeout} annotation.
 *
 * @author Rafael Marmelo
 * @since 1.1
 */
public class TimeoutTest extends ChiliTest {
    @Inject
    private TimeoutTestService service;

    /**
     * Test timeout with default arguments (never timeout).
     * @throws java.lang.InterruptedException
     */
    @Test
    public void emptyTimeoutTest() throws InterruptedException {
        service.emptyTimeout();
    }

    /**
     * Test timeout failure (timeouts at 100ms but work only takes 50ms).
     * @throws java.lang.InterruptedException
     */
    @Test
    public void failureTimeoutTest() throws InterruptedException {
        service.wait100Timeout(50);
    }

    /**
     * Test timeout success (timeouts at 100ms and work takes 200ms).
     * @throws InterruptedException
     */
    @Test(expected = TimeoutException.class)
    public void successTimeoutTest() throws InterruptedException {
        service.wait100Timeout(200);
    }

    /**
     * Test that timed out functions may throw exceptions.
     * @throws java.lang.Exception
     */
    @Test(expected = IOException.class)
    public void exceptionTimeoutTest() throws Exception {
        service.exceptionTimeout();
    }
}
