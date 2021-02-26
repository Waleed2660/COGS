import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.graphics.*;
import java.util.ArrayList;
import java.io.File;

public class PauseMenu implements MenuMaker{
    private TextManager buttons[] = new TextManager[3];
    private MMWindow window;
    private int returnToMenu = 0;

    private boolean pauseMenuIsOpen = true;

    private GameObject background;

    /**
     * Constructs Pause Menu
     */
    public PauseMenu(MMWindow window){
        this.window = window;
        if(new File("resources/common/back.png").isFile())
        {
            background = new GameObject(window.getViewZone().left, window.getViewZone().top, "resources/common/back.png", null);
        }
        buttons[0] = new TextManager("Continue", window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.4), 50);
        buttons[1] = new TextManager("Main Menu", window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.6), 50);
        buttons[2] = new TextManager("Exit", window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.8), 50);
    }

    /**
     * Returns 1 if need to return to main menu and 0 if game should be continued.
     */
    @Override
    public int displayMenu() {

        while(window.isOpen() && pauseMenuIsOpen)
        {
            float mouseX = Mouse.getPosition(window).x + window.getViewZone().left
                    , mouseY = Mouse.getPosition(window).y + window.getViewZone().top;

            window.clear(Color.BLACK);

            window.draw(background);

            // Renders pMenu Buttons
            for (TextManager b : buttons) 
            {
                window.draw(b);
            }
            window.display();
            // Handles Mouse Input
            Event event = window.waitEvent();
            if (event.type == Event.Type.CLOSED) {
                //CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                window.close();
            }
            // Blinks Button
            if (event.type == Event.Type.MOUSE_MOVED) {

                for (TextManager button : buttons) {
                    button.blinkButton(mouseX, mouseY, Color.RED);   //Blinks button
                }
            }
            // When Button is pressed
            if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                checkButtons(mouseX, mouseY);
            }
        }
        return returnToMenu;
    }

    @Override
    public void checkButtons(float MouseX, float MouseY) {

        for (TextManager b : buttons) {
            if (b.buttonPressed(MouseX, MouseY, "Continue")) {
                pauseMenuIsOpen = false;
            }
            else if (b.buttonPressed(MouseX, MouseY, "Main Menu")) {
                pauseMenuIsOpen = false;
                returnToMenu = 1;
            }
            else if (b.buttonPressed(MouseX, MouseY, "Exit")) {
                window.close();
            }
        }
    }

    public void openPauseMenu(MMWindow window){

        float yAxis = (float)0.2;

        for (TextManager b : buttons) {
            b.setPosition(window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*yAxis));
            System.out.println("Button Position: "+b.getPosition());
            yAxis += (float)0.2;
        }
        if(new File("resources/common/back.png").isFile())
        {
            background = new GameObject(0, 0, "resources/common/back.png", window.getViewZone());
        }
        pauseMenuIsOpen = true;
    }
}
