import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Solution {
    public List<Delivery> deliveries = new ArrayList<>();
    
    private Input input;
    private int[] teams;

    public Solution(Input input) {
        this.input = input;
        this.teams = Arrays.copyOf(input.teams, input.teams.length);
    }

    public void solve() {
        for (int t = 4; t >= 2; t--) {
            while (teams[t] > 0 && input.pizzas.size() > t) {
                Delivery delivery = new Delivery();

                for (int i = 0; i < t; i++) {
                    int pizza = findBestPizza(delivery);
                    delivery.addPizza(input.pizzas.get(pizza));
                    input.pizzas.remove(pizza);
                }

                deliveries.add(delivery);
                teams[t]--;
            }
        }
    }

    private int findBestPizza(Delivery delivery) {
        int bestPizza = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < input.pizzas.size(); i++) {
            int common = commonIngredients(input.pizzas.get(i).ingredients, delivery.ingredients);
            if (common == 0) {
                return i;
            }
            if (common < min) {
                min = common;
                bestPizza = i;
            }
        }
        return bestPizza;
    }

    private int commonIngredients(List<String> list, Set<String> set) {
        int count = 0;
        for (String s : list) {
            if (set.contains(s)) {
                count++;
            }
        }
        return count;
    }
}
