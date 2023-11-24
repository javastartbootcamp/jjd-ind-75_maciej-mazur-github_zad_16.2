package pl.javastart.task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        TimeConverter converter = new TimeConverter();
        Map<TimeZoneItem, ZoneId> zoneIdMap = converter.createMapOfZoneIds();
        System.out.println("Podaj datę:");
        LocalDateTime inputDateTime = converter.createDateFromString(scanner.nextLine());
        printTimeInTimeZones(inputDateTime, zoneIdMap);
    }

    private void printTimeInTimeZones(LocalDateTime inputDateTime, Map<TimeZoneItem, ZoneId> zoneIdMap) {
        if (inputDateTime == null) {
            System.out.println("Brak wpisanej prawidłowej daty");
            return;
        }
        ZonedDateTime zonedInputDateTime = inputDateTime.atZone(TimeZone.getDefault().toZoneId());
        for (Map.Entry<TimeZoneItem, ZoneId> entry : zoneIdMap.entrySet()) {
            zonedInputDateTime = zonedInputDateTime.withZoneSameInstant(entry.getValue());
            //LocalDateTime zonedLocalDateTime = LocalDateTime.of(inputDateTime, entry.getValue());
            System.out.printf("%s: %s%n",
                    entry.getKey().getTranslation(),
                    zonedInputDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } 
    }
}
