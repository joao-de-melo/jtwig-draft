package org.jtwig.util;

import com.google.common.base.Objects;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Arrays.asList;

public class JtwigValue implements Comparable<JtwigValue> {
    public static JtwigValue empty() {
        return new JtwigValue(null);
    }

    private final Object value;

    public JtwigValue(Object value) {
        this.value = value;
    }

    public boolean isPresent () {
        return value != null;
    }
    public boolean isNull () {
        return value == null;
    }

    public String asString() {
        if (value == null) return null;
        else {
            return value.toString();
        }
    }

    public Object asObject() {
        return value;
    }

    public BigDecimal asNumber() {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        return new BigDecimal(ExpressionUtils.numberAsString(value));
    }

    public Boolean asBoolean () {
        return ExpressionUtils.isTrue(value);
    }

    @Override
    public int compareTo(JtwigValue other) {
        if (Objects.equal(value, other.value)) return 0;
        return value.toString().compareTo(other.value.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JtwigValue) {
            JtwigValue jtwigValue = (JtwigValue) obj;
            return Objects.equal(jtwigValue.value, value);
        } else {
            return super.equals(obj);
        }
    }

    public Collection<Object> asCollection() {
        if (isNull()) return Collections.emptyList();
        else if (value instanceof Iterable) return collectionFromIterable((Iterable) value);
        else if (value.getClass().isArray()) return asList((Object[]) value);
        else if (value instanceof Map) return ((Map) value).values();
        else return asList(value);
    }

    private Collection<Object> collectionFromIterable(Iterable iterable) {
        Collection<Object> result = new ArrayList<>();
        for (Object value : iterable) {
            result.add(value);
        }
        return result;
    }

    public Map<Object, Object> asMap() {
        if (isNull()) return new HashMap<>();
        else if (value instanceof Map) return (Map<Object, Object>) value;
        else if (value instanceof Iterable) return mapFromIterable((Iterable) value);
        else if (value.getClass().isArray()) return mapFromIterable(asList((Object[])value));
        else return new HashMap<Object, Object>() {{ put(0, value); }};
    }

    private Map<Object, Object> mapFromIterable(Iterable value) {
        Map<Object, Object> result = new LinkedHashMap<>();
        Iterator iterator = value.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Object next = iterator.next();
            result.put(index++, next);
        }
        return result;
    }
}
