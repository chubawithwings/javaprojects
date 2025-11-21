import java.util.*;

interface Subscriber {
    void update(String weather);
}

interface Subject {
    void addSubscriber(Subscriber o);
    void removeSubscriber(Subscriber o);
    void notifySubscribers();
}

class Weatherapp implements Subject {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String weather;

    @Override
    public void addSubscriber(Subscriber o) {
        observers.add(o);
    }

    @Override
    public void removeSubscriber(Subscriber o) {
        observers.remove(o);
    }

    @Override
    public void notifySubscriber() {
        for (Subscriber o : subscriber) {
            o.update(weather);
        }
    }

    public void setWeather(String weather) {
        this.weather = weather;
        System.out.println("\nПогода изменилась на: " + weather);
        notifySubscribers();
    }
}
class User implements Subscriber {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String weather) {
        System.out.println(name + " получил уведомление: сейчас " + weather);
    }
}

public class PZ_12 {
    public static void main(String[] args) {
        Weatherapp weatherStation = new WeatherStation();

        User user1 = new User("Лера");
        User user2 = new User("Ксюша");
        User user3 = new User("Ульяна");

        weatherStation.addSubscriber(user1);
        weatherStation.addSubscriber(user2);
        weatherStation.addSubscriber(user3);

        weatherStation.setWeather("солнечно");
        weatherStation.setWeather("дождливо");

        weatherStation.removeObserver(user2);

        weatherStation.setWeather("снежно");
    }
}
