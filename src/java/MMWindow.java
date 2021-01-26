import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MMWindow extends RenderWindow
{
    private View view;
    /** 
         * Used by MenuMaker
         * <p>
         * Used to create the window in the application.
    */
    public MMWindow(int res1, int res2, String name)
    {
        super();
        // creates a new window at User's screensize with a window name "Main menu"
        // Window.style is set to 8 (Full Screen)
        this.create(new VideoMode(res1, res2), name, 8);
        this.setMouseCursorVisible(true); //lets user see cursor
        this.setFramerateLimit(30); //limits the frame rate
        view 
    }
}