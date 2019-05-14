package helppage;


import helppage.questionlistitem.QuestionListItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelpPage extends AnchorPane {

    private final List<QuestionListItem> questions = new ArrayList<>();
    @FXML AnchorPane mainPane;
    @FXML FlowPane questionFlowPane;

    public HelpPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("helppage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        questions.add(new QuestionListItem("Har iMat några fysiska butiker?",
                "Nej, iMat finnes endast på internet."));
        questions.add(new QuestionListItem("Kan jag betala med kort?",
                "Ja, självklart kan du betala med kort. Välj \"kort\" som alternativ" +
                        " när du betalar så kommer du att betala med kort"));
        questions.add(new QuestionListItem("Hur handlar jag?",
                "1. Välj dina varor. När du trycker på lägg till så läggs din vara till i varukorgen\n"+
                "2. Gå till kassan. Om du trucker på varukorgen högst upp till höger så kommer du kunna ta dig till kassan\n" +
                "3. Betala. Följ instruktionerna vid kassan."));

        for(QuestionListItem q : questions)
            questionFlowPane.getChildren().add(q);

    }
}
