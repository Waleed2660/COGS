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
  private ArrayList<GameObject> ground = new ArrayList<GameObject>();
  public ArrayList<GameObject> background = new ArrayList<GameObject>();
  public ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //had to change to public so i could access in GameRunner  for collsion damage detection
  private ArrayList<Platform> platforms = new ArrayList<Platform>();
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
    System.out.println(playArea.toString());
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

          if(spl[0].equals("Ground"))
          {
            GameObject temp = new GameObject(Float.parseFloat(spl[1]), Float.parseFloat(spl[2]), Float.parseFloat(spl[3]), Float.parseFloat(spl[4]));
            ground.add(temp);
            objectList.add(temp);
          }
          else if(spl[0].equals("player"))
          {
            player = new Player(Float.parseFloat(spl[1]), Float.parseFloat(spl[2]), 10, 100, this, filePath.concat("assets/").concat(spl[0]).concat(".png/"));
            objectList.add(player);
          }
          else if(spl[0].contains("Background"))
          {
            playArea = new FloatRect(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), Integer.parseInt(spl[3]), Integer.parseInt(spl[4]));
            for(int i = 0; i < Integer.parseInt(spl[3])/view.width; i++)
            {
              background.add(new GameObject(view.width*i, 0, filePath.concat("assets/").concat(spl[0]).concat(".png/"), new FloatRect(view.width*i, 0, view.width, view.height)));
            }
          }
          else if(spl[0].contains("dog"))
          {
            Enemy temp = new Enemy(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/"), 1);
            enemies.add(temp);
            objectList.add(temp);
          }
          else if(spl[0].contains("robot"))
          {
            Enemy temp = new Enemy(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/"), 2);
            enemies.add(temp);
            objectList.add(temp);
          }
          else if(spl[0].contains("platform"))
          {
            Platform temp = new Platform(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/"));
            platforms.add(temp);
            objectList.add(temp);
          }
          else
          {
            objectList.add(new GameObject(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/"), null));
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