public interface MenuMaker
{
    /**
     * Method for displaying all the menu contents. Returns a code on what was the outcome of the menu.
     * 
     */
    public int displayMenu();

    /**
     * Method for checking what button was pressed and do stuff accordingly
     */
    public void checkButtons(float MouseX,float MouseY);
}
