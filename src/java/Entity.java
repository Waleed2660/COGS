import java.util.ArrayList;

public abstract class Entity extends GameObject
{
    private double lastBulletTime = System.currentTimeMillis();

    protected final float speed, g;

    /**
     * Constructor for an enityt object
     * 
     * @param x coordinate
     * @param y coordinate
     * @param texPath the path to the texture
     * @param speed the entity speed on the x axis
     * @param level the level the object is in (used to get enviromental variables like gravity). Can be null if gravity is not used
     */
    public Entity(float x, float y, String texPath, float speed, Level level)
    {
        super(x, y, texPath, null);
        this.speed = speed;
        if(level != null)
        {
            g = level.getGravity();
        }
        else{
            g = 0;
        }
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
    public abstract void update(ArrayList<GameObject> objectsInView);
}
