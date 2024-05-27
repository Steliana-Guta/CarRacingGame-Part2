import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Maneuvering {
    protected Handler handler;
    protected float x,y; // Float for smooth movement of car
    protected int width, height;
    protected Rectangle boundary; // Player boundary for collision detection
    public Maneuvering(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boundary = new Rectangle(0,0,width,height);
    }
    public abstract void initiate(); // Initiate
    public abstract void render(Graphics g); // Apply graphics
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
