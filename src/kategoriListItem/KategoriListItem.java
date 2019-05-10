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

public class KategoriListItem extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML Label kategoriLable;

    private ProductCategory[] kategorier;
    private String katName;
    static private final List<IKategoriListner> listeners = new ArrayList<>();

    public KategoriListItem(ProductCategory[] kat, String kategoriNamn) {

        katName = kategoriNamn;
        kategorier = kat;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kategorilistitem.fxml"));
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
    
    private void onClick(){
        for(IKategoriListner l: listeners)
            l.notify(kategorier);
    }
    
    static public void addListener(IKategoriListner listner){
        listeners.add(listner);
    }
}
