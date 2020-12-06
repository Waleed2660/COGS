
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MMButtons
{
    public RectangleShape rect;
    
    public MMButtons(float x,float y,float xPos, float yPos)
    {
        rect = new RectangleShape(new Vector2f(x,y)); 
        rect.setOrigin(x / 2, y / 2); //center of rectangle
        rect.setPosition(xPos, yPos); // sets x and y position
        
    }
    public void Draw(RenderWindow window)
    {
        window.draw(rect);
    }
}