import java.util.Comparator;

class ServerComparator implements Comparator<Server> {
    private final double threshold = 1E-15;

    public int compare(Server s1, Server s2) {
        if (equals(s1.getTime(0), s2.getTime(0))) {
            return Integer.valueOf(s1.toString()) -
                    Integer.valueOf(s2.toString());

        } else {
            return moreThan(s1.getTime(0), s2.getTime(0));
        }
    }

    int moreThan(double t1, double t2) {
        if (t1 - t2 < threshold) {
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
