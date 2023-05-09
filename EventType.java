enum EventType {

    ARRIVE("Arrive"),
    WAIT("Wait"),
    SERVED("Served"),
    DONE("Done"),
    LEAVE("Leave");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    String value() {
        return this.type;
    }

}
