package customerPage.receiptHistoryPage;


import customerPage.SettingsPane;
import customerPage.receiptHistoryPage.receiptListItem.ReceiptListItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReceiptHistoryPage extends SettingsPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private static ReceiptHistoryPage self;
    private static int id = 0;

    @FXML ScrollPane scrollPane;
    @FXML FlowPane receiptLsit;

    private ReceiptHistoryPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receipthistorypage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        scrollPane.setHvalue(0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        update();
    }

    @Override
    public void update(){
            receiptLsit.getChildren().clear();

            List<Order> orders = handler.getOrders();
            Collections.sort(orders, Comparator.comparing(Order::getDate).reversed());
            for(Order o: orders)
                receiptLsit.getChildren().add(new ReceiptListItem(o));
    }

    public static ReceiptHistoryPage getInstance(){
        if(self == null)
            self= new ReceiptHistoryPage();

        return self;
    }
}
