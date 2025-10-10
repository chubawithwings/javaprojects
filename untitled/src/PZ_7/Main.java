package PZ_7;


import java.util.ArrayDeque;
import java.util.Deque;

class StackOverflowException extends RuntimeException {
    public StackOverflowException(String message) {
        super(message);
    }
}

class MyStack<T> {
    private final Deque<T> deque;
    private final int maxSize;

    public MyStack(int maxSize) {
        this.deque = new ArrayDeque<>();
        this.maxSize = maxSize;
    }

    public void push(T element) {
        if (deque.size() >= maxSize) {
            throw new StackOverflowException("Стек переполнен! Максимальный размер: " + maxSize);
        }
        deque.push(element);
    }

    public T pop() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.pop();
    }

    public T peek() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.peek();
    }

    public int size() {
        return deque.size();
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Тестирование стека с Integer");
        testIntegerStack();

        System.out.println("Тестирование стека с String");
        testStringStack();

        System.out.println("Тестирование переполнения стека");
        testStackOverflow();
    }

    public static void testIntegerStack() {
        MyStack<Integer> intStack = new MyStack<>(3);

        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println("Верхний элемент: " + intStack.peek());
        System.out.println("Размер стека: " + intStack.size());

        while (intStack.peek() != null) {
            System.out.println("Извлечен: " + intStack.pop());
        }
    }

    public static void testStringStack() {
        MyStack<String> stringStack = new MyStack<>(3);

        stringStack.push("Hello");
        stringStack.push("World");
        stringStack.push("Java");

        System.out.println("Верхний элемент: " + stringStack.peek());
        System.out.println("Размер стека: " + stringStack.size());

        while (stringStack.peek() != null) {
            System.out.println("Извлечен: " + stringStack.pop());
        }
    }

    public static void testStackOverflow() {
        MyStack<Double> doubleStack = new MyStack<>(2);

        try {
            doubleStack.push(1.1);
            doubleStack.push(2.2);
            doubleStack.push(3.3);
        } catch (StackOverflowException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }
    }
}