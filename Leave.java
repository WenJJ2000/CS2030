class Leave implements Event {

    private final Customer customer;
    private final double time;
    private static final int priority = 3;
    private final boolean toPrint;

    private static final EventType type = EventType.LEAVE;

    Leave(Customer customer) {
        this.customer = customer;
        this.time = this.customer.getTime();
        this.toPrint = true;
    }

    public Pair<Event, ImList<Servable>> process(ImList<Servable> server) {
        return new Pair<Event, ImList<Servable>>(this, server);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }

    public EventType getType() {
        return Leave.type;
    }

    public int getPriority() {
        return Leave.priority;
    }

    public boolean canPrint() {
        return this.toPrint;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves", this.getTime(), this.customer);
    }

}
