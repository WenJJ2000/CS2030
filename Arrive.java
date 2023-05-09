class Arrive implements Event {

    private final Customer customer;
    private final double time;
    private static final int priority = 4;
    private final boolean toPrint;

    private static final EventType type = EventType.ARRIVE;

    Arrive(Customer customer) {
        this.customer = customer;
        this.time = this.customer.getTime();
        this.toPrint = true;
    }

    int isFree(ImList<Servable> list) {
        for (int i = 0; i < list.size(); i++) {
            Servable currServer = list.get(i);
            if (currServer.isCounter()) {
                ImList<Servable> q = currServer.getQ();
                for (Servable c : q) {
                    if (this.customer.getTime() >= c.getTime(i)) {
                        return i;
                    }
                }
            } else {
                if (this.customer.getTime() >= currServer.getTime(0)) {
                    return i;
                }
            }
        }
        return -1;
    }

    int canWait(ImList<Servable> list) {
        for (int i = 0; i < list.size(); i++) {
            Servable currServer = list.get(i);
            if (currServer.canWait()) {
                return i;
            }
        }
        return -1;
    }

    public Pair<Event, ImList<Servable>> process(ImList<Servable> serverList) {
        ImList<Servable> updatedServer;
        Event nextEvent;
        int free = isFree(serverList);
        int wait = canWait(serverList);
        if (free >= 0) {
            Servable s = serverList.get(free);
            if (!s.isCounter()) {
                s = s.setTime(free, customer.getTime() + customer.getDuration());
                updatedServer = serverList.set(free, s);
                nextEvent = new Serves(customer, s);
            } else {
                ImList<Servable> q = s.getQ();
                updatedServer = serverList;
                nextEvent = this;
                for (int i = 0; i < q.size(); i++) {
                    Servable curr = q.get(i);
                    if (!curr.canServe(i, this.customer)) {
                        continue;
                    }
                    curr = curr.setTime(i, customer.getTime() + customer.getDuration());
                    q = q.set(i, curr);
                    s = s.setQ(q);
                    updatedServer = updatedServer.set(free, s);
                    nextEvent = new Serves(customer, curr);
                    break;
                }
            }
        } else if (wait >= 0) {
            Servable s = serverList.get(wait);
            s = s.updateWait(1);
            updatedServer = serverList.set(wait, s);
            if (!s.isCounter()) {
                nextEvent = new Wait(this.customer, s);
            } else {
                nextEvent = new Wait(this.customer, s.getQ().get(0));
            }
        } else {
            updatedServer = serverList;
            nextEvent = new Leave(customer);
        }

        return new Pair<Event, ImList<Servable>>(nextEvent, updatedServer);
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public boolean canPrint() {
        return this.toPrint;
    }

    public EventType getType() {
        return Arrive.type;
    }

    public int getPriority() {
        return Arrive.priority;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives", this.getTime(), this.customer);

    }

}
