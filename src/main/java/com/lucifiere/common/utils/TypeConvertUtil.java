package com.lucifiere.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * 类型转换器
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年4月4日
 */
public class TypeConvertUtil {

    private static final double DEFAULT_DOUBLE_VALUE = 0.0;

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeConvertUtil.class);

    /**
     * 转换数据为long
     */
    public static long asLong(String value, long defaultValue) {
        if (value != null && !value.isEmpty()) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {

            }
        }

        return defaultValue;
    }

    /**
     * 转换数据为int
     */
    public static int asInt(String value, int defaultValue) {
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {

            }
        }

        return defaultValue;
    }

    /**
     * 若longStr 不是数字，那么返回0
     */
    public static long asLong(String longStr) {
        return asLong(longStr, 0L);
    }

    public static long asLong(Object obj) {
        return asLong(obj, 0L);
    }

    public static long asLong(Object obj, long defaultValue) {
        if (obj != null) {
            if (obj instanceof Long) {
                return (Long) obj;
            } else {
                return asLong(obj.toString(), defaultValue);
            }
        }

        return defaultValue;
    }

    /**
     * 若入参不是数字，那么返回0
     */
    public static int asInt(String intObj) {
        return asInt(intObj, 0);
    }

    public static int asInt(Object obj) {
        return asInt(obj, 0);
    }

    public static int asInt(Object obj, int defaultValue) {
        if (obj != null) {
            if (obj instanceof Integer) {
                return (Integer) obj;
            } else {
                return asInt(obj.toString(), defaultValue);
            }
        }

        return defaultValue;
    }

    public static Date asDate(String dateString, String format) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        FastDateFormat fdf = FastDateFormat.getInstance(format);

        try {
            return fdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date asDate(String dateString) {
        return asDate(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static short asShort(String value, short defaultValue) {
        if (value != null && !value.isEmpty()) {
            try {
                return Short.parseShort(value);
            } catch (NumberFormatException e) {

            }
        }

        return defaultValue;
    }

    public static short asShort(String shortObj) {
        return asShort(shortObj, (short) 0);
    }

    public static String asString(Object object) {
        if (object == null) {
            return StringUtils.EMPTY;
        }
        return String.valueOf(object);
    }

    public static String asString(Object object, String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return asString(object);
    }

    /**
     * 将object转换为布尔类型，转换失败，返回false
     */
    public static boolean asBoolean(Object object, boolean defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof String) {
            return "true".equals(object);
        }

        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        return defaultValue;
    }

    /**
     * 将object转换为布尔类型，转换失败，返回false
     */
    public static boolean asBoolean(Object object) {
        return asBoolean(object, false);
    }

    public static double asDouble(Object object) {
        return asDouble(object, DEFAULT_DOUBLE_VALUE);
    }

    public static double asDouble(Object object, double defaultValue) {
        if (object == null) {
            return defaultValue;
        }

        if (object instanceof Double) {
            return (Double) object;
        } else if (object instanceof Number) {
            return ((Number) object).doubleValue();
        } else if (object instanceof String) {
            String strValue = ((String) object).trim();
            try {
                return Double.valueOf(strValue);
            } catch (Throwable t) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static <K> long asLong(Map<K, String> map, K key) {
        return asLong(map, key, -1);
    }

    public static <K> long asLong(Map<K, String> map, K key, long defaultValue) {
        if (map != null) {
            String value = map.get(key);
            return asLong(value, defaultValue);
        }

        return defaultValue;
    }

    public static <K> long asLongValue(Map<K, Long> map, K key, long defaultValue) {
        if (map != null) {
            Long value = map.get(key);
            if (value != null) {
                return value.longValue();
            }
        }
        return defaultValue;
    }

    public static <K> Long asLongObj(Map<K, String> map, K key) {
        if (map != null) {
            String value = map.get(key);
            if (value != null && !value.isEmpty()) {
                try {
                    return Long.parseLong(value);
                } catch (NumberFormatException e) {

                }
            }
        }

        return null;
    }

    public static <K> int asInt(Map<K, String> map, K key) {
        return asInt(map, key, -1);
    }

    public static <K> int asInt(Map<K, String> map, K key, int defaultValue) {
        if (map != null) {
            String value = map.get(key);
            return asInt(value, defaultValue);
        }

        return defaultValue;
    }

    public static <K> Integer asIntObj(Map<K, String> map, K key) {
        if (map != null) {
            String value = map.get(key);
            if (value != null && !value.isEmpty()) {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {

                }
            }
        }

        return null;
    }

    public static <K> boolean asBoolean(Map<K, String> map, K key) {
        return asBoolean(map, key, false);
    }

    public static <K> boolean asBoolean(Map<K, String> map, K key, boolean defaultValue) {
        if (map != null) {
            String value = map.get(key);
            if (value != null && !value.isEmpty()) {
                return Boolean.valueOf(value);
            }
        }

        return defaultValue;
    }

    public static <K> Boolean asBooleanObj(Map<K, String> map, K key) {
        if (map != null) {
            String value = map.get(key);
            if (value != null && !value.isEmpty()) {
                return Boolean.valueOf(value);
            }
        }

        return null;
    }

    public static <K> String getAsString(Map<K, String> map, K key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }

        return null;
    }

    public static <K> String getAsString(Map<K, String> map, K key, String defaultValue) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }

        return defaultValue;
    }

    /**
     * 反转义
     */
    public static final char ESCAPE_COMMA = '\u0003';
    public static final char COMMA = ',';
    public static final char ESCAPE_SEMICOLON = '\u0002';
    public static final char SEMICOLON = ';';
    public static final char ESCAPE_VERTICAL_LINE = '\u0005';
    public static final char VERTICAL_LINE = '|';
    public static final char ESCAPE_NONE = '\u0001';
    public static final char ESCAPE_COLON = '\u0004';
    public static final char COLON = ':';
    public static final char ESCAPE_DOLLAR = '\u0006';
    public static final char DOLLAR = '$';

    public static String unEscape(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        char c, replaceChar;
        int lastIndex = 0;
        for (int i = 0; i < content.length(); i++) {
            c = content.charAt(i);
            switch (c) {
                case ESCAPE_SEMICOLON:
                    replaceChar = SEMICOLON;
                    break;
                case ESCAPE_COLON:
                    replaceChar = COLON;
                    break;
                case ESCAPE_COMMA:
                    replaceChar = COMMA;
                    break;
                case ESCAPE_VERTICAL_LINE:
                    replaceChar = VERTICAL_LINE;
                    break;
                case ESCAPE_DOLLAR:
                    replaceChar = DOLLAR;
                    break;
                default:
                    replaceChar = ESCAPE_NONE;
            }

            if (replaceChar != ESCAPE_NONE) {
                if (lastIndex < i) {
                    sb.append(content.substring(lastIndex, i));
                }
                sb.append(replaceChar);
                lastIndex = i + 1;
            }
        }

        if (lastIndex < content.length()) {
            sb.append(content.substring(lastIndex));
        }
        return sb.toString();
    }

    public static String getValueAsString(String value) {
        return value;
    }

    public static String getAmountAsString(BigDecimal bigDecimal) {
        String amount = bigDecimal.toString();
        String s = ".00";
        if (amount.endsWith(s)) {
            return amount.substring(0, amount.length() - 3);
        } else {
            return amount;
        }
    }
}