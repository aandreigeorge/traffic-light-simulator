package traffic;

import java.util.concurrent.TimeUnit;

class QueueThread extends Thread {

    private final CircularQueue roadsQueue;
    private int systemTimer;
    private boolean running, isInSystemMenu;


    QueueThread(CircularQueue roadsQueue) {
        super("QueueThread");
        this.roadsQueue = roadsQueue;
        this.systemTimer = 0;
        this.running = true;
        this.isInSystemMenu = false;
    }


    void flipSystemMenuState() {
        this.isInSystemMenu = !this.isInSystemMenu;
    }

    void stopThread() {
        this.running = false;
    }

    private void printSystemInfo() {

        System.out.printf("""
                ! %ds. hava passed since system startup !
                ! Number of roads: %d !
                ! Interval: %d !%n""", systemTimer, roadsQueue.getIntersectionSize(), roadsQueue.getIntervalTimer());


        roadsQueue.printRoadStatus();

        System.out.printf("""
                ! Press "Enter" to open menu !%n""");
    }

    @Override
    public void run() {

        while (running) {

            try {

                systemTimer++;
                if (isInSystemMenu) {
                    printSystemInfo();
                }
                roadsQueue.updateRoadStatesAndTimers();

                TimeUnit.SECONDS.sleep(1);

                if (isInSystemMenu) {
                    ConsoleCleaner.cleanConsole();
                }

            } catch (InterruptedException e) {
                System.out.println("Interrupted exception in Queue Thread");
            }

        }
    }

}
