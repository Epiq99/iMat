package helppage.questionlistitem;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionListItem extends AnchorPane {

    private static final List<QuestionListItem> listItems = new ArrayList<>();
    @FXML AnchorPane mainPane;
    @FXML Label arrowLable;
    @FXML FlowPane answerFlowPane;
    @FXML Label questionLable;
    Text answerText;

    private boolean isCollapsed;
    public QuestionListItem(String question, String answer) {

        listItems.add(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("questionlistitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        isCollapsed = true;
        answerText = new Text(answer);
        questionLable.setText(question);
        answerText.getStyleClass().add("answerLable");

        answerText.setWrappingWidth(350);
        mainPane.setPrefHeight(20);
        mainPane.setMaxHeight(20);

        arrowLable.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                onQuestionClicked()
        );
        questionLable.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                onQuestionClicked()
        );
    }

    private void onQuestionClicked(){
        if(!isCollapsed){
            collaps();
            return;
        }

        for(QuestionListItem q: listItems) {
            q.collaps();
        }
        extend();

    }

    private void collaps(){
        answerFlowPane.getChildren().clear();
        isCollapsed = true;
        arrowLable.setRotate(0);
    }

    private void extend(){
        answerFlowPane.getChildren().add(answerText);
        isCollapsed = false;
        arrowLable.setRotate(90);
    }
}
