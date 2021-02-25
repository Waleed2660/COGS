import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.system.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;


/**
 * Used to run the game
 */
public class GameRunner {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Bullet> hostileBullets = new ArrayList<>();
    private RectangleShape hpBar = new RectangleShape();
    private double lastBulletTime = System.currentTimeMillis();
    private double lastHitTime = 0;
    private double lastBurnTime = 0;
    private MMWindow window;
    private Level level;
    private Player player;
    private double rpfStart = 0, invStart = 0;
    private float check = 100, hpInc = 20;
    private boolean invincible = false, machinegun = false, hpPack = false;

    /**
     * Creates the gamerunner for a specific level and gives the window to use
     *
     * @param window   window on which to draw
     * @param levelNum level number. Should be "Level1", "Level2", or "Level3"
     */
    public GameRunner(MMWindow window, String levelNum) {
        this.window = window;
        window.resetView();
        window.clear(Color.BLACK);
        window.draw(new TextManager("Loading ...", window.getViewZone().left+window.getViewZone().width*(float)0.1, window.getViewZone().top+(window.getViewZone().height*(float)0.8), 50));
        window.display();

        hpBar.setSize(new Vector2f(check*2,20));
        
        window.setKeyRepeatEnabled(false);
        if(levelNum.equals("Level3"))
        {
            this.level = new Level(levelNum, (float) -1, (float)0.4, window);
        }
        else
        {
            this.level = new Level(levelNum, (float) -2.5, (float)0.4, window);
        }

        this.player = level.getPlayer();
        window.moveView(window.getViewZone().width*((int)(player.getGlobalBounds().left/window.getViewZone().width)), window.getViewZone().height*((int)(player.getGlobalBounds().top/window.getViewZone().height)));
    }

    /**
     * Runs the level and controls game flow.
     *
     * @return return 0 if return to menu, 1 if load next level, 2 if died and should reload
     */
    public int run() {
        hpBar.setFillColor(Color.RED);
        float winSizeX = window.getSize().x, winSizeY = window.getSize().y;

        while (window.isOpen())
        {
            ArrayList<GameObject> objectsInView = drawAll(level, window);
            GameObject playerCollides = player.collides(objectsInView);
            if (this.controller(objectsInView) == 1) {
                return 0;
            }

            Event e = window.pollEvent();
            if (e != null) 
            {

                if (e.type == Event.Type.CLOSED) 
                {
                    window.close();
                    //IMPORTANT CLOSES WINDOW UPON PRESSING CLOSE DO NOT ALTER
                }
            }

            if (player.collides(level.enemies) != null) 
            {
                if(System.currentTimeMillis() - invStart >5000)
                {
                    if (System.currentTimeMillis() - lastHitTime > 500) 
                    {
                        check = player.dmghp(20);
                        lastHitTime = System.currentTimeMillis();
                        player.hitAway();
                    }

                    if (check == 0 || check == -1) 
                    {
                        player.setHP(100);
                        window.resetView();
                        return 2;
                    }
                }
            }
            
            if (playerCollides != null && playerCollides.getType().equals("fire")) 
            {
                if(System.currentTimeMillis() - invStart > 5000)
                {
                    if (System.currentTimeMillis() - lastBurnTime > 100) 
                    {
                        check = player.dmghp(2);
                        System.out.println("Collision with fire" + check);
                        lastBurnTime = System.currentTimeMillis();
                    }

                    if (check == 0 || check == -1) 
                    {
                        player.setHP(100);
                        window.resetView();
                        return 2;
                    }
                }

            }
            if(playerCollides != null && playerCollides.getType().equals("hp"))
            {
                
                for(int h = 0; h < level.objectList.size(); h++ )
                {
                    GameObject hpInd = level.objectList.get(h);
                    if(hpInd.pCollides(player)!= null)
                    {
                        level.objectList.remove(h);
                        h = level.objectList.size();
                    }
                }
                check +=20;
                player.setHP(check);
            }
            if(playerCollides != null && playerCollides.getType().equals("rapidfire"))
            {
                rpfStart = System.currentTimeMillis();
                for(int h = 0; h < level.objectList.size(); h++ )
                {
                    GameObject rpInd = level.objectList.get(h);
                    if(rpInd.pCollides(player)!= null)
                    {
                        level.objectList.remove(h);
                        h = level.objectList.size();
                    }
                }
            }
            if(playerCollides != null && playerCollides.getType().equals("invincibility"))
            {
                invStart = System.currentTimeMillis();
                for(int h = 0; h < level.objectList.size(); h++ )
                {
                    GameObject invInd = level.objectList.get(h);
                    if(invInd.pCollides(player)!= null)
                    {
                        level.objectList.remove(h);
                        h = level.objectList.size();
                    }
                }
            }
            if (playerCollides != null && playerCollides.getType().equals("portal")) 
            {
                window.resetView();
                return 1;
            }
        }
        return 0;
    }


    /**
     * Checks if a key is pressed and moves the player. More functionality could be added.
     * KeyPress is used to detect which key is pressed exactly
     * It will be used to move the player's sprite around the window
     *
     * @param blocks the GameObject to check for collision with
     * @return return 1 if return to menu
     */
    public int controller(ArrayList<GameObject> blocks) {
        if (Keyboard.isKeyPressed(Keyboard.Key.SPACE)) {
            //Creates a Bullet Object inside Player, also Checks delay between each bullet
            
            if(System.currentTimeMillis() - rpfStart < 10000)
            {
                if(player.insertDelay(250))
                {
                    bullets.add(player.shoot());
                }
            }
            else
            {
                if (player.insertDelay(500)) 
                {
                    bullets.add(player.shoot());
                }
            }
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.A)) {
            player.walk(-1);
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.D)) {
            player.walk(1);
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.UP) || Keyboard.isKeyPressed(Keyboard.Key.W)) {
            player.jump();
        }
        //havent fully implemented yet
        if (Keyboard.isKeyPressed(Keyboard.Key.DOWN) || Keyboard.isKeyPressed(Keyboard.Key.S)) {
            player.crouch();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
            return 1;
        }
        player.update(blocks);
        return 0;
    }

    /**
     * Draws all objects in a level dynamically.
     *
     * @param level
     * @param window
     * @return returns a list of all objects currently in view
     */
    public ArrayList<GameObject> drawAll(Level level, MMWindow window) {
        ArrayList<GameObject> result = new ArrayList<GameObject>();
        FloatRect viewZone = window.getViewZone();
        window.clear(Color.BLACK);

        //window.draw(level.background);
        for (GameObject b : level.background) {
            if (viewZone.intersection(b.getHitBox()) != null) {
                window.draw(b);
            }
        }
        for (GameObject a : level.objectList) {
            if (viewZone.intersection(a.getHitBox()) != null) {
                result.add(a);
                window.draw(a);
            }
        }
        hpBar.setPosition(window.getViewZone().left+100,600);
        hpBar.setSize(new Vector2f(check*2,20));


        // Handles Enemy objects
        handleEnemy(viewZone,result);

        window.draw(hpBar);
        // Animates bullet once fired #changed to animate if there are any bullets
        if (!bullets.isEmpty()) {

            for (int x = 0; x < bullets.size(); x++) {
                Bullet bullet = bullets.get(x);
                window.draw(bullet);
                bullet.update(null);

                // De-spawns the bullet when it goes out of frame/ hits object
                GameObject bulletHit = bullet.collides(result);
                if (!bullet.bulletInSight(window) || bulletHit != null) {

                    if (bulletHit != null && bulletHit instanceof Enemy) {
                        if (((Enemy) bulletHit).dmghp() <= 0) 
                        {
                            level.enemies.remove(bulletHit);
                        }
                    }
                    bullets.remove(x);
                }
            }
        }
        window.display();
        return result;
    }

    /**
     * This method renders, moves & makes enemy shoot
     * @param viewZone  Current player view zone
     * @param result    ArrayList containing objects within viewZone
     */
    public void handleEnemy(FloatRect viewZone, ArrayList<GameObject> result){
        for (Enemy a : level.enemies) {  //Changed object type to Enemy in order to access methods

            if (viewZone.intersection(a.getHitBox()) != null) {

                //Initiates movement for each enemy
                a.update(level.objectList);

                // Enemy only shoots if player is in range
                if (a.lookForPlayer(player) && a.shoot() != null) {
                    hostileBullets.add(a.shoot());
                }

                // Renders Enemies
                result.add(a);
                window.draw(a);
            }
        }
        if (!hostileBullets.isEmpty()) {

            //  Render Bullets fired by Enemies
            for (int x = 0; x < hostileBullets.size(); x++) {

                Bullet bullet = hostileBullets.get(x);
                bullet.update(null);
                window.draw(bullet);

                // De-spawns bullets upon impact
                GameObject bulletHit = bullet.collides(result);
                if (!bullet.bulletInSight(window) || bulletHit != null) {

                    if (bulletHit != null && bulletHit instanceof Player) {
                        //do something when player gets hit
                    }
                    hostileBullets.remove(x);
                }
            }
        }
    }
}
