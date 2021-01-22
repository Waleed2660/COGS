import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * A class for representing a constant object in game. Should be improved. Could be ommited because sprite has bounds.
 */

public class GameObject extends Sprite
{
    private FloatRect hitBox;

    public GameObject(float x, float y, String texturePath)
    {
        super();
        try{
            Texture temp = new Texture();
            temp.loadFromFile(Paths.get(texturePath));
            this.setTexture(temp);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        this.setPosition(x, y);
        setHitBoxToTexture();
    }

    public void setHitBoxToTexture()
    {
        hitBox = new FloatRect(this.getPosition(), new Vector2f(this.getLocalBounds().width, this.getLocalBounds().height));
        //System.out.println(hitBox.toString());
    }

    public void setCustomHitBox(float x, float y, float width, float height)
    {
        hitBox = new FloatRect(x, y, width, height);
    }

    public FloatRect getHitBox()
    {
        return hitBox;
    }
}
