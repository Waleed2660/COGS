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
  private ArrayList<FloatRect> ground = new ArrayList<FloatRect>();
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  private ArrayList<Platform> platforms = new ArrayList<Platform>();
  private Player player;

  private float gravity = 0;

  /**
   * Constructor for a level.
   * 
   * @param levNum - the level to construct. Currently can be Level1, Level2 or Level3. Should make the choices into enums
   */
  public Level(String levNum, float gravity)
  {
      levelNum = levNum;
      addFromFile("./levels/".concat(levelNum).concat("/"));
      this.gravity = gravity;
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
              ground.add(new FloatRect(Float.parseFloat(spl[1]), Float.parseFloat(spl[2]), Float.parseFloat(spl[3]), Float.parseFloat(spl[4])));
            }
            else if(spl[0].equals("player"))
            {
              player = new Player(Float.parseFloat(spl[1]), Float.parseFloat(spl[2]), 10, 1, filePath.concat("assets/").concat(spl[0]).concat(".png/"));
              objectList.add(player);
            }
            else if(spl[0].contains("Background"))
            {
              background = new GameObject(Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), filePath.concat("assets/").concat(spl[0]).concat(".png/"));
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

  public Player getPlayer()
  {
    return player;
  }
}