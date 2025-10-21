package PZ_10;

interface Engine {
    void createEngine();
}

interface Body {
    void createBody();
}

interface Interior {
    void createInterior();
}

class ToyotaEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("Создан двигатель Toyota");
    }
}

class ToyotaBody implements Body {
    @Override
    public void createBody() {
        System.out.println("Создан кузов Toyota");
    }
}

class ToyotaInterior implements Interior {
    @Override
    public void createInterior() {
        System.out.println("Создан интерьер Toyota");
    }
}

class BMWEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("Создан двигатель BMW");
    }
}

class BMWBody implements Body {
    @Override
    public void createBody() {
        System.out.println("Создан кузов BMW");
    }
}

class BMWInterior implements Interior {
    @Override
    public void createInterior() {
        System.out.println("Создан интерьер BMW");
    }
}

interface CarFactory {
    Engine createEngine();
    Body createBody();
    Interior createInterior();
}

class ToyotaFactory implements CarFactory {
    @Override
    public Engine createEngine() {
        return new ToyotaEngine();
    }

    @Override
    public Body createBody() {
        return new ToyotaBody();
    }

    @Override
    public Interior createInterior() {
        return new ToyotaInterior();
    }
}

class BMWFactory implements CarFactory {
    @Override
    public Engine createEngine() {
        return new BMWEngine();
    }

    @Override
    public Body createBody() {
        return new BMWBody();
    }

    @Override
    public Interior createInterior() {
        return new BMWInterior();
    }
}

class CarClient {
    private final Engine engine;
    private final Body body;
    private final Interior interior;

    public CarClient(CarFactory factory) {
        engine = factory.createEngine();
        body = factory.createBody();
        interior = factory.createInterior();
    }

    public void showCarParts() {
        engine.createEngine();
        body.createBody();
        interior.createInterior();
    }
}

public class PZ_10 {
    public static void main(String[] args) {
        System.out.println("=== Toyota ===");
        CarClient toyotaCar = new CarClient(new ToyotaFactory());
        toyotaCar.showCarParts();

        System.out.println("\n=== BMW ===");
        CarClient bmwCar = new CarClient(new BMWFactory());
        bmwCar.showCarParts();
    }
}
