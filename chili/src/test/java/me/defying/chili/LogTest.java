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

import me.defying.chili.log.LogTestService;
import me.defying.chili.util.ChiliTest;

/**
 * Test class for {@code Log} annotation.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class LogTest extends ChiliTest {
    @Inject
    private LogTestService service;

    /**
     * Test logging without arguments and void return.
     */
    @Test
    public void simpleTest() {
        service.simple();
        assertMatches(log.toString().trim(), String.format(format, "LogTestService.simple", "", "null"));
    }

    /**
     * Test trace logging level.
     */
    @Test
    public void levelTraceTest() {
        service.levelTrace();
        assertMatches(log.toString().trim(), "^TRACE .*$");
    }

    /**
     * Test debug logging level.
     */
    @Test
    public void levelDebugTest() {
        service.levelDebug();
        assertMatches(log.toString().trim(), "^DEBUG .*$");
    }

    /**
     * Test info logging level.
     */
    @Test
    public void levelInfoTest() {
        service.levelInfo();
        assertMatches(log.toString().trim(), "^INFO .*$");
    }

    /**
     * Test warning logging level.
     */
    @Test
    public void levelWarningTest() {
        service.levelWarning();
        assertMatches(log.toString().trim(), "^WARN .*$");
    }

    /**
     * Test error logging level.
     */
    @Test
    public void levelErrorTest() {
        service.levelError();
        assertMatches(log.toString().trim(), "^ERROR .*$");
    }
    
    /**
     * Test logging with one argument and void return.
     */
    @Test
    public void hasArgumentTest() {
        service.hasArgument(1);
        assertMatches(log.toString().trim(), String.format(format, "LogTestService.hasArgument", "1", "null"));
    }
    
    /**
     * Test logging with two arguments and void return.
     */
    @Test
    public void hasArgumentsTest() {
        service.hasArguments(1, 2);
        assertMatches(log.toString().trim(), String.format(format, "LogTestService.hasArguments", "1, 2", "null"));
    }
    
    /**
     * Test logging with varargs and void return.
     */
    @Test
    public void hasVarArgsTest() {
        service.hasVarArgs(1, 2, 3, 4, 5);
        assertMatches(log.toString().trim(), String.format(format, "LogTestService.hasVarArgs", "1, 2, 3, 4, 5", "null"));
    }
    
    /**
     * Test logging without arguments and with return.
     */
    @Test
    public void hasReturnTest() {
        service.hasReturn();
        assertMatches(log.toString().trim(), String.format(format, "LogTestService.hasReturn", "", "1"));
    }
    
    /**
     * Test logging with one argument and with return.
     */
    @Test
    public void hasArgumentAndReturnTest() {
        service.hasArgumentAndReturn(1);
        assertMatches(log.toString().trim(), String.format(format, "LogTestService.hasArgumentAndReturn", "1", "1"));
    }
    
    /**
     * Test that logged functions may throw exceptions.
     */
    @Test(expected = IOException.class)
    public void exceptionTest() throws Exception {
        service.exception();
    }
}
