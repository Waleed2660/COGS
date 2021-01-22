import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import java.nio.file.Paths;

/**
 * This class creates a Sprite by importing texture from given directory,
 * it also provides functionality to update it's properties
 */
public class Sprites extends Sprite {
    private String spriteName;
    private Texture imageTexture = new Texture();


    public Sprites(String filePath){
        super();
        try {
            imageTexture.loadFromFile(Paths.get(filePath));
        }catch (Exception e){
            System.out.println("Can't load texture");
        }
        this.setTexture(imageTexture);
        setName(filePath);    //Setting sprite name for later use
    }

    /**
     * Assigns sprite name
     * @param name  File name
     */
    private void setName(String name){
        spriteName = name.split("[/.]")[1];
    }

    /**
     * Returns name of the sprite
     * @return  String
     */
    public String getName(){
        return spriteName;
    }

    /**
     * Sets position of the sprite
     * @param x x-coordinate of the sprite
     * @param y y-coordinate of the sprite
     */
    public void setPos(float x, float y){
        this.setPosition(x,y);
    }

    /** Returns true if given object is within the boundaries of object which the method was
     * called on.
     * @param objectPosition    Takes position Vector2f of an object
     * @return          Boolean value
     */
    public boolean checkForCollision(Vector2f objectPosition){
        float xLoc = objectPosition.x, yLoc = objectPosition.y;

        // Checks if given sprite is  is within the bounds of rectangle
        return this.getPosition().x <= xLoc && xLoc <= (this.getPosition().x + this.getLocalBounds().width) &&
                this.getPosition().y <= yLoc && yLoc <= (this.getPosition().y + +this.getLocalBounds().height);
    }
}
