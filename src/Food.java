import java.util.Random;

public class Food {

    public Food() {
    }

    public static void addFood() {
        start:
        do {
            // Generate Random XY placement for Food
            Random rand = new Random();
            Game.foodX = rand.nextInt(Game.width);
            Game.foodY = rand.nextInt(Game.height);

            // Run through number of squares currently in snake (ArrayList of Squares)
            for (Square square : Snake.snake) {
                if (square.x == Game.foodX && square.y == Game.foodY) {
                    continue start;
                }
            }
            break;
        } while (true);
    }
}
