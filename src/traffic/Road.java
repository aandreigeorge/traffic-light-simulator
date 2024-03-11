package traffic;

class Road {

    private final String roadName;
    private boolean roadStatus;
    private int roadTimer;


    Road(String roadName) {
        this.roadName = roadName;
        this.roadStatus = false;
        this.roadTimer = 0;
    }

    public boolean isRoadOpenOrClosed() {
        return roadStatus;
    }

    public void setOpenOrClosedState(boolean openOrClosed) {
        this.roadStatus = openOrClosed;
    }

    String getRoadName() {
        return this.roadName;
    }

    public int getRoadTimer() {
        return roadTimer;
    }

    public void setRoadTimer(int timer) {
        this.roadTimer = timer;
    }

    @Override
    public String toString() {

        String textColor = this.roadStatus ? "\u001B[32m" : "\u001B[31m";
        String status = this.roadStatus ? "open" : "closed";
        String resetColor = "\u001B[0m";
        return String.format("Road %s will be %s%s for %ds.%s", this.roadName, textColor, status, this.roadTimer, resetColor);
    }
}
