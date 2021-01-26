/**
 * A class that represents the player entity. Extends GameObject.
 */

public class Player extends GameObject
{
    private float speed;
    private float friction;

    public Player(float x, float y, float speed, float friction, String texPath)
    {
        super(x, y, texPath);
        this.speed = speed;
        this.friction = friction;
    }

    public void movement(int xDir, int yDir)
    {
        
    }
}
