import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Used to run the game
 */
public class GameRunner
{
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private double lastBulletTime = System.currentTimeMillis();
    private MMWindow window;
    private Level level;
    private Player player;

    //private GameOver over;
    private boolean ticker;
    private int tocker = 0;

    /**
     * Creates the gamerunner for a specific level and gives the window to use
     * 
     * @param window window on which to draw
     * @param levelNum level number. Should be "Level1", "Level2", or "Level3"
     */
    public GameRunner(MMWindow window, String levelNum)
    {
          this.window = window;
          window.resetView();
          window.setKeyRepeatEnabled(false);
          this.level = new Level(levelNum, (float)2.5, 2, window.getViewZone());
          this.player = level.getPlayer();
    }

    /**
     * Runs the level and controls game flow.
     * 
     * @return return 0 if return to menu, 1 if load next level
     */
    public int run()
    {
         float xlocl = 10, ylocl = 10;
         float winSizeX = window.getSize().x, winSizeY = window.getSize().y;
         int check = player.hp;

        while(window.isOpen()) {

               ArrayList<GameObject> objectsInView = drawAll(level, window);
               if(this.controller(objectsInView) == 1)
               {
                    return 0;
               }

               Event e = window.pollEvent();
               if(e != null) {

                    if(e.type == Event.Type.CLOSED) {

                         window.close();
                         //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                    }
                    if(e.type == Event.Type.MOUSE_BUTTON_PRESSED) {

                         // Clickable Button
                    }
               }

               /*if(player.eCollides(level.enemies) != null)
               {
                    System.out.println("Collision with Alive Enemy" + check);
                    check = player.dmghp();
                    if(check == 0 || check == -1)
                    {
                         System.out.println("dead");
                         player.setHP(100);
                         return 0;
                    }
               }*/
               GameObject playerCollides = player.collides(objectsInView);
               if(playerCollides != null && playerCollides.getType().equals("portal"))
               {
                    return 1;
               }
          }
          return 0;
    }

     /**
      * Checks if a key is pressed and moves the player. More functionality could be added.
      * KeyPress is used to detect which key is pressed exactly
      * It will be used to move the player's sprite around the window
      *
      * @param blocks the GameObject to check for collision with
      * @return return 1 if return to menu
     */
     public int controller(ArrayList<GameObject> blocks)
     {
          if(Keyboard.isKeyPressed(Keyboard.Key.SPACE)) {
               renderBullets();
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
               player.walk(-1);
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
               player.walk(1);
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.UP)) {
               player.jump();
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
               return 1;
          }
          player.movement(blocks, window);
          return 0;
     }

    /**
     * Renders Bullets upon calling, also ensures delay in each shot
     */
    public void renderBullets(){
        if (System.currentTimeMillis() - lastBulletTime > 500) {
            bullets.add(player.shoot());
            lastBulletTime = System.currentTimeMillis();
        }
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

          //window.draw(level.background);
          for(GameObject b : level.background)
          {
               if(viewZone.intersection(b.getHitBox()) != null)
               {
                    window.draw(b);
               }
          }
          for(GameObject a : level.objectList)
          {
               if(viewZone.intersection(a.getHitBox()) != null)
               {
                    result.add(a);
                    window.draw(a);
               }
          }
        for(GameObject a : level.enemies)
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
                    if (!bullets.get(x).bulletInSight(window) || bullets.get(x).collides(result) != null) {

                         if(bullets.get(x).eCollides(level.enemies) != null) {

                              for(int f = 0; f < level.enemies.size(); f++) {

                                   if(level.enemies.get(f).bCollides(bullets) != null) {

                                        if(level.enemies.get(f).dmghp() <= 0) {

                                             System.out.println("Enemy dead");
                                             level.enemies.remove(f);
                                        }
                                   }
                              }
                         }
                         bullets.remove(x);
                    }
               }
          }
          window.display();
          return result;
    }
}