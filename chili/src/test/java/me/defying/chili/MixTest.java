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

import com.google.inject.Inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import me.defying.chili.memoize.Wrap;
import me.defying.chili.mix.MixTestService;
import me.defying.chili.util.ChiliTest;

/**
 * Test class for multiple annotation.
 *
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MixTest extends ChiliTest {
    @Inject
    private MixTestService service;

    /**
     * Test multiple annotations.
     */
    @Test
    public void simpleTest() {
        // different arguments yield different results
        final Wrap a = service.simple("a");
        final Wrap b = service.simple("b");
        assertNotEquals(a, b);

        // equal arguments yield the same, cached, result
        final Wrap a1 = service.simple("a");
        assertEquals(a, a1);

        // assert log
        final String[] lines = LOG.toString().split("\n");
        assertEquals(3, lines.length);
        assertMatches(lines[0].trim(), String.format(format, "MixTestService.simple", "a", ".*"));
        assertMatches(lines[1].trim(), String.format(format, "MixTestService.simple", "b", ".*"));
        assertMatches(lines[2].trim(), String.format(format, "MixTestService.simple", "a", ".*"));
    }
}
