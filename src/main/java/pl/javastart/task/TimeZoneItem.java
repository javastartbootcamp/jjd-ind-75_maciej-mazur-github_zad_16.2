package pl.javastart.task;

public enum TimeZoneItem {
    LOCAL_TIME("Czas lokalny"),
    UTC("UTC"),
    LONDON("Londyn"),
    LOS_ANGELES("Los Angeles"),
    SYDNEY("Sydney");

    private final String translation;

    TimeZoneItem(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public String toString() {
        /*
        Chcę odwzorować oryginalne formatowanie nazw krajów zwróconych
         */
        return String.format("%s%s", name().charAt(0), name().substring(1).toLowerCase());
    }

    public static TimeZoneItem getTimeZoneItemFromZoneId(String s) {
        /*
        Poniższa operacja jest konieczna, ponieważ stream z klasy TimeConverter zwraca stringi reprezentujące
        ZoneId w formacie kontynent/kraj, poniżej zatem pozbywam się frazy zawierającej kontynent i ukośnik.
         */
        return valueOf(s.split("/")[1].toUpperCase());
    }
}
