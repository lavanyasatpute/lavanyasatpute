import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Place implements Serializable {
    int id;
    String name;
    float rating;
    float best_time_begin;
    float best_time_end;
    float time;
    boolean visited;

    Place() {
        id = Integer.MAX_VALUE;
        name = "";
        rating = 5;
        best_time_begin = 6.0f;
        best_time_end = 6.0f;
        visited = false;
        time = 1.5f;
    }

    public void setVisited() {
        visited = true;
    }
}

public class Locations {
    String locations[] = {
            "Panhala Fort",
            "Dajipur Wildlife Sanctuary",
            "Shri Chatrapati Shahu Museum (New Palace)",
            "Gaganbawada",
            "Ramtirth Waterfall",
            "Sagareshwar Deer Sanctuary",
            "Kopeshwar Temple",
            "Narsinhwadi Datta Mandir",
            "Vishalgad",
            "Mahalaxmi Temple, Kolhapur",
            "Binkhambi Ganesh Temple",
            "Gangagiri Maharaj Math Temple",
            "Rankala Lake",
            "Jyotiba Temple",
            "Temlabai Mandir",
            "Siddhagiri Museum",
            "Chinmaya Ganadhish",
            "DYP City Mall",
            "Sajja Kothi",
            "Bhavani Mandap",
            "Radhanagari Dam",
            "Teen Darwaza",
            "Botanical Gardens in Kolhapur",
            "Khasbag Maidan",
    };

    public void printLocations() {
        int i = 0;
        for (String s : locations) {
            System.out.println(i++ + "\t: " + s);
        }
    }

    public void createAndWriteObject(String s) {
        try {
            String inputFile = "updated_best_time_to_visit.txt";
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(s));
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            int i = 0;
            Place p;
            String line;
            for (String str : locations) {
                line = br.readLine();
                String[] parts = line.split("\\s+");

                p = new Place();
                p.id = i;
                p.name = str;
                p.best_time_begin = Float.parseFloat(parts[0]);
                p.best_time_end = Float.parseFloat(parts[1]);

                os.writeObject(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Locations l = new Locations();
        // l.printLocations();
        l.createAndWriteObject("Places.obj");
    }
}