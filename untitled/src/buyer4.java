
interface BuyerInterface {
    String getFullName();
    int getAge();
    void printInfo();
}

class Buyer implements BuyerInterface {
    String surname;
    String name;
    String patronymic;
    int age;
    String gender;
    String nationality;
    String phone;
    String address;
    String cardNumber;
    String bankAccount;

    Buyer(String surname, String name, String patronymic, int age, String gender,
          String nationality, String phone, String address, String cardNumber, String bankAccount) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.phone = phone;
        this.address = address;
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
    }

    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }

    public int getAge() {
        return age;
    }

    public void printInfo() {
        System.out.println("ФИО: " + getFullName());
        System.out.println("Возраст: " + age);
        System.out.println("Пол: " + gender);
        System.out.println("Национальность: " + nationality);
        System.out.println("Телефон: " + phone);
        System.out.println("Адрес: " + address);
        System.out.println("Карта: " + cardNumber);
        System.out.println("Счёт: " + bankAccount);
        System.out.println();
    }
}

public class buyer4 {
    public static void main(String[] args) {
        Buyer[] buyers = new Buyer[] {
                new Buyer("Иванов", "Иван", "Иванович", 25, "М", "Русский", "+7 900 111 22 33", "Москва, ул. Ленина, 10", "1234 5678 9012 3456", "4081781000001"),
                new Buyer("Сидорова", "Мария", "Петровна", 22, "Ж", "Русская", "+7 900 222 33 44", "Ростов-на-Дону, ул. Советская, 27", "4444 3333 2222 1111", "4081781000002"),
                new Buyer("Коваль", "Алексей", "Сергеевич", 30, "М", "Русский", "+7 900 333 44 55", "Минск, ул. Карла Маркса, 3", "2222 3333 4444 5555", "4081781000003")
        };

        System.out.println("Все покупатели:");
        for (Buyer b : buyers) {
            b.printInfo();
        }

        System.out.println("Покупатели старше 23 лет:");
        for (Buyer b : buyers) {
            if (b.getAge() > 23) {
                b.printInfo();
            }
        }
    }
}
