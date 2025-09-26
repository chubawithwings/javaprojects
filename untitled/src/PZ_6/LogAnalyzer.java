package PZ_6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

// Класс для хранения данных одной записи лога
class LogEntry {
    String ip;
    LocalDateTime timestamp;
    String method;
    String path;
    int status;
    int size;
    String userAgent;

    public LogEntry(String ip, LocalDateTime timestamp, String method,
                    String path, int status, int size, String userAgent) {
        this.ip = ip;
        this.timestamp = timestamp;
        this.method = method;
        this.path = path;
        this.status = status;
        this.size = size;
        this.userAgent = userAgent;
    }

    // Геттеры для доступа к полям
    public String getIp() { return ip; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getMethod() { return method; }
    public String getPath() { return path; }
    public int getStatus() { return status; }
    public int getSize() { return size; }
    public String getUserAgent() { return userAgent; }

    @Override
    public String toString() {
        return String.format("IP: %s, Time: %s, %s %s, Status: %d",
                ip, timestamp, method, path, status);
    }
}

public class LogAnalyzer {

    // Регулярное выражение для разбора логов с именованными группами
    // Используем ленивые квантификаторы для user-agent
    private static final String LOG_PATTERN =
            "^(?<ip>\\d+\\.\\d+\\.\\d+\\.\\d+) - - \\[" +
                    "(?<datetime>[^\\]]+)\\] \\\"(?<method>\\w+) " +
                    "(?<path>[^\\\"]+?) HTTP/\\d\\.\\d\\\" " +
                    "(?<status>\\d+) (?<size>\\d+) \\\"[^\\\"]*\\\" \\\"" +
                    "(?<useragent>.*?)\\\"$";

    private static final Pattern pattern = Pattern.compile(LOG_PATTERN);

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу логов: java LogAnalyzer путь/к/файлу.log");
            return;
        }

        String logFile = "C:\\Users\\chuba\\IdeaProjects\\javaprojects\\untitled\\src\\PZ_6\\test.log";
        List<LogEntry> logs = parseLogFile(logFile);

        if (logs.isEmpty()) {
            System.out.println("Не удалось разобрать логи или файл пуст");
            return;
        }

        // Генерация отчетов
        generateHourlyReport(logs);
        generateTopResourcesReport(logs);
        generate404Report(logs);
        generateStatusReport(logs);
    }

    // Метод для разбора файла логов
    private static List<LogEntry> parseLogFile(String filename) {
        List<LogEntry> entries = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                LogEntry entry = parseLogLine(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return entries;
    }

    // Метод для разбора одной строки лога
    private static LogEntry parseLogLine(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            try {
                // Извлекаем данные с помощью именованных групп
                String ip = matcher.group("ip");
                String datetimeStr = matcher.group("datetime");
                String method = matcher.group("method");
                String path = matcher.group("path");
                int status = Integer.parseInt(matcher.group("status"));
                int size = Integer.parseInt(matcher.group("size"));
                String userAgent = matcher.group("useragent");

                // Преобразуем дату/время (учитываем часовой пояс)
                LocalDateTime timestamp = parseDateTime(datetimeStr);

                return new LogEntry(ip, timestamp, method, path, status, size, userAgent);

            } catch (Exception e) {
                System.out.println("Ошибка разбора строки: " + line);
                return null;
            }
        } else {
            System.out.println("Строка не соответствует формату: " + line);
            return null;
        }
    }

    // Метод для преобразования строки даты/времени
    private static LocalDateTime parseDateTime(String datetimeStr) {
        // Формат: "10/Oct/2024:13:55:36 +0300"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        // Заменяем названия месяцев для корректного парсинга
        datetimeStr = datetimeStr.replace("Oct", "Oct")
                .replace("Nov", "Nov")
                .replace("Dec", "Dec")
                .replace("Jan", "Jan")
                .replace("Feb", "Feb")
                .replace("Mar", "Mar")
                .replace("Apr", "Apr")
                .replace("May", "May")
                .replace("Jun", "Jun")
                .replace("Jul", "Jul")
                .replace("Aug", "Aug")
                .replace("Sep", "Sep");

        return LocalDateTime.parse(datetimeStr, formatter);
    }

    // Отчет по уникальным IP за каждый час
    private static void generateHourlyReport(List<LogEntry> logs) {
        System.out.println("=== УНИКАЛЬНЫЕ ПОСЕТИТЕЛИ ПО ЧАСАМ ===");

        // Группируем по часам (без минут и секунд)
        Map<String, Set<String>> hourlyIps = logs.stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")),
                        Collectors.mapping(LogEntry::getIp, Collectors.toSet())
                ));

        // Сортируем по времени и выводим
        hourlyIps.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.printf("Hour %s:00 - unique_ips=%d%n",
                            entry.getKey(), entry.getValue().size());
                });
        System.out.println();
    }

    // Топ-10 самых запрашиваемых ресурсов
    private static void generateTopResourcesReport(List<LogEntry> logs) {
        System.out.println("=== ТОП-10 ЗАПРАШИВАЕМЫХ РЕСУРСОВ ===");

        Map<String, Long> resourceCounts = logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getPath,
                        Collectors.counting()
                ));

        resourceCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> {
                    System.out.printf("%s (%d запросов)%n", entry.getKey(), entry.getValue());
                });
        System.out.println();
    }

    // Отчет по 404 ошибкам
    private static void generate404Report(List<LogEntry> logs) {
        System.out.println("=== ОТЧЕТ ПО 404 ОШИБКАМ ===");

        // Фильтруем только 404 ошибки
        List<LogEntry> error404 = logs.stream()
                .filter(entry -> entry.getStatus() == 404)
                .collect(Collectors.toList());

        System.out.printf("Всего 404 ошибок: %d%n%n", error404.size());

        // Топ URL с 404 ошибками
        Map<String, Long> error404ByUrl = error404.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getPath,
                        Collectors.counting()
                ));

        System.out.println("Топ URL с 404 ошибками:");
        error404ByUrl.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> {
                    System.out.printf("%s (%d ошибок)%n", entry.getKey(), entry.getValue());
                });
        System.out.println();
    }

    // Статистика по HTTP-статусам
    private static void generateStatusReport(List<LogEntry> logs) {
        System.out.println("=== СТАТИСТИКА ПО HTTP-СТАТУСАМ ===");

        Map<Integer, Long> statusCounts = logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getStatus,
                        Collectors.counting()
                ));

        statusCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.printf("Status %d: %d запросов%n", entry.getKey(), entry.getValue());
                });
        System.out.println();
    }
}