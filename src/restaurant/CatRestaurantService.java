package restaurant;

import java.util.HashSet;
import java.util.List;

public interface CatRestaurantService {
    boolean isCatAvailable(String catName);

    boolean createOrder(int... menuIndex);

    Order getUntouchedOrder();

    List<String> getMenu();

    String getWaiterStatistic();

    HashSet<String> getFreeCats();

    boolean returnCat(String catName);

    boolean inviteCat(String nameCat);

    boolean isExistFreeCat();

    void addWaiterCount(String nameWaiter);
}
