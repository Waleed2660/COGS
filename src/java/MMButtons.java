import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MMButtons extends RectangleShape
{
    /** 
         * Used by MenuMaker
         * <p>
         * Extends RectangleShape in order to make shapes that will be used as buttons
    */
    public MMButtons(float x,float y,float xPos, float yPos)
    {
        super(new Vector2f(x,y)); 
        this.setOrigin(x / 2, y / 2); //center of rectangle
        this.setPosition(xPos, yPos); // sets x and y position
        this.setFillColor(Color.GREEN);
    }
}