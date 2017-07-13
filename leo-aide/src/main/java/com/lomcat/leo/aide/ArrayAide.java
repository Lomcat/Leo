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

import java.lang.reflect.Array;

/**
 * TODO com.lomcat.leo.aide.ArrayAide
 *
 * @author Kuniel - kuniel@lomcat.com
 * @since 1.0.0
 */
public class ArrayAide {

    public static int length(final Object array) {
        return array == null ? 0 : Array.getLength(array);
    }

    public static boolean isEmpty(final Object[] array) {
        return length(array) == 0;
    }

    public static <T> boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }
}
