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
package me.defying.chili.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.google.inject.Guice;
import com.google.inject.Injector;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;

import me.defying.chili.module.ChiliModule;

/**
 * Abstract class to be used by Chili project tests.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public abstract class ChiliTest {
    
    // output stream that will hold log entries
    protected static final ByteArrayOutputStream log = new ByteArrayOutputStream();
    
    // pattern format to be compared with log entries
    protected final String format = "^DEBUG Invoked %s\\(%s\\) => %s in [0-9]+ ms\\.$";

    @BeforeClass
    public static void setUpBeforeClass() {
        // logs produced will be in the format "LEVEL Message."
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        System.setProperty("org.slf4j.simpleLogger.showThreadName", "false");
        System.setProperty("org.slf4j.simpleLogger.showLogName", "false");

        // redefine error output stream to something we may read
        // slf4j-simple logger writes log to System.err by default
        System.setErr(new PrintStream(log));
    }
    
    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new ChiliModule());
        injector.injectMembers(this);
        
        // reset log before test
        log.reset();
    }
    
    //
    // helpers
    //
    
    /**
     * Asserts that a string matches a regular expression.
     * If it doesn't it throws an AssertionError without a message.
     * 
     * @param pattern the regular expression
     * @param string the string to be checked against the pattern
     */
    protected void assertMatches(String pattern, String string) {
        assertTrue(pattern.matches(string));
    }
}
