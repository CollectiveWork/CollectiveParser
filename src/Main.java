import java.io.IOException;

/**
 * Created by Cristi on 2/25/14.
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println( Reader.readFile("resources//code.txt", null));
            CharacterSet.readCharacterSet("resources//javaCharset.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < CharacterSet.graphic.length; i++) {
            System.out.print(CharacterSet.graphic[i]);
        }


    }
}
