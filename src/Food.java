import java.util.Random;

public class Food {

    static boolean isFastFood = false;
    static boolean isSteroidFood = false;
    static boolean isNormalFood = true;

    public Food() {
    }

    public static void foodMenu() {
        Random rand = new Random();
        int foodMenu = rand.nextInt(3);

        if (foodMenu == 1) {
            isNormalFood = false;
            isFastFood = true;
            isSteroidFood = false;
        }
        else if (foodMenu == 2) {
            isNormalFood = false;
            isFastFood = false;
            isSteroidFood = true;
        }
        else {
            isNormalFood = true;
            isFastFood = false;
            isSteroidFood = false;
        }
    }
    public static void addFood() {
        Random rand = new Random();
        start:
        do {
            foodMenu();
            // Generate Random XY placement for Food
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
