import org.jsfml.graphics.*;

public class Boss extends Enemy
{
    int ffs = 0; 
    private int hp = 100;
    public Boss(float x, float y, String texPath, Level level)
    {
        super(x,y,texPath,0,level,15);
    }

    

    
    
}