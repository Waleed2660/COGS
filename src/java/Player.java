import java.util.ArrayList;
import org.jsfml.window.event.Event;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

/**
 * A class that represents the player entity. Extends GameObject.
 */

public class Player extends Entity
{
    private float speedY = 0, speedX = 0;
    private float jumpHeight, friction;
    private int direction = 1;
    private double lastBulletTime = System.currentTimeMillis();
    private float hp = 100000; //5 hits to ko 20 hp per hit  // enemies dog - 1 hit ko robot 2 - hit ko // fires 2 damage more often than enemy damage
    private boolean inAir = false;
    private boolean crouched = false;
    private FloatRect playArea;
    private MMWindow window;
    private int textureNumber = 0;

    private Texture idle;
    private ArrayList<Texture> walkingAnim;

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
    public Player(float x, float y, float maxSpeedX, float jumpHeight, Level level, MMWindow window, String texPath, ArrayList<Texture> walkingAnim)
    {
        super(x, y, texPath, maxSpeedX, level);
        idle = (Texture)this.getTexture();
        this.jumpHeight = jumpHeight;
        this.window = window;
        this.friction = level.getFriction();
        this.playArea = level.getPlayArea();
        this.walkingAnim = walkingAnim;
    }

    /**
     * Increases the speed of the player causing him to move in the next movement call.
     * 
     * @param direction x direction to move to
     */
    public void walk(int direction)
    {
        this.direction = direction;
        if(speedX <= speed)
        {
            speedX += speed*(friction*1.5);
        }
    }

    /**
     * Performs a jump of the set height
     */
    public void jump()
    {
        if(!inAir)
        {
            speedY = (float)Math.sqrt(-2*g*jumpHeight);
            inAir = true;
        }
    }

    /**
     * Makes the player crouch.
     * 
     * Wasnt implemented yet.
     */
    public void crouch()
    {
        crouched = true;
    }

    /**
    *  Here for when the enenmy hits the player to get them away wasn't sure what else to do tbh
    */
    public void hitAway()
    {
        speedY = jumpHeight/10;
    }
    
    /**
     * Executes any movement for the player(With checking for collision).
     *  
     * @param objectsInView an array of the object that are in view and should be checked for collision.
     */
    @Override
    public void update(ArrayList<GameObject> objectsInView)
    {
        //falling flag
        boolean landed = false;
        boolean diagCheck = true;
        float tempX = speedX;

        for(GameObject a : objectsInView)
        {
            if(!a.equals(this) && !(a instanceof Enemy) && !a.getType().equals("portal") && !a.getType().equals("fire") && !a.getType().equals("hp") && !a.getType().equals("rapidfire") && !a.getType().equals("invincibility"))
            {
                //  Checks if the player collides with anything on the y axis and if it does checks if its above or bellow and changes the speed
                //  so it ends up right next to it. Same for the x axis.
                if(this.getFutureHitBox(0, speedY*-1).intersection(a.getHitBox()) != null)
                {
                    diagCheck = false;
                    //if collides bellow
                    if(a.getHitBox().top >= this.getPosition().y+this.getLocalBounds().height)
                    {
                        if(a.getType().equals("platform") && crouched)
                        {
                            inAir = true;
                        }
                        else
                        {
                            landed = true;
                            speedY = (a.getHitBox().top-(this.getPosition().y+this.getLocalBounds().height))*-1;
                        }

                    }
                    //if collides above
                    else if(a.getHitBox().top+a.getHitBox().height <= this.getPosition().y && !a.getType().equals("platform"))
                    {
                        speedY = this.getPosition().y-(a.getHitBox().top+a.getHitBox().height);
                    }
                }
                
                if(this.getFutureHitBox(speedX*direction, 0).intersection(a.getHitBox()) != null)
                {
                    diagCheck = false;
                    //if collides on the right
                    if(!a.getType().equals("platform"))
                    {
                        if(a.getHitBox().left >= this.getPosition().x+this.getLocalBounds().width)
                        {
                            speedX = a.getHitBox().left-(this.getPosition().x+this.getLocalBounds().width);
                        }
                        //if collides on the left
                        else if(a.getHitBox().left+a.getHitBox().width <= this.getPosition().x)
                        {
                            speedX = this.getPosition().x-(a.getHitBox().left+a.getHitBox().width);
                        }
                    }

                }
                //If in air for collision diaganolly. Temporary fix to bug
                if(diagCheck)
                {
                    FloatRect diagCollision = this.getFutureHitBox(speedX*direction, speedY*-1).intersection(a.getHitBox());
                    if(diagCollision != null && !a.getType().equals("platform"))
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
        if(this.getPosition().x+(speedX*direction) <= playArea.left || this.getPosition().x+(speedX*direction)+this.getLocalBounds().width >= playArea.left+playArea.width)
        {
            speedX = 0;
        }

        if(window.getFutureViewZone(speedX*direction, 0).intersection(playArea).width == window.getViewZone().width &&
            this.getPosition().x >= window.getViewZone().width/3 &&
            this.getPosition().x <= window.getViewZone().left+(window.getViewZone().width/3)*2)
        {
            window.moveView(speedX*direction, 0);
        }
        if( window.getFutureViewZone(0, speedY*-1).top > playArea.top &&
            window.getFutureViewZone(0, speedY*-1).top+window.getViewZone().height < playArea.top+playArea.height &&
            this.getPosition().y >= window.getViewZone().height/2 &&
            this.getPosition().y <= window.getViewZone().top+window.getViewZone().height/2)
        {
            window.moveView(0, speedY*-1);
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
        speedY += g;
        crouched = false;

        // reduces the speed gradually relative to the friction coefficient
        if(!landed && speedX > 0)
        {
            speedX -= speed*(friction/2);
        }
        else if(speedX > 0)
        {
            speedX -= speed*friction;
        }
        if(speedX < 0){
            speedX = 0;
        }
    }

    /**
     * Returns Bullet Object
     */
    public Bullet shoot()
    {
        if (direction == 1) // Extended code so that bullet detect doesnt hit player and de-spawn player
            return new Bullet(direction, this.getPosition().x + this.getHitBox().width + 20, this.getPosition().y + this.getLocalBounds().height / 2, 30, "resources/common/laser.png");
        else
            return new Bullet(direction, this.getPosition().x - 20, this.getPosition().y + this.getLocalBounds().height / 2, 30, "resources/common/laser.png");
    }

    /**
     * Reduces player's health leading eventually to ko
     * 
     * @param hp tracks the players health
     */
    public float dmghp(int dmgpt)
    {
        hp -= dmgpt;
        if(hp <= 0)
        {
            return -1;
        }
        return hp;
    }

    /**
     * Resets player's hp after death incase of restart
     * 
     * @param hp tracks the player's health
     */
    public void setHP(float hp)
    {
        this.hp = hp;
    }

    /**
     * Returns value of direction
     * 1 = player facing right
     * -1 = player facing left
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Loads character sprites. Contains code for switching between sprites in order.
     */
    public void animation()
    {
        this.setTexture(walkingAnim.get(textureNumber));
        if(direction == -1)
        {
            this.setOrigin((float)this.getLocalBounds().width, 0);
            this.setScale((float)direction, (float)1);
        }
        else
        {
            this.setOrigin(0, 0);
            this.setScale((float)direction, (float)1);
        }
    
        System.out.println(this.getOrigin());
        textureNumber += 1;
        if(textureNumber > walkingAnim.size()-1)
        {
            textureNumber = 0;
        }
    }
}