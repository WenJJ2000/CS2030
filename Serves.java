class Serves implements Event {

    private final Customer customer;
    private final double time;
    private static final int priority = 2;
    private final Servable server;
    private final boolean toPrint;

    private static final EventType type = EventType.SERVED;

    Serves(Customer customer, Servable s) {
        this.customer = customer;
        this.time = customer.getTime();
        this.server = s;
        this.toPrint = true;
    }

    public Pair<Event, ImList<Servable>> process(ImList<Servable> serverList) {
        Servable currServer = serverList.get(this.server.getIdx());
        Customer newCus = this.customer.setTime(this.server.getTime(0));
        currServer = currServer.updateWait(-1);
        serverList = serverList.set(this.server.getIdx(),
                currServer);
        Event nextEvent = new Done(newCus, currServer);
        if (currServer.isCounter()) {
            nextEvent = new Done(newCus, this.server);
        }
        return new Pair<Event, ImList<Servable>>(nextEvent, serverList);
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;

    }

    public EventType getType() {
        return Serves.type;
    }

    public int getPriority() {
        return Serves.priority;
    }

    public Servable getServer() {
        return this.server;
    }

    public boolean canPrint() {
        return this.toPrint;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s",
                this.getTime(), this.customer.toString(), this.server.toString());

    }

}
