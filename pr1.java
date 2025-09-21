public class Main {
    public static void main(String[] args) {

        Buyer[] buyers = new Buyer[3];

        buyers[0] = new Buyer("Иванов", "Иван", "Иванович", "мужской",
                "русский", 180, 80, 33,
                new Address("123456", "Россия", "Московская", "Центральный", "Москва", "Ленина", 10, 5),
                "79991234567", "1234 5678 9012 3456", "40817810099910004312");

        buyers[1] = new Buyer("Петрова", "Мария", "Сергеевна", "женский",
                "русская", 165, 60, 28,
                new Address("654321", "Россия", "Ленинградская", "Северный", "Санкт-Петербург", "Невский", 20, 12),
                "79997654321", "2345 6789 0123 4567", "40817810099910004313");

        buyers[2] = new Buyer("Сидоров", "Алексей", "Петрович", "мужской",
                "русский", 175, 75, 35,
                new Address("344000", "Россия", "Ростовская", "Центральный", "Ростов-на-Дону", "Садовая", 15, 7),
                "380501234567", "3456 7890 1234 5678", "40817810099910004314");

        System.out.println("Все покупатели:");
        for (Buyer b : buyers) {
            System.out.println(
                    b.getLastName() + " " + b.getFirstName() + " " + b.getMiddleName() + ", " +
                            b.getGender() + ", " + b.getNationality() + ", рост: " + b.getHeight() +
                            ", вес: " + b.getWeight() + ", возраст: " + b.getAge() +
                            ", адрес: " + b.getAddress().getCountry() + ", " + b.getAddress().getRegion() +
                            ", " + b.getAddress().getDistrict() + ", " + b.getAddress().getCity() +
                            ", " + b.getAddress().getStreet() + " д." + b.getAddress().getHouse() +
                            " кв." + b.getAddress().getApartment() +
                            ", телефон: " + b.getPhoneNumber() + ", карта: " + b.getCardNumber() +
                            ", счет: " + b.getBankAccount()
            );
        }

        System.out.println("\nЖенщины старше 25 лет:");
        for (Buyer b : buyers) {
            if (b.getGender().equals("женский") && b.getAge() > 25) {
                System.out.println(b.getLastName() + " " + b.getFirstName() +
                        " " + b.getMiddleName() + ", возраст: " + b.getAge());
            }
        }
    }
}


class Buyer {
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private String nationality;
    private int height;
    private int weight;
    private int age;
    private Address address;
    private String phoneNumber;
    private String cardNumber;
    private String bankAccount;

    public Buyer(String lastName, String firstName, String middleName, String gender,
                 String nationality, int height, int weight, int age,
                 Address address, String phoneNumber, String cardNumber, String bankAccount) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.gender = gender;
        this.nationality = nationality;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
    }


    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getGender() { return gender; }
    public String getNationality() { return nationality; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public int getAge() { return age; }
    public Address getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCardNumber() { return cardNumber; }
    public String getBankAccount() { return bankAccount; }

}

class Address {
    private String postalCode;
    private String country;
    private String region;
    private String district;
    private String city;
    private String street;
    private int house;
    private int apartment;

    public Address(String postalCode, String country, String region, String district,
                   String city, String street, int house, int apartment) {
        this.postalCode = postalCode;
        this.country = country;
        this.region = region;
        this.district = district;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public String getRegion() { return region; }
    public String getDistrict() { return district; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public int getHouse() { return house; }
    public int getApartment() { return apartment; }
}