import java.util.List;
import java.util.Map;

public class Output {
    public Map<Integer, List<StreetSchedule>> schedules;

    public Output(Map<Integer, List<StreetSchedule>> schedules) {
        this.schedules = schedules;
    }
}
