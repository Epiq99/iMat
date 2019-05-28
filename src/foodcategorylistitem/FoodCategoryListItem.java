package foodcategorylistitem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import kategoriListItem.KategoriListItem;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodCategoryListItem extends KategoriListItem {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML Label kategoriLable;

    private ProductCategory[] kategorier;

    static private final List<IFoodCategoryListner> listeners = new ArrayList<>();

    public FoodCategoryListItem(ProductCategory[] kat, String kategoriNamn) {

        super(kategoriNamn);

        kategorier = kat;

    }

    @Override
    public void onClick(){
        for(IFoodCategoryListner l: listeners)
            l.notify(this);
    }
    
    static public void addListener(IFoodCategoryListner listner){
        listeners.add(listner);
    }

    public ProductCategory[] getCategories() {return kategorier;}
}
