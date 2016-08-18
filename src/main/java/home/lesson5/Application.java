package home.lesson5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LL on 18.08.2016.
 */
public class Application {

    private static boolean readContent(String url) throws BusinessExceptions {
        try {
            URL resource = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
            while (reader.ready()) {
                System.out.println(reader.readLine());
            }
            reader.close();
        } catch (MalformedURLException e) {
            throw new BusinessExceptions();
        } catch (IOException e) {
            throw new BusinessExceptions("Can't get content of the page " + url + "\nTry an another url: ");
        } catch (Exception e) {
            throw new BusinessExceptions();
        }
        return true;
    }
    public static void main(String[] args) throws Exception{
        boolean success = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Type the URL: ");
        while (!success) {
            try {
                String url = reader.readLine();
                success = readContent(url);
            } catch (BusinessExceptions e) {
                System.out.print(e.getMessage());
            } catch (IOException e) {
                System.out.print("Something wrong with console. Try again: ");
            } catch (Exception e) {
                throw new Exception("Sorry, unhandled exception. We are working on it...", e);
            } finally {
                if (success) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
    }
}
