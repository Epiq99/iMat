package kategoriListItem;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public abstract class KategoriListItem extends AnchorPane implements Comparable{

    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML Label kategoriLable;

    private String katName;

    public KategoriListItem(String kategoriNamn) {

        katName = kategoriNamn;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../kategoriListItem/kategorilistitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        kategoriLable.setText(kategoriNamn);
        
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClick());
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof KategoriListItem))
            return 0;

        return this.getCategoryName().compareTo(((KategoriListItem)o).katName);
    }

    abstract public void onClick();

    public String getCategoryName(){return katName;}
}
