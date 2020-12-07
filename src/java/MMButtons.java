import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MMButtons extends RectangleShape
{
    public RectangleShape rect;
    
    public MMButtons(float x,float y,float xPos, float yPos)
    {
        super();
        rect = new RectangleShape(new Vector2f(x,y)); 
        rect.setOrigin(x / 2, y / 2); //center of rectangle
        rect.setPosition(xPos, yPos); // sets x and y position
        rect.setFillColor(Color.GREEN);
        
    }
    public void draw(RenderWindow window)
    {
        window.draw(rect);
    }
}