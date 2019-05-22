package paymentWizard;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import se.chalmers.cse.dat216.project.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class paymentWizard implements Initializable {

    @FXML private AnchorPane wizardPane;

    // Första sidan
    @FXML private AnchorPane pageOne;
    @FXML private Label deliveryTitle;
    @FXML private RadioButton deliveryNormal;
    @FXML private RadioButton deliveryExpress;
    @FXML private ImageView POCancel;
    @FXML private ImageView PONext;

    // Andra sidan
    @FXML private AnchorPane pageTwo;
    @FXML private Label payTitle;
    @FXML private RadioButton payCard;
    @FXML private RadioButton payInvoice;
    @FXML private ImageView infoButton;
    @FXML private ImageView PTPay;
    @FXML private ImageView PTPrevious;
    @FXML private Label priceLabel;
    @FXML private Label cardLabel;
    @FXML private PasswordField cvcField;

    // Tredje sidan
    @FXML private AnchorPane pageThree;
    @FXML private Label titleConfirm;
    @FXML private ImageView backToFront;
    @FXML private ImageView recipeButton;

    // Info CVC pop-up
    @FXML private AnchorPane infoPage;

    // Recipe pop-up
    @FXML private AnchorPane recipePage;
    @FXML private TextArea recipeList;


    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();

public paymentWizard () {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paymentWizard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
        fxmlLoader.load();
         } catch (
                 IOException exception) {
            throw new RuntimeException(exception);
        }

}


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        updateTotalCost();
        updateCardInfo();

    }


        // Metoder för feedback

    @FXML
    public void cancelEnter(){
        POCancel.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/CancelClick_00000.png")));
    }

    @FXML
    public void nextEnter(){
        PONext.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/NextClick_00000.png")));
    }

    @FXML
    public void backEnter(){
        PTPrevious.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BackClick_00000.png")));
    }

    @FXML
    public void payEnter(){
        PTPay.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/PayClick_00000.png")));
    }

    @FXML
    public void BTFEnter(){
        backToFront.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BTFClick_00000.png")));
    }

    @FXML
    public void recipeEnter(){
        recipeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/KvittoClick_00000.png")));
    }

    @FXML
    public void infoEnter(){
        infoButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/InfoClick_00000.png")));
    }



    //// ---------------------- ////

    @FXML
    public void cancelExited(){
        POCancel.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/CancelNormal_00000.png")));
    }

    @FXML
    public void nextExited(){
        PONext.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/NextNormal_00000.png")));
    }

    @FXML
    public void backExited(){
        PTPrevious.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BackNormal_00000.png")));
    }

    @FXML
    public void payExited(){
        PTPay.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/PayNormal_00000.png")));
    }

    @FXML
    public void BTFExited(){
        backToFront.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BTFNormal_00000.png")));
    }

    @FXML
    public void recipeExited(){
        recipeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/KvittoNormal_00000.png")));
    }

    @FXML
    public void infoExited(){
        infoButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/InfoNormal_00000.png")));
    }

    // Metoder för knapptryck

    @FXML
    public void cancelClicked(){

        // TODO: Lägg till funktionalitet för att stänga fönstret.

    }

    @FXML
    public void nextClick(){
        pageTwo.toFront();
    }

    @FXML
    public void payClick(){
        pageThree.toFront();
    }

    @FXML
    public void backClick(){
        pageOne.toFront();
    }

    @FXML
    public void BTFClick(){

        // TODO: Lägg till funktionalitet för att återgå till startsidan.

    }

    @FXML
    public void infoClick(){
        infoPage.toFront();
    }

    @FXML
    public void recipeClick(ShoppingCart shoppingCart){
        updateRecipe(shoppingCart);
        recipePage.toFront();
    }


    // Hjälpmetoder

    private void updateTotalCost () {

        priceLabel.setText(String.format("%.2f",shoppingCart.getTotal()) + " kr");

    }

    private  void updateCardInfo () {

        cardLabel.setText(iMatDataHandler.getCreditCard().getCardNumber());

    }

    private void updateRecipe(ShoppingCart shoppingCart){

        StringBuilder sb = new StringBuilder();
        List<ShoppingItem> itemsList = shoppingCart.getItems();

        for ( ShoppingItem product : itemsList) {
            sb.append(product).append(" - ").append(product.getProduct().getPrice()).append("\n");
        }
        sb.append("\n").append("Totalpris: ").append(shoppingCart.getTotal());
        recipeList.setText(sb.toString());

    }


     //

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

}
