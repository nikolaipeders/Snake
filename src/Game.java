import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Game {

    static int score = 0;
    static int width = 25;
    static int height = 25;
    static int foodX = 0;
    static int foodY = 0;
    static int squareSize = 20;
    static Direction direction = Direction.right;
    static boolean gameOver = false;
    static boolean insaneMode = false;
    Canvas canvas;
    GraphicsContext graphicsContext;
    Timer timer = new Timer();
    Timeline tl = new Timeline();

    public Game() {
    }

    public Canvas loadCanvas() {

        canvas = new Canvas(width * squareSize, height * squareSize);
        graphicsContext = canvas.getGraphicsContext2D();

        return canvas;

    }

    public void executeGame() {

        // Different Content for Game Over
        if (gameOver) {

            // Display this
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("", 14));
            graphicsContext.fillText("YOU DIED", 20, 60);

            // Reset Stats
            score = 0;
            Snake.clearSnake();
            return;
        }

        // Draw Board
        initializeBoard();

        // Different Rotation for Insane Mode
        if (score == 1) {
            insaneMode = true;
        }

        // Function for moving Snake
        for (int i = Snake.snake.size() - 1 ; i >= 1 ; i--) {
            Snake.snake.get(i).x = Snake.snake.get(i-1).x;
            Snake.snake.get(i).y = Snake.snake.get(i-1).y;
        }

        // Fetch controls
        Input.controlDirection();

        // Increment Size of Snake when Food is reached
        if (foodX == Snake.snake.get(0).x && foodY == Snake.snake.get(0).y) {
            Snake.snake.add(new Square(-1, -1));
            score++;
            Food.addFood();
            timer.resetTimerToZero();
        }
        else if (timer.elapsedTime() > 5000) {
            System.out.println(timer.elapsedTime());
            Food.addFood();
            timer.resetTimerToZero();
        }

        // Game Over if Snake eats Snake
        for (int i = 1; i < Snake.snake.size(); i++) {
            if (Snake.snake.get(0).x == Snake.snake.get(i).x && Snake.snake.get(0).y == Snake.snake.get(i).y) {
                gameOver = true;
                break;
            }
        }
    }

    public void snakeAnimation () {

        // Setup Timeline but do not Play (This happens in Application)
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.setAutoReverse(false);
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(120), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { executeGame(); }
        }));
    }

    public void resetGame() {
        gameOver = false;
        direction = Direction.right;
        Snake.clearSnake();
        Snake.initializeSnake();
    }

    public void initializeBoard() {

        // Set Background for Canvas
        graphicsContext.setFill(Color.MISTYROSE);
        graphicsContext.fillRect(0, 0, width * squareSize, height * squareSize);

        // Set Color of Food
        graphicsContext.setFill(Color.DARKRED);
        graphicsContext.fillOval(foodX * squareSize, foodY * squareSize, squareSize, squareSize);

        // Paint Snake in Canvas
        for (Square sq : Snake.snake) {
            graphicsContext.setFill(Color.DARKOLIVEGREEN);
            graphicsContext.fillRect(sq.x * squareSize, sq.y * squareSize, squareSize , squareSize);
        }

        // Add Score to Canvas
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(new Font("", 14));
        graphicsContext.fillText("Score: " + score, 20, 20);

        // Add Timer to Canvas
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(new Font("", 14));
        graphicsContext.fillText("Time: " + timer.getElapsedTime(), 20, 40);
    }
}
