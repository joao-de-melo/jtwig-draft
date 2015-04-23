package org.jtwig.util;

public final class ExpressionUtils {

    private ExpressionUtils() {
    }

    public static boolean isTrue(Object value) {
        if (value == null) {
            return false;
        } else {
            if (value.getClass().isArray()) {
                return ((Object[]) value).length > 0;
            } else if (value instanceof Iterable) {
                return ((Iterable) value).iterator().hasNext();
            } else if (value instanceof Boolean) {
                return (Boolean) value;
            } else if (value instanceof String) {
                return !((String) value).isEmpty();
            } else if (value instanceof Number) {
                return ((Number) value).intValue() != 0;
            }
        }
        return true;
    }

    public static String numberAsString (Object value) {
        if (value == null) {
            return Integer.toString(0);
        } else {
            if (value instanceof Boolean) {
                return (Boolean.class.cast(value)) ? Integer.toString(1) : Integer.toString(0);
            } else if (value instanceof Number) {
                return value.toString();
            } else {
                String representation = value.toString();
                if (representation.matches("-?\\d*\\.?\\d+")) {
                    return representation;
                } else {
                    throw new IllegalArgumentException(String.format("Unable to convert '%s' into a number", representation));
                }
            }
        }
    }
}
