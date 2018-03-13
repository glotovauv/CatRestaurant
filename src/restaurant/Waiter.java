package restaurant;

public class Waiter implements Runnable {
    private String nameWaiter;
    private CatRestaurantService restaurant;

    public Waiter(String nameWaiter, CatRestaurantService restaurant) {
        this.nameWaiter = nameWaiter;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (true) {
            Order order = restaurant.getUntouchedOrder();
            if (order == null) {
                waitOrder(3000);
            } else {
                waitOrder(1000);
                CliHandler.informedCompleteOrder(order);
                restaurant.addWaiterCount(nameWaiter);
            }
        }
    }

    private void waitOrder(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getNameWaiter() {
        return nameWaiter;
    }
}
