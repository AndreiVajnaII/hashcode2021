import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Simulator {
    private Input input;
    private IntersectionState[] intersections;
    private Map<String, Queue<CarState>> streetQueues;
    private Map<String, Street> streets;
    public int score;

    public Simulator(Input input) {
        this.input = input;
    }

    public int simulate(Output output) {
        this.intersections = new IntersectionState[input.nrOfIntersections];
        streetQueues = new HashMap<>();
        streets = new HashMap<>();
        for (Street street : input.streets) {
            streets.put(street.name, street);
            streetQueues.put(street.name, new LinkedList<>());
        }

        for (int i = 0; i < intersections.length; i++) {
            var schedule = output.schedules.get(i);
            if (schedule != null) {
                intersections[i] = new IntersectionState(0, schedule.get(0).duration);
            }
        }
        for (int i = 0; i < input.paths.length; i++) {
            streetQueues.get(input.paths[i][0]).add(new CarState(i, 0, 0));
        }

        score = 0;
        for (int t = 0; t < input.simuationTime; t++) {
            List<CarToAdd> carsToAdd = new ArrayList<>();

            for (var entry : streetQueues.entrySet()) {
                var queue = entry.getValue();
                if (queue.size() > 0) {
                    var street = entry.getKey();
                    var car = queue.peek();
                    var path = this.input.paths[car.carIndex];
                    car.remaining--;
                    if (car.remaining <= 0) {
                        if (car.streetIndex == path.length - 1) {
                            queue.remove();
                            score = this.input.points + input.simuationTime - t;
                        } else if (isGreen(street, output.schedules)) {
                            queue.remove();
                            car.streetIndex++;
                            var nextStreet = path[car.streetIndex];
                            car.remaining = streets.get(nextStreet).duration;
                            carsToAdd.add(new CarToAdd(car, streetQueues.get(nextStreet)));
                        }
                    }
                }
            }
            
            for (CarToAdd carToAdd : carsToAdd) {
                carToAdd.add();
            }

            for (int i = 0; i < this.intersections.length; i++) {
                if (output.schedules.containsKey(i)) {
                    if (intersections[i].remaining == 0) {
                        List<StreetSchedule> schedules = output.schedules.get(i);
                        var nextSchedule = findNextSchedule(schedules, intersections[i].streetIndex);
                        intersections[i].streetIndex = nextSchedule;
                        intersections[i].remaining = schedules.get(nextSchedule).duration;
                    } else {
                        intersections[i].remaining--;
                    }
                }
            }
        }
        return score;
    }

    private boolean isGreen(String street, Map<Integer, List<StreetSchedule>> schedules) {
        var intersectionIndex = streets.get(street).end;
        var schedule = schedules.get(intersectionIndex);
        if (schedule != null) {
            var streetSchedule = schedule.get(intersections[intersectionIndex].streetIndex);
            return streetSchedule.street.equals(street);
        } else {
            return false;
        }
    }

    private int findNextSchedule(List<StreetSchedule> schedules, int current) {
        return current == schedules.size() - 1 ? 0 : current + 1;
    }

    private class CarToAdd {
        private CarState car;
        private Queue<CarState> queue;
        
        public CarToAdd(CarState car, Queue<CarState> queue) {
            this.car = car;
            this.queue = queue;
        }

        public void add() {
            this.queue.add(this.car);
        }
    }
}
