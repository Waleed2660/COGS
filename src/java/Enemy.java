import org.jsfml.graphics.FloatRect;

import java.util.ArrayList;

/**
 * A class that represents an enemy entity. Can be either dog or robot. Extends GameObject.
 */

public class Enemy extends GameObject
{
    private float speedY = 0, speedX = 5, g = 10/6;
    private int direction = -1;

    public int hp = 100; //5 hits to ko 20 hp per hit  // enemies dog - 1 hit ko robot 2 - hit ko
    int health = 0; // used to store health for iframes
    private boolean inAir = false;


    public Enemy(float x, float y, String texPath,int hp)
    {
        super(x, y, texPath, null);
        this.hp = hp;
    }

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

                    //  Keeps Enemy from falling from top platform
                    if(this.getFutureHitBox(-5, speedY*-1).intersection(a.getHitBox()) == null) {
                        direction = 1;  // Turns right if edge on left
                    }
                    else if(this.getFutureHitBox(5, speedY*-1).intersection(a.getHitBox()) == null) {
                        direction = -1; // Turns left if edge on right
                    }

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
                    if(a.getHitBox().left >= this.getPosition().x+this.getLocalBounds().width && a.getClass() != Platform.class)
                    {
                        direction = -1;
                    }
                    //if collides on the left
                    else if(a.getHitBox().left+a.getHitBox().width <= this.getPosition().x && a.getClass() != Platform.class)
                    {
                        direction = 1;
                    }
                }
                //If in air for collision diagonally. Temporary fix to bug
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


    }

    /**
    * Returns Bullet Object
     */
    public Bullet shoot()
    {
        if (direction == 1) { // Extended code so that bullet detect doesnt hit player and despawn player
            return new Bullet(direction, this.getPosition().x + 40, this.getPosition().y + this.getLocalBounds().height / 2, "resources/laser.png");
        }
        else {
            return new Bullet(direction, this.getPosition().x - 20, this.getPosition().y + this.getLocalBounds().height / 2, "resources/laser.png");
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
}