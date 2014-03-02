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

//        System.out.println(""+ Tokens.checkIdentifier("ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("$ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("234ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("ce543va"));
//        System.out.println(""+ Tokens.checkIdentifier("ce$v63a"));
//        System.out.println(""+ Tokens.checkIdentifier("_ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("ce_va"));
//        System.out.println(""+ Tokens.checkIdentifier("Ce va"));


//        System.out.println(Tokens.Literals.checkIntegerLiteral("100"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("100a"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("a100"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("1 00"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("0100"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("0105"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("0x108"));

        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1E2"));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1E-2"));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1.00"));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1."));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1.E2"));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1.23E-2"));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral(".001"));
        System.out.println(Tokens.Literals.checkFloatingPointLiteral(".001E5"));

    }
}
