import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * Update_Distance_Matrix
 */
public class Update_Distance_Matrix {

    public static void main(String[] args) {
        String inputFile = "distance_matrix.txt";
        String outputFile = "updated_distance.obj";
        String outputTimefile = "time_to_travel.obj";
        String val;

        int mn = 662, mx = 145916;
        int diff = mx - mn, dist;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
                ObjectOutputStream ots = new ObjectOutputStream(new FileOutputStream(outputTimefile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                float[] distances = new float[values.length];
                float[] time = new float[values.length];

                for (int i = 0; i < values.length; i++) {
                    val = values[i].trim();
                    if (!val.equals("None")) {
                        dist = Integer.parseInt(values[i].trim());
                        time[i] = ((float) dist / 1000) / 40.0f;
                        distances[i] = ((dist - mn) / (float) (diff)) + 1;
                    } else {
                        distances[i] = 10.0f;
                        time[i] = 1000;
                    }
                }

                System.out.println("  " + Arrays.toString(distances));
                oos.writeObject(distances);
                ots.writeObject(time);
            }

            System.out.println("Arrays written to file: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}