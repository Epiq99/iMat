import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import kategoriListItem.IKategoriListner;
import kategoriListItem.KategoriListItem;
import se.chalmers.cse.dat216.project.*;
import browseListItem.BrowseListItem;

import java.net.URL;
import java.util.*;

public class iMatController implements Initializable, IKategoriListner {
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML TilePane browserPane;
    @FXML ListView<KategoriListItem> kategoriListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        browserPane.setVgap(30);
        browserPane.setHgap(30);

        for(Product p: handler.getProducts())
            browserPane.getChildren().add(new BrowseListItem(p));

        kategoriListView.setFixedCellSize(50);
        setCategories();
    }

    void setCategories(){
        KategoriListItem.addListener(this);
        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.MEAT},"Kött"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.POD, ProductCategory.CABBAGE,
                        ProductCategory.HERB, ProductCategory.ROOT_VEGETABLE,
                        ProductCategory.POTATO_RICE},"Grönsaker"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.BERRY, ProductCategory.NUTS_AND_SEEDS},"Bär"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.FISH},"Fisk"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.FRUIT, ProductCategory.CITRUS_FRUIT,
                        ProductCategory.EXOTIC_FRUIT, ProductCategory.VEGETABLE_FRUIT,
                        ProductCategory.MELONS},"Frukt"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.DAIRIES},"Mjölkprodukter"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.COLD_DRINKS, ProductCategory.HOT_DRINKS},"Drickor"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.SWEET},"Godis"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.BREAD},"Bröd"));

        kategoriListView.getItems().add(new KategoriListItem(new ProductCategory[]
                {ProductCategory.FLOUR_SUGAR_SALT, ProductCategory.PASTA,
                        ProductCategory.POTATO_RICE},"Torrvaror"));
    }


    @Override
    public void notify(ProductCategory[] pc) {
        browserPane.getChildren().clear();
        for(Product p: handler.getProducts())
            if(Arrays.asList(pc).contains(p.getCategory()))
                browserPane.getChildren().add(new BrowseListItem(p));
    }
}
