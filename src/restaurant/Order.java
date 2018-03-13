package restaurant;

import java.util.ArrayList;

public class Order {
    private ArrayList<String> orderList;

    public Order(ArrayList<String> menu, int... menuIndex) {
        orderList = new ArrayList<>(menuIndex.length);
        for (int index : menuIndex) {
            orderList.add(menu.get(index));
        }
    }

    public ArrayList<String> getOrderList() {
        return orderList;
    }
}
