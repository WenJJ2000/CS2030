class SelfServeCounterManager implements Servable {

    private final ImList<Servable> q;
    private static final double THRESHOLD = 1E-15;
    private final int maxWait;
    private final int currWait;
    private final boolean isCounter;
    private final int idx;

    SelfServeCounterManager(ImList<Servable> start, int maxWait, int currWait, int idx) {
        q = start;
        this.idx = idx;
        this.maxWait = maxWait;
        this.currWait = currWait;
        this.isCounter = true;
    }

    public SelfServeCounterManager setTime(int idx, double time) {
        Servable updatedCounter = q.get(idx).setTime(0, time);
        return new SelfServeCounterManager(q.set(
                idx, updatedCounter), this.maxWait, this.currWait, this.idx);
    }

    public SelfServeCounterManager updateWait(int num) {
        int change = this.currWait + num;
        change = change <= 0 ? 0 : change;
        change = change > this.maxWait ? this.maxWait : change;
        return new SelfServeCounterManager(q, this.maxWait, change, this.idx);

    }

    public boolean canServe(int idx, Customer cus) {
        Servable currCounter = q.get(idx);
        double diff = currCounter.getTime(0) - cus.getTime();
        return Math.abs(diff) < THRESHOLD || diff < 0;
    }

    public int getIdx() {
        return this.idx;
    }

    public boolean isCounter() {
        return this.isCounter;
    }

    public ImList<Servable> getQ() {
        return this.q;
    }

    public Servable setQ(ImList<Servable> nextQ) {
        return new SelfServeCounterManager(
                nextQ, this.maxWait, this.currWait, this.idx);
    }

    public boolean canWait() {
        return this.currWait < this.maxWait;
    }

    public int getWait() {
        return this.currWait;
    }

    public double getTime(int idx) {
        return q.get(idx).getTime(0);
    }

    public double getRest() {
        return 0;
    }

    public int getMaxWait() {
        return this.maxWait;
    }

    public int getName() {
        return q.get(0).getName();
    }

    @Override
    public String toString() {
        return q.get(0).toString();
    }

}
