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

import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.defying.chili.ToString;
import me.defying.chili.util.InvocationUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Guice interceptor for {@code ToString} annotation.
 *
 * @author Rafael Marmelo
 * @since 1.1
 */
public class ToStringInterceptor implements MethodInterceptor {

    /**
     * Constructs an instance of <code>ToStringInterceptor</code>.
     */
    public ToStringInterceptor() {
        // empty
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final ToString annotation = InvocationUtils.getClassAnnotation(invocation, ToString.class);
        final Class clazz = InvocationUtils.getClass(invocation);

        final List<Field> fields;

        if (annotation.fields() == null || annotation.fields().length == 0) {
            fields = Arrays.asList(clazz.getDeclaredFields());
        } else {
            fields = new ArrayList<>();
            for (final String fieldName : annotation.fields()) {
                try {
                    fields.add(clazz.getDeclaredField(fieldName));
                } catch (final NoSuchFieldException e) {
                    // ignore unknown fields
                }
            }
        }

        final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(clazz);

        for (final Field field : fields) {
            field.setAccessible(true);
            helper.add(field.getName(), field.get(invocation.getThis()));
        }

        return helper.toString();
    }
}
