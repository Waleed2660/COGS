import org.jsfml.graphics.*;
import java.io.IOException;
import java.nio.file.*;


public class TextManager extends Text{
    private Font arial = new Font();

    /**
     * Used by MenuMaker
     * <p>
     * Inserts Text
     * @param x width of object
     * @param y height of object
     * @param xPos  Position on x-axis
     * @param yPos  Position on y-axis
    */
        public TextManager(String str, float x, float y, float xPos, float yPos) {
            super();                            // creates an empty text
            createFont();
            this.setString(str);
            this.setColor(Color.BLACK);
            this.setCharacterSize((int) (xPos * 0.24));
            this.setStyle(Text.BOLD);
            this.setFont(arial);
            this.setOrigin(0, 0);          // center of rectangle
            this.setPosition(xPos, yPos);       // sets x and y position

            System.out.println("TEXT: "+ this.getLocalBounds());
        }

        /**
         * This method creates Font from given .ttf file
         */
        private void createFont() {
            String filePath = "resources/arial.ttf";
            try {
                arial.loadFromFile(Paths.get(filePath));
            } catch (IOException e) {
                System.out.println("Couldn't load Font for textHandler");
            }
        }

        /**
         * Updates Position for text
         * @param x     width of text
         * @param y     height of text
         * @param xPos  Position on x-axis
         * @param yPos  Position on y-axis
         */
        public void updateText(float xPos, float yPos){
           // this.setPosition(xPos, yPos);       // sets x and y position
            this.setCharacterSize((int) (xPos/5 * 0.12));
        }

    /** Returns true if cursor is within the boundaries of Text Box
     * @param MouseX    x-coordinate for mouse
     * @param MouseY    y-coordinate for mouse
     * @param col       new Color for text
     * @return          Boolean value
     */
    public boolean blinkButton(float MouseX, float MouseY,Color col){
        // Checks if mouse is within the bounds of rectangle
        if(this.getPosition().x <= MouseX && MouseX <= (this.getPosition().x + this.getLocalBounds().width) &&
                this.getPosition().y <= MouseY && MouseY <= (this.getPosition().y + + this.getLocalBounds().height)) {
            this.setColor(col);
            return true;
        }
        else
            this.setColor(Color.BLACK);
        return false;
    }
}

