import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MenuMaker
{
    /** 
         * Creates the main menu (for now very basic)
         * Must be first called upon game being run.
         * <p>
         * MenuMaker makes use of MMButtons and MMwindow
         * 
         * @param int user specified resolution 
    */
    public static void main(String[] args)
    {   
        int res1 = 1024, res2 = 640; //used for setting resolution of window
        int x = 100, y = 100, xBPos = 100, yBPos = 100; //used for setting button size and position
        MMWindow window = new MMWindow(res1,res2,"Main menu");
        MMButtons button = new MMButtons(x, y,xBPos, yBPos);
        GameRunner game = new GameRunner();

        while(window.isOpen())
        {
            //add code to display buttons etc here / user interaction
            window.clear(Color.BLUE);
            window.draw(button);
            window.display();

            for(Event event : window.pollEvents())
            {
                
                if(event.type == Event.Type.MOUSE_MOVED)
                {
                    //code for what happens when the mouse is moved inside the window
                }
                if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
                {
                    //code for what happens when the mouse leaves the window
                    //temp test file to see transition
                    game.run(window);
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