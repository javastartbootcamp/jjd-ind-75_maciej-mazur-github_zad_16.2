package pl.javastart.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TimeConverter {
    public LocalDateTime createDateFromString(String input) {
        List<String> dateTimeFormatterStrings = Arrays.asList("yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy HH:mm:ss");
        List<String> dateFormatterStrings = Arrays.asList("yyyy-MM-dd");

        if (input.split(" ").length == 1) {
            return parseDateTime(input, dateFormatterStrings, true);
        } else {
            return parseDateTime(input, dateTimeFormatterStrings, false);
        }
    }

    private LocalDateTime parseDateTime(String input, List<String> formatterStrings, boolean isTimeCompletionNeeded) {
        LocalDateTime localDateTime = null;

        for (String formatterString : formatterStrings) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);

                if (isTimeCompletionNeeded) {
                    LocalDate localDate = LocalDate.parse(input, formatter);
                    localDateTime = LocalDateTime.of(localDate, LocalTime.parse("00:00"));
                } else {
                    localDateTime = LocalDateTime.parse(input, formatter);
                }
            } catch (DateTimeParseException ignore) {
                //
            }
        }

        return localDateTime;
    }
}
