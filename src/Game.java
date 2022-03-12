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
    static boolean canvasTurned = false;
    static boolean bigHead = false;
    static Rotate rotate = new Rotate();
    Canvas canvas;
    static long speed = 1;
    GraphicsContext graphicsContext;
    Timer timer = new Timer();
    Timeline tl = new Timeline();

    public Game() {
    }

    public Canvas loadCanvas() {

        canvas = new Canvas(width * squareSize, height * squareSize);
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.getTransforms().add(rotate);

        return canvas;

    }

    public static void turnCanvas(){

        rotate.setPivotY(height * squareSize / 2.0);
        rotate.setPivotX(width * squareSize / 2.0);
        rotate.setAngle(90);

    }

    public static void turnCanvasBack(){
        rotate.setAngle(0);
        Game.canvasTurned = false;
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
        if (score == 30 && !canvasTurned) {
            turnCanvas();
            canvasTurned = true;
        }

        // Function for moving Snake
        for (int i = Snake.snake.size() - 1 ; i >= 1 ; i--) {
            Snake.snake.get(i).x = Snake.snake.get(i-1).x;
            Snake.snake.get(i).y = Snake.snake.get(i-1).y;
        }

        // Fetch controls
        Input.controlDirection();

        // Determine speed
        tl.setRate(speed);

        // Set Action when Food is reached
        if (bigHead && foodX == Snake.snake.get(-0).x-1 && foodY == Snake.snake.get(0).y-1 ||
                bigHead && foodX == Snake.snake.get(-0).x && foodY == Snake.snake.get(0).y ||
                bigHead && foodX == Snake.snake.get(-0).x+1 && foodY == Snake.snake.get(0).y+1 ||
                bigHead && foodX == Snake.snake.get(-0).x+2 && foodY == Snake.snake.get(0).y+2) {

            incrementSnake();
        }
        if (foodX == Snake.snake.get(0).x && foodY == Snake.snake.get(0).y) {

            incrementSnake();
        }

        // If Food isn't reached in 8 seconds
        else if (timer.elapsedTime() > 8000) {
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
        direction = Direction.right;
        gameOver = false;
        bigHead = false;
        Food.isNormalFood = true;
        Food.isFastFood = false;
        Food.isSteroidFood = false;

        Snake.clearSnake();
        Snake.initializeSnake();

        Timer timer = new Timer();
        timer.resetTimerToZero();
        timer.startDisplayTimer();
        Game.turnCanvasBack();
        Food.addFood();
        speed = 1;

    }

    public void initializeBoard() {

        // Set Background for Canvas
        graphicsContext.setFill(Color.MISTYROSE);
        graphicsContext.fillRect(0, 0, width * squareSize, height * squareSize);

        // Set Color of Food
        if (Food.isFastFood) {
            graphicsContext.setFill(Color.DARKORANGE);
        }
        else if (Food.isSteroidFood) {
            graphicsContext.setFill(Color.DARKSLATEBLUE);
        }
        else {
            graphicsContext.setFill(Color.DARKRED);
        }
        graphicsContext.fillOval(foodX * squareSize, foodY * squareSize, squareSize, squareSize);

        // Paint Snake in Canvas
        graphicsContext.setFill(Color.DARKOLIVEGREEN);

        for (int i = 1; i < Snake.snake.size(); i++) {
            if (bigHead) {
                graphicsContext.fillRect((Snake.snake.get(0).x-1) * squareSize, (Snake.snake.get(0).y-1) * squareSize, squareSize * 3, squareSize * 3);
            } else {
                graphicsContext.fillRect(Snake.snake.get(0).x * squareSize, Snake.snake.get(0).y * squareSize, squareSize, squareSize);
            }
            graphicsContext.fillRect(Snake.snake.get(i).x * squareSize, Snake.snake.get(i).y * squareSize, squareSize, squareSize);
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

    public void determineSpeed() {
        // Determine Speed
        if (Food.isFastFood) {
            speed = 2;
        }
        else {
            speed = 1;
        }
    }

    public void incrementSnake() {

        Snake.snake.add(new Square(-1, -1));
        score++;

        timer.resetTimerToZero();

        determineSpeed();

        bigHead = Food.isSteroidFood;

        Food.addFood();
    }
}
