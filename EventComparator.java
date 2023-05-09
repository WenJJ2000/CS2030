import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    private static final double threshold = 1E-15;

    public int compare(Event p1, Event p2) {
        Event e1 = p1;
        Event e2 = p2;
        if (equals(e1.getTime(), e2.getTime())) {
            return Integer.valueOf(e1.getCustomer().toString()) -
                    Integer.valueOf(e2.getCustomer().toString());

        }

        return moreThan(e1.getTime(), e2.getTime());
    }

    int moreThan(double t1, double t2) {
        if (Math.abs(t1 - t2) < threshold) {
            return 0;
        } else if (t1 - t2 < threshold) {
            return -1;
        } else {
            return 1;
        }
    }

    boolean equals(double t1, double t2) {
        if (Math.abs(t1 - t2) < threshold) {
            return true;
        } else {
            return false;
        }
    }

}
