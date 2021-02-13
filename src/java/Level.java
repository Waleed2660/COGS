import java.io.File;
import java.io.FileNotFoundException;
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
  public ArrayList<GameObject> fires = new ArrayList<GameObject>();
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
   * @param view the view to load
   */
  public Level(String levNum, float gravity, float friction, FloatRect view)
  {
    levelNum = levNum;
    this.gravity = gravity;
    this.friction = friction;
    addFromFile("./levels/".concat(levelNum).concat("/"), view);
  }

  /**
   * Adds objects from a text file
   *
   * @param filePath the file path of the folder where assets and level information are stored
   */
  private void addFromFile(String filePath, FloatRect view)
  {
    try {
      File myObj = new File(filePath.concat(levelNum).concat(".txt"));
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
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
          player = new Player(x, y, 10, 150, this, filePath.concat("assets/").concat(name).concat(".png/"));
          objectList.add(player);
        }
        else if(name.contains("Background"))
        {
          playArea = new FloatRect(x, y, width, height);
          for(int i = 0; i < width/view.width; i++)
          {
            for(int j = 0; j < height/view.height; j++)
            {
              background.add(new GameObject(view.width*i, view.height*j, filePath.concat("assets/").concat(name).concat(".png/"), new FloatRect(view.width*i, view.height*j, view.width, view.height)));
            }
          }
        }
        else if(name.contains("dog"))
        {
          Enemy temp = new Enemy(x, y, filePath.concat("assets/").concat(name).concat(".png/"), 1);
          enemies.add(temp);
        }
        else if(name.contains("robot"))
        {
          Enemy temp = new Enemy(x, y, filePath.concat("assets/").concat(name).concat(".png/"), 2);
          enemies.add(temp);
        }
        else
        {
          if(type.contains("hitbox"))
          {
            objectList.add(new GameObject(x, y, width, height, name));
          }
          else{
            objectList.add(new GameObject(x, y, filePath.concat("assets/").concat(name).concat(".png/"), name, null));
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
}