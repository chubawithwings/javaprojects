package PZ_11;

interface Image {
    void display();
}

class RealImage implements Image {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromServer();
    }

    private void loadFromServer() {
        System.out.println("Загрузка изображения с сервера: " + filename);
    }

    @Override
    public void display() {
        System.out.println("Отображение изображения: " + filename);
    }
}

class ImageProxy implements Image {
    private final String filename;
    private RealImage realImage;

    public ImageProxy(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

public class PZ_11 {
    public static void main(String[] args) {
        Image image1 = new ImageProxy("photo1.jpg");
        Image image2 = new ImageProxy("photo2.png");

        System.out.println("Изображения созданы, но пока не загружены.\n");

        image1.display();
        System.out.println();

        image1.display();
        System.out.println();

        image2.display();
    }
}
