import java.util.ArrayList;

public abstract class Entity extends GameObject
{
    private double lastBulletTime = System.currentTimeMillis();

    private float speed, g;

    public Entity(float x, float y, String texPath, float speed, Level level)
    {
        super(x, y, texPath, null);
        this.speed = speed;
        g = level.getGravity();
    }

    /**
     * Returns true if given amount of time has been passed
     *
     * @param delay Delay you want in milliseconds
     * @return boolean value
     */
    public boolean insertDelay(double delay) {
        if (System.currentTimeMillis() - lastBulletTime > delay) {
            lastBulletTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /**
     * A method that should execute any movement for the entity and check for collision.
     *  
     * @param objectsInView an array of the object that are in view and should be checked for collision.
     */
    public abstract void movement(ArrayList<GameObject> objectsInView);
}
