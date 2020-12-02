package MenuMaker;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class MenuMaker
{
    public static void main(String[] args)
    {   
        int res1 = 1000, res2 = 600; //used for setting resolution of window
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(res1, res2), "Main menu"); 
        // creates a new window at 1000x600 with a window name "Main menu"

        window.setFramerateLimit(30); 
        //limits the frame rate

        while(window.isOpen())
        {
            //add code to display buttons etc here
        
            for(Event event : window.pollEvents())
            {
                if(event.type == Event.Type.CLOSED)
                {
                    window.close();
                    //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                }
            }
        }
    }
}