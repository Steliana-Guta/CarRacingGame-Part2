import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

public class RaceGraphics extends State {
    private ManeuveringControl maneuveringControl;
    private int collisions = 0; // Variable to check for crashing
    public RaceGraphics(Handler handler) {
        super(handler);
        maneuveringControl = new ManeuveringControl(handler, new SpeedControl(handler, 700, 350, Media.playerGreen_move, "wasd"  ),
                new SpeedControl(handler, 750, 350, Media.playerBlue_move, "arrows") ); // Assigning key controls to each driver/car
    }
    @Override
    public void readUserAction() {
        maneuveringControl.maneuvers(); // Read keys
    }
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        Color c1 = new Color(0,153,0); // Dark Green color for the grass
        g.setColor( c1 );
        g.fillRect( 150, 200, 550, 300 ); // Inner grass

        Color c2 = Color.black;
        g.setColor( c2 );
        g.drawRect(50, 100, 750, 500);  // Outer edge of road
        g.drawRect(150, 200, 550, 300); // Inner edge of road

        Color c3 = Color.yellow;
        g.setColor( c3 );
        g.drawRect( 100, 150, 650, 400 ); // Road center line

        Color c4 = Color.white;
        g.setColor( c4 );
        g.fillRect( 700, 350, 800, 5 ); // Start line

        g.setColor(Color.red);
        g.drawLine(50, 350, 150, 350 ); // Checkpoint

        Area outer = new Area(new Rectangle(0, 0, 850, 650 )); // Outer grass area ends
        Rectangle inner = new Rectangle(50, 100, 750, 500); // Outer grass area start
        outer.subtract(new Area(inner)); // Subtract anything in the middle to fill the outer
        g2d.setColor(new Color(0,153,0));  // Dark Green color for the grass
        g2d.fill(outer); // Fill outer grass in dark green
        maneuveringControl.render(g);
        if (maneuveringControl.isCrash()) {gameOver(g);} // Check for car crash
    }
    public void gameOver(Graphics g) {
        for (Maneuvering m : maneuveringControl.getDrivers()) {
            g.drawImage(Media.fire, (int)m.x, (int)m.y,null); // Fire image at crash location
        }
        // Giving the system time to check and render fire on cars
        if (collisions == 2) {
            JOptionPane.showMessageDialog(null, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION); // Game ends
            System.exit(0); // Exit
        }
        collisions++;
    }
}
