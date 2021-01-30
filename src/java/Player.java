import java.util.ArrayList;
import org.jsfml.window.event.Event;
import org.jsfml.graphics.FloatRect;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

/**
 * A class that represents the player entity. Extends GameObject.
 */
/**
 * TODO:
 *  1. properly calculate Y speed in relation to jump height
 *  2. properly calculate the max Y speed in relation to jump height
 *  3. passable but standable platforms
 */

public class Player extends GameObject
{
    private float speedY = 0, speedX = 0;
    private float maxSpeedX, jumpHeight;
    private float friction;
    private int direction = 1;
    private boolean inAir = false;
    private float g;
    private float[] xEdges;

    /**
     * Constructor for player.
     * 
     * @param x position
     * @param y position
     * @param maxSpeedX max speed on the x axis
     * @param jumpHeight the player jump height
     * @param level the level the player is in
     * @param texPath texture path
     */
    public Player(float x, float y, float maxSpeedX, float jumpHeight, Level level, String texPath)
    {
        super(x, y, texPath);
        this.maxSpeedX = maxSpeedX;
        this.jumpHeight = jumpHeight;
        this.friction = level.getFriction();
        this.g = level.getGravity();
        xEdges = level.getEdgeCoords();
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
     */
    public void walk(int direction)
    {
        this.direction = direction;
        speedX = maxSpeedX;
    }

    /**
     * Performs a jump of the set height
     */
    public void jump()
    {
        if(!inAir)
        {
            //needs adjusting
            speedY = jumpHeight/3;
            inAir = true;
        }
    }

    /**
     * Executes any movement for the player.
     *  
     * @param objectsInView an array of the object that are in view and should be checked for collision
     * @param window the game window
     */
    public void movement(ArrayList<GameObject> objectsInView, MMWindow window)
    {
        //falling flag
        boolean landed = false;
    
        //Checks that it doesnt go off the screen
        if(this.getPosition().x+speedX*direction < xEdges[0] || (this.getPosition().x+this.getLocalBounds().width)+speedX*direction > xEdges[1])
        {
            speedX = 0;
        }

        for(GameObject a : objectsInView)
        {
            if(!a.equals(this) && a.getClass() != Enemy.class)
            {
                //  Checks if the player collides with anything on the y axis and if it does checks if its above or bellow and changes the speed
                //  so it ends up right next to it. Same for the x axis.
                FloatRect yCollision = this.getFutureHitBox(0, speedY*-1).intersection(a.getHitBox());
                if(yCollision != null)
                {
                    //if collides bellow
                    if(yCollision.top >= this.getPosition().y+this.getLocalBounds().height)
                    {
                        landed = true;
                        speedY = (yCollision.top-(this.getPosition().y+this.getLocalBounds().height))*-1;
                    }
                    //if collides above
                    else if(yCollision.top+yCollision.height <= this.getPosition().y && a.getClass() != Platform.class)
                    {
                        speedY = this.getPosition().y-(yCollision.top+yCollision.height);
                    }
                }
                FloatRect xCollision = this.getFutureHitBox(speedX*direction, 0).intersection(a.getHitBox());
                if(xCollision != null)
                {
                    //if collides on the right
                    if(xCollision.left >= this.getPosition().x+this.getLocalBounds().width && a.getClass() != Platform.class)
                    {
                        speedX = xCollision.left-(this.getPosition().x+this.getLocalBounds().width);
                    }
                    //if collides on the left
                    else if(xCollision.left+xCollision.width <= this.getPosition().x && a.getClass() != Platform.class)
                    {
                        speedX = this.getPosition().x-(xCollision.left+xCollision.width);
                    }
                }
                //If in air for collision diaganolly. Temporary fix to bug
                if(inAir)
                {
                    FloatRect diagCollision = this.getFutureHitBox(speedX*direction, speedY*-1).intersection(a.getHitBox());
                    if(diagCollision != null && a.getClass() != Platform.class)
                    {
                        speedX = 0;
                    }
                }
            }

        }

        if(window.getViewZone().left+speedX*direction > xEdges[0]
            && (window.getViewZone().left+window.getViewZone().width)+speedX*direction < xEdges[1]
            && this.getPosition().x >= window.getViewZone().left+window.getViewZone().width/3
            && this.getPosition().x <= (window.getViewZone().left+(window.getViewZone().width/3)*2))
        {
            window.moveView(speedX*direction);
        }
        this.moveObject(speedX*direction, speedY*-1);
        
        
        if(landed)
        {
            inAir = false;
            speedY = 0;
        }
        else{
            inAir = true;
            speedY -= g;
        }
        
        //needs adjusting
        if(speedY*-1 > jumpHeight/2)
        {
            speedY = (jumpHeight/2)*-1;
        }

        // reduces the speed gradually
        if(!landed && speedX > 0)
        {
            speedX -= friction/2;
        }
        else if(speedX > 0)
        {
            speedX -= friction;
        }
        else{
            speedX = 0;
        }
    }

    /**
     * Adds a bullet to the list given.
     * 
     * @param bullets list to add bullets to
     */
    public void shoot(ArrayList<Bullet> bullets)
    {
        bullets.add(new Bullet(direction, this.getPosition().x+this.getLocalBounds().width*direction, this.getPosition().y+this.getLocalBounds().height/2, "resources/laser.png"));
    }
}
