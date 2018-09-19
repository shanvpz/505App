package in.techfantasy.hope;

public class cardMenuItem {
    private String menuText;
    private int menuImage;
    private String action;

    public cardMenuItem(){

    }

    public cardMenuItem(String menuText, int menuImage, String action) {
        this.menuText = menuText;
        this.menuImage = menuImage;
        this.action = action;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public int getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(int menuImage) {
        this.menuImage = menuImage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
