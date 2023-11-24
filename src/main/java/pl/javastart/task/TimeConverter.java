package pl.javastart.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class TimeConverter {
    public LocalDateTime createDateFromString(String input) {
        List<String> formatterStrings = Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "dd.MM.yyyy HH:mm:ss");
        /*
        Poniższego nulla zabezpieczę później w metodzie printTimeInTimeZones()
         */
        LocalDateTime localDateTime = null;

        for (String string : formatterStrings) {
            try {
                DateTimeFormatter formatter;
                String[] split = string.split(" ");
                boolean isTimeCompletionNeeded = false;
                if (split.length == 2) {
                    formatter = DateTimeFormatter.ofPattern(string);
                } else {
                    formatter = DateTimeFormatter.ofPattern(split[0]);
                    isTimeCompletionNeeded = true;
                }

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

    public Map<TimeZoneItem, ZoneId> createMapOfZoneIds() {
        List<String> allZoneIds = Arrays.asList(TimeZone.getAvailableIDs());
        List<String> expectedZoneIds = Arrays.asList("UTC", "London", "Los_Angeles", "Sydney");
        Map<TimeZoneItem, ZoneId> filteredZoneIds = allZoneIds.stream()
                .filter(s -> containsAnyOf(s, expectedZoneIds))
                .filter(s -> s.contains("/"))   // aby uniknąć powtórzenia UTC i Etc/UTC
                .collect(Collectors.toMap(
                        TimeZoneItem::getTimeZoneItemFromZoneId,
                        ZoneId::of
                ));

        /*
        Chcę przywrócić porządek stref czasowych do tego z przykładu zawartego w treści zadania.
        TreeMap posortuje powyższą mapę zgodnie z naturalnym porządkiem enuma TimeZoneItem
         */
        TreeMap<TimeZoneItem, ZoneId> resultMap = new TreeMap<>(filteredZoneIds);
        /*
        Poniżej dodanie brakującego w mapie czasu lokalnego. Nie dodał się, ponieważ w outpucie TimeZone.getAvailableIDs()
        nie ma ZoneId o nazwie "Local_time"
         */
        resultMap.put(TimeZoneItem.LOCAL_TIME, TimeZone.getDefault().toZoneId());
        return resultMap;
    }

    public boolean containsAnyOf(String checkedString, List<String> listOfExpectedSubstrings) {
        for (String expectedZoneId : listOfExpectedSubstrings) {
            if (checkedString.contains(expectedZoneId)) {
                return true;
            }
        }
        return false;
    }
}
