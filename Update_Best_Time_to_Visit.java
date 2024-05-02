import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Update_Best_Time_to_Visit {
    private static String convertTo24Hour(String hourStr, String period) {
        int hour = Integer.parseInt(hourStr);
        if (period.equalsIgnoreCase("pm") && hour < 12) {
            hour += 12;
        } else if (period.equalsIgnoreCase("am") && hour == 12) {
            hour = 0;
        }
        return String.format("%02d", hour); // Format as 2-digit with leading zero if needed
    }

    public static void main(String[] args) {
        String inputFile = "Best Time to Visit.txt";
        String outputFile = "updated_best_time_to_visit.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+"); // Split by whitespace

                if (parts.length == 4) {
                    String startTime = convertTo24Hour(parts[0], parts[1]);
                    String endTime = convertTo24Hour(parts[2], parts[3]);

                    String updatedLine = startTime + " " + endTime;
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }

            System.out.println("Updated time data written to file: " + outputFile);
        }catch(IOException e){ 
            e.printStackTrace();
        }
    }
}


