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
        RenderWindow window = new RenderWindow(new VideoMode(1024, 600), "Test");
        Level level = new Level("Level1");
        window.setFramerateLimit(30);
        window.clear(Color.WHITE);
        View v = new View(new FloatRect(0, 0, 1024, 640));
        v.setViewport(new FloatRect((float)0.5, (float)0.5, (float)0.5, (float)0.5));
        window.setView(v);

        drawAll(level, window);

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

                v.move(20*x, 0);
                window.setView(v);
                drawAll(level, window);
            }
        }
            for(Event e : window.pollEvents())
            {
                if(e.type == Event.Type.CLOSED)
                {
                    window.close();
                }
            }
        }

    public static void drawAll(Level level, RenderWindow window)
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
