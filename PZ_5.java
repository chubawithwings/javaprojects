import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
interface Processor<T> {
    void process(T obj);
}

record Book(String title, String author, double price) {
    @Override
    public String toString() {
        return String.format("Книга: '%s', автор: %s, цена: %.2f руб.", title, author, price);
    }
}

public class PZ_5 {
    public static void main(String[] args) {
        Book[] books = {
            new Book("Преступление и наказание", "Достоевский Ф.М.", 450.50),
            new Book("Мастер и Маргарита", "Булгаков М.А.", 680.00),
            new Book("1984", "Оруэлл Дж.", 320.75),
            new Book("Война и мир", "Толстой Л.Н.", 890.25),
            new Book("Гарри Поттер и философский камень", "Роулинг Дж.К.", 550.80),
            new Book("Маленький принц", "Экзюпери А.", 280.30)
        };

        // Вычисление средней цены
        double averagePrice = Arrays.stream(books)
                .mapToDouble(Book::price)
                .average()
                .orElse(0.0);

        System.out.printf("Средняя цена книг: %.2f руб.\n", averagePrice);
        System.out.println("Книги дороже средней цены, отсортированные по цене:");

        // Фильтрация и сортировка
        List<Book> filteredSortedBooks = Arrays.stream(books)
                .filter(book -> book.price() > averagePrice)
                .sorted(Comparator.comparingDouble(Book::price))
                .toList();

        // Создание делегата для вывода информации о книге
        Processor<Book> printer = book -> System.out.println(book.toString());

        // Вывод результатов с использованием делегата
        if (filteredSortedBooks.isEmpty()) {
            System.out.println("Книги не найдены");
        } else {
            filteredSortedBooks.forEach(printer::process);
        }
    }
}
