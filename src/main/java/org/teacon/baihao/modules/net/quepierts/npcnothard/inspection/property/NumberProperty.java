package org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property;

public interface NumberProperty<T extends Number> extends Property<T> {

    void setNumber(double value);

    double getNumber();

    @Override
    default void set(T value) {
        this.setNumber(value.floatValue());
    }
}
