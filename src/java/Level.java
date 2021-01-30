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
  public GameObject background;
  //Arrays for different objects. could be usseful in the future but not right now
  private ArrayList<GameObject> ground = new ArrayList<GameObject>();
  public ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //had to change to public so i could access in GameRunner  for collsion damage detection
  private ArrayList<Platform> platforms = new ArrayList<Platform>();
  private Player player;

  private final float gravity;
  private final float friction;

  /**
   * Constructor for a level.
   * 
   * @param levNum - the level to construct. Currently can be Level1, Level2 or Level3. Should make the choices into enums
   * @param gravity gravity force to be applied in the level
   * @param friction friction force to be applied in the level
   */
  public Level(String levNum, float gravity, float friction)
  {
<<<<<<< HEAD
      levelNum = levNum;
      addFromFile("./Levels/".concat(levelNum).concat("/"));
      this.gravity = gravity;
=======
    levelNum = levNum;
    this.gravity = gravity;
    this.friction = friction;
    addFromFile("./levels/".concat(levelNum).concat("/"));
>>>>>>> f9fade8e097aadb0d6fa4fabba099b9ce2bb0357
  }

  /**
   * Adds objects from a text file
   * 
   * @param filePath the file path of the folder where assets and level information are stored
   */
  private void addFromFile(String filePath)
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
            background = new GameObject(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/"));
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
            objectList.add(new GameObject(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/")));
          }
        }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
  }

  /**
   * Sets how much of the background should be drawn. Used for dynamic loading.
   * 
   * @param v the rectangle representing the users view
   */
  public void setBackgroundView(FloatRect v)
  {
    background.setPosition(v.left, v.top);
    background.setTextureRect(new IntRect(v));
  }

  /**
   * Returns the player object
   * @return
   */
  public Player getPlayer()
  {
    return player;
  }

  /**
   * Returns level gravity
   * @return
   */
  public float getGravity()
  {
    return this.gravity;
  }

  /**
   * Returns level friction
   * @return
   */
  public float getFriction()
  {
    return this.friction;
  }

  /**
   * Return an array that has the levels x edge coordinates
   * @return an array of size 2
   */
  public float[] getEdgeCoords()
  {
    return new float[]{background.getPosition().x, background.getPosition().x+background.getLocalBounds().width};
  }
}