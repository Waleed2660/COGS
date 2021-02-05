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
    private int xBPos = width/2, yBPos = height/2; //used for setting button size and position
    public MMWindow window = new MMWindow(width,height,"Main menu", false); // changed temp to public for GameOver access
    private TextManager buttons[] = new TextManager[4];     // Hold buttons for Menu
    //private GameRunner game = new GameRunner(window, "Level1");

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
        buttons[0] = new TextManager("Start",xBPos/(float)6, yBPos+(yBPos*(float)0.10));
        buttons[1] = new TextManager("Help",xBPos/(float)6, yBPos+(yBPos*(float)0.25));
        buttons[2] = new TextManager("Settings",xBPos/(float)6, yBPos+(yBPos*(float)0.40));
        buttons[3] = new TextManager("Exit",xBPos/(float)6, yBPos+(yBPos*(float)0.55));


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
            if (button.buttonPressed(MouseX, MouseY, "Start")) new GameRunner(window, "Level1").run();
            if (button.buttonPressed(MouseX, MouseY, "Help"))  {}
            if (button.buttonPressed(MouseX, MouseY, "Settings"))  {}
            if (button.buttonPressed(MouseX, MouseY, "Exit"))  window.close();
        }
    }
    public static void main(String[] args) {
       new MenuMaker();
    }
}