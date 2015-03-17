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
package me.defying.chili.log;

import java.io.IOException;

import me.defying.chili.Log;

/**
 * Test service for {@code Log} annotation.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class LogTestService {

    @Log
    public void simple() {
        // empty
    }

    @Log(level = LogLevel.TRACE)
    public void levelTrace() {
        // empty
    }

    @Log(level = LogLevel.DEBUG)
    public void levelDebug() {
        // empty
    }

    @Log(level = LogLevel.INFO)
    public void levelInfo() {
        // empty
    }

    @Log(level = LogLevel.WARNING)
    public void levelWarning() {
        // empty
    }

    @Log(level = LogLevel.ERROR)
    public void levelError() {
        // empty
    }

    @Log
    public void hasArgument(int x) {
        // empty
    }

    @Log
    public void hasArguments(int x, int y) {
        // empty
    }

    @Log
    public void hasVarArgs(int... x) {
        // empty
    }

    @Log
    public int hasReturn() {
        return 1;
    }

    @Log
    public int hasArgumentAndReturn(int x) {
        return x;
    }
    
    @Log
    public void exception() throws Exception {
        throw new IOException();
    }
}
