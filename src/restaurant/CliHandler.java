package restaurant;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Scanner;

public class CliHandler {
    private CatRestaurantService restaurant;

    public CliHandler(CatRestaurantService restaurant){
        this.restaurant = restaurant;
    }

    public void startWork() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showService();
            switch (scanner.nextInt()) {
                case 1:
                    chooseCat();
                    break;
                case 2:
                    returnCat();
                    break;
                case 3:
                    showMenu();
                    break;
                case 4:
                    createOrder();
                    break;
                case 5:
                    showWaiterStatistic();
                    break;
                default:
                    return;
            }
        }
    }

    private void showService() {
        System.out.println(
                "Доступны следующие действия:\n" +
                        "1 - Пригласить кота\n" +
                        "2 - Вернуть кота\n" +
                        "3 - Взять меню\n" +
                        "4 - Сделать заказ\n" +
                        "5 - Показать статистику работы официантов\n" +
                        "6 - Покинуть ресторан\n");
    }

    private void chooseCat() {
        if (restaurant.isExistFreeCat()) {
            showFreeCat();
            System.out.println("Укажите имя кота:");
            String catName = (new Scanner(System.in)).nextLine();
            if (restaurant.inviteCat(catName)) {
                System.out.println("Кот " + catName + " проведет с вами вечер.\n");
            } else System.out.println("К сожалению, кот " + catName + " не доступен.\n");
        } else {
            System.out.println("Все коты заняты!\n");
        }
    }

    private void showFreeCat() {
        System.out.println("Коты, свободные на данный момент:");
        HashSet<String> freeCats = restaurant.getFreeCats();
        for (String cat : freeCats) {
            System.out.println(cat);
        }
        System.out.println();
    }

    private void returnCat() {
        System.out.println("Введите имя возвращаемого кота:");
        String catName = (new Scanner(System.in)).nextLine();
        if (restaurant.returnCat(catName)) {
            System.out.println("Надеемся, Вам понравился кот " + catName + "!\n");
        } else {
            System.out.println("Кажется, тут ошибка! Это не наш кот.\n");
        }
    }

    private void showMenu() {
        System.out.println("Наше меню:");
        ListIterator<String> listIterator = restaurant.getMenu().listIterator();
        while (listIterator.hasNext()) {
            System.out.println((listIterator.nextIndex() + 1) + " - " + listIterator.next());
        }
        System.out.println();
    }

    private void createOrder() {
        System.out.println("Введите номера из меню через запятую:");
        String[] order = (new Scanner(System.in)).nextLine().split(",");
        int[] indexMenu = new int[order.length];
        for (int i = 0; i < order.length; i++) {
            try {
                indexMenu[i] = Integer.parseInt(order[i].trim()) - 1;
                if (indexMenu[i] < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Ошибка при вводе данных!\n");
                return;
            }
        }
        if (restaurant.createOrder(indexMenu)) {
            System.out.println("Ваш заказ принят, ожидайте!\n");
        } else {
            System.out.println("Заказ некорректен. Номера заказа должны быть строго из меню.\n");
        }
    }

    private void showWaiterStatistic() {
        System.out.println(restaurant.getWaiterStatistic());
        System.out.println();
    }

    public static void informedCompleteOrder(Order order) {
        System.out.println("Заказ " + order.getOrderList() + " готов!");
        System.out.println();
    }

}
