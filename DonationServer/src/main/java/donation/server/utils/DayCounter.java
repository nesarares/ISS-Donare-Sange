package donation.server.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DayCounter {

    public static long getDaysBetween(String  initialDate,String currentDate){



        LocalDate localDateFirst = LocalDate.parse(initialDate);
        LocalDate localDateSecond  = LocalDate.parse(currentDate);

        LocalDateTime d1 = LocalDateTime.of(localDateFirst, LocalTime.of(0,0));

        LocalDateTime d2 = LocalDateTime.of(localDateSecond,LocalTime.of(0,0));

        return Duration.between(d1,d2).toHours() / 24;
    }
}
