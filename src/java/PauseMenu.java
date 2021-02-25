import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

public class PauseMenu implements MenuMaker{
    private TextManager buttons[] = new TextManager[3];
    private MMWindow window;
    private Boolean returnToMenu = false;

    private Boolean pauseMenuIsOpen = true;

    /**
     * Constructs Pause Menu
     */
    public PauseMenu(MMWindow window){
        this.window = window;
        buttons[0] = new TextManager("Continue", window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.4), 50);
        buttons[1] = new TextManager("Main Menu", window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.6), 50);
        buttons[2] = new TextManager("Exit", window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*(float)0.8), 50);
    }


    @Override
    public void displayMenu(MMWindow window) {

        float mouseX = Mouse.getPosition(window).x + window.getViewZone().left
                , mouseY = Mouse.getPosition(window).y + window.getViewZone().top;

        window.clear(Color.BLACK);

        // Renders pMenu Buttons
        for (TextManager b : buttons) {
            window.draw(b);
        }

        // Handles Mouse Input
        for (Event event : window.pollEvents()) {
            for (TextManager b : buttons) {
                if (event.type == Event.Type.CLOSED) {
                    //CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                    window.close();
                }
                // Blinks Button
                if (event.type == Event.Type.MOUSE_MOVED) {

                    b.blinkButton(mouseX, mouseY, Color.RED);
                }
                // When Button is pressed
                if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                    checkButtons(mouseX, mouseY);
                }
            }
        }
        window.display();
    }

    @Override
    public void checkButtons(float MouseX, float MouseY) {

        for (TextManager b : buttons) {
            if (b.buttonPressed(MouseX, MouseY, "Continue")) {
                pauseMenuIsOpen = false;
            }
            else if (b.buttonPressed(MouseX, MouseY, "Main Menu")) {
                returnToMenu = true;
            }
            else if (b.buttonPressed(MouseX, MouseY, "Exit")) {
                window.close();
            }
        }
    }

    public Boolean pauseMenuIsOpen() {
        return pauseMenuIsOpen;
    }

    public Boolean getReturnToMenu() { return returnToMenu; }

    public void openPauseMenu(MMWindow window){

        float yAxis = (float)0.2;

        for (TextManager b : buttons) {
            b.setPosition(window.getViewZone().left+window.getViewZone().width*(float)0.4, window.getViewZone().top+(window.getViewZone().height*yAxis));
            System.out.println("Button Position: "+b.getPosition());
            yAxis += (float)0.2;
        }
        pauseMenuIsOpen = true;
    }
}
