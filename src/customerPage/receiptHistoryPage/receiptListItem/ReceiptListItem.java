package customerPage.receiptHistoryPage.receiptListItem;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import paymentWizard.reciptItem.ReciptItem;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReceiptListItem extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Order order;
    private static int id = 0;
    private boolean isExtended;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM");

    @FXML FlowPane itemList;
    @FXML Button showButton;
    @FXML AnchorPane mainPane;
    @FXML Label dateLabel, orderNumberLabel, sumLabel;

    public ReceiptListItem(Order receipt) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receiptlistitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        order = receipt;
        isExtended = false;

        showButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> showItems());

        orderNumberLabel.setText(String.valueOf(order.getOrderNumber()));
        dateLabel.setText(dateFormat.format(order.getDate()));
        sumLabel.setText(String.valueOf(getSum(order.getItems())));

        if(id++%2==0)
            mainPane.setStyle("-fx-background-color: lightgray");
    }

    private double getSum(List<ShoppingItem> list){
        double sum = 0;
        for(ShoppingItem i:list)
            sum += i.getProduct().getPrice() * i.getAmount();

        return sum;
    }

    private void showItems(){

        if(isExtended)
        {
            itemList.getChildren().clear();
            itemList.getStyleClass().clear();
            showButton.setText("VISA");
        }
        else {
            for(ShoppingItem s: order.getItems())
                itemList.getChildren().add(new ReciptItem(s.getProduct().getName(), (int) s.getAmount(), s.getTotal()));

            itemList.getStyleClass().add("extendedPane");
            showButton.setText("DÖLJ");
        }

        isExtended = !isExtended;
    }
}
