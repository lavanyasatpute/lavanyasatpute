import java.util.*;
import java.io.*;

class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A f, B s) {
        first = f;
        second = s;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}

class RT_Design {
    ArrayList<Place> placeLst = new ArrayList<>();
    ArrayList<float[]> time = new ArrayList<>();
    ArrayList<float[]> distance = new ArrayList<>();

    private void fillQueue(PriorityQueue<Pair<Integer, Float>> que, int loc, float strt, float end) {
        float[] t = time.get(loc);
        float[] d = distance.get(loc);
        Place p;
        float tme;
        for (int i = 0; i < placeLst.size(); i++) {
            if (loc == i)
                continue;
            p = placeLst.get(i);
            if (!p.visited) {
                tme = strt + t[i];
                // System.out.println("\n\t" + p.name + " time: " + tme);
                // System.out.println("\tBTB: " + p.best_time_begin + " & BTE: " +
                // p.best_time_end);
                if (tme <= end && tme >= p.best_time_begin && tme <= p.best_time_end) {
                    Pair pr = new Pair(i, p.rating - d[i]);
                    que.add(pr);
                }
            }
        }
    }

    private void fillQueueDistance(PriorityQueue<Pair<Integer, Float>> que, int loc, float strt, float end) {
        float[] t = time.get(loc);
        float[] d = distance.get(loc);
        Place p;
        float tme;
        for (int i = 0; i < placeLst.size(); i++) {
            if (loc == i)
                continue;
            p = placeLst.get(i);
            if (!p.visited) {
                tme = strt + t[i];

                if (tme <= end) {
                    Pair pr = new Pair(i, p.rating - d[i]);
                    que.add(pr);
                }
            }
        }
    }

    public void designRouteDistance(int loc, float strt, float end) {
        Place p = placeLst.get(loc);
        System.out.println("\t" + p.name + "  ->  ");
        p.visited = true;
        strt += p.time;
        PriorityQueue<Pair<Integer, Float>> que = new PriorityQueue<>((a, b) -> {
            Float num1 = a.getSecond();
            Float num2 = b.getSecond();

            if (num1 == num2)
                return 0;
            if (num1 > num2)
                return 1;
            else
                return -1;
        });
        que.clear();
        Pair pr;
        int l;
        fillQueueDistance(que, loc, strt, end);
        if (!que.isEmpty()) {
            pr = que.poll();
            l = (int) pr.getFirst();
            strt += time.get(loc)[l];
            designRouteDistance(loc, strt, end);
            return;
        }
    }

    public void designRouteTime(int loc, float strt, float end) {
        Place p = placeLst.get(loc);
        System.out.println("\t" + p.name + "  ->  ");
        p.visited = true;
        strt += p.time;
        PriorityQueue<Pair<Integer, Float>> que = new PriorityQueue<>((a, b) -> {
            return Float.compare(b.getSecond(), a.getSecond());
        });
        que.clear();
        Pair pr;
        int l;
        fillQueue(que, loc, strt, end);
        if (!que.isEmpty()) {
            pr = que.poll();
            l = (int) pr.getFirst();
            strt += time.get(loc)[l];
            designRouteTime(l, strt, end);
            return;
        }
        fillQueueDistance(que, loc, strt, end);
        if (!que.isEmpty()) {
            pr = que.poll();
            l = (int) pr.getFirst();
            strt += time.get(loc)[l];
            designRouteDistance(loc, strt, end);
            return;
        }
    }

    private void setLocations() {
        for (Place p : placeLst)
            p.visited = false;
    }

    public void readObjectPlaces() {
        String places = "Places.obj";
        String tme = "time_to_travel.obj";
        String dist = "updated_distance.obj";
        try {
            ObjectInputStream p = new ObjectInputStream(new FileInputStream(places));
            ObjectInputStream t = new ObjectInputStream(new FileInputStream(tme));
            ObjectInputStream d = new ObjectInputStream(new FileInputStream(dist));

            while (true) {
                Place plce = (Place) p.readObject();

                placeLst.add(plce);
                float[] a = (float[]) t.readObject();
                float[] b = (float[]) d.readObject();

                time.add(a);
                distance.add(b);
            }
        } catch (IOException | ClassNotFoundException e) {
            if (!(e instanceof java.io.EOFException))
                e.printStackTrace();

        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        RT_Design ob = new RT_Design();
        ob.readObjectPlaces();
        String s = "ok";
        int loc;
        float strt;
        float end;

        while (true) {
            System.out.print("Enter start location: ");
            s = sc.next();
            if (s.equals("exit"))
                break;
            loc = Integer.parseInt(s);
            System.out.print("Enter start time: ");
            strt = sc.nextFloat();
            System.out.print("Enter end time: ");
            end = sc.nextFloat();
            ob.setLocations();
            ob.designRouteTime(loc, strt, end);
            System.out.println("\1");
            // ob.designRouteDistance(loc, strt, end);
            // clSystem.out.println("\1\n\n\n");
        }
        sc.close();
    }
}