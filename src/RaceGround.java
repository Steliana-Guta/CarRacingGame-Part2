import java.awt.*;
import java.awt.image.BufferStrategy;

public class RaceGround implements Runnable{
    private Tracking track;
    public int width, height; // Variables for the game window
    public String title; // Game window title
    private boolean running = false; // When players crash: running = false
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    // States
    private State gameState;
    private State menuState;
    private KeyboardControl keyControl; // Key input
    private MouseControl mouseControl; // Mouse input
    private Handler handler; // Handler
    public KeyboardControl getKeyManager() {
        return keyControl;
    } // Keys control
    public MouseControl getMouseManager() {
        return mouseControl;
    } // Mouse control
    public RaceGround(String title, int width, int height) {
        this.width = width; // Game window width
        this.height = height; // Game window height
        this.title = title; // Game window title
        keyControl = new KeyboardControl();
        mouseControl = new MouseControl();
    }
    private void initiate() {
        track = new Tracking(title, width, height);
        track.getFrame().addKeyListener(keyControl);
        // Need both for good focus
        track.getFrame().addMouseListener(mouseControl);
        track.getCanvas().addMouseListener(mouseControl);
        Media.initiate(); // Loads all car pictures
        handler = new Handler(this);
        // Initialises game state
        gameState = new RaceGraphics(handler);
        menuState = new Menu(handler);
        State.setState(menuState); // Set state to Menu when game loads up
    }
    private void keys() { // Read keyboard
        keyControl.keys();
        if(State.getState() != null)
            State.getState().readUserAction();
    }
    private void render() {
        bs = track.getCanvas().getBufferStrategy(); // Buffers to build/draw to game
        if(bs == null) {
            track.getCanvas().createBufferStrategy(3); // Triple buffer
            return;
        }
        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g.create();
        g.clearRect(0, 0, width, height); // Clear
        g.fillRect(0, 0, handler.getGame().width, handler.getGame().height); // Build
        if(State.getState() != null)
            State.getState().render(g);
        bs.show(); // Make visible
        g.dispose(); // Clear
        g2d.dispose(); // Clear
    }
    public State getGameState() {
        return gameState;
    }
    public void run() {
        running = true;
        initiate();
        // Manually setting speed settings, needed for part 3 so all machines use the same settings
        int fps = 60; // Frames per sec
        double timePerTick = 1000000000 / fps; // Measuring time
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); // System cloc
        // Loop for the game
        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;
            if(delta >= 1) {
                keys();
                render();
                delta--;
            }
        }
        stop();
    }
    public synchronized void stop() {
        if(!running) // Cannot stop an already stopped thread
            return;
        running = false;
        try {
            thread.join(); // Thread stop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
