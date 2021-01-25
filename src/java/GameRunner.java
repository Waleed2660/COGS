import org.jsfml.graphics.Color;
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

    public void run(MMWindow window)
    {
         float xlocl = 10, ylocl = 10;
         float winSizeX = window.getSize().x, winSizeY = window.getSize().y;
         TextManager backToMenu = new TextManager("Back",100,200);

         GameObject enemy = new GameObject(winSizeX/2 + 400, winSizeY/2,"resources/enemy.gif");
         enemy.setScale((float) 0.2,(float)0.2);

         while(window.isOpen()) {
             window.clear(Color.BLACK);
             window.draw(backToMenu);
             window.draw(enemy);

             // Animates bullet once fired
             if(shotFired){
                 for (int  x = 0; x < bullets.size(); x++) {
                     window.draw(bullets.get(x));
                     bullets.get(x).moveBullet(direction);
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
}