/*
 * The MIT License (MIT)
 * Copyright (c) 2018 Rafael Marmelo
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
package me.defying.chili.tostring;

import com.google.common.collect.ImmutableList;
import java.util.List;
import me.defying.chili.ToString;

/**
 * Test class with unknown fields for {@code ToString} annotation.
 *
 * @author Rafael Marmelo
 * @since 1.1
 */
@ToString(fields = { "c", "unknown", "i", "", "random" })
public class UnknownFieldsClass {
    private final boolean b = true;
    private final char c = 'a';
    private final int i = 1;
    private final long l = 2L;
    private final float f = 3.0f;
    private final double d = 4.0;
    private final String s = "string";
    private final List<String> list = ImmutableList.of("a", "b", "c");
}
