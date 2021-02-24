import org.jsfml.graphics.Color;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

public class LevelSelect implements MenuMaker
{
    private TextManager[] buttons = new TextManager[4];
    private MMWindow window;
    private boolean open = true;
    private int numOfLevels;

    public LevelSelect(MMWindow window, int numOfLevels)
    {
        this.numOfLevels = numOfLevels;
        this.window = window;
        buttons[0] = new TextManager("Level 1: Earth",window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.2), 50);
        buttons[1] = new TextManager("Level 2: Rocket",window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.4), 50);
        buttons[2] = new TextManager("Level 3: Moon",window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.6), 50);
        buttons[3] = new TextManager("Back",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.8), 30);
    }

    public void displayMenu(MMWindow window)
    {
        open = true;
        
        while(window.isOpen() && open)
        {
            window.clear(Color.BLACK);
            // Real-Time Location parameters for Cursor
            float MouseX = Mouse.getPosition(window).x, MouseY = Mouse.getPosition(window).y;

            for (TextManager button : buttons) {
                window.draw(button);    // Draws all buttons
            }

            window.display();

            Event event = window.waitEvent(); //waits for an event to happen instead of constantly spinning in loop
            if(event.type == Event.Type.MOUSE_MOVED) {
                //code for what happens when the mouse is moved inside the window
                for (TextManager button : buttons) {
                    button.blinkButton(MouseX, MouseY, Color.RED);   //Blinks button
                }
            }
            if(event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                checkButtons(MouseX,MouseY);
            }
            if(event.type == Event.Type.CLOSED) {
                window.close();
                //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
            }

        }
    }

    public void checkButtons(float MouseX, float MouseY)
    {
        for (TextManager button : buttons)
        {
            if (button.buttonPressed(MouseX, MouseY, "Level 1: Earth"))
            {
                LevelManager.runLevels(1, numOfLevels, window);
            }
            if (button.buttonPressed(MouseX, MouseY, "Level 2: Rocket"))
            {
                LevelManager.runLevels(2, numOfLevels, window);
            }
            if (button.buttonPressed(MouseX, MouseY, "Level 3: Moon"))
            {
                LevelManager.runLevels(3, numOfLevels, window);
            }
            if (button.buttonPressed(MouseX, MouseY, "Back"))  open = false;
        }
    }
}
