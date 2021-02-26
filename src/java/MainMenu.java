import org.jsfml.graphics.Color;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import java.awt.*;
import java.io.File;
import org.jsfml.graphics.*;
import java.util.ArrayList;


public class MainMenu extends LevelManager implements MenuMaker
{
    private int numOfLevels = 3;

    private Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
    //private int width = screenRes.width, height = screenRes.height; //used for setting resolution of window
    private int width = 1024, height = 640; //used for setting resolution of window
    private MMWindow window = new MMWindow(width,height,"Main menu", false);
    private LevelSelect levelSelect;

    private GameObject background;

    private TextManager buttons[] = new TextManager[4];     // Hold buttons for Menu

    /**
     * Creates the main menu (for now very basic)
     * Must be first called upon game being run.
     * <p>
     * MenuMaker makes use of MMButtons and MMWindow
     */
    public MainMenu()
    {
        if(new File("resources/common/back.png").isFile())
        {
            background = new GameObject(window.getViewZone().left, window.getViewZone().top, "resources/common/back.png", null);
        }
        buttons[0] = new TextManager("Start",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.5), 30);
        buttons[1] = new TextManager("Level Select",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.6), 30);
        buttons[2] = new TextManager("Settings",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.7), 30);
        buttons[3] = new TextManager("Exit",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.8), 30);

        levelSelect = new LevelSelect(window, numOfLevels);

        displayMenu();
    }
    
    @Override
    public int displayMenu()
    {
        // Creating Button
        while(window.isOpen())
        {
            window.clear(Color.BLACK);
            // Real-Time Location parameters for Cursor
            float MouseX = Mouse.getPosition(window).x, MouseY = Mouse.getPosition(window).y;

            FloatRect view = window.getViewZone();

            window.draw(background);
            
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
        return 0;
    }

    @Override
    public void checkButtons(float MouseX,float MouseY)
    {
        // Clickable Button
        for (TextManager button : buttons) {
            if (button.buttonPressed(MouseX, MouseY, "Start"))
            {
                this.runLevels(1, numOfLevels, window);
            }
            if (button.buttonPressed(MouseX, MouseY, "Level Select"))
            {
                levelSelect.displayMenu();
            }
            if (button.buttonPressed(MouseX, MouseY, "Settings"))  {}
            if (button.buttonPressed(MouseX, MouseY, "Exit"))  window.close();
        }
    }

    public static void main(String[] args) {
       new MainMenu();
    }
}