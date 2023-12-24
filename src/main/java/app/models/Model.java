package app.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import net.bytebuddy.utility.RandomString;

public interface Model {
    public default String generateKey(Object classObject) {
        return classObject.getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25);
    }

    public default Date generateDate() {
        return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
