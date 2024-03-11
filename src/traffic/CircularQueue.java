package traffic;

class CircularQueue {

    private final int intersectionSize, intervalTimer;
    private final Road[] roadsQueue;
    private volatile int front, rear, currentNumberOfRoads;


    CircularQueue(int intersectionSize, int intervalTimer) {
        this.intersectionSize = intersectionSize;
        this.intervalTimer = intervalTimer;
        this.front = this.rear = -1;
        this.currentNumberOfRoads = 0;
        this.roadsQueue = new Road[intersectionSize];
    }


    private boolean isEmpty() {
        return front == -1;
    }

    private boolean isFull() {
        return (front == 0 && rear == intersectionSize - 1) || (front == rear + 1);
    }

    int getIntersectionSize() {
        return this.intersectionSize;
    }

    int getIntervalTimer() {
        return this.intervalTimer;
    }

    synchronized void addRoad(String roadName) {

        if (!isFull()) {
            currentNumberOfRoads++;
            if (isEmpty()) {
                front = 0;
                rear = (rear + 1) % intersectionSize;
                roadsQueue[rear] = new Road(roadName);
                roadsQueue[rear].setOpenOrClosedState(true);
                roadsQueue[rear].setRoadTimer(intervalTimer);
            } else {
                rear = (rear + 1) % intersectionSize;
                roadsQueue[rear] = new Road(roadName);
                roadsQueue[rear].setOpenOrClosedState(false);
                if (currentNumberOfRoads > 2) {
                    int prevElementIndex = (rear - 1 + intersectionSize) % intersectionSize;
                    int prevElementTimer = roadsQueue[prevElementIndex].getRoadTimer();
                    roadsQueue[rear].setRoadTimer(prevElementTimer + intervalTimer);
                } else {
                    roadsQueue[rear].setRoadTimer(roadsQueue[rear - 1].getRoadTimer());
                }
            }
            System.out.println("Road " + roadName + " added to the queue.");

        } else {
            System.out.println("Queue is full! Road " + roadName + "not added.");
        }
    }

    synchronized void deleteRoad() {

        if (!isEmpty()) {
            currentNumberOfRoads--;
            System.out.println("Road " + roadsQueue[front].getRoadName() + " was deleted from the queue.");
            roadsQueue[front].setOpenOrClosedState(false);
            roadsQueue[front] = null;
            if (front == rear) {
                front = rear = -1;
            } else {
                front = (front + 1) % intersectionSize;
            }

        } else {
            System.out.println("Queue is empty! No roads to delete.");
        }
    }

    synchronized void updateRoadStatesAndTimers() {

        for (int i = 0; i < roadsQueue.length; i++) {
            if (roadsQueue[i] != null) {
                if (roadsQueue[i].getRoadTimer() > 1) {
                    roadsQueue[i].setRoadTimer(roadsQueue[i].getRoadTimer() - 1);
                } else if (roadsQueue[i].getRoadTimer() == 1) {
                    if (currentNumberOfRoads == 1) {
                        roadsQueue[i].setOpenOrClosedState(true);
                        roadsQueue[i].setRoadTimer(intervalTimer);
                    } else if (currentNumberOfRoads > 1) {
                        if (roadsQueue[i].isRoadOpenOrClosed()) {
                            roadsQueue[i].setOpenOrClosedState(false);
                            roadsQueue[i].setRoadTimer((currentNumberOfRoads - 1) * intervalTimer);
                        } else {
                            roadsQueue[i].setOpenOrClosedState(true);
                            roadsQueue[i].setRoadTimer(intervalTimer);
                        }
                    }
                }
            }
        }
    }

    synchronized void printRoadStatus() {

        if (!isEmpty()) {
            if (rear >= front) {
                for (int i = front; i <= rear; i++) {
                    System.out.println(roadsQueue[i]);
                }
            } else {
                for (int i = front; i < roadsQueue.length; i++) {
                    System.out.println(roadsQueue[i]);
                }
                for (int i = 0; i <= rear; i++) {
                    System.out.println(roadsQueue[i]);
                }
            }
        }
    }

}

