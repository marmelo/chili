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
package me.defying.chili.log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.defying.chili.Log;
import me.defying.chili.util.InvocationUtils;

/**
 * Guice interceptor for {@code Log} annotation.
 *
 * @author Rafael Marmelo
 * @since 1.0
 */
public class LogInterceptor implements MethodInterceptor {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    /**
     * Constructs an instance of <code>LogInterceptor</code>.
     */
    public LogInterceptor() {
        // empty
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // get annotation, class, method and arguments
        final Log annotation = InvocationUtils.getAnnotation(invocation, Log.class);
        final Class clazz = InvocationUtils.getClass(invocation);
        final Method method = InvocationUtils.getMethod(invocation);
        final List<Object> arguments = InvocationUtils.getArguments(invocation);

        // capture execution time
        long time = System.currentTimeMillis();
        final Object result = invocation.proceed();
        time = System.currentTimeMillis() - time;

        if (!annotation.onlyTypes()) {
            // log real values
            log(annotation.level(), "Invoked {}.{}({}) => {} in {} ms.",
                    clazz.getSimpleName(),
                    method.getName(),
                    Joiner.on(", ").join(arguments),
                    result,
                    time);
        } else {
            final List<Object> argumentsTypes = new ArrayList<>();
            final Object resultType;

            // convert instances to types
            for (final Object argument : arguments) {
                argumentsTypes.add(typeOf(argument));
            }
            resultType = typeOf(result);

            // log only types
            log(annotation.level(), "Invoked {}.{}({}) => {} in {} ms.",
                    clazz.getSimpleName(),
                    method.getName(),
                    Joiner.on(", ").join(argumentsTypes),
                    resultType,
                    time);
        }

        return result;
    }

    /**
     * Converts an instance to its type.
     *
     * @param obj the instance.
     * @return the instance's type.
     */
    private String typeOf(final Object obj) {
        return obj == null ? "null" : obj.getClass().getSimpleName();
    }

    /**
     * Logs a message with a custom level.
     *
     * @param level the log level
     * @param message the message to be logged
     * @param args the list of optional message arguments
     */
    private void log(final LogLevel level, final String message, final Object... args) {
        switch (level) {
            case TRACE:
                LOGGER.trace(message, args);
                break;
            case DEBUG:
                LOGGER.debug(message, args);
                break;
            case INFO:
                LOGGER.info(message, args);
                break;
            case WARNING:
                LOGGER.warn(message, args);
                break;
            case ERROR:
                LOGGER.error(message, args);
                break;
            default:
                // do nothing
        }
    }
}
