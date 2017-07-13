/* 
 * Copyright Lomcat and/or its affiliates.
 * 
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.lomcat.leo.aide;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 断言工具
 *
 * @author Kuniel - kuniel@lomcat.com
 * @since 1.0.0
 */
public class AssertAide {

    public static void isTrue(final boolean expression, final String message, final Object... messageArgs) {
        if (!expression) {
            throwIllegalArgumentException(message, messageArgs);
        }
    }

    public static void isTrue(final boolean expression) {
        isTrue(expression, "The expression is false.");
    }

    public static void isFalse(final boolean expression, final String message, final Object... messageArgs) {
        if (expression) {
            throwIllegalArgumentException(message, messageArgs);
        }
    }

    public static void isFalse(final boolean expression) {
        isFalse(expression, "The expression is true.");
    }

    public static <T> void notNull(final T object, final String message, final Object... messageArgs) {
        if (object == null) {
            throwNullPointException(message, messageArgs);
        }
    }

    public static <T> void notNull(final T object) {
        notNull(object, "The object is null.");
    }

    public static <T> T[] notEmpty(final T[] array, final String message, final Object... messageArgs) {
        if (array == null) {
            throwNullPointException(message, messageArgs);
        }
        if (array.length == 0) {
            throwIllegalArgumentException(message, messageArgs);
        }
        return array;
    }

    public static <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, "The array is empty.");
    }

    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... messageArgs) {
        if (collection == null) {
            throwNullPointException(message, messageArgs);
        }
        if (collection.isEmpty()) {
            throwIllegalArgumentException(message, messageArgs);
        }
        return collection;
    }

    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return notEmpty(collection, "The collection is empty.");
    }

    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... messageArgs) {
        if (map == null) {
            throwNullPointException(message, messageArgs);
        }
        if (map.isEmpty()) {
            throwIllegalArgumentException(message, messageArgs);
        }
        return map;
    }

    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return notEmpty(map, "The map is empty.");
    }

    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... messageArgs) {
        if (chars == null) {
            throwNullPointException(message, messageArgs);
        }
        if (chars.length() == 0) {
            throwIllegalArgumentException(message, messageArgs);
        }
        return chars;
    }

    public static <T extends CharSequence> T notEmpty(final T chars) {
        return notEmpty(chars, "The character sequence is empty");
    }

    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... messageArgs) {
        if (chars == null) {
            throwNullPointException(message, messageArgs);
        }
        if (StringAide.isBlank(chars)) {
            throwIllegalArgumentException(message, messageArgs);
        }
        return chars;
    }

    public static <T extends CharSequence> T notBlank(final T chars) {
        return notBlank(chars, "The character sequence is blank.");
    }

    public static <T> T[] noNull(final T[] array, final String message, final Object... messageArgs) {
        notNull(array);
        for (T element : array) {
            if (element == null) {
                throwIllegalArgumentException(message, messageArgs);
            }
        }
        return array;
    }

    public static <T> T[] noNull(final T[] array) {
        notNull(array);
        for (int i = 0; i < array.length; i ++) {
            if (array[i] == null) {
                throwIllegalArgumentException("The array contains null element at index: %d", i);
            }
        }
        return array;
    }

    public static <T extends Iterable<?>> T noNull(final T iterable, final String message, String messageArgs) {
        notNull(iterable);
        for (Object element : iterable) {
            if (element == null) {
                throwIllegalArgumentException(message, messageArgs);
            }
        }
        return iterable;
    }

    public static <T extends Iterable<?>> T noNull(final T iterable) {
        notNull(iterable);
        int i = 0;
        for (final Iterator<?> iter = iterable.iterator(); iter.hasNext(); i ++) {
            if (iter.next() == null) {
                throwIllegalArgumentException("The collection contains null element at index: %d", i);
            }
        }
        return iterable;
    }

    // ----- throw exception ----- begin
    private static void throwNullPointException(final String message, final Object... messageArgs) {
        throw new NullPointerException(String.format(message, messageArgs));
    }

    private static void throwIllegalArgumentException(final String message, final Object... messageArgs) {
        throw new IllegalArgumentException(String.format(message, messageArgs));
    }
    // ----- throw exception ----- end
}
