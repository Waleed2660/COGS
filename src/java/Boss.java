import org.jsfml.graphics.*;

public class Boss extends Sprite
{
    private int hp = 100;
    public Boss(float x, float y, String texPath)
    {
        
    }

    /**
     * Damages enemy health
     * 
     * @returns hp value if above 0 else returns 0
     */
    public int dmghp()
    {
        hp -= 2;
        if(hp == 0)
        {
            return 0;
        }
        return hp;
    }

    /**
     * used to retrieve hp
     * @return hp
     */
    public int gethp()
    {
        return hp;
    }

    
    /**
     * Shoots red laser
     * 
     * @return bullet for red
     */
    public Bullet rShoot()
    {
        return null;
    }

    /**
     * Shoots green laser
     * 
     * @return bullet for green
     */
    public Bullet gShoot()
    {
        return null;
    }
    /**
     * Shoots blue laser
     * 
     * @return bullet for blue
     */
    public Bullet bShoot()
    {
        return null;
    }

    /**
     * Shoots yellow laser
     * 
     * @return bullet for yellow
     */
    public Bullet yShoot()
    {
        return null;
    }
    
}