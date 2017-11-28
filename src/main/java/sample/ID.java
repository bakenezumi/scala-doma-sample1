package sample;

import org.seasar.doma.Domain;

import java.io.Serializable;

@Domain(valueType = long.class, factoryMethod = "of")
public final class ID<ENTITY> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final long value;
    private ID(final long value) {
        this.value = value;
    }
    public long getValue() {
        return value;
    }
    public static <ENTITY> ID<ENTITY> of(final long value) {
        return new ID<>(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ID && ((ID) obj).value == value;
    }

    @Override
    public String toString() {
        return (String.format("ID(%d)", value));
    }
}
