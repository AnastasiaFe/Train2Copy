package ua.nure.fedorenko.kidstim.service.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {

    public static LocalDateTime getTimeFromLong(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public static long getMillisFromTime(LocalDateTime time) {
        ZonedDateTime zdt = time.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}
