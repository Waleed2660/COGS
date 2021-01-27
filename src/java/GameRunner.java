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
    private MMWindow window;
    private Level level;
    private int health = 100; //5 hits to ko 20 hp per hit  // enemies dog - 1 hit ko robot 2 - hit ko
    private boolean levelOpen = true;
    private Player player;

    /**
     * Creates the gamerunner for a specific level and gives the window to use
     * 
     * @param window window on which to draw
     * @param levelNum level number. Should be "Level1", "Level2", or "Level3"
     */
    public GameRunner(MMWindow window, String levelNum)
    {
          this.window = window;
          window.setKeyRepeatEnabled(false);
          this.level = new Level(levelNum, 2);
          this.player = level.getPlayer();
    }

    /**
     * Runs the level and controls game flow.
     */
    public void run()
    {
         float xlocl = 10, ylocl = 10;
         float winSizeX = window.getSize().x, winSizeY = window.getSize().y;
         //TextManager backToMenu = new TextManager("Back",100,200);

         //GameObject enemy = new GameObject(winSizeX/2 + 700, winSizeY/2,"resources/enemy.gif");
         //enemy.setScale((float) 0.2,(float)0.2);
         while(levelOpen && window.isOpen())
         {
               //window.draw(backToMenu);
               this.controller(drawAll(level, window));

               Event e = window.pollEvent();
               if(e != null)
               {
                    if(e.type == Event.Type.KEY_PRESSED)
                    {
                         keyPress(e);
                    }
                    if(e.type == Event.Type.CLOSED)
                    {
                         window.close();
                         //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                    }
                    if(e.type == Event.Type.MOUSE_BUTTON_PRESSED)
                    {
                         // Clickable Button
                    }
               }
         }
    }

     /** 
      * KeyPress uses a MMWindow and Player. A bit redundant. Could be removed or merged with controller?
     * 
     * <p>
     * KeyPress is used to detect which key is pressed exactly
     * It will be used to move the player's sprite around the window
     * 
     * @param event the key event
     */
    public void keyPress(Event event)
    {
          if(event.asKeyEvent().key == Keyboard.Key.SPACE)
          {
               player.shoot(bullets);
          }
          if(event.asKeyEvent().key == Keyboard.Key.ESCAPE)
          {
               levelOpen = false;
          }
          if(event.asKeyEvent().key == Keyboard.Key.UP)
          {
               player.jump(80);
          }
    }

     /**
     * Checks if a key is pressed and moves the player. More functionality could be added.
     * 
     * @param blocks the GameObject to check for collision with
     */
    public void controller(ArrayList<GameObject> blocks)
    {
        if(Keyboard.isKeyPressed(Keyboard.Key.LEFT))
        {
          player.walk(-1, 10/3);
        }
        if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT))
        {
          player.walk(1, 10/3);
        }
        player.movement(blocks, window);
    }

    /**
     * Draws all objects in a level dynamically.
     * 
     * @param level
     * @param window
     * @return returns a list of all objects currently in view
     */
    public ArrayList<GameObject> drawAll(Level level, MMWindow window)
    {
          ArrayList<GameObject> result = new ArrayList<GameObject>();
          FloatRect viewZone = window.getViewZone();
          window.clear(Color.BLACK); 

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
        window.display();
        return result;
    }
}