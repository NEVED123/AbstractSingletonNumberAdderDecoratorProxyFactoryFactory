import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;

class Main {
    public static void main(String[] args) {
        NumberAdderFactory numberAdderFactory = NumberAdderFactoryFactory.CreateNumberAdderFactoryInstance(1, 2);
        NumberAdder numberAdder = numberAdderFactory.CreateNumberAdderInstance("Not Loaded!");
        System.out.println("Loading number adder... the extreme cleverness of this code is unparalleled!");
        try {
            numberAdder.load();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(numberAdder.add());
    }
}

// This obviously isn't "industry grade" addition on its own, not enough abstraction...
class BoringOldAdder {
    public int add(int a, int b) {
        return a + b;
    }
}

//Abstract - Defines the interface for the object without defining the implementation
abstract class NumberAdder {
    public abstract void load() throws InterruptedException;
    public abstract int add();
}

class NumberAdderFactory {

    int a;
    int b;
    BoringOldAdder boringOldAdder;

    public NumberAdderFactory(int a, int b, BoringOldAdder boringOldAdder) {
        this.a = a;
        this.b = b;
        this.boringOldAdder = boringOldAdder;
    }

    //Factory - handles initialization of objects
    public NumberAdder CreateNumberAdderInstance(String errorMessage) {
        return ConcreteAdder.getNumberAdderInstance(a, b, boringOldAdder, errorMessage);
    }

}

class NumberAdderFactoryFactory {
    public static NumberAdderFactory CreateNumberAdderFactoryInstance(int a, int b) {
        return new NumberAdderFactory(a, b, new BoringOldAdder());
    }
}

class ConcreteAdder extends NumberAdder{

    private int a;
    private int b;
    private boolean loaded;
    private String errorMessage;

    //Decorator - Adds functionality to an object by wrapping it in another object.
    private BoringOldAdder boringOldAdder;
    private static ConcreteAdder instance;

    //Singleton - Only one instance of the object can be created
    public static NumberAdder getNumberAdderInstance(int a, int b, BoringOldAdder boringOldAdder, String errorMessage) {
        if (instance == null) {
            instance = new ConcreteAdder(a, b, boringOldAdder, errorMessage);
        }

        return instance;
    }

    private ConcreteAdder(int a, int b, BoringOldAdder boringOldAdder, String errorMessage) {
        this.a = a;
        this.b = b;
        this.boringOldAdder = boringOldAdder;
        this.errorMessage = errorMessage;
        loaded = false;
    }

    //Proxy: A representation of a class that takes its "full form" when required at runtime, usually to avoid the overhead of creating a lot of a large objects at once.
    @Override
    public void load() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5); // We can get rid of this before the next retrospective meeting so the manager sees improvement in code performance 
        loaded = true;
    }

    public int add() {
        if(loaded){
            return boringOldAdder.add(a, b);
        } else {
            throw new IllegalStateException(errorMessage);
        }
    }
}