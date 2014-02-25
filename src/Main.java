import java.io.IOException;

/**
 * Created by Cristi on 2/25/14.
 */
public class Main {

    public static void main(String[] args) {
        Reader.readFile("resources//code.txt", null);
        CharacterSet.readCharacterSet("resources//javaCharset.txt");
        Tokens.readTokens();

        System.out.println(Tokens.keywords);

    }
}
