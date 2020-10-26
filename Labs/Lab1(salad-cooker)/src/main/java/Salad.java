import vegetables.Vegetable;

import java.util.*;

public class Salad implements Eatable {
    private String name = "";
    private List<Vegetable> vegetables;

    public Salad(String name) {
        this.name = name;
        this.vegetables = new ArrayList<>();
    }

    public Salad() {
        this.vegetables = new ArrayList<>();
    }

    public Salad(String name, List<Vegetable> vegetables) {
        this.name = name;
        this.vegetables = new ArrayList<>(vegetables.size());
        this.vegetables.addAll(vegetables);
    }

    public Salad(List<Vegetable> vegetables) {
        this.vegetables = new ArrayList<>(vegetables.size());
        this.vegetables.addAll(vegetables);
    }

    public String getName() {
        return name;
    }

    public List<Vegetable> getVegetables() {
        return vegetables;
    }

    public void renameSalad(String name) {
        this.name = name;
    }

    public void addVegetable(Vegetable vegetable) {
        vegetables.add(vegetable);
    }

    public int remove(String vegetableName) {
        Iterator<Vegetable> iterator = vegetables.iterator();
        int removedAmount = 0;
        while (iterator.hasNext()) {
            Vegetable vegetable = iterator.next();
            // Do something
            if (vegetable.getName().equals(vegetableName)) {
                ++removedAmount;
                iterator.remove();
            }
        }
        return removedAmount;
    }

    public int removeGivenAmount(String vegetableName, int amountToDelete) {
        Iterator<Vegetable> iterator = vegetables.iterator();
        int removedAmount = 0;
        while (iterator.hasNext()) {
            Vegetable vegetable = iterator.next();
            if (vegetable.getName().equals(vegetableName)) {
                if (removedAmount < amountToDelete) {
                    ++removedAmount;
                    iterator.remove();
                }
            }
        }
        return removedAmount;
    }

    public float calculateCalories() {
        float totalCalories = 0;
        for (Vegetable vegetable : vegetables) {
            totalCalories += vegetable.getCalories();
        }
        return totalCalories;
    }

    public void sortVegetables(Comparator<Vegetable> comparator) {
        vegetables.sort(comparator);
    }

    public List<Vegetable> findVegetablesInCaloriesInterval(float low, float high) {
        List<Vegetable> result = new ArrayList<>();
        for (Vegetable vegetable : vegetables) {
            if (vegetable.getCalories() >= low && vegetable.getCalories() <= high) {
                result.add(vegetable);
            }
        }
        return result;
    }

    public void eat() {
        vegetables.clear();
        System.out.println("Consumed " + calculateCalories() + "calories from salad " + name);
    }
}
