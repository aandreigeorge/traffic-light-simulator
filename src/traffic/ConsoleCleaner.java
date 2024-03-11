package traffic;

import java.io.IOException;

class ConsoleCleaner {

    static void cleanConsole() {

        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Some Exception");
        }
    }

}
