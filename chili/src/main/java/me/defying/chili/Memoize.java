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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Indicates that a method invocation is cached.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Memoize {
    /**
     * Returns for how long the method invocation should be cached according to
     * the {@code TimeUnit} value. If unspecified or equal to zero the cache
     * will not be time bound.
     * @return how long the method invocation should be cached.
     */
    long time() default 0;
    
    /**
     * Returns the {@code TimeUnit} the time value refers to.
     * @return the {@code TimeUnit} the time value refers to. 
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
    
    /**
     * Returns the maximum entries the cache may have.
     * @return the maximum entries the cache may have. 
     */
    long size() default 0;
    
    /**
     * Returns whether or not cache statistics should be logged.
     * @return whether or not cache statistics should be logged. 
     */
    boolean statistics() default false;
}
