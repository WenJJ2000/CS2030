interface Event {

    Pair<Event, ImList<Servable>> process(ImList<Servable> server);

    double getTime();

    int getPriority();

    Customer getCustomer();

    public String toString();

    public EventType getType();

    boolean canPrint();

}
