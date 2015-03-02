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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Guice interceptor for {@code Memoize} annotation.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeInterceptor implements MethodInterceptor {
    final Logger logger = LoggerFactory.getLogger(MemoizeInterceptor.class);

    private final LoadingCache<Method, Cache<Object, Optional<Object>>> caches = CacheBuilder
            .newBuilder()
            .build(new MemoizeCacheLoader());

    public MemoizeInterceptor() {
        // empty
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Object result;

        // get or create method cache
        Cache<Object, Optional<Object>> cache = caches.get(invocation.getMethod());

        // get method arguments
        List<Object> arguments = new ArrayList(Arrays.asList(invocation.getArguments()));
        
        // if the method contains a varargs then the last parameter must be flattened
        if (invocation.getMethod().isVarArgs() && !arguments.isEmpty()) {
            int last = arguments.size() - 1;
            Object varargs = arguments.remove(last);
            arguments.addAll(Arrays.asList((Object[]) varargs));
        }

        // immutable object lists can compared
        // cannot use Guava ImmutableList because the list may contain nulls
        List<Object> key = Collections.unmodifiableList(arguments);

        // underlying method invoker
        Callable<Optional<Object>> invoker = new MemoizeInvoker(invocation);

        try {
            // get or compute result
            result = cache.get(key, invoker).orElse(null);
        } catch (ExecutionException ex) {
            // when the underlying method invokation throws an exception we need
            // to unrap it in order to existing code get the real exception
            if (ex.getCause() instanceof MemoizeException) {
                throw ex.getCause().getCause();
            } else {
                throw ex.getCause();
            }
        }

        // log statistics
        if (logger.isDebugEnabled() && cache.stats().requestCount() > 0) {
            logger.debug("Method {}.{} :: {} requests :: {} hits ({}%).",
                    invocation.getMethod().getDeclaringClass().getSimpleName(),
                    invocation.getMethod().getName(),
                    cache.stats().requestCount(),
                    cache.stats().hitCount(),
                    ((int) (cache.stats().hitRate() * 1000)) / 10.0);
        }

        return result;
    }
}
