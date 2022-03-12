import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {

    public Input() {
    }

    public static void sceneControls(Scene scene, Game snake) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {

            // This one initiates the Timeline from SnakeAnimation in Game
            if (key.getCode() == KeyCode.ENTER) {
                snake.resetGame();
                snake.tl.play();
                Timer timer = new Timer();
                timer.resetTimerToZero();
                timer.startDisplayTimer();
                Food.addFood();
            }

            if (key.getCode() == KeyCode.UP) {
                if (Game.direction != Direction.down) {
                    Game.direction = Direction.up;
                }
            }
            if (key.getCode() == KeyCode.RIGHT) {
                if (Game.direction != Direction.left) {
                    Game.direction = Direction.right;
                }
            }
            if (key.getCode() == KeyCode.DOWN) {
                if (Game.direction != Direction.up) {
                    Game.direction = Direction.down;
                }
            }
            if (key.getCode() == KeyCode.LEFT) {
                if (Game.direction != Direction.right) {
                    Game.direction = Direction.left;
                }
            }
        });
    }

    public static void controlDirection() {
        switch (Game.direction) {
            case up:
                Snake.snake.get(0).y--;
                if (Snake.snake.get(0).y < 0) {
                    Game.gameOver = true;
                }
                break;
            case right:
                Snake.snake.get(0).x++;
                if (Snake.snake.get(0).x >= Game.width) {
                    Game.gameOver = true;
                }
                break;
            case down:
                Snake.snake.get(0).y++;
                if (Snake.snake.get(0).y >= Game.height) {
                    Game.gameOver = true;
                }
                break;
            case left:
                Snake.snake.get(0).x--;
                if (Snake.snake.get(0).x < 0) {
                    Game.gameOver = true;
                }
                break;
        }
    }
}

