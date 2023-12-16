package app.models;

import net.bytebuddy.utility.RandomString;

public interface Model {
    default String generateKey() {
        return this.getClass().getName().toLowerCase().substring(0, 2) + "_" + RandomString.make(25);
    }
}
