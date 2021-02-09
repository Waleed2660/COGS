import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * A class for representing an object in game.
 */

public class GameObject extends Sprite
{
    private String type = "";
    private FloatRect hitBox;
    private double lastBulletTime = System.currentTimeMillis();
    private Path textPath; 
    /**
     * Constructor for a game object with a texture also sets the texture.
     * 
     * @param x position
     * @param y position
     * @param texturePath the path to the texture to set
     * @param size if given sets the texture to a part specified from the given file, if null sets the texture to the whole given file
     */
    public GameObject(float x, float y, String texturePath, FloatRect size)
    {
        super();
        textPath = Paths.get(texturePath);
        if(size == null)
        {
            try{
                Texture temp = new Texture();
                temp.loadFromFile(textPath);
                this.setTexture(temp);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            this.setPosition(x, y);
        }
        else
        {
            try{
                Texture temp = new Texture();
                temp.loadFromFile(textPath, new IntRect(size));
                this.setTexture(temp);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            //this.setPosition(size.left, size.top);
            this.setPosition(x, y);
        }
        setHitBoxToTexture();
    }

        /**
     * Constructor for a game object with a texture also sets the texture.
     * 
     * @param x position
     * @param y position
     * @param texturePath the path to the texture to set
     * @param type the name of the object
     * @param size if given sets the texture to a part specified from the given file, if null sets the texture to the whole given file
     */
    public GameObject(float x, float y, String texturePath, String type, FloatRect size)
    {
        super();
        this.type = type;
        textPath = Paths.get(texturePath);
        if(size == null)
        {
            try{
                Texture temp = new Texture();
                temp.loadFromFile(textPath);
                this.setTexture(temp);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            this.setPosition(x, y);
        }
        else
        {
            try{
                Texture temp = new Texture();
                temp.loadFromFile(textPath, new IntRect(size));
                this.setTexture(temp);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            //this.setPosition(size.left, size.top);
            this.setPosition(x, y);
        }
        setHitBoxToTexture();
    }

    /**
     * Constructor for a game object without a texture
     * 
     * @param x position
     * @param y position
     * @param width
     * @param height
     */
    public GameObject(float x, float y, float width, float height)
    {
        super();
        
        this.setPosition(x, y);
        setCustomHitBox(x, y, width, height);
    }

    /**
     * Sets the hitbox to be the same size as the texture
     */
    public void setHitBoxToTexture()
    {
        hitBox = new FloatRect(this.getPosition(), new Vector2f(this.getLocalBounds().width, this.getLocalBounds().height));
        //System.out.println(hitBox.toString());
    }

    /**
     * Sets a hitbox of a custom size.
     * 
     * @param x position
     * @param y position
     * @param width
     * @param height
     */
    public void setCustomHitBox(float x, float y, float width, float height)
    {
        hitBox = new FloatRect(x, y, width, height);
    }

    /**
     * Returns the name of the type of object.
     * @return
     */
    public String getType()
    {
        return type;
    }

    /**
     * Returns the objects hitbox
     * @return hitbox
     */
    public FloatRect getHitBox()
    {
        return hitBox;
    }

    /**
     * Moves the sprite and its hitbox
     * 
     * @param x offset to move by
     * @param y offset to move by
     */
    public void moveObject(float x, float y)
    {
        this.move(x, y);
        hitBox = new FloatRect(hitBox.left+x, hitBox.top+y, hitBox.width, hitBox.height);
    }

    /**
     * Returns the future hitbox of the object
     * 
     * @param xOff future offset
     * @param yOff future offset
     * @return future hitbox
     */
    public FloatRect getFutureHitBox(float xOff, float yOff)
    {
        return new FloatRect(hitBox.left+xOff, hitBox.top+yOff, hitBox.width, hitBox.height);
    }

    /**
     * A collision detection method that checks if the current object collides with any in the given list and returns the one it collides with.
     * 
     * @param listToDetect GameObjects to check for collision with
     * @return null - doesnt collide with anything, GameObject - the object it collides with
     */
    public GameObject collides(ArrayList<GameObject> listToDetect)
    {
        for(GameObject a : listToDetect)
        {
            if(!a.equals(this))
            {
                if(this.getHitBox().intersection(a.getHitBox()) != null)
                {
                    return a;
                }
            }

        }
        return null;
    }
    
    /**
     * Same as collides but for enemies
     * 
     * @param listToDetect Enemy list to check for collision with
     * @return null - doesnt collide with anything, GameObject - the object it collides with
     */
    public GameObject eCollides(ArrayList<Enemy> listToDetect)
    {
        for(GameObject a : listToDetect)
        {
            if(!a.equals(this))
            {
                if(this.getFutureHitBox(10,10).intersection(a.getHitBox()) != null)
                {
                    return a;
                }
            }

        }
        return null;
    }
    public GameObject bCollides(ArrayList<Bullet> listToDetect)
    {
        for(GameObject a : listToDetect)
        {
            if(!a.equals(this))
            {
                if(this.getHitBox().intersection(a.getHitBox()) != null)
                {
                    return a;
                }
            }

        }
        return null;
    }

    /**
     * Returns true if given amount of time has been passed
     * @param delay Delay you want in milliseconds
     * @return  boolean value
     */
    public boolean insertDelay(double delay){
        if (System.currentTimeMillis() - lastBulletTime > delay) {
            lastBulletTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
