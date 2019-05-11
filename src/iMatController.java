import browseListItem.IBrowseListItemListener;
import browseListItem.ListItemPool;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import kategoriListItem.IKategoriListner;
import kategoriListItem.KategoriListItem;
import se.chalmers.cse.dat216.project.*;
import browseListItem.BrowseListItem;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.*;

public class iMatController implements Initializable, IKategoriListner, IBrowseListItemListener {
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Image shoppingCartImage = new Image("images/shoppingcart.png");

    @FXML TilePane browserPane;
    @FXML ListView<KategoriListItem> kategoriListView;
    @FXML Label browseTitleLable;
    @FXML Button favoriteButton;
    @FXML TextField searchBar;
    @FXML Button searchButton;
    @FXML Label cartImdicatorLabel;
    @FXML AnchorPane cartImdicatorPnane;
    @FXML ImageView cartImage;

    private ListItemPool itemPool;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        itemPool = ListItemPool.getInstance();

        browserPane.setVgap(30);
        browserPane.setHgap(30);

        BrowseListItem.addListener(this);


        cartImage.setImage(shoppingCartImage);

        browseTitleLable.setText("Alla produkter");

        for(Product p: handler.getProducts())
            browserPane.getChildren().add(itemPool.getBrowserListItem(p));

        kategoriListView.setFixedCellSize(50);
        setCategories();

        favoriteButton.setOnAction(event->favoriteClicked());
        cartImdicatorPnane.setVisible(false);
    }

    void favoriteClicked(){
        browseTitleLable.setText("Favoriter");

        browserPane.getChildren().clear();
        for(Product p: handler.favorites())
            browserPane.getChildren().add(itemPool.getBrowserListItem(p));
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

    public void search(){
        browserPane.getChildren().clear();
        browseTitleLable.setText("Sök: \"" + searchBar.getText() + "\"");
        for(Product p : handler.findProducts(searchBar.getText()))
            browserPane.getChildren().add(itemPool.getBrowserListItem(p));
    }

    @Override
    public void notify(KategoriListItem item) {
        browseTitleLable.setText(item.getCategoryName());

        browserPane.getChildren().clear();
        for(Product p: handler.getProducts())
            if(Arrays.asList(item.getCategories()).contains(p.getCategory()))
                browserPane.getChildren().add(itemPool.getBrowserListItem(p));
    }

    @Override
    public void notify(BrowseListItem item) {
        int temp = handler.getShoppingCart().getItems().size();
        if(temp>0)
            cartImdicatorPnane.setVisible(true);
        cartImdicatorLabel.setText(String.valueOf(temp));
    }
}
