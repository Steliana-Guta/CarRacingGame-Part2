import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
public class ManeuveringControl {
    private Handler handler;
    private SpeedControl playerGreen;
    private SpeedControl playerBlue;
    private ArrayList<Maneuvering> maneuvers;
    private boolean collision;
    private int width = 40, height = 40;
    public ManeuveringControl(Handler handler, SpeedControl playerGreen, SpeedControl playerBlue) {
        this.handler = handler;
        this.playerGreen = playerGreen;
        this.playerBlue = playerBlue;
        maneuvers = new ArrayList<Maneuvering>(); // Array for drivers
        addDriver(playerGreen);
        addDriver(playerBlue);
    }
    public void driverCrash() {
        float playerGreenX = maneuvers.get(0).getX();
        float playerGreenY = maneuvers.get(0).getY();
        Rectangle driverBox = new Rectangle((int) playerGreenX + 5, (int) playerGreenY + 5, width, height); // Only one needed
        // As a detection of the other car touching the driverBox is run bellow
        float playerBlueX = maneuvers.get(1).getX();
        float playerBlueY = maneuvers.get(1).getY();
        // Check if drivers crash/touch
        if (driverBox.intersects(playerBlueX + 5, playerBlueY + 5, width, height)) {
            Media.sound.get("crash").play(); // Play crash sound
            collision = true;
        }
    }
    public void maneuvers() {
        for (int i = 0; i < maneuvers.size(); i++) {
            Maneuvering e = maneuvers.get(i);
            e.initiate();
        }
        driverCrash(); // Game lost if drivers crash
    }
    public void render(Graphics g) {
        for(Maneuvering m : maneuvers) {
            m.render(g);
        }
    }
    public void addDriver(Maneuvering m) {
        maneuvers.add(m);
    }
    public ArrayList<Maneuvering> getDrivers() {
        return maneuvers;
    }
    public boolean isCrash() {
        return collision;
    }
}
