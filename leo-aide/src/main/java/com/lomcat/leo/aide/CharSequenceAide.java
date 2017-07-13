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

/**
 * 字符序列
 *
 * <pre>
 *     {@code null}: 空
 *     {@code non-null}: 非空
 *     {@code empty}: 空串，包括 {@code null}，如""、 null
 *     {@code non-empty}: 非空 且 非空串
 *     {@code blank}: 空白，包括 {@code empty} 和 {@code null}，如"  "、 ""、 null，空白字符由 {@link Character#isWhitespace(char)} 定义
 *     {@code non-blank}: 非空 且 非空串 且 非空白
 * </pre>
 *
 * @author Kuniel - kuniel@lomcat.com
 * @since 1.0.0
 */
public class CharSequenceAide {

    public final static int INDEX_NOT_FOUND = -1;

    CharSequenceAide() {}

    /**
     * <p>检查字符序列是否 {@code empty}</p>
     *
     * <pre>
     * CharSequenceAide.isEmpty(null)      = true
     * CharSequenceAide.isEmpty("")        = true
     * CharSequenceAide.isEmpty(" ")       = false
     * CharSequenceAide.isEmpty("bob")     = false
     * CharSequenceAide.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param sequence 一个字符序列
     * @return 当参数 {@code empty} 时返回 {@code true}， 否则 {@code false}
     */
    public static boolean isEmpty(final CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    /**
     * <p>检查字符序列是否 {@code non-empty}。</p>
     *
     * <pre>
     * CharSequenceAide.isNotEmpty(null)      = false
     * CharSequenceAide.isNotEmpty("")        = false
     * CharSequenceAide.isNotEmpty(" ")       = true
     * CharSequenceAide.isNotEmpty("bob")     = true
     * CharSequenceAide.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param sequence 一个字符序列
     * @return 当参数 {@code non-empty} 时返回 {@code false}，否则 {@code true}。
     */
    public static boolean isNotEmpty(final CharSequence sequence) {
        return !isEmpty(sequence);
    }

    /**
     * <p>检查指定字符序列数组中是否包含 {@code empty}</p>
     *
     * <pre>
     * CharSequenceAide.containEmpty(null)        = true
     * CharSequenceAide.containEmpty("")          = true
     * CharSequenceAide.containEmpty("", "a")     = true
     * CharSequenceAide.containEmpty(" ", "a")    = false
     * CharSequenceAide.containEmpty("", null)    = true
     * </pre>
     *
     * @param sequences 字符序列数组
     * @return 当指定数组中包含 {@code empty} 时返回 {@code true}，否则 {@code false}
     */
    public static boolean containEmpty(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return true;
        }
        for (final CharSequence sequence : sequences) {
            if (isEmpty(sequence)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>检查指定字符序列数组中是否包含 {@code non-empty}</p>
     *
     * <pre>
     * CharSequenceAide.containNonEmpty(null)        = false
     * CharSequenceAide.containNonEmpty("")          = false
     * CharSequenceAide.containNonEmpty("", "a")     = true
     * CharSequenceAide.containNonEmpty(" ", "a")    = true
     * CharSequenceAide.containNonEmpty("", null)    = false
     * </pre>
     *
     * @param sequences 字符序列数组
     * @return 当指定数组中包含 {@code non-empty} 时返回 {@code true}，否则 {@code false}
     */
    public static boolean containNonEmpty(final CharSequence... sequences) {
        return firstNonEmpty(sequences) != null;
    }

    /**
     * <p>检查字符序列数组中是否全为 {@code empty}</p>
     *
     * <pre>
     * CharSequenceAide.isAllEmpty(null)        = true
     * CharSequenceAide.isAllEmpty("", null)    = true
     * CharSequenceAide.isAllEmpty("", "a")     = false
     * CharSequenceAide.isAllEmpty(" ")         = false
     * </pre>
     *
     * @param sequences 字符序列数组
     * @return 若数组中的元素全为 {@code empty} 返回 {@code true}，否则 {@code false}
     */
    public static boolean isAllEmpty(final CharSequence... sequences) {
        return firstNonEmpty(sequences) == null;
    }

    /**
     * <p>检查字符序列数组中是否全部 {@code non-empty}</p>
     *
     * <pre>
     * CharSequenceAide.isAllEmpty(null)        = false
     * CharSequenceAide.isAllEmpty("", null)    = false
     * CharSequenceAide.isAllEmpty("", "a")     = false
     * CharSequenceAide.isAllEmpty(" ")         = true
     * CharSequenceAide.isAllEmpty(" ", "a")    = true
     * </pre>
     *
     * @param sequences 字符序列数组
     * @return 若数组中的元素全部 {@code non-empty} 返回 {@code true}，否则 {@code false}
     */
    public static boolean isAllNonEmpty(final CharSequence... sequences) {
        return !containEmpty(sequences);
    }

    /**
     * <p>返回数组中第一个 {@code non-empty} 的字符串。</p>
     *
     * <p>如果数组为 {@code null} 或 empty 或数组中没有 {@code non-empty} 字符串，则返回 {@code null}。</p>
     *
     * <pre>
     * CharSequenceAide.firstNonEmpty(null, null)      = null
     * CharSequenceAide.firstNonEmpty(null, "")        = null
     * CharSequenceAide.firstNonEmpty(null, null, "")  = null
     * CharSequenceAide.firstNonEmpty(null, "zz")      = "zz"
     * CharSequenceAide.firstNonEmpty("abc", *)        = "abc"
     * CharSequenceAide.firstNonEmpty("", "xyz", *)    = "xyz"
     * CharSequenceAide.firstNonEmpty()                = null
     * </pre>
     *
     * @param sequences 要检验的值，可以为 {@code null} 或 empty
     * @param <T> 数组元素的类型
     * @return {@code values} 中第一个 {@code non-empty} 的值，或 {@code null}
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonEmpty(final T... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (final T cs : sequences) {
                if (isNotEmpty(cs)) {
                    return cs;
                }
            }
        }
        return null;
    }

    /**
     * <p>检查字符序列是否为 {@code blank} </p>
     *
     * <pre>
     * CharSequenceAide.isBlank("")     = true
     * CharSequenceAide.isBlank("\t")   = true
     * CharSequenceAide.isBlank(" ")    = true
     * CharSequenceAide.isBlank("\n")   = true
     * CharSequenceAide.isBlank("\r")   = true
     * CharSequenceAide.isBlank("a")    = false
     * CharSequenceAide.isBlank(null)   = true
     * </pre>
     *
     * @param sequence 字符序列
     * @return 字符序列为 {@code blank} 时返回 {@code true}，否则 {@code false}
     */
    public static boolean isBlank(final CharSequence sequence) {
        int strLen;
        if (sequence == null || (strLen = sequence.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i ++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>检查字符序列是否为 {@code non-blank} </p>
     *
     * @param sequence 字符序列
     * @return 若字符序列 {@code non-blank} 则返回 {@code true}，否则 {@code false}
     */
    public static boolean isNotBlank(final CharSequence sequence) {
        return !isBlank(sequence);
    }

    /**
     * <p>检查多个字符序列中是否包含 {@code blank} </p>
     *
     * @param sequences 字符序列数组
     * @return 若包含 {@code blank} 返回 {@code true}，否则 {@code false}
     */
    public static boolean containBlank(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return true;
        }
        for (final CharSequence sequence : sequences) {
            if (isBlank(sequence)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>检查多个字符序列中是否包含 {@code non-blank} </p>
     *
     * @param sequences 字符序列数组
     * @return 若包含 {@code non-blank} 返回 {@code true}，否则 {@code false}
     */
    public static boolean containNonBlank(final CharSequence... sequences) {
        return firstNonBlank(sequences) != null;
    }

    /**
     * <p>检查多个字符序列是否全为 {@code blank} </p>
     *
     * @param sequences 字符序列数组
     * @return 若多个字符序列全部为 {@code blank} 则返回 {@code true}，否则 {@code false}
     */
    public static boolean isAllBlank(final CharSequence... sequences) {
        return firstNonBlank(sequences) == null;
    }

    /**
     * <p>检查多个字符序列是否全都 {@code non-blank} </p>
     *
     * @param sequences 字符序列数组
     * @return 若全都 {@code non-blank} 返回 {@code true}，否则 {@code false}
     */
    public static boolean isAllNonBlank(final CharSequence... sequences) {
        return !containBlank(sequences);
    }

    /**
     * <p>返回数组中第一个 {@code non-blank} 的字符串</p>
     *
     * <p>如果数组为 {@code null} 或 empty 或数组中没有非空白字符串，则返回 {@code null}。</p>
     *
     * <pre>
     * CharSequenceAide.firstNonEmpty(null, null)      = null
     * CharSequenceAide.firstNonEmpty(null, "")        = null
     * CharSequenceAide.firstNonEmpty(null, null, "")  = null
     * CharSequenceAide.firstNonEmpty(null, "zz")      = "zz"
     * CharSequenceAide.firstNonEmpty("abc", *)        = "abc"
     * CharSequenceAide.firstNonEmpty("", "xyz", *)    = "xyz"
     * CharSequenceAide.firstNonEmpty()                = null
     * </pre>
     *
     * @param sequences 要检验的值，可以为 {@code null} 或 empty
     * @param <T> 数组元素的类型
     * @return {@code values} 中第一个 {@code non-blank} 的值，或 {@code null}
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonBlank(final T... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (final T sequence : sequences) {
                if (isNotBlank(sequence)) {
                    return sequence;
                }
            }
        }
        return null;
    }

    /**
     * 获取字符序列的长度，若字符序列为 {@code null} 则返回 0
     *
     * @param sequence 字符序列，可以为 {@code null}
     * @return 字符序列长度或0
     */
    public static int length(final CharSequence sequence) {
        return sequence == null ? 0 : sequence.length();
    }

    /**
     * <p>比较两个字符序列的相等性。</p>
     *
     * <pre>
     * CharSequenceAide.equals(null, null)   = true
     * CharSequenceAide.equals(null, "abc")  = false
     * CharSequenceAide.equals("abc", null)  = false
     * CharSequenceAide.equals("abc", "abc") = true
     * CharSequenceAide.equals("abc", "ABC") = false
     * </pre>
     *
     * @param sequence1 第一个字符序列
     * @param sequence2 第二个字符序列
     * @return 如果两个字符序列相等，则返回 {@code true}；否则返回 {@code false}。
     */
    public static boolean equals(final CharSequence sequence1, final CharSequence sequence2) {
        if (sequence1 == sequence2) {
            return true;
        }
        if (sequence1 == null || sequence2 == null) {
            return false;
        }
        if (sequence1.length() != sequence2.length()) {
            return false;
        }
        if (sequence1 instanceof String && sequence2 instanceof String) {
            return sequence1.equals(sequence2);
        }
        return regionMatches(false, sequence1, 0, sequence2, 0, sequence1.length());
    }

    /**
     * <p>比较两个字符序列的相等性，忽略大小写。</p>
     *
     * <pre>
     * CharSequenceAide.equalsIgnoreCase(null, null)   = true
     * CharSequenceAide.equalsIgnoreCase(null, "abc")  = false
     * CharSequenceAide.equalsIgnoreCase("abc", null)  = false
     * CharSequenceAide.equalsIgnoreCase("abc", "abc") = true
     * CharSequenceAide.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param sequence1 第一个字符序列
     * @param sequence2 第二个字符序列
     * @return 如果两个字符序列相等（忽略大小写），则返回 {@code true}；否则返回 {@code false}。
     */
    public static boolean equalsIgnoreCase(final CharSequence sequence1, final CharSequence sequence2) {
        return sequence1 == sequence2
                ||
                (
                    !(sequence1 == null || sequence2 == null)
                        && sequence1.length() == sequence2.length()
                        && regionMatches(true, sequence1, 0, sequence2, 0, sequence1.length())
                );
    }

    /**
     * <p>比较两个字符序列 {@code sequence1} 和 {@code sequence2} 指定区域，如果表示相同的字符序列，则结果为 {@code true}。</p>
     *
     * <p>当 {@code ignoreCase} 为 {@code true} 时忽略大小写。</p>
     *
     * <p>要比较的 {@code sequence1} 从索引 {@code offset1} 处开始，长度为 {@code length}，
     * 要比较的 {@code sequence2} 从索引 {@code offset2} 处开始，长度为 {@code length}。</p>
     *
     * <p>当 {@code offset1} 、 {@code offset2} 、 {@code length} 任一为负时，结果为 {@code false}。</p>
     *
     * <p>当 {@code offset1 + length} 大于 {@code sequence1} 的长度时，结果为 {@code false}。</p>
     *
     * <p>当 {@code offset2 + length} 大于 {@code sequence2} 的长度时，结果为 {@code false}。</p>
     *
     * @param ignoreCase 是否忽略大小写，true 忽略，false 不忽略
     * @param sequence1 要比较的第一个字符序列
     * @param offset1 第一个字符序列的开始索引
     * @param sequence2 要比较的第二个字符序列
     * @param offset2 第二个字符序列的开始索引
     * @param length 要比较的长度
     * @return 如果两个字符序列的指定区域想匹配，则返回 {@code true}，否则返回 {@code false}。
     */
    public static boolean regionMatches(final boolean ignoreCase, final CharSequence sequence1, final int offset1,
                                        final CharSequence sequence2, final int offset2, final int length) {
        if (sequence1 instanceof String && sequence2 instanceof String) {
            return ((String) sequence1).regionMatches(ignoreCase, offset1, (String) sequence2, offset2, length);
        }

        if (offset1 < 0 || offset2 < 0 || length < 0) {
            return false;
        }

        int index1 = offset1;
        int index2 = offset2;
        int tempLen = length;
        final int srcLen = sequence1.length() - offset1;
        final int otherLen = sequence2.length() - offset2;

        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tempLen-- > 0) {
            final char c1 = sequence1.charAt(index1++);
            final char c2 = sequence2.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>检查指定字符序列是否与数组中的任意元素相等。</p>
     *
     * <pre>
     * CharSequenceAide.equalsAny(null, (CharSequence[]) null)  = false
     * CharSequenceAide.equalsAny(null, null, null)             = true
     * CharSequenceAide.equalsAny(null, "abc", "def")           = false
     * CharSequenceAide.equalsAny("abc", null, "def")           = false
     * CharSequenceAide.equalsAny("abc", "abc", "def")          = true
     * CharSequenceAide.equalsAny("abc", "ABC", "DEF")          = false
     * </pre>
     *
     * @param sequence 字符序列
     * @param sequences 字符序列数组
     * @return 若 {@code cs} 与 {@code css} 中的任意一个元素相等，则返回 true， 否则 false
     */
    public static boolean equalsAny(final CharSequence sequence, final CharSequence... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (CharSequence next : sequences) {
                if (equals(sequence, next)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>检查指定字符序列是否与数组中的任意元素相等（忽略大小写）。</p>
     *
     * <pre>
     * CharSequenceAide.equalsAnyIgnoreCase(null, (CharSequence[]) null)    = false
     * CharSequenceAide.equalsAnyIgnoreCase(null, null, null)               = true
     * CharSequenceAide.equalsAnyIgnoreCase(null, "abc", "def")             = false
     * CharSequenceAide.equalsAnyIgnoreCase("abc", null, "def")             = false
     * CharSequenceAide.equalsAnyIgnoreCase("abc", "abc", "def")            = true
     * CharSequenceAide.equalsAnyIgnoreCase("abc", "ABC", "DEF")            = true
     * </pre>
     *
     * @param sequence 字符序列
     * @param sequences 字符序列数组
     * @return 若 {@code cs} 与 {@code css} 中的任意一个元素相等（忽略大小写），则返回 true， 否则 false
     */
    public static boolean equalsAnyIgnoreCase(final CharSequence sequence, final CharSequence... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (CharSequence next : sequences) {
                if (equalsIgnoreCase(sequence, next)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>查找指定字符在字符序列中首次出现的索引。
     * 如果字符序列为 {@code null} 或 {@code empty}，则返回 {@link #INDEX_NOT_FOUND} (-1)。</p>
     *
     * <pre>
     * CharSequenceAide.indexOf(null, *)         = -1
     * CharSequenceAide.indexOf("", *)           = -1
     * CharSequenceAide.indexOf("aabaabaa", 'a') = 0
     * CharSequenceAide.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param sequence 字符序列
     * @param ch 要查找的字符
     * @return {@code ch} 在 {@code sequence} 中的第一个索引，
     *          若 {@code sequence} 为 {@code null} 或 {@code empty} 返回 -1，
     *          若 {@code sequence} 不包含 {@code ch} 返回 -1
     */
    public static int indexOf(final CharSequence sequence, final int ch) {
        return indexOf(sequence, ch, 0);
    }

    /**
     * <p>查找指定字符 {@code ch} 在字符序列 {@code sequence} 从指定位置 {@code fromIndex} 之后首次出现的索引。
     * 如果字符序列为 {@code null} 或 {@code empty}，则返回 {@link #INDEX_NOT_FOUND} (-1)。</p>
     *
     * <pre>
     * CharSequenceAide.indexOf(null, *, *)          = -1
     * CharSequenceAide.indexOf("", *, *)            = -1
     * CharSequenceAide.indexOf("aabaabaa", 'b', 0)  = 2
     * CharSequenceAide.indexOf("aabaabaa", 'b', 3)  = 5
     * CharSequenceAide.indexOf("aabaabaa", 'b', 9)  = -1
     * CharSequenceAide.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     *
     * @param sequence 字符序列
     * @param ch 要查找的字符
     * @param fromIndex 开始查找的位置，负数作 0 处理
     * @return {@code ch} 在 {@code sequence} 从 {@code fromIndex} 之后的第一个索引，
     *          若 {@code sequence} 为 {@code null} 或 {@code empty} 返回 -1，
     *          若 {@code sequence} 从 {@code fromIndex} 之后不包含 {@code ch} 返回 -1
     */
    public static int indexOf(final CharSequence sequence, final int ch, int fromIndex) {
        if (isEmpty(sequence)) {
            return INDEX_NOT_FOUND;
        }
        if (sequence instanceof String) {
            return ((String) sequence).indexOf(ch, fromIndex);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        for (int i = fromIndex; i < sequence.length(); i ++) {
            if (sequence.charAt(i) == ch) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>查找 {@code subSequence} 在 {@code sequence} 中首次出现的索引。
     * 若 {@code sequence} 或 {@code subSequence} 为 {@code null}，则返回 {@link #INDEX_NOT_FOUND} (-1)。</p>
     *
     * <pre>
     * CharSequenceAide.indexOf(null, *)          = -1
     * CharSequenceAide.indexOf(*, null)          = -1
     * CharSequenceAide.indexOf("", "")           = 0
     * CharSequenceAide.indexOf("", *)            = -1 (except when * = "")
     * CharSequenceAide.indexOf("aabaabaa", "a")  = 0
     * CharSequenceAide.indexOf("aabaabaa", "b")  = 2
     * CharSequenceAide.indexOf("aabaabaa", "ab") = 1
     * CharSequenceAide.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param sequence 字符序列
     * @param subSequence 要查找的字符序列
     * @return {@code subSequence} 在 {@code sequence} 中首次出现的索引，
     *          若 {@code sequence} 或 {@code subSequence} 为 {@code null}，返回 -1，
     *          若 {@code sequence} 不包含 {@code subSequence}，返回 -1
     */
    public static int indexOf(final CharSequence sequence, final CharSequence subSequence) {
        return indexOf(sequence, subSequence, 0);
    }

    /**
     * <p>查找 {@code subSequence} 在 {@code sequence} 从指定位置 {@code fromIndex} 之后首次出现的索引。
     * 若 {@code sequence} 或 {@code subSequence} 为 {@code null}，则返回 {@link #INDEX_NOT_FOUND} (-1)。</p>
     *
     * <pre>
     * CharSequenceAide.indexOf(null, *, *)          = -1
     * CharSequenceAide.indexOf(*, null, *)          = -1
     * CharSequenceAide.indexOf("", "", 0)           = 0
     * CharSequenceAide.indexOf("", *, 0)            = -1 (except when * = "")
     * CharSequenceAide.indexOf("aabaabaa", "a", 0)  = 0
     * CharSequenceAide.indexOf("aabaabaa", "b", 0)  = 2
     * CharSequenceAide.indexOf("aabaabaa", "ab", 0) = 1
     * CharSequenceAide.indexOf("aabaabaa", "b", 3)  = 5
     * CharSequenceAide.indexOf("aabaabaa", "b", 9)  = -1
     * CharSequenceAide.indexOf("aabaabaa", "b", -1) = 2
     * CharSequenceAide.indexOf("aabaabaa", "", 2)   = 2
     * CharSequenceAide.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param sequence 字符序列
     * @param subSequence 要查找的字符序列
     * @param fromIndex 开始查找的位置，负数作 0 处理
     * @return {@code subSequence} 在 {@code sequence} 从 {@code fromIndex} 之后首次出现的索引，
     *          若 {@code sequence} 或 {@code subSequence} 为 {@code null}，返回 -1，
     *          若 {@code sequence} 从 {@code fromIndex} 之后不包含 {@code subSequence}，返回 -1
     */
    public static int indexOf(final CharSequence sequence, final CharSequence subSequence, final int fromIndex) {
        if (sequence == null || subSequence == null) {
            return INDEX_NOT_FOUND;
        }
        return sequence.toString().indexOf(subSequence.toString(), fromIndex);
    }

    public static int lastIndexOf(final CharSequence sequence, final CharSequence subSequence, final int fromIndex) {
        return sequence.toString().lastIndexOf(subSequence.toString(), fromIndex);
    }

    /**
     * <p>查找 {@code subSequence} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引（查找方向根据 {@code lastIndex} 确定）。</p>
     *
     * @param sequence 字符序列
     * @param subSequence 要查找的字符序列
     * @param ordinal 字符序列出现的次序
     * @param lastIndex true: {@link #ordinalIndexOf(CharSequence, CharSequence, int)}; false: {@link #ordinalLastIndexOf(CharSequence, CharSequence, int)}
     * @return 索引值
     */
    public static int ordinalIndexOf(final CharSequence sequence, final CharSequence subSequence, final int ordinal, final boolean lastIndex) {
        if (sequence == null || subSequence == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (length(subSequence) == 0) {
            return lastIndex ? sequence.length() : 0;
        }
        int found = 0;
        int index = lastIndex ? sequence.length() : INDEX_NOT_FOUND;
        do {
            if (lastIndex) {
                index = lastIndexOf(sequence, subSequence, index - 1);
            } else {
                index = indexOf(sequence, subSequence, index + 1);
            }
            if (index < 0) {
                return index;
            }
            found ++;
        } while (found < ordinal);
        return index;
    }

    /**
     * <p>查找 {@code subSequence} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引（从 {@code sequence} 的开始位置向后查找）。</p>
     *
     * @param sequence 字符序列
     * @param subSequence 要查找的字符序列
     * @param ordinal 字符序列出现的次序
     * @return 索引值
     */
    public static int ordinalIndexOf(final CharSequence sequence, final CharSequence subSequence, final int ordinal) {
        return ordinalIndexOf(sequence, subSequence, ordinal, false);
    }

    /**
     * <p>查找 {@code subSequence} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引（从 {@code sequence} 的最后位置向前查找）。</p>
     *
     * @param sequence 字符序列
     * @param subSequence 要查找的字符序列
     * @param ordinal 字符序列出现的次序
     * @return 索引值
     */
    public static int ordinalLastIndexOf(final CharSequence sequence, final CharSequence subSequence, final int ordinal) {
        return ordinalIndexOf(sequence, subSequence, ordinal, true);
    }


}
