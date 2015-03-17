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

import java.io.IOException;

import com.google.inject.Inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import me.defying.chili.memoize.MemoizeTestService;
import me.defying.chili.memoize.Wrap;
import me.defying.chili.util.ChiliTest;

/**
 * Test class for {@code Memoize} annotation.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class MemoizeTest extends ChiliTest {
    @Inject
    private MemoizeTestService service;

    /**
     * Test simple memoization, without any arguments.
     */
    @Test
    public void simpleTest() {
        // different arguments yield different results
        Wrap a = service.simple("a");
        Wrap b = service.simple("b");
        assertNotEquals(a, b);

        // equal arguments yield the same, cached, result
        Wrap a1 = service.simple("a");
        assertEquals(a, a1);
    }

    /**
     * Test count-based memoization keeping only the last 3 entries.
     */
    @Test
    public void keep3Test() {
        // insert 4 entries
        Wrap a = service.keep3("a");
        Wrap b = service.keep3("b");
        Wrap c = service.keep3("c");
        Wrap d = service.keep3("d");

        // get the first entry, which is not cached anymore
        Wrap a1 = service.keep3("a");
        assertNotEquals(a, a1);

        // get the third entry, which is still cached
        Wrap c1 = service.keep3("c");
        assertEquals(c, c1);
    }

    /**
     * Test time-bound memoization keeping all entries for 1 second.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void keep1SecTest() throws InterruptedException {
        // most likely, two sequencial accesses will yield a cached result
        Wrap a = service.keep1Sec("a");
        Wrap a1 = service.keep1Sec("a");
        assertEquals(a, a1);

        // after one second, all entries will be gone
        Thread.sleep(1000);
        Wrap a2 = service.keep1Sec("a");
        assertNotEquals(a, a2);
    }

    /**
     * Test count and time-bound memoization keeping only the last 3 entries for 1 second.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void keep3for1SecTest() throws InterruptedException {
        // insert 4 entries
        Wrap a = service.keep3for1Sec("a");
        Wrap b = service.keep3for1Sec("b");
        Wrap c = service.keep3for1Sec("c");
        Wrap d = service.keep3for1Sec("d");

        // get the first entry, which is not cached anymore
        Wrap a1 = service.keep3for1Sec("a");
        assertNotEquals(a, a1);

        // get the third entry, which is most likely still cached
        Wrap c1 = service.keep3for1Sec("c");
        assertEquals(c, c1);
        
        // after one second, all entries will be gone
        Thread.sleep(1000);
        Wrap c2 = service.keep3for1Sec("c");
        assertNotEquals(c, c2);
    }
    
    /**
     * Test memoization statistics.
     */
    @Test
    public void statisticsTest() {
        Wrap a = service.statistics("a");
        Wrap a1 = service.statistics("a");
        Wrap a2 = service.statistics("a");
        assertEquals(a, a1);
        assertEquals(a, a2);
        
        // assert statistics log
        String[] lines = log.toString().split("\n");
        assertEquals(3, lines.length);
        assertMatches(lines[0].trim(), "^DEBUG Method MemoizeTestService\\.statistics :: 1 requests :: 0 hits \\(0\\.0%\\)\\.$");
        assertMatches(lines[1].trim(), "^DEBUG Method MemoizeTestService\\.statistics :: 2 requests :: 1 hits \\(50\\.0%\\)\\.$");
        assertMatches(lines[2].trim(), "^DEBUG Method MemoizeTestService\\.statistics :: 3 requests :: 2 hits \\(66\\.6%\\)\\.$");
    }
    
    /**
     * Test that memoized functions may throw exceptions.
     */
    @Test(expected = IOException.class)
    public void exceptionTest() throws Exception {
        service.exception("a");
    }

    /**
     * Test that memoized functions may have no arguments.
     */
    @Test
    public void noArgsTest() {
        Wrap a = service.noArgs();
        Wrap a1 = service.noArgs();
        assertNotNull(a);
        assertEquals(a, a1);
    }

    /**
     * Test that memoized functions allow multiple arguments.
     */
    @Test
    public void twoArgsTest() {
        Wrap ab = service.twoArgs("a", "b");
        Wrap ab1 = service.twoArgs("a", "b");
        assertEquals(ab, ab1);
    }

    /**
     * Test that memoized functions allow variable arguments.
     */
    @Test
    public void varArgsTest() {
        // no args
        Wrap no = service.varArgs();
        Wrap no1 = service.varArgs();
        assertEquals(no, no1);
        
        // one arg
        Wrap a = service.varArgs("a");
        Wrap a1 = service.varArgs("a");
        assertEquals(a, a1);
        
        // two args
        Wrap ab = service.varArgs("a", "b");
        Wrap ab1 = service.varArgs("a", "b");
        assertEquals(ab, ab1);
        
        // two args with null
        Wrap an = service.varArgs("a", null);
        Wrap an1 = service.varArgs("a", null);
        assertEquals(an, an1);
        
        // three args
        Wrap abc = service.varArgs("a", "b", "c");
        Wrap abc1 = service.varArgs("a", "b", "c");
        assertEquals(abc, abc1);
        
        // one normal, three varargs
        Wrap abcd = service.mixedVarArgs("a", "b", "c", "d");
        Wrap abcd1 = service.mixedVarArgs("a", "b", "c", "d");
        assertEquals(abcd, abcd1);
        
        // primitive varargs
        Wrap p123 = service.primitiveVarArgs(1, 2, 3);
        Wrap p1231 = service.primitiveVarArgs(1, 2, 3);
        assertEquals(p123, p1231);
    }

    /**
     * Test that memoized functions allow null arguments.
     */
    @Test
    public void nullArgsTest() {
        Wrap a = service.simple(null);
        Wrap a1 = service.simple(null);
        assertEquals(a, a1);
    }
    
    /**
     * Tests that memoized function may return null values.
     */
    @Test
    public void nullResultTest() {
        Wrap a = service.nullResult("a");
        assertNull(a);
    }
    
    /**
     * Tests that memoized function allow arrays.
     */
    @Test
    public void arrayTest() {
        String[] a = new String[] { "a" };
        String[] a1 = new String[] { "a" };
        
        // the same array instance yields the same result
        Wrap aa = service.array(a);
        Wrap aa1 = service.array(a);
        assertEquals(aa, aa1);
        
        // but different array instances do not
        Wrap bb = service.array(a1);
        assertNotEquals(aa, bb);
    }
}
