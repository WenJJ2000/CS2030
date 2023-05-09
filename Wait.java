class Wait implements Event {

    private final Customer customer;
    private final double time;
    private static final int priority = 1;
    private final Servable server;
    private final boolean toPrint;

    private static final EventType type = EventType.WAIT;

    Wait(Customer customer, Servable s) {
        this.customer = customer;
        this.time = this.customer.getTime();
        this.server = s;
        this.toPrint = true;
    }

    Wait(Customer customer, Servable s, boolean p) {
        this.customer = customer;
        this.server = s;
        this.time = this.customer.getTime();
        this.toPrint = p;
    }

    public Pair<Event, ImList<Servable>> process(ImList<Servable> serverList) {
        Servable s = serverList.get(this.server.getIdx());
        Customer newCus;
        Event nextEvent;
        if (!s.isCounter()) {
            if (s.canServe(0, customer)) {
                newCus = this.customer.setTime(s.getTime(0));
                s = s.setTime(0, s.getTime(0) + this.customer.getDuration());
                nextEvent = new Serves(newCus, s);
            } else {
                newCus = this.customer.setTime(s.getTime(0));
                nextEvent = new Wait(newCus, s, false);
            }
            serverList = serverList.set(this.server.getIdx(), s);

        } else {
            nextEvent = new Wait(this.customer, s);
            ImList<Servable> q = s.getQ();
            boolean hasServe = false;
            for (int i = 0; i < q.size(); i++) {
                Servable currSelfCounter = q.get(i);
                if (currSelfCounter.canServe(i, this.customer)) {
                    newCus = this.customer.setTime(currSelfCounter.getTime(0));
                    currSelfCounter = currSelfCounter.setTime(0,
                            currSelfCounter.getTime(0) + this.customer.getDuration());
                    q = q.set(i, currSelfCounter);
                    s = s.setQ(q);
                    nextEvent = new Serves(newCus, currSelfCounter);
                    hasServe = true;
                    break;
                }
            }
            if (!hasServe) {
                Servable earliestCounter = q.get(0);
                for (int i = 0; i < q.size(); i++) {
                    Servable curr = q.get(i);
                    if (earliestCounter.getTime(0) > curr.getTime(0)) {
                        earliestCounter = curr;
                    }
                }
                newCus = this.customer.setTime(earliestCounter.getTime(0));
                nextEvent = new Wait(newCus, s, false);
            }
            serverList = serverList.set(this.server.getIdx(), s);

        }
        return new Pair<Event, ImList<Servable>>(nextEvent, serverList);
    }

    public double getTime() {
        return this.time;
    }

    public boolean canPrint() {
        return this.toPrint;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public EventType getType() {
        return Wait.type;
    }

    public int getPriority() {
        return Wait.priority;
    }

    public Servable getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s",
                this.getTime(), this.customer.toString(), this.server.toString());

    }
}
