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

import com.lomcat.leo.aide.exception.CloneException;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * {@code Object} 操作
 *
 * @author Kuniel - kuniel@lomcat.com
 * @since 1.0.0
 */
public class ObjectAide {

    private ObjectAide() {}

    /**
     * <p>当 {@code null} 有多个意义时，用于表示 {@code null} 的占位符。</p>
     *
     * <p>
     *     例如，{@link java.util.HashMap HashMap} 中的 {@link java.util.HashMap#get(java.lang.Object) get(Object)} 方法返回 {@code null} 时，
     *     可能是这个Map中包含了一个 {@code null}，也可能是没有匹配的key，{@link NULL} 占位符即可用于区分这两种情况。
     * </p>
     *
     * <p>再如，{@link java.util.Hashtable Hashtable} 无法存储 {@code null}，可用 {@link NULL} 占位符表示 {@code null}。</p>
     */
    public static final NULL NULL = new NULL();

    /**
     * <p>判断对象是否为 {@code null}。</p>
     *
     * @param object 一个对象
     * @return 参数为 {@code null} 时返回 {@code true}，否则 {@code false}
     * @see Objects#isNull(Object)
     */
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    /**
     * <p>判断对象是否为 non-{@code null}。</p>
     *
     * @param object 一个对象
     * @return 参数为 non-{@code null} 时返回 {@code true}，否则 {@code false}
     * @see Objects#nonNull(Object)
     */
    public static boolean isNotNull(Object object) {
        return Objects.nonNull(object);
    }

    /**
     * <p>检查参数中是否包含 {@code null}。</p>
     *
     * <p>如果参数为 {@code null} 或 empty 或参数中的值全为 non-{@code null} 时返回 {@code false}，否则返回 {@code true}。</p>
     *
     * <pre>
     *     ObjectAide.containNull(*)                = false
     *     ObjectAide.containNull(*, null)          = true
     *     ObjectAide.containNull(null, *)          = true
     *     ObjectAide.containNull(null, null, *, *)     = true
     *     ObjectAide.containNull(null)             = true
     *     ObjectAide.containNull(null, null)       = true
     * </pre>
     *
     * @param values 要检查的值
     * @return 参数中至少包含一个 {@code null} 的值时返回 {@code true}；
     *          参数为 {@code null} 或 {@code empty} 或全为 non-{@code null} 时返回 {@code false}
     */
    public static boolean containNull(final Object... values) {
        if (ArrayAide.isNotEmpty(values)) {
            for (final Object value : values) {
                if (value == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>检查参数中是否包含 non-{@code null} 的值。</p>
     *
     * <p>如果参数为 {@code null} 或 empty 或参数中的值全为 {@code null} 时返回 {@code false}，否则返回 {@code true}。</p>
     *
     * <pre>
     *     ObjectAide.containNonNull(*)                = true
     *     ObjectAide.containNonNull(*, null)          = true
     *     ObjectAide.containNonNull(null, *)          = true
     *     ObjectAide.anyNotNull(null, null, *, *)     = true
     *     ObjectAide.containNonNull(null)             = false
     *     ObjectAide.containNonNull(null, null)       = false
     * </pre>
     *
     * @param values 要检查的值
     * @return 参数中至少包含一个 non-{@code null} 的值时返回 {@code true}；
     *          参数为 {@code null} 或 {@code empty} 或全为 {@code null} 时返回 {@code false}
     */
    public static boolean containNonNull(final Object... values) {
        return firstNonNull(values) != null;
    }

    /**
     * <p>检查参数中的值是否全部为 {@code null}。</p>
     *
     * @param values 要检查的值
     * @return 全为 {@code null} 时返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isAllNull(final Object... values) {
        return firstNonNull(values) == null;
    }

    /**
     * <p>检查参数中的值是否全部为 non-{@code null}。
     *
     * @param values 要检查的值
     * @return 不包含 {@code null} 时返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isAllNonNull(final Object... values) {
        return !containNull(values);
    }

    /**
     * <p>返回数组中第一个非 {@code null} 的元素。</p>
     *
     * <p>如果数组为 {@code null} 或 empty 或数组中没有非 {@code null} 的值，则返回 {@code null}。</p>
     *
     * <pre>
     *     ObjectAide.firstNonNull(null, null)      = null
     *     ObjectAide.firstNonNull(null, "")        = ""
     *     ObjectAide.firstNonNull(null, null, "")  = ""
     *     ObjectAide.firstNonNull(null, "zz")      = "zz"
     *     ObjectAide.firstNonNull("abc", *)        = "abc"
     *     ObjectAide.firstNonNull(null, "xyz", *)  = "xyz"
     *     ObjectAide.firstNonNull(Boolean.TRUE, *) = Boolean.TRUE
     *     ObjectAide.firstNonNull()                = null
     * </pre>
     *
     * @param values 要检验的值，可以为 {@code null} 或 empty
     * @param <T> 数组元素的类型
     * @return {@code values} 中第一个非 {@code null} 的值，或 {@code null}
     */
    @SafeVarargs
    public static <T> T firstNonNull(final T... values) {
        if (ArrayAide.isNotEmpty(values)) {
            for (final T value : values) {
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * <p>如果参数相等则返回 {@code true}，否则返回 {@code false}。</p>
     *
     * <p>
     *     如果参数都为 {@code null}，则返回 {@code true}；
     *     如果只有一个参数为 {@code null}，则返回 {@code false}；
     *     如果参数都不为 {@code null}，则使用第一个参数的 {@link Object#equals(Object) equals} 方法进行比较并返回。
     * </p>
     *
     * <pre>
     *     ObjectAide.equals(null, null)                  = true
     *     ObjectAide.equals(null, "")                    = false
     *     ObjectAide.equals("", null)                    = false
     *     ObjectAide.equals("", "")                      = true
     *     ObjectAide.equals(Boolean.TRUE, null)          = false
     *     ObjectAide.equals(Boolean.TRUE, "true")        = false
     *     ObjectAide.equals(Boolean.TRUE, Boolean.TRUE)  = true
     *     ObjectAide.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     *
     * @param a 一个对象
     * @param b 一个与 {@code a} 进行相等性比较的对象
     * @return 相等为 {@code true}，否则 {@code false}
     * @see Objects#equals(Object, Object)
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * <p>与 {@link #equals(Object, Object)} 相反。</p>
     *
     * <pre>
     *     ObjectAide.notEquals(null, null)                  = false
     *     ObjectAide.notEquals(null, "")                    = true
     *     ObjectAide.notEquals("", null)                    = true
     *     ObjectAide.notEquals("", "")                      = false
     *     ObjectAide.notEqual(Boolean.TRUE, null)          = true
     *     ObjectAide.notEquals(Boolean.TRUE, "true")        = true
     *     ObjectAide.notEquals(Boolean.TRUE, Boolean.TRUE)  = false
     *     ObjectAide.notEquals(Boolean.TRUE, Boolean.FALSE) = true
     * </pre>
     *
     * @param a 一个对象
     * @param b 一个与 {@code a} 进行比较的对象
     * @return 不相等为 {@code true}，相等为 {@code false}
     * @see #equals(Object, Object)
     */
    public static boolean notEquals(Object a, Object b) {
        return !equals(a, b);
    }

    /**
     * <p>如果参数深层相等则返回 {@code true}，否则返回 {@code false}。两个 null 值深层相等。</p>
     *
     * <p>
     *     如果两个参数为数组，则使用 {@link Arrays#deepEquals(Object[], Object[]) Arrays.deepEquals} 方法比较。
     *     否则使用第一个参数的 {@link Object#equals equals} 方法比较。
     * </p>
     *
     * @param a 一个对象
     * @param b 一个与 {@code a} 进行比较的独享
     * @return 深层相等为 {@code true}，否则 {@code false}
     * @see Objects#deepEquals(Object, Object)
     */
    public static boolean deepEquals(Object a, Object b) {
        return Objects.deepEquals(a, b);
    }

    /**
     * <p>与 {@link #deepEquals(Object, Object)} 相反。</p>
     *
     * @param a 一个对象
     * @param b 一个与 {@code a} 进行比较的对象
     * @return 非深层相等为 {@code true}，深层相等为 {@code false}
     * @see #deepEquals(Object, Object)
     */
    public static boolean notDeepEquals(Object a, Object b) {
        return !deepEquals(a, b);
    }

    /**
     * <p>比较多个对象是否相等。</p>
     *
     * <pre>
     *     ObjectAide.allEquals(null)                        = true
     *     ObjectAide.allEquals()                            = true
     *     ObjectAide.allEquals("a")                         = true
     *     ObjectAide.allEquals(null, null)                  = true
     *     ObjectAide.allEquals(null, "")                    = false
     *     ObjectAide.allEquals("a", "a")                    = true
     *     ObjectAide.allEquals("a", "b")                    = false
     *     ObjectAide.allEquals(Boolean.TRUE, true)          = true
     *     ObjectAide.allEquals(Boolean.TRUE, "true")        = false
     *     ObjectAide.allEquals(Boolean.TRUE, Boolean.TRUE)  = true
     *     ObjectAide.allEquals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     *
     * @param values 要比较的对象
     * @return 若所有对象均相等，则返回 {@code true}，否则返回 {@code false}，
     * 			values 为 {@code null} 或  empty 或只有一个对象时返回 {@code true}
     */
    public static boolean allEquals(final Object... values) {
        if (ArrayAide.length(values) <= 1) {
            return true;
        }
        for (int i = 0; i < values.length; i ++) {
            if (notEquals(values[i], values[i + 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>如果传递的object为 {@code null}，则返回指定的默认值。</p>
     *
     * <pre>
     *     ObjectAide.defaultIfNull(null, null)      = null
     *     ObjectAide.defaultIfNull(null, "")        = ""
     *     ObjectAide.defaultIfNull(null, "zz")      = "zz"
     *     ObjectAide.defaultIfNull("abc", *)        = "abc"
     *     ObjectAide.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     *     ObjectAide.defaultIfNull(ObjectAide.NULL, *) = ObjectAide.NULL
     * </pre>
     *
     * @param <T> 所传递的value的类型
     * @param value 要判断的对象
     * @param defaultValue 当object为 {@code null} 时返回的默认值
     * @return 当所传递的object为 {@code null} 时，返回defaultValue，否则直接返回object
     */
    public static <T> T defaultIfNull(final T value, final T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * <p>参数为 {@code null} 时返回 0，否则返回其 hash code。</p>
     *
     * @param object 一个对象
     * @return hash code 或 0
     * @see Objects#hashCode(Object)
     */
    public static int hashCode(Object object) {
        return Objects.hashCode(object);
    }

    /**
     * <p>为多个参数生成一个 hash code。</p>
     *
     * @param objects 一个或多个对象
     * @return hash code
     * @see Objects#hash(Object...)
     */
    public static int hash(Object... objects) {
        return Objects.hash(objects);
    }

    /**
     * <p>参数为 {@code null} 时返回 {@code "null"}，否则调用其 {@code toString} 方法返回。</p>
     *
     * @param object 一个对象
     * @return {@code "null"} 或 参数的 {@code toString} 方法返回值
     * @see Objects#toString(Object)
     */
    public static String toString(Object object) {
        return Objects.toString(object);
    }

    /**
     * <p>第一个参数为 {@code null} 时返回第二个参数指定的值，否则使用第一个参数的 {@code toString} 方法返回。</p>
     *
     * @param object 一个对象
     * @param nullDefault 当 {@code object} 为 {@code null} 时的默认值
     * @return {@code nullDefault} 或 {@code object} 的 {@code toString}
     * @see Objects#toString(Object, String)
     */
    public static String toString(Object object, String nullDefault) {
        return Objects.toString(object, nullDefault);
    }

    /**
     * <p>如果参数 {@code a} 和 {@code b} 相等（==），则返回 0，否则使用参数 {@code c} 指定的比较器进行运算并返回。</p>
     *
     * @param a 一个对象
     * @param b 一个与 {@code a} 进行比较的对象
     * @param c 比较器
     * @param <T> {@code a} 和 {@code b} 的类型
     * @return 0 或 使用 {@code c} 的比较结果
     * @see Objects#compare(Object, Object, Comparator)
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return Objects.compare(a, b ,c);
    }

    /**
     * <p>比较两个对象的大小，{@code null} 小于 non-{@code null}。</p>
     *
     * @param a 一个可比较的对象
     * @param b 另一个可比较的对象
     * @param <T> 参数类型
     * @return 如果 a &lt; b 返回负数；如果 a = b 返回 0；如果 a &gt; b 返回正数
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b) {
        return compare(a, b, false);
    }

    /**
     * <p>比较两个对象的大小。</p>
     *
     * @param a 一个可比较的对象
     * @param b 另一个可比较的对象
     * @param nullGreater 如果为 {@code true} 则认为 {@code null} 大于 non-{@code null} ，反之 non-{@code null} 大于 {@code null}。
     * @param <T> 参数类型
     * @return 如果 a &lt; b 返回负数；如果 a = b 返回 0；如果 a &gt; b 返回正数
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b, boolean nullGreater) {
        if (a == b) {
            return 0;
        } else if (a == null) {
            return nullGreater ? 1 : -1;
        } else if (b == null) {
            return nullGreater ? -1 : 1;
        }
        return a.compareTo(b);
    }

    /**
     * <p>返回参数中的最小对象。</p>
     *
     * @param values 可比较的对象集
     * @param <T> 参数类型
     * @return
     *  <ul>
     *      <li>如果所有对象都 non-{@code null} 且不相等，返回最小对象</li>
     *      <li>如果所有对象都 non-{@code null} 且相等，返回第一个对象</li>
     *      <li>如果存在 {@code null} 对象，则 non-{@code null} 对象较小</li>
     *      <li>如果所有对象都为 {@code null}，则返回 {@code null}</li>
     *  </ul>
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> T min(final T... values) {
        T result = null;
        if (ArrayAide.isNotEmpty(values)) {
            for (final T value : values) {
                if (compare(value, result, true) < 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    /**
     * <p>返回参数中的最大对象。</p>
     *
     * @param values 可比较的对象集
     * @param <T> 参数类型
     * @return
     *  <ul>
     *      <li>如果所有对象都 non-{@code null} 且不相等，返回最大对象</li>
     *      <li>如果所有对象都 non-{@code null} 且相等，返回第一个对象</li>
     *      <li>如果存在 {@code null} 对象，则 non-{@code null} 对象较大</li>
     *      <li>如果所有对象都为 {@code null}，则返回 {@code null}</li>
     *  </ul>
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> T max(final T... values) {
        T result = null;
        if (ArrayAide.isNotEmpty(values)) {
            for (final T value : values) {
                if (compare(value, result, false) > 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    /**
     * <p>获取对象默认的 {@code toString} 方法计算的文本，当参数为 {@code null} 时返回 {@code null}。</p>
     *
     * 格式为：
     * <pre>
     *     object.getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre>
     *
     * <pre>
     *     ObjectAide.identityString(null)         = null
     *     ObjectAide.identityString("")           = "java.lang.String@4ee285c6"
     *     ObjectAide.identityString(Boolean.TRUE) = "java.lang.Boolean@5b80350b"
     * </pre>
     *
     * @param object 一个对象
     * @return 对象默认 {@code toString} 文本
     */
    public static String defaultIdentity(final Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName() + '@' + Integer.toHexString(hashCode(object));
    }

    /**
     * <p>克隆对象</p>
     *
     * @param src 要克隆的源对象，当为 {@code null} 时返回 {@code null}
     * @param <T> 对象类型
     * @return 如果对象实现了 {@link Cloneable} 则返回其克隆，否则返回 {@code null}
     * @throws CloneException 当对象可克隆（cloneable）但克隆失败时抛出此异常
     */
    public static <T> T clone(final T src) throws CloneException {
        if (src instanceof Cloneable) {
            final Object result;
            if (src.getClass().isArray()) {
                final Class<?> componentType = src.getClass().getComponentType();
                if (!componentType.isPrimitive()) {
                    result = ((Object[]) src).clone();
                } else {
                    int length = Array.getLength(src);
                    result = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(result, length, Array.get(src, length));
                    }
                }
            } else {
                try {
                    final Method clone = src.getClass().getMethod("clone");
                    result = clone.invoke(src);
                } catch (NoSuchMethodException e) {
                    throw new CloneException("Cloneable type " + src.getClass().getName() + " has no clone method", e);
                } catch (IllegalAccessException e) {
                    throw new CloneException("Cannot clone Cloneable type " + src.getClass().getName(), e);
                } catch (InvocationTargetException e) {
                    throw new CloneException("Exception cloning Cloneable type " + src.getClass().getName(), e.getCause());
                }
            }
            @SuppressWarnings("unchecked")
            final T checked = (T) result;
            return checked;
        }
        return null;
    }

    /**
     * <p>直接将提供的参数返回，可以防止 javac 内联常量字段。</p>
     *
     * 如：
     * <pre>
     *     public final static String MAGIC_STRING = ObjectAide.CONST("abc");
     * </pre>
     *
     * <p>如果字段的值将来发生更改，则引用此字段的任何 jar 都不必重新编译。</p>
     *
     *
     * @param value 要返回的值
     * @param <T> 参数类型
     * @return 直接将提供的参数返回
     */
    public static <T> T CONST(final T value) {
        return value;
    }

    // ----- Inner classes ----- begin
    /**
     * 当 {@code null} 有多个意义时，用于表示 {@code null} 的占位符。
     *
     * <p>
     *     例如，{@link java.util.HashMap HashMap} 中的 {@link java.util.HashMap#get(java.lang.Object) get(Object)} 方法返回 {@code null} 时，
     *     可能是这个Map中包含了一个 {@code null}，也可能是没有匹配的key，{@link NULL} 占位符即可用于区分这两种情况。
     * </p>
     *
     * <p>再如，{@link java.util.Hashtable Hashtable} 无法存储 {@code null}，可用 {@link NULL} 占位符表示 {@code null}。</p>
     *
     * <p>单例，可序列化。</p>
     */
    public static class NULL implements Serializable {
        private static final long serialVersionUID = -3624154868651403387L;

        private NULL() {
            super();
        }
    }
    // ----- Inner classes ----- end
}
