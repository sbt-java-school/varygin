package home.lesson2;

import java.io.*;

/**
 * Created by LL on 07.08.2016.
 */
public class Truck {
    public static void main(String[] args) {
        processFile();
//        processConsole();
//        processSimple();
    }

    private static void processSimple() {
        int m = 10;
        int resCnt = 0;
        int resValue = 0;
        int[] values = {5, 7, 3, 9, 1};
        for (int value : values) {
            if (resValue + value <= m) {
                resValue += value;
                resCnt++;
            }
        }
        System.out.println(resCnt + " " + resValue + System.lineSeparator());
    }

    private static void processConsole() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        ) {
            process(reader, writer);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Count things less then value of n.");
        } catch (IOException e) {
            System.out.println("Some IO Exception.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processFile() {
        try (
                BufferedReader reader = new BufferedReader(new FileReader("files/home/lesson2/input.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("files/home/lesson2/output.txt"));
        ) {
            while (reader.ready()) {
                process(reader, writer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File input.txt not found.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Count things less then value of n.");
        } catch (IOException e) {
            System.out.println("Some IO Exception.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(BufferedReader reader, BufferedWriter writer) throws Exception {
        int n, m;
        int resCnt, resValue;

        String[] params = reader.readLine().split(" ");
        if (params.length < 2) {
            return;
        }

        n = Integer.parseInt(params[0]);
        m = Integer.parseInt(params[1]);

        resCnt = 0;
        resValue = 0;

        String[] valuesStr = reader.readLine().split(" ");
        if (valuesStr.length < n) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < n; i++) {
            if (resValue + Integer.parseInt(valuesStr[i]) <= m) {
                resValue += Integer.parseInt(valuesStr[i]);
                resCnt++;
            }
        }
        writer.write(resCnt + " " + resValue + System.lineSeparator());
    }
}
