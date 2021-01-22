import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class Movement extends RectangleShape // THIS WILL NEED TO CHANGE LATER ON JUST A TEMP UNTIL ASSETS ARE MADE!!
{
    /** 
         * Movement foundation
         * <p>
         * Used to test movement and will be foundation to movement and sprite addition
         * 
    */

    public Movement(float x, float y)
    {
        super(new Vector2f(100,100));
        this.setOrigin(50,50);
        this.setPosition(100,100);
        this.setFillColor(Color.BLUE);
    }

    private mvmt()
    {
        
    }
}