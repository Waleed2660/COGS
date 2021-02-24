import org.jsfml.graphics.Color;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import java.awt.*;

public class MenuMaker
{
    private Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
    //private int width = screenRes.width, height = screenRes.height; //used for setting resolution of window
    private int width = 1024, height = 640; //used for setting resolution of window
    public MMWindow window = new MMWindow(width,height,"Main menu", false); // changed temp to public for GameOver access
    private TextManager buttons[] = new TextManager[4];     // Hold buttons for Menu

    /**
     * Creates the main menu (for now very basic)
     * Must be first called upon game being run.
     * <p>
     * MenuMaker makes use of MMButtons and MMWindow
     */
    public MenuMaker()
    {
        makerMenu();
    }
    
    public void makerMenu()
    {
        // Creating Button
        buttons[0] = new TextManager("Start",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.5), 30);
        buttons[1] = new TextManager("Help",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.6), 30);
        buttons[2] = new TextManager("Settings",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.7), 30);
        buttons[3] = new TextManager("Exit",window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.8), 30);


        while(window.isOpen()) {
            window.clear(Color.BLACK); //someone forgot to clear before drawing
            // Real-Time Location parameters for Cursor
            float MouseX = Mouse.getPosition(window).x, MouseY = Mouse.getPosition(window).y;

            //add code to display buttons etc here / user interaction
            for (TextManager button : buttons) {
                window.draw(button);    // Draws all buttons
            }

            for(Event event : window.pollEvents()) {

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
            window.display();
        }
    }

    private void checkButtons(float MouseX,float MouseY){
        // Clickable Button
        for (TextManager button : buttons) {
            if (button.buttonPressed(MouseX, MouseY, "Start"))
            {
                if(new GameRunner(window, "Level1").run() == 1)
                {
                    if(new GameRunner(window, "Level2").run() == 1)
                    {
                        if(new GameRunner(window, "Level3").run() == 1)
                        {
                            // game ends here
                        }
                    }
                }
                window.resetView();
            }
            if (button.buttonPressed(MouseX, MouseY, "Help"))  {}
            if (button.buttonPressed(MouseX, MouseY, "Settings"))  {}
            if (button.buttonPressed(MouseX, MouseY, "Exit"))  window.close();
        }
    }
    public static void main(String[] args) {
       new MenuMaker();
    }
}