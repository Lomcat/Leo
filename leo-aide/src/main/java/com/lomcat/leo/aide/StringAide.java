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
 * 字符串操作
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
public class StringAide extends CharSequenceAide {

    public static final String EMPTY = "";

    private StringAide() {
        super();
    }

    /**
     * 将字符串去掉首尾的空白字符后返回，若字符串为 {@code null} 则返回 {@code null}
     *
     * <pre>
     * StringAide.trim(null)          = null
     * StringAide.trim("")            = ""
     * StringAide.trim("     ")       = ""
     * StringAide.trim("abc")         = "abc"
     * StringAide.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str 字符串，可以为 {@code null}
     * @return 去掉首尾空白字符后的字符串，或 {@code null}
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 将字符串去掉首尾的空白字符后返回，若字符串为 {@code null} 或 {@code empty} 则返回 {@code null}
     *
     * <pre>
     * StringAide.trimToNull(null)          = null
     * StringAide.trimToNull("")            = null
     * StringAide.trimToNull("     ")       = null
     * StringAide.trimToNull("abc")         = "abc"
     * StringAide.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * @param str 字符串，可以为 {@code null}
     * @return 去掉首尾空白字符后的字符串，或 {@code null}
     */
    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * 将字符串去掉首尾的空白字符后返回，若字符串为 {@code null} 或 {@code empty} 则返回 {@code empty}
     *
     * <pre>
     * StringAide.trimToEmpty(null)          = ""
     * StringAide.trimToEmpty("")            = ""
     * StringAide.trimToEmpty("     ")       = ""
     * StringAide.trimToEmpty("abc")         = "abc"
     * StringAide.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str 字符串，可以为 {@code null}
     * @return 去掉首尾空白字符后的字符串，或 {@code empty}
     */
    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * <p>去掉字符串中的所有空白字符</p>
     *
     * @param str 字符串，可以为 {@code null}
     * @return 去掉空白字符后的字符串，或 {@code null}
     */
    public static String trims(final String str) {
        if (str == null) {
            return null;
        }
        StringBuilder ts = new StringBuilder(trim(str));
        if (isEmpty(ts)) {
            return EMPTY;
        }
        for (int i = 0; i < ts.length(); i++) {
            if (Character.isWhitespace(ts.charAt(i))) {
                ts.deleteCharAt(i);
            }
        }
        return ts.toString();
    }

    /**
     * <p>去掉字符串中的所有空白字符，若字符串为 {@code null} 或 {@code empty} 则返回 {@code null}</p>
     *
     * @param str 字符串，可以为 {@code null}
     * @return 去掉空白字符后的字符串，或 {@code null}
     */
    public static String trimsToNull(final String str) {
        final String ts = trims(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <p>去掉字符串中的所有空白字符，若字符串为 {@code null} 或 {@code empty} 则返回 {@code empty}</p>
     *
     * @param str 字符串，可以为 {@code null}
     * @return 去掉空白字符后的字符串，或 {@code empty}
     */
    public static String trimsToEmpty(final String str) {
        return str == null ? EMPTY : trims(str);
    }

    /**
     * <p>截取字符串，等同于 StringAide.truncate(str, 0, maxLength)</p>
     *
     * @param str 字符串，可为 {@code null}
     * @param maxLength 截取的最大长度
     * @return 截取后的字符串
     * @see #truncate(String, int, int)
     */
    public static String truncate(final String str, int maxLength) {
        return truncate(str, 0, maxLength);
    }

    /**
     * <p>截取字符串</p>
     *
     * <ul>
     *     <li>当字符串为 {@code null} 时返回 {@code null}</li>
     *     <li>当 {@code offset} 小于 0 时，作 0 处理</li>
     *     <li>当 {@code offset} 大于字符串长度时返回 {@code empty}</li>
     *     <li>当 {@code maxLength} 小于 0 时返回 {@code null}</li>
     *     <li>当 {@code maxLength} 大于字符串长度时则取到字符串最后一位</li>
     *     <li>当截取范围（{@code offset} + {@code maxLength}）超出字符串长度时则取到字符串最后一位</li>
     * </ul>
     *
     * <pre>
     * StringAide.truncate(null, 0, 0)                                                  = null
     * StringAide.truncate(null, 2, 4)                                                  = null
     * StringAide.truncate("", 0, 10)                                                   = ""
     * StringAide.truncate("", 2, 10)                                                   = ""
     * StringAide.truncate("abcdefghij", 0, 3)                                          = "abc"
     * StringAide.truncate("abcdefghij", 5, 6)                                          = "fghij"
     * StringAide.truncate("raspberry peach", 10, 15)                                   = "peach"
     * StringAide.truncate("abcdefghijklmno", 0, 10)                                    = "abcdefghij"
     * StringAide.truncate("abcdefghijklmno", -1, 10)                                   = "abcdefghij"
     * StringAide.truncate("abcdefghijklmno", Integer.MIN_VALUE, 10)                    = "abcdefghij"
     * StringAide.truncate("abcdefghijklmno", Integer.MIN_VALUE, Integer.MAX_VALUE)     = "abcdefghijklmno"
     * StringAide.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE)                     = "abcdefghijklmno"
     * StringAide.truncate("abcdefghijklmno", 1, 10)                                    = "bcdefghijk"
     * StringAide.truncate("abcdefghijklmno", 2, 10)                                    = "cdefghijkl"
     * StringAide.truncate("abcdefghijklmno", 5, 5)                                     = "fghij"
     * StringAide.truncate("abcdefghijklmno", 5, 3)                                     = "fgh"
     * StringAide.truncate("abcdefghijklmno", 10, 3)                                    = "klm"
     * StringAide.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE)                    = "klmno"
     * StringAide.truncate("abcdefghijklmno", 13, 1)                                    = "n"
     * StringAide.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE)                    = "no"
     * StringAide.truncate("abcdefghijklmno", 14, 1)                                    = "o"
     * StringAide.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE)                    = "o"
     * StringAide.truncate("abcdefghijklmno", 15, 1)                                    = ""
     * StringAide.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE)                    = ""
     * StringAide.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE)     = ""
     * StringAide.truncate("abcdefghij", 3, -1)                                         = null
     * StringAide.truncate("abcdefghij", -2, 4)                                         = "abcd"
     * </pre>
     *
     * @param str 字符串，可为 {@code null}
     * @param offset 要截取的起始位置
     * @param maxLength 截取的最大长度
     * @return 截取后的字符串
     */
    public static String truncate(final String str, int offset, int maxLength) {
        if (str == null || maxLength < 0) {
            return null;
        }
        offset = Math.max(offset, 0);
        if (offset > str.length()) {
            return EMPTY;
        }
        if (maxLength >= str.length()) {
            return str.substring(offset);
        }
        int endIndex = Math.min(offset + maxLength, str.length());
        return str.substring(offset, endIndex);
    }

    /**
     * <p>将指定字符集 {@code stripChars} 中的字符从字符串左侧移除</p>
     *
     * <p>当 {@code stripChars} 为 {@code null} 时，则作移除空白字符处理。</p>
     *
     * <p>若字符串 {@code str} 为 {@code null} 则返回 {@code null}，
     * 若为 {@code empty} 则返回 {@code empty}。</p>
     *
     * <pre>
     * StringAide.stripStart(null, *)          = null
     * StringAide.stripStart("", *)            = ""
     * StringAide.stripStart("abc", "")        = "abc"
     * StringAide.stripStart("abc", null)      = "abc"
     * StringAide.stripStart("  abc", null)    = "abc"
     * StringAide.stripStart("abc  ", null)    = "abc  "
     * StringAide.stripStart(" abc ", null)    = "abc "
     * StringAide.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str 要处理的字符串，可以为 {@code null}
     * @param stripChars 要移除的字符集
     * @return 处理后的字符串，或 {@code null}
     */
    public static String stripStart(final String str, final String stripChars) {
        int strLength = length(str);
        if (strLength == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start < strLength && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (start < strLength && stripChars.indexOf(str.charAt(start)) != -1) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * <p>将指定字符集 {@code stripChars} 中的字符从字符串右侧移除</p>
     *
     * <p>当 {@code stripChars} 为 {@code null} 时，则作移除空白字符处理。</p>
     *
     * <p>若字符串 {@code str} 为 {@code null} 则返回 {@code null}，
     * 若为 {@code empty} 则返回 {@code empty}。</p>
     *
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str 要处理的字符串，可以为 {@code null}
     * @param stripChars 要移除的字符集
     * @return 处理后的字符串，或 {@code null}
     */
    public static String stripEnd(final String str, final String stripChars) {
        int endIndex = length(str);
        if (endIndex == 0) {
            return str;
        }
        if (stripChars == null) {
            while (endIndex != 0 && Character.isWhitespace(str.charAt(endIndex - 1))) {
                endIndex--;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (endIndex != 0 && stripChars.indexOf(str.charAt(endIndex - 1)) != -1) {
                endIndex--;
            }
        }
        return str.substring(0, endIndex);
    }

    /**
     * <p>将指定字符集 {@code stripChars} 中的字符从字符串两端移除</p>
     *
     * <p>当 {@code stripChars} 为 {@code null} 时，则作移除空白字符处理。</p>
     *
     * <p>若字符串 {@code str} 为 {@code null} 则返回 {@code null}，
     * 若为 {@code empty} 则返回 {@code empty}。</p>
     *
     * @param str 要处理的字符串，可以为 {@code null}
     * @param stripChars 要移除的字符集
     * @return 处理后的字符串，或 {@code null}
     */
    public static String strip(String str, final String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        return stripEnd(stripStart(str, stripChars), stripChars);
    }

    /**
     * <p>按字典顺序比较两个字符串。</p>
     *
     * <pre>
     * StringAide.compare(null, null, *)     = 0
     * StringAide.compare(null , "a", true)  &lt; 0
     * StringAide.compare(null , "a", false) &gt; 0
     * StringAide.compare("a", null, true)   &gt; 0
     * StringAide.compare("a", null, false)  &lt; 0
     * StringAide.compare("abc", "abc", *)   = 0
     * StringAide.compare("a", "b", *)       &lt; 0
     * StringAide.compare("b", "a", *)       &gt; 0
     * StringAide.compare("a", "B", *)       &gt; 0
     * StringAide.compare("ab", "abc", *)    &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @param nullIsLess 如果为 true 则 {@code null} 小于 {@code non-null}
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @see #compare(String, String)
     */
    public static int compare(final String str1, final String str2, final boolean nullIsLess) {
        if (equals(str1, str2)) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.compareTo(str2);
    }

    /**
     * <p>按字典顺序比较两个字符串（{@code null} 小于 {@code non-null}）。</p>
     *
     * <pre>
     * StringAide.compare(null, null)   = 0
     * StringAide.compare(null , "a")   &lt; 0
     * StringAide.compare("a", null)    &gt; 0
     * StringAide.compare("abc", "abc") = 0
     * StringAide.compare("a", "b")     &lt; 0
     * StringAide.compare("b", "a")     &gt; 0
     * StringAide.compare("a", "B")     &gt; 0
     * StringAide.compare("ab", "abc")  &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @see #compare(String, String, boolean)
     */
    public static int compare(final String str1, final String str2) {
        return compare(str1, str2, true);
    }

    /**
     * <p>按字典顺序比较两个字符串，忽略大小写。</p>
     *
     * <pre>
     * StringAide.compareIgnoreCase(null, null, *)     = 0
     * StringAide.compareIgnoreCase(null , "a", true)  &lt; 0
     * StringAide.compareIgnoreCase(null , "a", false) &gt; 0
     * StringAide.compareIgnoreCase("a", null, true)   &gt; 0
     * StringAide.compareIgnoreCase("a", null, false)  &lt; 0
     * StringAide.compareIgnoreCase("abc", "abc", *)   = 0
     * StringAide.compareIgnoreCase("abc", "ABC", *)   = 0
     * StringAide.compareIgnoreCase("a", "b", *)       &lt; 0
     * StringAide.compareIgnoreCase("b", "a", *)       &gt; 0
     * StringAide.compareIgnoreCase("a", "B", *)       &lt; 0
     * StringAide.compareIgnoreCase("A", "b", *)       &lt; 0
     * StringAide.compareIgnoreCase("ab", "abc", *)    &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @param nullIsLess 如果为 true 则 {@code null} 小于 {@code non-null}
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @see #compareIgnoreCase(String, String)
     */
    public static int compareIgnoreCase(final String str1, final String str2, final boolean nullIsLess) {
        if (equals(str1, str2)) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.compareToIgnoreCase(str2);
    }

    /**
     * <p>按字典顺序比较两个字符串，忽略大小写（{@code null} 小于 {@code non-null}）。</p>
     *
     * <pre>
     * StringAide.compareIgnoreCase(null, null)   = 0
     * StringAide.compareIgnoreCase(null , "a")   &lt; 0
     * StringAide.compareIgnoreCase("a", null)    &gt; 0
     * StringAide.compareIgnoreCase("abc", "abc") = 0
     * StringAide.compareIgnoreCase("abc", "ABC") = 0
     * StringAide.compareIgnoreCase("a", "b")     &lt; 0
     * StringAide.compareIgnoreCase("b", "a")     &gt; 0
     * StringAide.compareIgnoreCase("a", "B")     &lt; 0
     * StringAide.compareIgnoreCase("A", "b")     &lt; 0
     * StringAide.compareIgnoreCase("ab", "ABC")  &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @see #compareIgnoreCase(String, String, boolean)
     */
    public static int compareIgnoreCase(final String str1, final String str2) {
        return compareIgnoreCase(str1, str2, true);
    }


}
