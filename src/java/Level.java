import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsfml.graphics.*;

/**
 * A class that represents a level
 */

public class Level
{
  private String levelNum;
  public ArrayList<GameObject> objectList = new ArrayList<GameObject>(); //public for now should improve the way to access it and change it eventually
  //Arrays for different objects. could be usseful in the future but not right now
  public ArrayList<GameObject> background = new ArrayList<GameObject>();
  public ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //had to change to public so i could access in GameRunner  for collsion damage detection
  private Player player;
  private FloatRect playArea;

  private final float gravity;
  private final float friction;

  /**
   * Constructor for a level.
   *
   * @param levNum - the level to construct. Currently can be Level1, Level2 or Level3. Should make the choices into enums
   * @param gravity gravity force to be applied in the level
   * @param friction friction force to be applied in the level
   * @param window the game window
   */
  public Level(String levNum, float gravity, float friction, MMWindow window)
  {
    levelNum = levNum;
    this.gravity = gravity;
    this.friction = friction;
    addFromFile("./levels/"+levNum+"/", window);
  }

  /**
   * Adds objects from a text file
   *
   * @param filePath the file path of the folder where assets and level information are stored
   */
  private void addFromFile(String filePath, MMWindow window)
  {
    FloatRect view = window.getViewZone();
    try {
      File myObj = new File(filePath+levelNum+".txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) 
      {
        String data = myReader.nextLine();
        String[] spl = data.split(" ");

        String name = spl[0];
        String type = spl[1];
        float x = Float.parseFloat(spl[2]);
        float y = Float.parseFloat(spl[3]);
        float width = Float.parseFloat(spl[4]);
        float height = Float.parseFloat(spl[5]);

        if(name.contains("player"))
        {
          ArrayList<Texture> walkAnim = new ArrayList<Texture>();
          for(int i = 1; i <= 4; i++)
          {
            if(new File("./resources/player/walk_"+i+".png").isFile())
            {
              try {
                Texture temp = new Texture();
                temp.loadFromFile(Paths.get("./resources/player/walk_"+i+".png"));
                walkAnim.add(temp);
              } catch (IOException e) {
                  e.printStackTrace();
              }
            }
          }
          if(new File("./resources/player/"+name+".png").isFile())
          {
            player = new Player(x, y, 15, 200, this, window, "./resources/player/player.png", walkAnim);
            player.addSound("shoot", "./resources/sound/Shooting.wav");
            player.addSound("damaged", "./resources/sound/Player damage.wav");
            player.addSound("dead", "./resources/sound/Player dies.wav");
            objectList.add(player);
          }
          else
          {
            //some error message for not being able to find the file
          }
        }
        else if(name.contains("Background"))
        {
          playArea = new FloatRect(x, y, width, height);
          if(new File(filePath+"assets/"+name+".png").isFile())
          {
            for(int i = 0; i < width/view.width; i++)
            {
              for(int j = 0; j < height/view.height; j++)
              {
                background.add(new GameObject(view.width*i, view.height*j, filePath+"assets/"+name+".png", new FloatRect(view.width*i, view.height*j, view.width, view.height)));
              }
            }
          }
          else
          {
            //some error message for not being able to find the file
          }
        }
        else if(name.contains("dog"))
        {
          if(new File("./resources/enemies/"+name+".png").isFile())
          {
            Enemy temp = new Enemy(x, y, "./resources/enemies/"+name+".png", 5, this, 1);
            enemies.add(temp);
          }
          else
          {
            //some error message for not being able to find the file
          }
        }
        else if(name.contains("robot"))
        {
          if(new File("./resources/enemies/"+name+".png").isFile())
          {
            Enemy temp = new Enemy(x, y, "./resources/enemies/"+name+".png", 5, this, 1);
            enemies.add(temp);
          }
          else
          {
            //some error message for not being able to find the file
          }
        }
        else if(name.contains("boss"))
        {
          if(new File("./levels/Level3/assets/"+name+".png").isFile())
          {
            Boss temp= new Boss(x, y, "./levels/Level3/assets/"+name+".png", this);
            enemies.add(temp);
          }
        }
        else
        {
          if(type.contains("hitbox"))
          {
            objectList.add(new GameObject(x, y, width, height, name));
          }
          else{
            String path = findFilePath(name);
            if(path != null)
            {
              objectList.add(new GameObject(x, y, path, name, null));
            }
            else
            {
              //some error message for not being able to find the file
            }
          }
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Returns the player object.
   * @return
   */
  public Player getPlayer()
  {
    return player;
  }

  /**
   * Returns level gravity.
   * @return
   */
  public float getGravity()
  {
    return this.gravity;
  }

  /**
   * Returns level friction.
   * @return
   */
  public float getFriction()
  {
    return this.friction;
  }

  /**
   * Returns a FloatRect representing the play area.
   * @return
   */
  public FloatRect getPlayArea()
  {
    return playArea;
  }

  /**
   * Finds the possible path to file or returns null if not found
   * 
   * @param name name of file to find
   * @return string path or null
   */
  private String findFilePath(String name)
  {
    if(new File("./resources/"+name+"/"+name+".png").isFile())
    {
      return "./resources/"+name+"/"+name+".png";
    }
    else if(new File("./levels/"+levelNum+"/assets/"+name+".png").isFile())
    {
      return "./levels/"+levelNum+"/assets/"+name+".png";
    }
    else if(new File("./resources/common/"+name+".png").isFile())
    {
      return "./resources/common/"+name+".png";
    }
    else if(new File("./resources/"+name+".png").isFile())
    {
      return "./resources/"+name+".png";
    }
    return null;
  }
}