package restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private List<String> orderList;

    public Order(ArrayList<String> menu, int... menuIndex) {
        orderList = Arrays.stream(menuIndex).mapToObj(menu::get).collect(Collectors.toList());
    }

    public List<String> getOrderList() {
        return orderList;
    }
}
