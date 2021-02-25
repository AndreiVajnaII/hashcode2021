import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
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
            Solution solution = new Solution(input);
            solution.solve();
            
            Output output = new Output(solution.deliveries);
            writeFile(output, fileName, shouldPrintScore(args));
        }
    }

    public static Input readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("input/" + fileName + ".in"))) {
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
                String[] line = reader.readLine().split(regex)
            }
        }
    }

    public static void writeFile(Output output, String filename, boolean debug) throws IOException {
        NumberFormat nf = NumberFormat.getInstance();
        int score = 0;
        Files.createDirectories(Paths.get("output"));
        try (PrintWriter writer = new PrintWriter(new FileWriter("output/" + filename + ".out"))) {
            writer.println(output.deliveries.size());
            for (Delivery delivery : output.deliveries) {
                writer.print(delivery.pizzas.size());
                for (Integer pizza : delivery.pizzas) {
                    writer.print(" " + pizza);
                }
                score += delivery.score();
                if (debug) {
                    writer.print(" --> " + nf.format(delivery.score()));
                }
                writer.print("\n");
            }
            if (debug) {
                writer.println("--> " + nf.format(score));
            }
        }
    }

    private static boolean shouldPrintScore(String[] args) {
        return args.length > 0 && args[0].equals("--with-score");
    }
}
