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
          float xlocl = 10, ylocl = 10;
          Movement player = new Movement(xlocl,ylocl);
          
          while(window.isOpen())
          {
               window.clear(Color.GREEN);
               window.draw(player);
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
                         keyPress(); 
                    }
               }
          }
    }

    public void keyPress()
    {
         
          if(Keyboard.isKeyPressed(Keyboard.Key.W)) 
          {
               System.out.println("W");
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.S)) 
          {
               System.out.println("S");
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.A)) 
          {
               System.out.println("A");
          }
          if(Keyboard.isKeyPressed(Keyboard.Key.D)) 
          {
               System.out.println("D");
          }
    }
}