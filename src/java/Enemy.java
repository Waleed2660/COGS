import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that represents an enemy entity. Can be either dog or robot. Extends GameObject.
 */

public class Enemy extends Entity
{
    private float speedY = 0, speedX = 5, g = (float)-2.5;
    private int direction = -1;
    private boolean inAir = false;
    private int hp;
    private String name;


    /**
     * Constructor for enemy object
     * 
     * @param x coordinate
     * @param y coordinate
     * @param texPath texture file path
     * @param speed movement speed
     * @param level the level that this enemy is on
     * @param hp the max hit points of the enemy
     */
    public Enemy(float x, float y, String texPath, float speed, Level level, int hp)
    {
        super(x, y, texPath, speed, null);
        this.speedX = speed;
        this.g = level.getGravity();
        this.hp = hp;

        String[] nameList = texPath.split("/");
        String fileName = nameList[nameList.length-1];
        this.name = fileName.split("\\.")[0];
    }

    /**
     * Damages enemy health
     * 
     * @return hp value if above 0 else returns 0
     */
    public int dmghp()
    {
        hp -= 1;
        if(hp == 0)
        {
            return 0;
        }
        return hp;
    }

    /**
     * Executes any movement for the enemy(With checking for collision).
     *
     * @param objectsInView an array of the object that are in view and should be checked for collision.
     */
    @Override
    public void update(ArrayList<GameObject> objectsInView)
    {
        //falling flag
        boolean landed = false;
        boolean edge = true;
        boolean diagCheck = true;
        float tempX = speedX;
        float tempY = speedY;

        for(GameObject a : objectsInView)
        {
            if(!a.equals(this) && a.getClass() != Enemy.class && a.getClass() != Player.class && !a.getType().equals("noCollision"))
            {
                //  Checks if the player collides with anything on the y axis and if it does checks if its above or bellow and changes the speed
                //  so it ends up right next to it. Same for the x axis.
                if(this.getFutureHitBox(speedX*2*direction, tempY*-1).intersection(a.getHitBox()) != null)
                {
                    edge = false;
                }
                if(this.getFutureHitBox(0, speedY*-1).intersection(a.getHitBox()) != null)
                {
                    diagCheck = false;
                    
                    //if collides bellow
                    if(a.getHitBox().top >= this.getPosition().y+this.getLocalBounds().height)
                    {
                        landed = true;
                        speedY = (a.getHitBox().top-(this.getPosition().y+this.getLocalBounds().height))*-1;
                    }
                }

                if(this.getFutureHitBox(speedX*direction, 0).intersection(a.getHitBox()) != null)
                {
                    diagCheck = false;
                    //if collides on the right
                    if(a.getHitBox().left >= this.getPosition().x+this.getLocalBounds().width && !a.getType().equals("platform"))
                    {
                        direction = -1;
                    }
                    //if collides on the left
                    else if(a.getHitBox().left+a.getHitBox().width <= this.getPosition().x && !a.getType().equals("platform"))
                    {
                        direction = 1;
                    }
                }
                //If in air for collision diagonally. Temporary fix to bug
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

        if(landed)
        {
            speedY = 0;
        }
        if(edge)
        {
            direction *= -1;
        }

        this.animate();
        this.moveObject(speedX*direction, speedY*-1);

        speedY += g;
    }

    /**
    * Sets speed
    * 
    * @param x new value of speed
    */
    public void setSpeed(int x) {
        this.speedX = x;
    }
    
    /**
    * Returns Bullet Object
     */
    public Bullet shoot()
    {
        if (direction == 1) { // Extended code so that bullet detect doesnt hit player and despawn player
            return new Bullet(direction, this.getPosition().x + 40, this.getPosition().y + this.getLocalBounds().height -95, 30, "resources/common/laser.png");
        }
        else {
            return new Bullet(direction, this.getPosition().x - 20, this.getPosition().y + this.getLocalBounds().height -95, 30, "resources/common/laser.png");
        }
    }


    /**
     * This method checks if player is in range of current enemy,
     * It changes the enemy's direction to either left/right based on player's location
     * @param player    Object Reference to Player
     */
    public boolean lookForPlayer(Player player){

        //  Checks for player on top & right side of enemy
        if(player.getPosition().x <= this.getPosition().x + 300 && player.getPosition().x >= this.getPosition().x &&
                player.getPosition().y <= this.getPosition().y + 500 && player.getPosition().y >= this.getPosition().y && insertDelay(2500))    {

            return true;
        }
        //  Checks for player on left side of enemy
        else if(player.getPosition().x <= this.getPosition().x && player.getPosition().x >= this.getPosition().x - 300 &&
                player.getPosition().y <= this.getPosition().y + 500 && player.getPosition().y >= this.getPosition().y && insertDelay(2500))    {

            return true;
        }
        return false;
    }

    public void animate()
    {
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
    }

    /**
     * Returns name of current enemy
     * @return
     */
    public String getName() {
        return name;
    }
}