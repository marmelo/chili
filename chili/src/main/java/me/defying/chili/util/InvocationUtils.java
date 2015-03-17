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
package me.defying.chili.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;

/**
 * AOP method invocation helpers.
 * 
 * @author Rafael Marmelo
 * @since 1.0
 */
public class InvocationUtils {
    
    /**
     * Returns the annotation for the specified annotation type.
     * 
     * @param <T> the annotation type
     * @param invocation the method invocation
     * @param annotation the type of annotation
     * @return the annotation for the specified annotation type
     */
    public static <T extends Annotation> T getAnnotation(final MethodInvocation invocation, final Class<T> annotation) {
        return invocation.getMethod().getAnnotation(annotation);
    }
    
    /**
     * Returns the class being invoked.
     * 
     * @param invocation the method invocation
     * @return the class being invoked
     */
    public static Class getClass(final MethodInvocation invocation) {
        return invocation.getThis().getClass().getSuperclass();
    }
    
    /**
     * Returns the method being invoked.
     * 
     * @param invocation the method invocation
     * @return the method being invoked
     */
    public static Method getMethod(final MethodInvocation invocation) {
        return invocation.getMethod();
    }
    
    /**
     * Returns the arguments of the method invocation.
     * 
     * @param invocation the method invocation
     * @return the arguments of the method invocation
     */
    public static List<Object> getArguments(final MethodInvocation invocation) {
        // get method arguments
        List<Object> arguments = new ArrayList(Arrays.asList(invocation.getArguments()));
        
        // if the method contains a varargs then the last parameter must be flattened
        if (invocation.getMethod().isVarArgs() && !arguments.isEmpty()) {
            int last = arguments.size() - 1;
            Object varargs = arguments.remove(last);
            arguments.addAll(convert(varargs));
        }
        
        return arguments;
    }
    
    /**
     * Converts an {@code Object} array into a {@code List}.
     * 
     * @param array the array of objects
     * @return the list of objects
     */
    private static List<Object> convert(Object array) {
        List<Object> result = new ArrayList<>();
        int length = Array.getLength(array);
        
        for (int i = 0; i < length; ++i) {
            result.add(Array.get(array, i));
        }
        
        return result;
    }
}
