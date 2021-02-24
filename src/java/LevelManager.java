public class LevelManager
{
    public static void runLevels(int startLevel, int numOfLevels, MMWindow window)
    {
        for(int i = startLevel; i <= numOfLevels; i++)
        {
            int endCode = 2;
            while(endCode == 2)
            {
                endCode = new GameRunner(window, "Level"+i).run();
                window.resetView();
            }
            if(endCode == 0)
            {
                break;
            }
        }
    }
}
