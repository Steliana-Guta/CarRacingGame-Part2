import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SpeedControl extends GrassCollision {
    private CarFrames rotation; // Cra images from Move Sheet
    public static int currentImageIndex1 = 0, currentImageIndex2 = 0; // Both cars start at 0 degrees
    private float xSpeed = 0; // Temporary speed variable
    String controls; // String to separate the 2 car control keys
    public SpeedControl(Handler handler, float x, float y, BufferedImage[] car, String tempControls) {
        super(handler, x, y, GrassCollision.DEFAULT_PLAYER_WIDTH, GrassCollision.DEFAULT_PLAYER_HEIGHT, car );
        boundary.width = 40; // Hit grass if car is 20% smaller than the 50x50 car image, accounting for clear background
        boundary.height = 40; // Hit grass if car is 20% smaller than the 50x50 car image, accounting for clear background
        controls = tempControls;
        rotation = new CarFrames(car);
    }
    @Override
    public void initiate() {
        getInput();
        laps();
        moveX();
        moveY();
    }

    //Update any variables for the object
    private void getInput() {
        xMove = 0;
        yMove = 0;
        //https://stackoverflow.com/questions/44470075/handling-wasd-and-arrows-simultaneously-in-javascript-gamey
        if (controls == "wasd") {
            if(handler.getKeyControl().upKey) {
                setSpeed(1); // Increase speed each time arrow press
                speed = xSpeed;
                Media.sound.get("speed").play(); // Play sound
            }
            if(handler.getKeyControl().downKey) {
                setSpeed(-1); // Decreases speed each time arrow press
                speed = xSpeed;
            }
            direction(currentImageIndex1);
        }
        if (controls == "arrows") {
            //Speed
            if(handler.getKeyControl().upArrow) {
                setSpeed(1); // Increase speed each time arrow press
                speed = xSpeed;
                Media.sound.get("speed").play(); // Play sound
            }
            if(handler.getKeyControl().downArrow) {
                setSpeed(-1); // Decreases speed each time arrow press
                speed = xSpeed;
            }
            direction(currentImageIndex2);
        }
        getCurrentAngleFrame();
        // Delay to make rotation slower, car easier to control
        try {
            Thread.sleep(40); // After testing found 40 to be the best for easy controls and good fps
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void render(Graphics g) {
        if (controls == "wasd") {
            g.drawImage(rotation.getCurrentFrame(currentImageIndex1), (int)x, (int)y, width, height, null); //Driver 1 draw
        }
        if (controls == "arrows") {
            g.drawImage(rotation.getCurrentFrame(currentImageIndex2), (int)x, (int)y, width, height, null); //Driver 2 draw
        }
    }
    // Car angle rotations
    private void getCurrentAngleFrame() {
        if (controls == "wasd") {
            if(handler.getKeyControl().leftKey) { // If move to the left
                if (currentImageIndex1 <= 0) // If current index is 0 or lower (initial index = 0)
                {currentImageIndex1 = Media.playerGreen_move.length;} // Set index to 16 as Green moves .length
                --currentImageIndex1; // Then subtract 1 for each left move
            } else if (handler.getKeyControl().rightKey) { // If move to the right
                if (currentImageIndex1 >= Media.playerGreen_move.length - 1) // If index bigger or equal to 15
                {currentImageIndex1 = -1;} // Index equals to - 1
                ++currentImageIndex1; // The add 1 for each right move
            }
        }
        if (controls == "arrows") {
            if(handler.getKeyControl().leftArrow) { // If move to the left
                if (currentImageIndex2 <= 0)  // If current index is 0 or lower (initial index = 0)
                {currentImageIndex2 = Media.playerBlue_move.length;} // Set index to 16 as Green moves .length
                --currentImageIndex2; // Then subtract 1 for each left move
            } else if (handler.getKeyControl().rightArrow) { // If move to the right
                if (currentImageIndex2 >= Media.playerBlue_move.length - 1) // If index bigger or equal to 15
                {currentImageIndex2 = -1;} // Index equals to - 1
                ++currentImageIndex2; // The add 1 for each right move
            }
        }
    }
    protected void setSpeed(int delta) {
        xSpeed += delta;
        // Check the change in speed
        if (xSpeed < 0) {
            xSpeed = -1; // Allowing reverse if stuck
        } else if (xSpeed > 9) {
            xSpeed = 9; // Max speed
        }
    }
}
