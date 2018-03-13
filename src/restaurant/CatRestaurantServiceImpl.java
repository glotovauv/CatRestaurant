package restaurant;

import java.util.*;

public class CatRestaurantServiceImpl implements CatRestaurantService {
    private HashSet<String> cats = new HashSet<>();

    private ArrayList<String> menu = new ArrayList<>();

    private Queue<Order> orderQueue = new LinkedList<>();

    private HashSet<String> busyCats = new HashSet<>();

    private HashSet<Waiter> waiters = new HashSet<>();

    private HashMap<String, Integer> waiterProductivity = new HashMap<>();


    public CatRestaurantServiceImpl() {
        cats.addAll(Arrays.asList(
                "Васька", "Граф", "Мурзик", "Кузя", "Рыжик"
        ));
        menu.addAll(Arrays.asList(
                "Суп", "Салат", "Плов", "Запеканка", "Десерт", "Чай", "Кофе"
        ));

        waiters.add(new Waiter("Александр", this));
        waiters.add(new Waiter("Николай", this));
        waiters.add(new Waiter("Ольга", this));
        startWaitersWork();
    }

    public CatRestaurantServiceImpl(HashSet<String> cats, ArrayList<String> menu, HashSet<String> nameWaiters) {
        this.cats = cats;
        this.menu = menu;
        for(String nameWaiter: nameWaiters){
            this.waiters.add(new Waiter(nameWaiter, this));
        }
        startWaitersWork();
    }


    private void startWaitersWork(){
        for (Waiter waiter: waiters){
            waiterProductivity.put(waiter.getNameWaiter(), 0);

            Thread thread = new Thread(waiter);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public boolean isCatAvailable(String catName) {
        return busyCats.contains(catName);
    }

    private boolean isCatRestaurant(String catName) {
        return cats.contains(catName);
    }

    @Override
    public boolean isExistFreeCat(){
        return busyCats.size() < cats.size();
    }

    @Override
    public boolean inviteCat(String nameCat) {
        if (!isCatRestaurant(nameCat) || isCatAvailable(nameCat)) {
            return false;
        }
        busyCats.add(nameCat);
        return true;
    }

    @Override
    public boolean returnCat(String catName){
        if(isCatRestaurant(catName) && isCatAvailable(catName)){
            busyCats.remove(catName);
            return true;
        }
        return false;
    }

    @Override
    public HashSet<String> getFreeCats(){
        HashSet<String> freeCats = new HashSet<>();
        for(String cat: cats){
            if(!isCatAvailable(cat)){
                freeCats.add(cat);
            }
        }
        return freeCats;
    }

    @Override
    public boolean createOrder(int... menuIndex) {
        int menuSize = menu.size();
        for (int index : menuIndex) {
            if (index >= menuSize) {
                return false;
            }
        }
        orderQueue.add(new Order(menu, menuIndex));
        return true;
    }

    @Override
    public Order getUntouchedOrder() {
        Order order;
        synchronized (orderQueue) {
            order = orderQueue.poll();
        }
        return order;
    }

    @Override
    public List<String> getMenu() {
        return menu;
    }

    @Override
    public String getWaiterStatistic(){
        StringBuilder info = new StringBuilder();
        for (Map.Entry<String, Integer> entry: waiterProductivity.entrySet()){
            info.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return info.toString();
    }

    @Override
    public void addWaiterCount(String nameWaiter) {
        synchronized (waiterProductivity) {
            Integer count = waiterProductivity.get(nameWaiter);
            waiterProductivity.merge(nameWaiter, count == null ? 1 : count, (key, value) -> value + 1);
        }
    }


}