package pl.javastart.task;

import java.time.LocalDateTime;
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
        System.out.println("Podaj datę:");
        LocalDateTime inputDateTime = converter.createDateFromString(scanner.nextLine());
        printTimeInTimeZones(inputDateTime);
    }

    private void printTimeInTimeZones(LocalDateTime inputDateTime) {
        if (inputDateTime == null) {
            System.out.println("Brak wpisanej prawidłowej daty");
            return;
        }
        ZonedDateTime zonedInputDateTime = inputDateTime.atZone(TimeZone.getDefault().toZoneId());
        for (TimeZoneItem timeZone : TimeZoneItem.values()) {
            zonedInputDateTime = zonedInputDateTime.withZoneSameInstant(timeZone.getZoneId());
            System.out.printf("%s: %s%n",
                    timeZone.getTranslation(),
                    zonedInputDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }
}
