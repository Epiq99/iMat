package customerPage;

import javafx.scene.layout.AnchorPane;
import kategoriListItem.KategoriListItem;

import java.util.ArrayList;
import java.util.List;

public class SettingCategoryListItem extends KategoriListItem{

    private static final List<ISettingCategoryListener> listeners = new ArrayList<>();
    private SettingsPane settingspanel;

    public SettingCategoryListItem(String catName, SettingsPane pane){
        super(catName);
        settingspanel = pane;
    }
    @Override
    public void onClick() {
        settingspanel.update();

        for(ISettingCategoryListener l: listeners)
            l.settingCategoryPressed(settingspanel);
    }

    public static void addListener(ISettingCategoryListener listener)
    {
        listeners.add(listener);
    }
}