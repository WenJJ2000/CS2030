import java.util.function.Supplier;
import java.util.Random;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;
    private final Supplier<Double> restTime;

    Simulator(int numOfServers, int numOfSelfChecks, int qmax,
            ImList<Pair<Double, Supplier<Double>>> inputTimes,
            Supplier<Double> restTime) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
        this.restTime = restTime;
        this.numOfSelfChecks = numOfSelfChecks;
    }

    String simulate() {
        String output = "";
        int cusServed = 0;
        double waitTime = 0;
        int cusLeave = 0;
        int count = 1;

        PQ<Event> pq = new PQ<Event>(new EventComparator());
        ImList<Servable> serverList = new ImList<Servable>();

        for (Pair<Double, Supplier<Double>> curr : inputTimes) {
            Customer currCustomer = new Customer(curr.first(), curr.second(), count++);
            Arrive arrive = new Arrive(currCustomer);
            pq = pq.add(arrive);
        }

        for (int i = 0; i < this.numOfServers; i++) {
            serverList = serverList.add(new Server(
                    0, i + 1, this.qmax, this.restTime));
        }
        if (this.numOfSelfChecks > 0) {
            ImList<Servable> q = new ImList<>();
            for (int i = 0; i < this.numOfSelfChecks; i++) {
                q = q.add(new SelfCheckCounter(this.numOfServers + 1 + i, 0, this.numOfServers));
            }
            serverList = serverList.add(
                    new SelfServeCounterManager(q, this.qmax, 0, this.numOfServers));
        }

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> currItem = pq.poll();
            Event event = currItem.first();

            output += event.canPrint() ? event.toString() + "\n" : "";

            Event currEvent = event;
            Pair<Event, ImList<Servable>> nextItem = currEvent.process(serverList);
            Event nextEvent = nextItem.first();

            ImList<Servable> nextServer = nextItem.second();
            switch (currEvent.getType().value()) {
                case "Wait":
                    waitTime += nextEvent.getCustomer().getTime() -
                            currEvent.getCustomer().getTime();
                    break;
                case "Served":
                    cusServed++;
                    break;
                case "Leave":
                    cusLeave++;
                    break;
                default:
                    break;
            }

            pq = nextEvent == event ? currItem.second()
                    : currItem.second().add(nextEvent);

            serverList = nextServer;

        }

        output += String.format("[%.3f %d %d]", waitTime / cusServed,
                cusServed, cusLeave);
        return output;
    }

}
