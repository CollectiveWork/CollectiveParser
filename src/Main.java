import java.io.IOException;

/**
 * Created by Cristi on 2/25/14.
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println( Reader.readFile("resources//code.txt", null));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
