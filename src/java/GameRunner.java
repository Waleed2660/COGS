import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class GameRunner
{
    /** 
         * used to run the game
         * 
         * <p>
         * Description
         * 
         * @param int user specified resolution 
    */
    public GameRunner()
    {
         
    }
    public void run(MMWindow window)
    {
          
          while(window.isOpen())
          {
               window.clear(Color.GREEN);
               window.display();
               for(Event event : window.pollEvents())
               {   
                    if(event.type == Event.Type.CLOSED)
                    {
                         window.close();
                         //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                    }
                    if(event.type == Event.Type.KEY_PRESSED)
                    {
                         keyPress(window); 
                    }
               }
          }
    }

    public void keyPress(MMWindow window/*,Player player*/)
    {
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