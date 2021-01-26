import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import java.util.ArrayList;

/**
 * Used to run the game
 */
public class GameRunner
{
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int direction = 1;
    private MMWindow window;
    private Level level;
    private int health = 100; //5 hits to ko 20 hp per hit  // enemies dog - 1 hit ko robot 2 - hit ko
    private boolean levelOpen = true;

    /**
     * Creates the gamerunner for a specific level and gives the window to use
     * 
     * @param window window on which to draw
     * @param levelNum level number. Should be "Level1", "Level2", or "Level3"
     */
    public GameRunner(MMWindow window, String levelNum)
    {
          this.window = window;
          this.level = new Level(levelNum, 2);
    }

    /**
     * Runs the level and controls game flow.
     */
    public void run()
    {
         float xlocl = 10, ylocl = 10;
         float winSizeX = window.getSize().x, winSizeY = window.getSize().y;
         TextManager backToMenu = new TextManager("Back",100,200);

         //GameObject enemy = new GameObject(winSizeX/2 + 700, winSizeY/2,"resources/enemy.gif");
         //enemy.setScale((float) 0.2,(float)0.2);
         while(levelOpen)
         {
               window.clear(Color.BLACK);
               window.draw(backToMenu);

               // Animates bullet once fired #changed to animate if there are any bullets
               if(!bullets.isEmpty()){
                    for (int  x = 0; x < bullets.size(); x++) {
                         window.draw(bullets.get(x));
                         bullets.get(x).moveBullet();

                         // De-spawns the bullet when it goes out of frame/ hits object
                         if (!bullets.get(x).bulletInSight(window)) {
                              bullets.remove(x);
                         }
                    }
               }

               for(Event event : window.pollEvents())
               {
                    if(event.type == Event.Type.KEY_PRESSED)
                    {
                         keyPress(event, bullets);
                    }
                    if(event.type == Event.Type.CLOSED)
                    {
                         window.close();
                         //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                    }
                    if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
                    {
                         // Clickable Button
                    }
               }
               window.display();
         }
    }

     /** 
      * KeyPress uses a MMWindow and Player
     * 
     * <p>
     * KeyPress is used to detect which key is pressed exactly
     * It will be used to move the player's sprite around the window
     * 
     */
    public void keyPress(Event event, ArrayList<Bullet> bullets)
    {
          float winSizeX = window.getSize().x, winSizeY = window.getSize().y;
        
          if(Keyboard.isKeyPressed(Keyboard.Key.LEFT))
          {
               direction = -1;    // Updates Bullet travel direction
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT))
          {
               direction = 1;     // Updates Bullet travel direction
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.SPACE))
          {
               bullets.add(new Bullet(direction,winSizeX/2,winSizeY/2,"resources/bullet.png"));
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
          {
               levelOpen = false;
          }
    }

    /**
     * Draws all objects in a level dynamically
     * 
     * @param level
     * @param window
     * @return returns a list of all objects currently in view
     */
    public ArrayList<GameObject> drawAll(Level level, MMWindow window)
    {
         ArrayList<GameObject> result = new ArrayList<GameObject>();
        FloatRect viewZone = window.getViewZone();
        window.clear(Color.WHITE);
        
        level.setBackgroundView(viewZone);
        window.draw(level.background);
        for(GameObject a : level.objectList)
        {
          if(viewZone.intersection(a.getHitBox()) != null)
          {
               result.add(a);
               window.draw(a);
          }
        }
        window.display();
        return result;
    }
}