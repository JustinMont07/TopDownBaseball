import java.awt.*;
import javax.swing.*;

/**
 * The FallingBall class is responsible for managing the life of
 * one ball that falls down the screen, stopping when it reaches the
 * bottom of the window.
 * 
 * @author 
 * @version Spring 2022
 */
 class Hit extends AnimatedGraphicsObject{
      // delay time between frames of animation (ms)

    // we don't want to move too quickly, so a delay here of about 33
    // ms will make the loop in run go around about 30 times per
    // second, which is a good enough refresh rate to ensure that the
    // animation looks smooth to the human eye and brain
    public static final int DELAY_TIME = 33;

    // pixels to move each frame
    public int ySpeed;

    public int xSpeed;

    // latest location of the ball
    private Point upperLeft;

    private Point endPoint;


    // how far to fall?
    private int bottom;


    private static Image baseballPic;

    private static final int SIZE = 30;


    private static final String ballPicFilename = "baseball.gif";



    // who do we live in so we can repaint?
    private JComponent container;

    /**
     * Construct a new FallingBall object.
     * 
     * @param startTopCenter the initial point at which the top of the
     *                       ball should be drawn
     * @param container      the Swing component in which this ball is being
     *                       drawn to allow it to call that component's repaint
     *                       method
     */
    public Hit(Point upperLeft, JComponent container, Point endPoint) {
        super(container);

        this.upperLeft = upperLeft;
        this.bottom = container.getHeight();
        this.container = container;
        this.endPoint= endPoint;
        int xMove = endPoint.x - upperLeft.x;
        int yMove = endPoint.y - upperLeft.y;

        ySpeed = yMove / 50;
        xSpeed = xMove / 50;

    }

    /**
     * Draw the ball at its current location.
     * 
     * @param g the Graphics object on which the ball should be drawn
     */
    public void paint(Graphics g) {

       // g.fillOval(upperLeft.x, upperLeft.y, SIZE, SIZE);

       g.drawImage(baseballPic, upperLeft.x, upperLeft.y, null);


    }

    /**
     * This object's run method, which manages the life of the ball as it
     * moves down the screen.
     */
    @Override
    public void run() {

        // the run method is what runs in this object's thread for the
        // time it is "alive"

        // this Ball's life as a thread will continue as long as this
        // ball is still located on the visible part of the screen
        while (!near(upperLeft, endPoint)) {
        

            try {
                sleep(DELAY_TIME);
            } catch (InterruptedException e) {
            }

            // every 30 ms or so, we move the coordinates of the ball down
            // by a pixel
            upperLeft.translate(xSpeed, ySpeed);

            // if we want to see the ball move to its new position, we
            // need to schedule a paint event on this container
            container.repaint();
        }

        done = true;
    }

    public boolean near(Point s, Point e){
        if(s.x > e.x -50 && s.x < e.x + 50){
            if(s.y > e.y -50 && s.y < e.y + 50)
                return true;
        }
        return false;
    }

    
    /**
     * Set the Image to be used by all Ball objects, to be
     * called by the main method before the GUI gets set up
     */
    public static void loadBallPic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();

         Hit.baseballPic = toolkit.getImage(ballPicFilename).getScaledInstance(30, 30, Image.SCALE_DEFAULT);


    }

    /**
     * Set the Image to be used by all Ball objects, to be
     * called by the main method before the GUI gets set up
     */
    public Point getLocation() {

       

        return new Point(upperLeft.x + 15, upperLeft.y + 15);
    }




}