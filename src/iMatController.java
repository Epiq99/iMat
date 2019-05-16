import browseListItem.IBrowseListItemListener;
import browseListItem.ListItemPool;
import browserTitle.BrowseTitle;
import customerPage.ISettingCategoryListener;
import customerPage.SettingCategoryListItem;
import customerPage.passwordsettings.PasswordSettingsPage;
import customerPage.paymentsettings.PaymentSettingPane;
import customerPage.personaldatapane.PersonalDataPane;
import detailedview.DetailedView;
import detailedview.IDetailedViewListener;
import foodcategorylistitem.FoodCategoryListItem;
import foodcategorylistitem.IFoodCategoryListner;
import helppage.HelpPage;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import se.chalmers.cse.dat216.project.*;
import browseListItem.BrowseListItem;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class iMatController implements Initializable, IFoodCategoryListner,
                                        IBrowseListItemListener, IDetailedViewListener,
                                        ISettingCategoryListener {
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Image shoppingCartImage = new Image("images/shoppingcart.png");

    @FXML FlowPane browserPane;
    @FXML TilePane kategoriTilePane;
    //@FXML Label browseTitleLable;
    //@FXML Button favoriteButton;
    @FXML TextField searchBar;
    @FXML Button searchButton;
    @FXML Label cartImdicatorLabel;
    @FXML AnchorPane cartImdicatorPnane;
    @FXML ImageView cartImage;
    @FXML AnchorPane handlaMenuPane;
    @FXML AnchorPane helpMenuPane;
    @FXML AnchorPane myPagesPane;
    @FXML ScrollPane mainScrollPane;
    private ListItemPool itemPool;

    private boolean foodCategoriesUp = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        itemPool = ListItemPool.getInstance();

        browserPane.setVgap(30);
        browserPane.setHgap(30);

        BrowseListItem.addListener(this);
        DetailedView.addListener(this);
        SettingCategoryListItem.addListener(this);
        FoodCategoryListItem.addListener(this);

        cartImage.setImage(shoppingCartImage);
        cartImdicatorPnane.setVisible(false);

        handlaMenuPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                setUpStartPage()
                );
        helpMenuPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                setUpHelpPage()
        );
        myPagesPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                setUpMyPages()
                );

        kategoriTilePane.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                int height = 0;
                for(Node p:kategoriTilePane.getChildren())
                    height += ((Pane)p).getHeight();

                kategoriTilePane.setMaxHeight(height);
                kategoriTilePane.setPrefHeight(height);
            }
        });
        setUpStartPage();

        //favoriteButton.setOnAction(event->favoriteClicked());
    }

    private void setUpMyPages(){
        kategoriTilePane.getChildren().clear();
        kategoriTilePane.getChildren().add(new SettingCategoryListItem("Personuppgifter",PersonalDataPane.getInstance()));
        kategoriTilePane.getChildren().add(new SettingCategoryListItem("Lösenord", PasswordSettingsPage.getInstance()));
        kategoriTilePane.getChildren().add(new SettingCategoryListItem("Betalningssätt", PaymentSettingPane.getInstance()));

        settingCategoryPressed(PersonalDataPane.getInstance());
        foodCategoriesUp = false;
    }

    private void setUpHelpPage(){
        setFoodCategories();
        browserPane.getChildren().clear();
        browserPane.getChildren().add(new HelpPage());
    }

    private void setUpStartPage(){
        setFoodCategories();
        browserPane.getChildren().clear();
        if(handler.favorites().size()>0) {
            browserPane.getChildren().add(new BrowseTitle("Favoriter"));
            for(Product p: handler.favorites())
                browserPane.getChildren().add(new BrowseListItem(p));
        }

        browserPane.getChildren().add(new BrowseTitle("Alla produkter"));

        for(Product p: handler.getProducts())
            browserPane.getChildren().add(itemPool.getBrowserListItem(p));

        mainScrollPane.setHvalue(0);
    }

    void setUpOfferPage(){

    }

    void favoriteClicked(){
        browserPane.getChildren().clear();
        browserPane.getChildren().add(new BrowseTitle("Favoriter"));

        browserPane.getChildren().clear();
        for(Product p: handler.favorites())
            browserPane.getChildren().add(itemPool.getBrowserListItem(p));
    }

    void setFoodCategories(){
        if(foodCategoriesUp)
            return;

        kategoriTilePane.getChildren().clear();
        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.MEAT},"Kött"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.POD, ProductCategory.CABBAGE,
                        ProductCategory.HERB, ProductCategory.ROOT_VEGETABLE,
                        ProductCategory.POTATO_RICE},"Grönsaker"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.BERRY, ProductCategory.NUTS_AND_SEEDS},"Bär"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.FISH},"Fisk"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.FRUIT, ProductCategory.CITRUS_FRUIT,
                        ProductCategory.EXOTIC_FRUIT, ProductCategory.VEGETABLE_FRUIT,
                        ProductCategory.MELONS},"Frukt"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.DAIRIES},"Mjölkprodukter"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.COLD_DRINKS, ProductCategory.HOT_DRINKS},"Drickor"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.SWEET},"Godis"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.BREAD},"Bröd"));

        kategoriTilePane.getChildren().add(new FoodCategoryListItem(new ProductCategory[]
                {ProductCategory.FLOUR_SUGAR_SALT, ProductCategory.PASTA,
                        ProductCategory.POTATO_RICE},"Torrvaror"));

        foodCategoriesUp = true;
    }

    public void search(){
        browserPane.getChildren().clear();
        browserPane.getChildren().add(new BrowseTitle("Sök: \"" + searchBar.getText() + "\""));
        for(Product p : handler.findProducts(searchBar.getText()))
            browserPane.getChildren().add(itemPool.getBrowserListItem(p));
    }

    @Override
    public void notify(FoodCategoryListItem item) {
        browserPane.getChildren().clear();
        browserPane.getChildren().add(new BrowseTitle(item.getCategoryName()));


        browserPane.getChildren().clear();
        for(Product p: handler.getProducts())
            if(Arrays.asList(item.getCategories()).contains(p.getCategory()))
                browserPane.getChildren().add(itemPool.getBrowserListItem(p));

        mainScrollPane.setHvalue(0);
    }

    @Override
    public void addToCartNotify(BrowseListItem item) {
        int temp = handler.getShoppingCart().getItems().size();
        if(temp>0)
            cartImdicatorPnane.setVisible(true);
        cartImdicatorLabel.setText(String.valueOf(temp));
    }

    @Override
    public void detailedViewNotify(BrowseListItem item){
        browserPane.getChildren().clear();
        browserPane.getChildren().add(new DetailedView(item.getProduct()));
    }

    @Override
    public void addToCartNotification(DetailedView item) {
        int temp = handler.getShoppingCart().getItems().size();
        if(temp>0)
            cartImdicatorPnane.setVisible(true);
        cartImdicatorLabel.setText(String.valueOf(temp));
    }

    @Override
    public void settingCategoryPressed(AnchorPane pane) {
        browserPane.getChildren().clear();
        browserPane.getChildren().add(pane);
    }
}
