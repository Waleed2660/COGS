import org.jsfml.graphics.Color;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import java.awt.*;

public class MenuMaker
{
    private Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = screenRes.width, height = screenRes.height; //used for setting resolution of window
    private int xBPos = width/2, yBPos = height/2; //used for setting button size and position
    private MMWindow window = new MMWindow(width,height,"Main menu");
    private TextManager buttons[] = new TextManager[4];     // Hold buttons for Menu
    private GameRunner game = new GameRunner();

    /**
     * Creates the main menu (for now very basic)
     * Must be first called upon game being run.
     * <p>
     * MenuMaker makes use of MMButtons and MMWindow
     */
    public MenuMaker(){

        // Creating Button
        buttons[0] = new TextManager("Start",xBPos/(float)6, yBPos+(yBPos*(float)0.10));
        buttons[1] = new TextManager("Help",xBPos/(float)6, yBPos+(yBPos*(float)0.25));
        buttons[2] = new TextManager("Settings",xBPos/(float)6, yBPos+(yBPos*(float)0.40));
        buttons[3] = new TextManager("Exit",xBPos/(float)6, yBPos+(yBPos*(float)0.55));


        while(window.isOpen()) {
            // Real-Time Location parameters for Cursor
            float MouseX = Mouse.getPosition(window).x, MouseY = Mouse.getPosition(window).y;

            //add code to display buttons etc here / user interaction
            for (TextManager button : buttons) {
                window.draw(button);    // Draws all buttons
            }
            window.display();

            for(Event event : window.pollEvents()) {

                if(event.type == Event.Type.MOUSE_MOVED) {
                    //code for what happens when the mouse is moved inside the window
                    for (TextManager button : buttons) {
                        button.blinkButton(MouseX, MouseY, Color.RED);   //Blinks button
                    }
                }
                if(event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                    //code for what happens when the mouse leaves the window
                    //temp test file to see transition
                    game.run(window);
                }
                if(event.type == Event.Type.RESIZED) {
                   //need to look up how to find the exterior window size and resize menu window size
                }
                if(event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                    // Clickable Button
                    buttonPressed(MouseX,MouseY);
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT)){

                }
                if(event.type == Event.Type.CLOSED || Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                    window.close();
                    //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                }
            }
        }
    }

    /**
     * This method checks if any button is pressed and does the corresponding action
     * @param MouseX    x-coordinate of the mouse
     * @param MouseY    y-coordinate of the mouse
     */
    public void buttonPressed(float MouseX, float MouseY){
        for (TextManager button : buttons) {
            // Starts Game
            if (button.blinkButton(MouseX, MouseY, Color.RED) && button.getString().equals("Start")) {
                // Level Selector Class can be called here
            }
            // Opens Help Menu
            else
            if (button.blinkButton(MouseX, MouseY, Color.RED) && button.getString().equals("Help")) {

            }
            // Opens Settings
            else
            if (button.blinkButton(MouseX, MouseY, Color.RED) && button.getString().equals("Settings")) {

            }
            // Closes Window
            else
            if (button.blinkButton(MouseX, MouseY, Color.RED) && button.getString().equals("Exit")) {
                window.close();
            }
        }
    }

    public static void main(String[] args) {
       new MenuMaker();
    }
}