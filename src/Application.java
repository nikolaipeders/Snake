import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Local Variables
        Game game = new Game();
        String stdFont = "-fx-font-family: monospace; -fx-font-size: 22; -fx-text-fill: black;";
        String stdBackGround = "-fx-background-color: white;";

        // Starting procedures and Set the Scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle(stdBackGround);
        Scene applicationScene = new Scene(root, 550, 600);

        // Add Canvas to Root
        root.getChildren().add(game.loadCanvas());

        // Handling input (Arrow Keys + Enter)
        Input.sceneControls(applicationScene, game);

        // Press Enter to Start
        Label pressEnter = new Label("- Press [ENTER] to start -");
        pressEnter.setStyle(stdFont);
        root.getChildren().add(pressEnter);

        // If Insane
        if (Game.insaneMode) {
            Rotate rotate = new Rotate();
            rotate.setPivotX(applicationScene.getHeight() / 2);
            rotate.setPivotY(applicationScene.getWidth() / 2);
            rotate.setAngle(90);
            root.getTransforms().add(rotate);
        }

        // Initialize Window
        game.initializeBoard();
        game.snakeAnimation();
        primaryStage.setScene(applicationScene);
        primaryStage.setTitle("Snake");
        primaryStage.show();
    }
}
