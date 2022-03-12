import java.util.ArrayList;
import java.util.List;

public class Snake {
    public static List<Square> snake = new ArrayList<>();

    public Snake() {
    }

    public static void initializeSnake() {
        snake.add(new Square(Game.width / 2, Game.height / 2));
        snake.add(new Square(Game.width / 2, Game.height / 2));
        snake.add(new Square(Game.width / 2, Game.height / 2));
    }

    public static void clearSnake() {
        snake.clear();
    }
}
