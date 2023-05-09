class Done implements Event {
    private final Customer customer;
    private final double time;
    private static final int priority = 0;
    private final Servable server;
    private final boolean toPrint;

    private static final EventType type = EventType.DONE;

    Done(Customer customer, Servable s) {
        this.customer = customer;
        this.time = customer.getTime();
        this.server = s;
        this.toPrint = true;
    }

    public Pair<Event, ImList<Servable>> process(ImList<Servable> server) {
        Servable currServer = server.get(this.server.getIdx());
        if (currServer.isCounter()) {

            return new Pair<Event, ImList<Servable>>(this, server);
        }
        currServer = currServer.setTime(0, currServer.getTime(0) + currServer.getRest());
        server = server.set(this.server.getIdx(),
                currServer);
        return new Pair<Event, ImList<Servable>>(this, server);
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;

    }

    public EventType getType() {
        return Done.type;
    }

    public int getPriority() {
        return Done.priority;
    }

    public boolean canPrint() {
        return this.toPrint;
    }

    public Servable getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s done serving by %s",
                this.getTime(), this.customer.toString(), this.server.toString());
    }

}
