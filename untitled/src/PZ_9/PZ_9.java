package PZ_9;

import java.util.ArrayList;
import java.util.List;

interface NotificationStrategy {
    void send(String message, String user);
}

class EmailNotification implements NotificationStrategy {
    public void send(String message, String user) {
        System.out.println("Отправка Email пользователю " + user + ": " + message);
    }
}

class SMSNotification implements NotificationStrategy {
    public void send(String message, String user) {
        System.out.println("Отправка SMS пользователю " + user + ": " + message);
    }
}

class PushNotification implements NotificationStrategy {
    public void send(String message, String user) {
        System.out.println("Отправка Push пользователю " + user + ": " + message);
    }
}

interface Subscriber {
    void update(String message);
}

class User implements Subscriber {
    private String name;
    private NotificationStrategy notificationStrategy;

    public User(String name, NotificationStrategy strategy) {
        this.name = name;
        this.notificationStrategy = strategy;
    }

    public void setNotificationStrategy(NotificationStrategy strategy) {
        this.notificationStrategy = strategy;
    }

    public void update(String message) {
        notificationStrategy.send(message, name);
    }
}


class NotificationService {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void notifySubscribers(String message) {
        for (Subscriber s : subscribers) {
            s.update(message);
        }
    }
}

public class PZ_9 {
    public static void main(String[] args) {
        User alice = new User("Alice", new EmailNotification());
        User bob = new User("Bob", new SMSNotification());
        User charlie = new User("Charlie", new PushNotification());

        NotificationService service = new NotificationService();
        service.addSubscriber(alice);
        service.addSubscriber(bob);
        service.addSubscriber(charlie);

        service.notifySubscribers("Привет! Это уведомление для всех!");

        System.out.println("\nТеперь Bob получает уведомления на Email!\n");
        bob.setNotificationStrategy(new EmailNotification());

        service.notifySubscribers("Второе уведомление!");
    }
}
