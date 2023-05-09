import java.util.function.Supplier;

class Server implements Servable {

    private final double currTime;
    private static final double THRESHOLD = 1E-15;
    private final int name;
    private final int maxWait;
    private final int currWait;
    private final Supplier<Double> restTime;
    private final boolean isCounter;

    Server(double time, int name, int maxWait, Supplier<Double> restTime) {
        this(time, name, maxWait, 0, restTime);
    }

    Server(double time, int name, int maxWait,
            int currWait, Supplier<Double> restTime) {
        this.currTime = time;
        this.name = name;
        this.maxWait = maxWait;
        this.currWait = currWait;
        this.restTime = restTime;
        this.isCounter = false;
    }

    public boolean canServe(int idx, Customer cus) {
        double diff = this.currTime - cus.getTime();
        return Math.abs(diff) < THRESHOLD || diff < 0;
    }

    public Server setTime(int idx, double time) {
        return new Server(time, this.name, this.maxWait, this.currWait, this.restTime);
    }

    public Server updateWait(int num) {
        int change = this.currWait + num;
        change = change <= 0 ? 0 : change;
        change = change > this.maxWait ? this.maxWait : change;
        return new Server(this.getTime(0), this.name, this.maxWait, change, this.restTime);

    }

    public int getMaxWait() {
        return this.maxWait;
    }

    public int getName() {
        return this.name;
    }

    public boolean isCounter() {
        return this.isCounter;
    }

    public boolean canWait() {
        return this.currWait < this.maxWait;
    }

    public int getWait() {
        return this.currWait;
    }

    public ImList<Servable> getQ() {
        return new ImList<Servable>();
    }

    public Servable setQ(ImList<Servable> nextQ) {
        return new Server(currTime, name, maxWait, restTime);
    }

    public double getRest() {
        return this.restTime.get();
    }

    public int getIdx() {
        return this.name - 1;
    }

    public double getTime(int idx) {
        return this.currTime;
    }

    @Override
    public String toString() {
        return String.format("%d", this.name);
    }

}
