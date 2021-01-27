import java.util.ArrayList;
import org.jsfml.window.event.Event;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

/**
 * A class that represents the player entity. Extends GameObject.
 */

public class Player extends GameObject
{
    private float speedY = 0, speedX = 0;
    private float maxSpeedX, maxSpeedY;
    private float friction;
    private int direction = 1;
    private float g = 10/6;

    /**
     * Constructor for player.
     * 
     * @param x position
     * @param y position
     * @param maxSpeedX max speed on the x axis
     * @param maxSpeedY max speed when falling
     * @param friction friction increment which stops the players movements
     * @param texPath texture path
     */
    public Player(float x, float y, float maxSpeedX, float maxSpeedY, float friction, String texPath)
    {
        super(x, y, texPath);
        this.maxSpeedX = maxSpeedX;
        this.maxSpeedY = maxSpeedY;
        this.friction = friction;
    }

    /**
     * Checks if a key is pressed and does stuff accordingly. Moved to GameRunner for now.
     * 
     * @param bullets the bullets array for storing shot bullets
     * @param window the window in which the player is
     * @param blocks the GameObject to check for collision with
     */
    /*public void controller(ArrayList<Bullet> bullets, MMWindow window, ArrayList<GameObject> blocks)
    {
        if(Keyboard.isKeyPressed(Keyboard.Key.LEFT))
        {
            direction = -1;    // Updates Bullet travel direction
            this.movement(direction, 0, blocks);
        }
        if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT))
        {
            direction = 1;     // Updates Bullet travel direction
            this.movement(direction, 0, blocks);
        }

        Event e = window.pollEvent();
        if(e != null && e.type == Event.Type.KEY_PRESSED)
        {
            if(e.asKeyEvent().key == Keyboard.Key.SPACE)
            {
                this.shoot(bullets);
            }
        }
    }*/

    /**
     * Increases the speed of the player causing him to move in the next movement call.
     * 
     * @param direction x direction to move to
     * @param a the acceleration increment
     */
    public void walk(int direction, float a)
    {
        this.direction = direction;
        if(speedX < maxSpeedX)
        {
            speedX += a;
        }
        if(speedX > maxSpeedX)
        {
            speedX = maxSpeedX;
        }
    }

    /**
     * Performs a jump of the given height
     * 
     * @param height the height of the jump
     */
    public void jump(float height)
    {
        if(speedY <= 0)
        {
            speedY = height/5;
        }
    }

    /**
     * Executes any movement for the player.
     * 
     * !!!BUG: for some reason sometimes collision isnt detected and it merges in a corner!!!
     * 
     * @param blocks
     * @param window
     */
    public void movement(ArrayList<GameObject> objectsInView, MMWindow window)
    {
        for(GameObject a : objectsInView)
        {
            if(a.getClass() != Player.class)
            {
                if(this.getFutureHitBox(0, speedY*-1).intersection(a.getHitBox()) != null)
                {
                    speedY = 0;
                }
                else if(this.getFutureHitBox(speedX*direction, 0).intersection(a.getHitBox()) != null)
                {
                    speedX = 0;
                }
            }

        }
        //if(!collidesX && !collidesY)
        //{
            this.moveObject(speedX*direction, speedY*-1);
            speedY -= g;
            if(speedX > 0)
            {
                speedX -= friction;
            }
            else{
                speedX = 0;
            }
            window.moveView(speedX*direction);
        /**}
        else if(!collidesY)
        {
            this.moveObject(0, speedY*-1);
            speedY -= g;
        }
        else{
            speedY = 0;
        }*/

    }

    /**
     * Adds a bullet to the list given.
     * 
     * @param bullets list to add bullets to
     */
    public void shoot(ArrayList<Bullet> bullets)
    {
        bullets.add(new Bullet(direction, this.getPosition().x, this.getPosition().y+this.getLocalBounds().height/2, "resources/laser.png"));
    }
}
