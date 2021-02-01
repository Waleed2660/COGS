/**
 * A class that represents an enemy entity. Can be either dog or robot. Extends GameObject.
 */

public class Enemy extends GameObject
{
    int hp;
    public Enemy(float x, float y, String texPath,int hp)
    {
        super(x, y, texPath, null);
        this.hp = hp;

    }

    public int dmghp()
    {
        hp -= 1;
        if(hp == 0)
        {
            return 0;
        }
        return hp;
    }
}
