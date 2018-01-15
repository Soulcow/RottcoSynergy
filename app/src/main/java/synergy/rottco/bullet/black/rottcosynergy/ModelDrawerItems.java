package synergy.rottco.bullet.black.rottcosynergy;

/**
 * Created by boghi on 12/9/2017.
 */

public class ModelDrawerItems {
    public static int TITLE =-3;
    public static int IMAGE =-2;
    public static int HEADER =0;
    public static int LIST_ITEM =1;
    public static int EMPTY =2;

    public int itemType;
    public String text;

    public ModelDrawerItems(int itemType,String text)
    {
        this.itemType = itemType;
        this.text=text;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
