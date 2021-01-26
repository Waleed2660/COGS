import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import java.util.ArrayList;

/**
 * used to run the game
 *
 * <p>
 * Description
 *
 */
public class GameRunner
{
    private boolean shotFired = false;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int direction = 1;
    private MMWindow window;
    private Level level;

    public GameRunner(MMWindow window, String levelNum)
    {
          this.window = window;
          this.level = new Level(levelNum, 2);
    }

    public void run()
    {
         boolean levelOpen = true;
         float xlocl = 10, ylocl = 10;
         float winSizeX = window.getSize().x, winSizeY = window.getSize().y;
         TextManager backToMenu = new TextManager("Back",100,200);

         //GameObject enemy = new GameObject(winSizeX/2 + 700, winSizeY/2,"resources/enemy.gif");
         //enemy.setScale((float) 0.2,(float)0.2);

         while(levelOpen) {
             window.clear(Color.BLACK);
             window.draw(backToMenu);
             //window.draw(enemy);

             // Animates bullet once fired
             if(shotFired){
                 for (int  x = 0; x < bullets.size(); x++) {
                     window.draw(bullets.get(x));
                     bullets.get(x).moveBullet();
                     // De-spawns the bullet when it goes out of frame/ hits object
                     if (!bullets.get(x).bulletInSight(window) || bullets.get(x).bulletCollision(enemy.getHitBox())) {
                         bullets.remove(x);
                     }
                 }
             }
             window.display();


             for(Event event : window.pollEvents()) {

                 if(event.type == Event.Type.CLOSED) {
                     window.close();
                     //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                 }
                 if(event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                     // Clickable Button
                 }
                 if(Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
                     direction = -1;    // Updates Bullet travel direction
                 }
                 if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
                     direction = 1;     // Updates Bullet travel direction
                 }
                 if(Keyboard.isKeyPressed(Keyboard.Key.SPACE)) {
                     bullets.add(new Bullet(direction,winSizeX/2,winSizeY/2,"resources/bullet.png"));
                     shotFired = true;
                 }
                 if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                     window.clear();
                     //menu.runMenu(window);
                 }
             }
         }
    }

    public void keyPress(MMWindow window,Player player) {

         /** 
         * KeyPress uses a MMWindow and Player
         * 
         * <p>
         * KeyPress is used to detect which key is pressed exactly
         * It will be used to move the player's sprite around the window
         * 
          */
          /*if(Keyboard.isKeyPressed(Keyboard.Key.W))
          {
               player.jump();
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.S))
          {
               player.drop();
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.A))
          {
               player.moveLeft();
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.D))
          {
               player.moveRight();
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.SPACE))
          {
               player.shoot();
          }
          
          */
          if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) 
          {
               window.close();
               //closes window if esc is pressed
          }
    }

    /**
     * Draws all objects in a level dynamically
     * 
     * @param level
     * @param window
     */
    public void drawAll(Level level, MMWindow window)
    {
        FloatRect viewZone = new FloatRect(window.getView().getCenter().x-window.getView().getSize().x/2, window.getView().getCenter().y-window.getView().getSize().y/2, window.getView().getSize().x, window.getView().getSize().y);
        window.clear(Color.WHITE);
        
        level.setBackgroundView(viewZone);
        window.draw(level.background);
        for(GameObject a : level.objectList)
        {
          if(viewZone.intersection(a.getHitBox()) != null)
          {
               window.draw(a);
          }
        }
        window.display();
    }
}