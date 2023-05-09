import java.util.function.Supplier;

class Customer {
    private final double startTime;
    private final Supplier<Double> serviceTime;
    private final int number;

    Customer(double startTime, Supplier<Double> serviceTime, int num) {
        this.startTime = startTime;
        this.serviceTime = serviceTime;
        this.number = num;
    }

    double getTime() {
        return this.startTime;
    }

    double getDuration() {
        return this.serviceTime.get();
    }

    Customer setTime(double time) {
        return new Customer(time, this.serviceTime,
                this.number);
    }

    @Override
    public String toString() {
        return String.format("%d", this.number);
    }

}
