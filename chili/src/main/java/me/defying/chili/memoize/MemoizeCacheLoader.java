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

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import me.defying.chili.Memoize;

/**
 * Creates a Guava cache for a specific method using the attributes provided by
 * the {@code Memoize} annotation attributes.
 *
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeCacheLoader extends CacheLoader<Method, Cache<Object, Optional<Object>>> {

    @Override
    public Cache<Object, Optional<Object>> load(final Method key) throws Exception {
        final CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
        final Memoize annotation = key.getAnnotation(Memoize.class);

        if (annotation.size() > 0) {
            builder.maximumSize(annotation.size());
        }

        if (annotation.time() > 0) {
            builder.expireAfterWrite(annotation.time(), annotation.unit());
        }

        if (annotation.statistics()) {
            builder.recordStats();
        }

        return builder.build();
    }
}
