import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        String[] fileNames = {
            "a",
            "b",
            "c",
            "d",
            "e",
            "f"
        };
        for (String fileName: fileNames) {
            Input input = readFile(fileName);
            
            var solution = new GenerateSchedule(input);
            var output = solution.getStreetsSchedule();
            
            if (shouldPrintScore(args)) {
                var simulator = new Simulator(input);
                System.out.println(NumberFormat.getInstance().format(simulator.simulate(output)));
            }
            
            writeFile(output, fileName);
        }
    }

    public static Input readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("input/" + fileName + ".txt"))) {
            String[] firstLine = reader.readLine().split(" ");
            int simulationTime = Integer.parseInt(firstLine[0]);
            int nrIntersections = Integer.parseInt(firstLine[1]);
            int nrStreets = Integer.parseInt(firstLine[2]);
            int nrCars = Integer.parseInt(firstLine[3]);
            int points = Integer.parseInt(firstLine[4]);

            Street[] streets = new Street[nrStreets];
            for (int i = 0; i < nrStreets; i++) {
                String[] line = reader.readLine().split(" ");
                streets[i] = new Street(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    line[2],
                    Integer.parseInt(line[3]));
            }

            String[][] cars = new String[nrCars][];
            for (int i = 0; i < nrCars; i++) {
                String[] line = reader.readLine().split(" ");
                int nrRoutes = Integer.parseInt(line[0]);
                String[] car = new String[nrRoutes];
                for (int j = 0; j < nrRoutes; j++) {
                    car[j] = line[j+1];
                }
                cars[i] = car;
            }

            return new Input(simulationTime, nrIntersections, points, streets, cars);
        }
    }

    public static void writeFile(Output output, String filename) throws IOException {
        Files.createDirectories(Paths.get("output"));
        try (PrintWriter writer = new PrintWriter(new FileWriter("output/" + filename + ".out"))) {
            writer.println(output.schedules.size());
            for (int intersection : output.schedules.keySet()) {
                writer.println(intersection);
                List<StreetSchedule> schedules = output.schedules.get(intersection);
                writer.println(schedules.size());
                for (StreetSchedule s : schedules) {
                    writer.println(s.street + " " + s.duration);
                }
            }
        }
    }

    private static boolean shouldPrintScore(String[] args) {
        return args.length > 0 && args[0].equals("--with-score");
    }
}
