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

/**
 * Exception thrown when the memoized underlying method invocation throws an
 * exception. This exception wraps the underlying exception to be rethrown by
 * the interceptor to existing code.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeException extends Exception {

    /**
     * Creates a new instance of <code>MemoizeException</code> without detail
     * message.
     */
    public MemoizeException() {
        // empty
    }

    /**
     * Constructs an instance of <code>MemoizeException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public MemoizeException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>MemoizeException</code> with the
     * specified cause.
     *
     * @param cause the cause.
     */
    public MemoizeException(Throwable cause) {
        super(cause);
    }
}
