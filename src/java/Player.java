import java.util.ArrayList;
import org.jsfml.window.event.Event;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

/**
 * A class that represents the player entity. Extends GameObject.
 */

public class Player extends GameObject
{
    private float speed;
    private float friction;
    private int direction = 1;

    public Player(float x, float y, float speed, float friction, String texPath)
    {
        super(x, y, texPath);
        this.speed = speed;
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
     * Checks for collision and if it doesnt hit anything moves the player.
     * 
     * @param xDir x direction to move to
     * @param yDir y direction to move to
     * @param blocks blocks to check for collision with
     */
    public void movement(int xDir, int yDir, ArrayList<GameObject> blocks)
    {
        direction = xDir;
        boolean collides = false;
        for(GameObject a : blocks)
        {
            if(a.getClass() != Player.class)
            {
                if(this.getFutureHitBox(xDir*speed, 0).intersection(a.getHitBox()) != null)
                {
                    collides = true;
                }
            }

        }
        if(!collides)
        {
            this.moveObject(xDir*speed, 0);
        }
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
