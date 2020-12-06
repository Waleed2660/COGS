import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MenuMaker
{
    public static void main(String[] args)
    {   
        int res1 = 1000, res2 = 600; //used for setting resolution of window
        int x = 5, y = 10, xBPos = 100, yBPos = 100;
        RenderWindow window = new MMWindow(res1,res2,"Main menu");
        MMButtons button = new MMButtons(x, y,xBPos, yBPos);

        while(window.isOpen())
        {
            //add code to display buttons etc here / user interaction
            window.clear();
            window.display();
            button.draw(window);

            for(Event event : window.pollEvents())
            {
                
                if(event.type == Event.Type.MOUSE_MOVED)
                {
                    //code for what happens when the mouse is moved inside the window
                }
                if(event.type == Event.Type.MOUSE_LEFT)
                {
                    //code for what happens when the mouse leaves the window
                }
                if(event.type == Event.Type.RESIZED)
                {
                    //need to look up how to find the exterior window size and resize menu window size
                }
                if(event.type == Event.Type.CLOSED)
                {
                    window.close();
                    //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                }
            }
        }
    }
}