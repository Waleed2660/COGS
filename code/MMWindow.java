import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MMWindow extends RenderWindow
{
    public MMWindow(int res1, int res2, String name)
    {
        super();
        this.create(new VideoMode(res1, res2), name);         // creates a new window at 1000x600 with a window name "Main menu"
        this.setMouseCursorVisible(true); //lets user see cursor
        this.setFramerateLimit(30); //limits the frame rate
    }

}