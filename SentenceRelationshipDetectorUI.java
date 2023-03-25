import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SentenceRelationshipDetectorUI extends Application {
  private SentenceRelationshipDetector detector;

  @Override
  public void start(Stage primaryStage) {
    detector = new SentenceRelationshipDetector();

    Label sentence1Label = new Label("Sentence 1:");
    Label sentence2Label = new Label("Sentence 2:");
    TextArea sentence1TextArea = new TextArea();
    TextArea sentence2TextArea = new TextArea();
    Button detectButton = new Button("Detect Relationship");
    Label resultLabel = new Label();

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(25, 25, 25, 25));
    gridPane.add(sentence1Label, 0, 0);
    gridPane.add(sentence1TextArea, 1, 0);
    gridPane.add(sentence2Label, 0, 1);
    gridPane.add(sentence2TextArea, 1, 1);
    gridPane.add(detectButton, 1, 2);
    gridPane.add(resultLabel, 1, 3);

    
    detectButton.setOnAction(e -> {
      String sentence1 = sentence1TextArea.getText();
      String sentence2 = sentence2TextArea.getText();
      String result = detector.detectRelationship(sentence1, sentence2);
      resultLabel.setText(result);
    });

    Scene scene = new Scene(gridPane, 400, 250);
    primaryStage.setTitle("Sentence Relationship Detector");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
