import org.jsfml.window.*;
import org.jsfml.window.event.*;

import java.util.ArrayList;

import org.jsfml.graphics.*;

//javac -d .\test\build\ -cp .\lib\jsfml.jar;.\src\java\ .\test\java\LevelTest.java
//java -cp .\test\build\;.\lib\jsfml.jar LevelTest

//The crash has something to do with mouse input

public class LevelTest {
    public static void main (String[] args)
    {
        RenderWindow window = new RenderWindow(new VideoMode(1024, 640), "Test");
        Level level3 = new Level("Level1");
        window.setFramerateLimit(30);
        window.clear(Color.WHITE);
        View v = new View(new FloatRect(0, 0, 1024, 640));
        window.setView(v);

        drawAll(level3.objectList, window);

        while(window.isOpen())
        {
            if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.UP) || Keyboard.isKeyPressed(Keyboard.Key.DOWN))
            {
                int x = 0;
                int y = 0;
                if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT))
                {
                    x += 1;
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.LEFT))
                {
                    x += -1;
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.UP))
                {
                    y += -1;
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.DOWN))
                {
                    y += 1;
                }

                v.move(10*x, 0);
                window.setView(v);
                }

                drawAll(level3.objectList, window);
            }
            

            for(Event e : window.pollEvents())
            {
                if(e.type == Event.Type.CLOSED)
                {
                    window.close();
                }
            }
        }

    public static void drawAll(ArrayList<GameObject> objects, RenderWindow window)
    {
        window.clear(Color.WHITE);
        for(GameObject a : objects)
        {
            window.draw(a);
        }
        window.display();
    }
}
