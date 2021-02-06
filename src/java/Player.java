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
 *  3. adjust the camera follow for Level 2
 */

public class Player extends GameObject
{
    private float speedY = 0, speedX = 0;
    private float maxSpeedX, jumpHeight, friction, g = 10/6;
    private int direction = 1;
    private double lastBulletTime = System.currentTimeMillis();
    public int hp = 100; //5 hits to ko 20 hp per hit  // enemies dog - 1 hit ko robot 2 - hit ko
    int health = 0; // used to store health for iframes

    private boolean inAir = false;
    private FloatRect playArea;

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
        super(x, y, texPath, null);
        this.maxSpeedX = maxSpeedX;
        this.jumpHeight = jumpHeight;
        this.friction = level.getFriction();
        this.g = level.getGravity();
        this.playArea = level.getPlayArea();
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
     * Executes any movement for the player(With checking for collision).
     *  
     * @param objectsInView an array of the object that are in view and should be checked for collision.
     * @param window the game window.
     */
    public void movement(ArrayList<GameObject> objectsInView, MMWindow window)
    {
        //falling flag
        boolean landed = false;
        boolean diagCheck = true;
        float tempX = speedX;

        for(GameObject a : objectsInView)
        {
            if(!a.equals(this) && a.getClass() != Enemy.class && !a.getType().equals("portal"))
            {
                //  Checks if the player collides with anything on the y axis and if it does checks if its above or bellow and changes the speed
                //  so it ends up right next to it. Same for the x axis.
                if(this.getFutureHitBox(0, speedY*-1).intersection(a.getHitBox()) != null)
                {
                    diagCheck = false;
                    //if collides bellow
                    if(a.getHitBox().top >= this.getPosition().y+this.getLocalBounds().height)
                    {
                        landed = true;
                        speedY = (a.getHitBox().top-(this.getPosition().y+this.getLocalBounds().height))*-1;
                    }
                    //if collides above
                    else if(a.getHitBox().top+a.getHitBox().height <= this.getPosition().y && a.getClass() != Platform.class)
                    {
                        speedY = this.getPosition().y-(a.getHitBox().top+a.getHitBox().height);
                    }
                }
                
                if(this.getFutureHitBox(speedX*direction, 0).intersection(a.getHitBox()) != null)
                {
                    diagCheck = false;
                    //if collides on the right
                    if(a.getHitBox().left >= this.getPosition().x+this.getLocalBounds().width && a.getClass() != Platform.class)
                    {
                        speedX = a.getHitBox().left-(this.getPosition().x+this.getLocalBounds().width);
                    }
                    //if collides on the left
                    else if(a.getHitBox().left+a.getHitBox().width <= this.getPosition().x && a.getClass() != Platform.class)
                    {
                        speedX = this.getPosition().x-(a.getHitBox().left+a.getHitBox().width);
                    }
                }
                //If in air for collision diaganolly. Temporary fix to bug
                if(diagCheck)
                {
                    FloatRect diagCollision = this.getFutureHitBox(speedX*direction, speedY*-1).intersection(a.getHitBox());
                    if(diagCollision != null && a.getClass() != Platform.class)
                    {
                        tempX = 0;
                    }
                }
            }

        }
        if(diagCheck)
        {
            speedX = tempX;
        }

        //Checks that it doesnt go off the screen
        if(this.getFutureHitBox(speedX*direction, speedY*-1).intersection(playArea) == null || this.getFutureHitBox(speedX*direction, speedY*-1).intersection(playArea).width != this.getLocalBounds().width)
        {
            speedX = 0;
        }

        if(window.getFutureViewZone(speedX*direction, 0).intersection(playArea).width == window.getViewZone().width &&
            this.getPosition().x >= window.getViewZone().width/3 &&
            this.getPosition().x <= window.getViewZone().left+(window.getViewZone().width/3)*2)
        {
            window.moveView(speedX*direction);
        }

        this.moveObject(speedX*direction, speedY*-1);
        
        if(landed)
        {
            inAir = false;
            speedY = 0;
        }
        else
        {
            inAir = true;
        }
        speedY -= g;
        
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
    public Bullet shoot()
    {
        if (direction == 1) // Extended code so that bullet detect doesnt hit player and despawn player
            return new Bullet(direction, this.getPosition().x + 40, this.getPosition().y + this.getLocalBounds().height / 2, "resources/laser.png");
        else
            return new Bullet(direction, this.getPosition().x - 20, this.getPosition().y + this.getLocalBounds().height / 2, "resources/laser.png");
    }

    /**
     * Reduces player's health leading eventually to ko
     * 
     * @param hp tracks the players health
     */
    public int dmghp()
    {
        hp -= 20;
        if(hp == 0)
        {
            return -1;
        }
        return hp;
    }
    public void invicible()
    {
        health = hp;
        //System.out.println(health);
        hp = -100;
    }

    public void setHP(int hp)
    {
        this.hp = hp;

        //bullets.add(new Bullet(direction, this.getPosition().x+this.getLocalBounds().width*direction, this.getPosition().y+this.getLocalBounds().height/2, "resources/laser.png"));

    }
}
