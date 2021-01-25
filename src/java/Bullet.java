import org.jsfml.graphics.FloatRect;

/**
 * This class provides basic bullet functionality
 */
public class Bullet extends GameObject{
    private MMWindow window;
    private float XSpeed = 15;

    /**
     * Creates a bullet object with given texture and coordinates
     *
     * @param x           x-coordinate for the object
     * @param y           y-coordinate for the object
     * @param texturePath the path to the texture to set
     */
    public Bullet(float direction, float x, float y, String texturePath) {
        super(x, y, texturePath);
        moveBullet(direction);
    }

    /**
     * Moves the bullet object given direction
     * @param direction use 1 to fire bullet to right & -1 for left
    */
    public void moveBullet(float direction){
        this.setPosition(this.getPosition().x + XSpeed * direction, this.getPosition().y);
    }

    /**
     * Checks if the bullet is within the boundaries of current frame
     * @param window    Object reference to MMWindow
     * @return          returns boolean
     */
    public boolean bulletInSight(MMWindow window){
        return this.getPosition().x >= 0 && this.getPosition().x <= window.getSize().x;

    }

    /**
     * Checks Collisions between bullet and given (Object) FloatRect
     * @param victimObject  FloatRect for Object
     * @return  returns true if collision detected
     */
    public boolean bulletCollision(FloatRect victimObject){
        return victimObject.contains(this.getPosition());
    }





}
