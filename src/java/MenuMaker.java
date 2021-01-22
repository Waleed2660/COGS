import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;


public class MenuMaker
{
    private int width = 1920, height = 1080; //used for setting resolution of window
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
        // Origin parameters for text
        float originX = width * (float)(0.07), originY = height * (float)(0.05);
        buttons[0] = new TextManager("Start",originX,originY, xBPos/(float)6, yBPos+80);
        buttons[1] = new TextManager("Help",originX,originY, xBPos/(float)6, yBPos+(2*80));
        buttons[2] = new TextManager("Settings",originX,originY, xBPos/(float)6, yBPos+(3*80));
        buttons[3] = new TextManager("Exit",originX,originY, xBPos/(float)6, yBPos+(4*80));


        while(window.isOpen()) {
            // Real-Time Location parameters for Cursor
            float MouseX = Mouse.getPosition(window).x, MouseY = Mouse.getPosition(window).y;

            //add code to display buttons etc here / user interaction
            window.clear(new Color(165,158,158));
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