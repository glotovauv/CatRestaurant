import restaurant.CatRestaurantServiceImpl;
import restaurant.CliHandler;

public class Main {
    public static void main(String[] args) {
        CliHandler cliHandler = new CliHandler(new CatRestaurantServiceImpl());
        cliHandler.startWork();
    }
}
