import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3); //Multi-threading
        RaceGround game = new RaceGround ("Racing Game - Part 2",850,650);
        pool.execute(game); // Start
        pool.shutdown();  //Close threads
    }
}
