import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class MMWindow extends RenderWindow
{
    private View view;
    private FloatRect viewZone; // the rectangle that represents what the user can see
    /** 
     * Used by MenuMaker
     * 
     * Used to create the window in the application.
    */
    public MMWindow(int res1, int res2, String name, boolean screenMode)
    {
        super();
        // creates a new window at User's screensize with a window name "Main menu"
        // Window.style is set to 8 (Full Screen)
        if(screenMode)
        {
            this.create(new VideoMode(res1, res2), name, 1);
        }
        else
        {
            this.create(new VideoMode(res1, res2), name);
        }
        this.setMouseCursorVisible(true); //lets user see cursor
        this.setFramerateLimit(30); //limits the frame rate

        view = new View(new FloatRect(0, 0, res1, res2));
        //view.setViewport(new FloatRect(0, 0, 1, (float)1.5));
        this.setView(view);
        viewZone = new FloatRect(this.getView().getCenter().x-this.getView().getSize().x/2, this.getView().getCenter().y-this.getView().getSize().y/2, this.getView().getSize().x, this.getView().getSize().y);
    }

    /**
     * Moves the view by a certain offset only on the x axis.
     * 
     * @param xOff x axis offset to move the view by.
     * @param yOff y axis offset to move the view by.
     */
    public void moveView(float xOff, float yOff)
    {
        view.move(xOff, yOff);
        viewZone = new FloatRect(this.getView().getCenter().x-this.getView().getSize().x/2, this.getView().getCenter().y-this.getView().getSize().y/2, this.getView().getSize().x, this.getView().getSize().y);
        this.setView(view);
    }

    /**
     * Resets the view to position 0, 0.
     */
    public void resetView()
    {
        view = new View(new FloatRect(0, 0, this.getSize().x, this.getSize().y));
        viewZone = new FloatRect(this.getView().getCenter().x-this.getView().getSize().x/2, this.getView().getCenter().y-this.getView().getSize().y/2, this.getView().getSize().x, this.getView().getSize().y);
        this.setView(view);
    }

    /**
     * Accessor for the view zone.
     * 
     * @return a rectangle representing the user view
     */
    public FloatRect getViewZone()
    {
        return viewZone;
    }

    /**
     * Returns a FloatRect of view with an offset
     * 
     * @param xOff x offset
     * @param yOff y offset
     * @return
     */
    public FloatRect getFutureViewZone(float xOff, float yOff)
    {
        return new FloatRect(this.getView().getCenter().x-this.getView().getSize().x/2+xOff, this.getView().getCenter().y-this.getView().getSize().y/2+yOff, this.getView().getSize().x, this.getView().getSize().y);
    }
}