import java.util.function.Supplier;

public class SelfCheckCounter implements Servable {

    private final double currTime;
    private static final double THRESHOLD = 1E-15;
    private final int name;
    private final int maxWait;
    private final int currWait;
    private final Supplier<Double> restTime;
    private final boolean isCounter;
    private final int fromIdx;

    SelfCheckCounter(double time, int name, int maxWait, int fromIdx) {
        this.currTime = time;
        this.name = name;
        this.maxWait = maxWait;
        this.currWait = 0;
        this.restTime = () -> 0.0;
        this.isCounter = true;
        this.fromIdx = fromIdx;
    }

    SelfCheckCounter(int name, int maxWait, int fromIdx) {
        this(0, name, maxWait, fromIdx);

    }

    public boolean canServe(int idx, Customer cus) {
        double diff = this.currTime - cus.getTime();
        return Math.abs(diff) < THRESHOLD || diff < 0;
    }

    public SelfCheckCounter setTime(int idx, double time) {
        return new SelfCheckCounter(time, this.name, this.maxWait, this.fromIdx);
    }

    @Override
    public Servable updateWait(int num) {
        int change = this.currWait + num;
        change = change <= 0 ? 0 : change;
        change = change > this.maxWait ? this.maxWait : change;
        return new SelfCheckCounter(this.getTime(0), this.name, this.maxWait, this.fromIdx);
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

    public double getTime(int idx) {
        return this.currTime;
    }

    public int getIdx() {
        return this.fromIdx;
    }

    @Override
    public String toString() {
        return String.format("self-check %s", this.getName());
    }
}
