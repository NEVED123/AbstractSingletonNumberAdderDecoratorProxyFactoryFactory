class Main {
    public static void main(String[] args) {
        NumberAdderFactory numberAdderFactory = NumberAdderFactoryFactory.CreateNumberAdderFactoryInstance(1, 2);
        NumberAdder numberAdder = numberAdderFactory.CreateNumberAdderInstance("Not Loaded!");
        numberAdder.load();
        System.out.println(numberAdder.add());
    }
}

class BoringOldAdder {
    public int add(int a, int b) {
        return a + b;
    }
}

//Abstract - Defines the interface for the object without defining the implementation
abstract class NumberAdder {

    public abstract void load();
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
        return new ConcreteAdder(a, b, boringOldAdder, errorMessage);
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
    private BoringOldAdder boringOldAdder = new BoringOldAdder();

    public ConcreteAdder(int a, int b, BoringOldAdder boringOldAdder, String errorMessage) {
        this.a = a;
        this.b = b;
        this.boringOldAdder = boringOldAdder;
        this.errorMessage = errorMessage;
        loaded = false;
    }

    //Proxy: A representation of a class that takes its "full form" when required at runtime, usually to avoid the overhead of creating a lot of a large objects at once.
    @Override
    public void load() {
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