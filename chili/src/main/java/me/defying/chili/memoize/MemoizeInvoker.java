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

import java.util.concurrent.Callable;

import com.google.common.base.Optional;
import me.defying.chili.module.ChiliException;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Invokes the underlying method of a memoize operation.
 *
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeInvoker implements Callable<Optional<Object>> {

    /**
     * The method invocation.
     */
    private final MethodInvocation invocation;

    /**
     * Constructs an instance of <code>MemoizeInvoker</code>.
     *
     * @param invocation the method invocation.
     */
    public MemoizeInvoker(final MethodInvocation invocation) {
        this.invocation = invocation;
    }

    @Override
    public Optional<Object> call() throws Exception {
        Object result;

        try {
            result = invocation.proceed();
        } catch (Throwable ex) {
            // when the underlying method invokation throws an exception it is
            // wrapped and sent to the interceptor in order to existing code get
            // the real exception
            throw new ChiliException(ex);
        }

        // guava caches do not allow null values
        return Optional.fromNullable(result);
    }
}
