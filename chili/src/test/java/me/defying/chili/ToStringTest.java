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
package me.defying.chili;

import com.google.inject.Inject;
import me.defying.chili.tostring.BasicModifiersClass;
import me.defying.chili.tostring.BasicTypesClass;
import me.defying.chili.tostring.EmptyClass;
import me.defying.chili.tostring.RestrictedFieldsClass;
import me.defying.chili.tostring.SimilarMethodsClass;
import me.defying.chili.tostring.ToStringClass;
import me.defying.chili.tostring.UnknownFieldsClass;
import me.defying.chili.util.ChiliTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test class for {@code ToString} annotation.
 *
 * @author Rafael Marmelo
 * @since 1.1
 */
public class ToStringTest extends ChiliTest {
    @Inject
    private EmptyClass emptyClass;

    @Inject
    private BasicTypesClass basicTypesClass;

    @Inject
    private BasicModifiersClass basicModifiersClass;

    @Inject
    private ToStringClass toStringClass;

    @Inject
    private SimilarMethodsClass similarMethodsClass;

    @Inject
    private RestrictedFieldsClass restrictedFieldsClass;

    @Inject
    private UnknownFieldsClass unknownFieldsClass;

    /**
     * Test zero fields.
     */
    @Test
    public void emptyTest() {
        assertEquals("EmptyClass{}", emptyClass.toString());
    }

    /**
     * Test basic types fields.
     */
    @Test
    public void basicTypesTest() {
        assertEquals("BasicTypesClass{b=true, c=a, i=1, l=2, f=3.0, d=4.0, s=string, list=[a, b, c]}", basicTypesClass.toString());
    }

    /**
     * Test basic modifiers fields.
     */
    @Test
    public void basicModifiersTest() {
        assertEquals("BasicModifiersClass{_default=1, _public=2, _private=3, _protected=4, _transient=5, _volatile=6, _static=7}", basicModifiersClass.toString());
    }

    /**
     * Test when method is defined.
     */
    @Test
    public void toStringTest() {
        assertEquals("ToStringClass{}", toStringClass.toString());
    }

    /**
     * Test similar toString methods are not intercepted.
     */
    @Test
    public void similarMethodsTest() {
        assertEquals("toString", similarMethodsClass.toString(true));
        assertEquals("someMethod", similarMethodsClass.someMethod());
        assertEquals("SimilarMethodsClass{}", similarMethodsClass.toString());
    }

    /**
     * Test restricted fields.
     */
    @Test
    public void restrictedFieldsTest() {
        assertEquals("RestrictedFieldsClass{c=a, l=2, s=string}", restrictedFieldsClass.toString());
    }

    /**
     * Test unknown fields.
     */
    @Test
    public void unknownFieldsTest() {
        assertEquals("UnknownFieldsClass{c=a, i=1}", unknownFieldsClass.toString());
    }
}
