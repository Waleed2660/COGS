/**
 * A class that represents a platform. Can be passable but possible to stand on or completely solid. Extends GameObject.
 */

public class Platform extends GameObject
{
    public Platform(float x, float y, String texPath)
    {
        super(x, y, texPath, null);
    }
}
