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
package me.defying.chili.example;

import com.google.inject.Inject;

/**
 * Example of {@code Memoize} annotation.
 *
 * <p>Please refer to {@code MemoizeTestService} for further examples.
 *
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeApplication {

    /**
     * The service to memoize.
     */
    @Inject
    private MemoizeService service;

    public void run() {
        // calculate five powers
        // (all with cache miss)
        System.out.println(service.power(1));
        System.out.println(service.power(2));
        System.out.println(service.power(3));
        System.out.println(service.power(4));
        System.out.println(service.power(5));

        // cache hit
        System.out.println(service.power(3));

        // cache miss
        System.out.println(service.power(1));

        // cache hit
        System.out.println(service.power(1));
    }
}
