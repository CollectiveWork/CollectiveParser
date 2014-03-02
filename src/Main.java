import java.io.IOException;

/**
 * Created by Cristi on 2/25/14.
 */
public class Main {

    public static void main(String[] args) {
        Reader.readFile("resources//java//code.txt", null);
        CharacterSet.readCharacterSet("resources//java//charset.txt");
        Tokens.readTokens();

        System.out.println(Tokens.keywords);

        System.out.println(""+ Tokens.checkIdentifier("ceva"));
        System.out.println(""+ Tokens.checkIdentifier("$ceva"));
        System.out.println(""+ Tokens.checkIdentifier("234ceva"));
        System.out.println(""+ Tokens.checkIdentifier("ce543va"));
        System.out.println(""+ Tokens.checkIdentifier("ce$v63a"));
        System.out.println(""+ Tokens.checkIdentifier("_ceva"));
        System.out.println(""+ Tokens.checkIdentifier("ce_va"));
        System.out.println(""+ Tokens.checkIdentifier("Ce va"));
    }
}
