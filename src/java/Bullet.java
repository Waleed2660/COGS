
/**
 * This class provides basic bullet functionality
 */
public class Bullet extends GameObject{
    private float XSpeed = 30;
    private float direction;

    /**
     * Creates a bullet object with given texture and coordinates
     *
     * @param x           x-coordinate for the object
     * @param y           y-coordinate for the object
     * @param texturePath the path to the texture to set
     */
    public Bullet(float direction, float x, float y, String texturePath) {
        super(x, y, texturePath, null);
        this.direction = direction;
    }

    /**
     * Moves the bullet object given direction
    */
    public void moveBullet(){
        this.moveObject(XSpeed*direction, 0);
    }

    /**
     * Checks if the bullet is within the boundaries of current frame
     * @param window    Object reference to MMWindow
     * @return          returns boolean
     */
    public boolean bulletInSight(MMWindow window){
        return this.getHitBox().intersection(window.getViewZone()) != null;
    }
}
