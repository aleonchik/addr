package fx.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Project: addr
 * Package: fx.util
 * <p>
 * User: alexey
 * Date: пт 10 окт. 2025
 */
public class DateUtil {
    // Шаблон даты, используемый для преобразования
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    // Форматировщик даты
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Возвращает отформатированную дату в виде хорошо отформатированной строки.
     * Используется определенный выше {@link DateUtil#DATE_PATTERN}.
     * @param date - дата, которая будет возвраащена в виде строки
     * @return отформатированную строку
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }

        return DATE_FORMATTER.format(date);
    }

    /**
     * Преобразует строку, которая отформатирована по правилам
     * шаблона {@link DateUtil#DATE_PATTERN} в объект {@link LocalDate}.
     *
     * Возвращает null, если строка не может быть преобразована
     *
     * @param dateString - дата в виде String
     * @return объект даты или null, если строка не может быть преобразована
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Проверяет, является ли строка валидной датой
     *
     * @param dateString
     * @return true, если стрка является корректной датой
     */
    public static boolean validDate(String dateString) {
        // ПЫтаемся разобрать строку
        return DateUtil.parse(dateString) != null;
    }
}
