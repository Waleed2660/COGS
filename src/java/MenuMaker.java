public interface MenuMaker
{
    /**
     * Method for displaying all the menu contents
     */
    public void displayMenu(MMWindow window);

    /**
     * Method for checking what button was pressed and do stuff accordingly
     */
    public void checkButtons(float MouseX,float MouseY);
}
