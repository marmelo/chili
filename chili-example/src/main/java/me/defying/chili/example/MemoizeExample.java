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

import com.google.inject.Guice;
import com.google.inject.Injector;

import me.defying.chili.module.ChiliModule;

/**
 * Example of {@code Memoize} annotation.
 * 
 * <p>Please refer to {@code MemoizeTestService} for further examples.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeExample {
    
    public static void main(String[] args) {
        // create the injector
        Injector injector = Guice.createInjector(new ChiliModule());
 
        // create our application main class
        MemoizeApplication application = injector.getInstance(MemoizeApplication.class);
        application.run();
        
        //
        // Expected stdout:
        //
        // 1  (miss)
        // 4  (miss)
        // 9  (miss)
        // 16 (miss)
        // 25 (miss)
        // 9  (hit)
        // 1  (miss)
        // 1  (hit)
        //
        //
        // Expected log messages:
        //
        // 2015-03-04 00:25:32.569 [main] [DEBUG] Method MemoizeService.power :: 1 requests :: 0 hits (0.0%).
        // 2015-03-04 00:25:32.570 [main] [DEBUG] Method MemoizeService.power :: 2 requests :: 0 hits (0.0%).
        // 2015-03-04 00:25:32.570 [main] [DEBUG] Method MemoizeService.power :: 3 requests :: 0 hits (0.0%).
        // 2015-03-04 00:25:32.579 [main] [DEBUG] Method MemoizeService.power :: 4 requests :: 0 hits (0.0%).
        // 2015-03-04 00:25:32.580 [main] [DEBUG] Method MemoizeService.power :: 5 requests :: 0 hits (0.0%).
        // 2015-03-04 00:25:32.582 [main] [DEBUG] Method MemoizeService.power :: 6 requests :: 1 hits (16.6%).
        // 2015-03-04 00:25:32.582 [main] [DEBUG] Method MemoizeService.power :: 7 requests :: 1 hits (14.2%).
        // 2015-03-04 00:25:32.582 [main] [DEBUG] Method MemoizeService.power :: 8 requests :: 2 hits (25.0%).
        //
    }
}
