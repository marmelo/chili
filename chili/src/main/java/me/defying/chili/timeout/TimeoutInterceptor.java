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

import java.util.concurrent.Callable;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.defying.chili.Timeout;
import me.defying.chili.module.ChiliException;
import me.defying.chili.util.InvocationUtils;

/**
 * Guice interceptor for {@code Timeout} annotation.
 *
 * @author Rafael Marmelo
 * @since 1.1
 */
public class TimeoutInterceptor implements MethodInterceptor {
    final Logger logger = LoggerFactory.getLogger(TimeoutInterceptor.class);
    
    public TimeoutInterceptor() {
        // empty
    }
    
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // get annotation, class, method and arguments
        Timeout annotation = InvocationUtils.getAnnotation(invocation, Timeout.class);
        
        // do nothing
        if (annotation.time() <= 0) {
            return invocation.proceed();
        }
        
        TimeLimiter limiter = new SimpleTimeLimiter();
        
        // underlying method invoker
        Callable<Object> invoker = new TimeoutInvoker(invocation);
        
        try {
            return limiter.callWithTimeout(invoker, annotation.time(), annotation.unit(), true);
        } catch (UncheckedTimeoutException ex) {
            throw new TimeoutException(ex);
        } catch (ChiliException ex) {
            // when the underlying method invokation throws an exception we need
            // to unrap it in order to existing code get the real exception
            throw ex.getCause();
        }
    }
}
