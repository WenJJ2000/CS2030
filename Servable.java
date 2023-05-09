interface Servable {

    boolean canServe(int idx, Customer cus);

    Servable setTime(int idx, double time);

    Servable updateWait(int num);

    int getName();

    int getMaxWait();

    boolean canWait();

    int getWait();

    ImList<Servable> getQ();

    public Servable setQ(ImList<Servable> nextQ);

    boolean isCounter();

    double getTime(int idx);

    int getIdx();

    double getRest();
}
