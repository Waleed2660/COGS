import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

public abstract class Entity extends GameObject
{
    protected Hashtable<String, Sound> sounds = new Hashtable<String, Sound>();
    private double lastBulletTime = System.currentTimeMillis();
    private double lastAnimation = System.currentTimeMillis();

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
     * Adds a sound to the entinties sound list.
     * 
     * @param name name of the sound
     * @param path path to the sound file
     */
    public void addSound(String name, String path)
    {
        if(new File(path).isFile())
        {
            try {
                SoundBuffer temp = new SoundBuffer();
                temp.loadFromFile(Paths.get(path));
                sounds.put(name, new Sound(temp));
                //System.out.println(sounds.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
     * Returns true if given amount of time has been passed
     *
     * @param delay Delay you want in milliseconds
     * @return boolean value
     */
    public boolean insertDelayAnimation(double delay) {
        if (System.currentTimeMillis() - lastAnimation > delay) {
            lastAnimation = System.currentTimeMillis();
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
