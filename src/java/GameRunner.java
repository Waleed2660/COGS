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
          boolean alt,ctrl,shift,system;
          Movement player = new Movement(xlocl,ylocl);
          int x = 1;
          String keystroke = "";
          /*Keyboard.Key d = Keyboard.Key.valueOf("D");
          Keyboard.Key a = Keyboard.Key.valueOf("A"); //this shit is going to be the death of me
          Keyboard.Key w = Keyboard.Key.valueOf("W");
          Keyboard.Key s = Keyboard.Key.valueOf("S");*/
          //event kevent = event.KeyEvent;
          Keyboard.Key space = Keyboard.Key.valueOf("SPACE");
          Keyboard.Key d = Keyboard.Key.D;
          Keyboard.Key a = Keyboard.Key.A;
          Keyboard.Key s = Keyboard.Key.S;
          Keyboard.Key w = Keyboard.Key.W;
          
          while(x==1)
          {
               window.clear(Color.GREEN);
               window.draw(player);
               window.display();
               for(Event event : window.pollEvents())
               {
                    if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
                    {
                        System.out.println("Working");
                    }         
                    if(event.type == Event.Type.CLOSED)
                    {
                         window.close();
                         //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                    }
                    if(event.type == Event.Type.KEY_PRESSED)
                    {
                         //WORKs NEED TO FIGURE OUT HOW TF TO MAKE THIS SHITTING THING KNOW WHICH KEY WAS PRESSED
                         //if(event.KeyEvent() == d)
                         keyPress();
                         
                    }
               }
          }
    }

    public void keyPress()
    {
          
          if(Keyboard.Key) 
          {
               System.out.println("W");
          }
    }
}