package customerPage;

import javafx.scene.layout.AnchorPane;
import kategoriListItem.KategoriListItem;

import java.util.ArrayList;
import java.util.List;

public class SettingCategoryListItem extends KategoriListItem{

    private static final List<ISettingCategoryListener> listeners = new ArrayList<>();
    private AnchorPane settingspanel;

    public SettingCategoryListItem(String catName, AnchorPane pane){
        super(catName);
        settingspanel = pane;
    }
    @Override
    public void onClick() {
        for(ISettingCategoryListener l: listeners)
            l.settingCategoryPressed(settingspanel);
    }

    public static void addListener(ISettingCategoryListener listener)
    {
        listeners.add(listener);
    }
}