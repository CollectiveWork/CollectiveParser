import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cristi on 2/25/14.
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<HashMap<String, String>> tokens;
        Reader.readFile("resources//java//code.txt", null);
        CharacterSet.readCharacterSet("resources//java//charset.txt");
        Tokens.readTokens();

        System.out.println(Tokens.keywords);
        Reader red = new Reader();
        String file = Reader.readFile("resources//java//code.txt", null);
        tokens = red.parseFile(file);

        for(HashMap<String, String> token : tokens){
            if(token.get("error") != null)
                System.out.print("!!!Error:\"" + token.get("error") + "\" ");
            System.out.print("Token:\"" + token.get("token").replace("\r", "") + "\" Line:" + token.get("line") + " Pointer:" + token.get("pointer") + " Type:" + token.get("type") + " Lenght:" + token.get("length") + "\n");
            //System.out.println(token);
        }

// check identifiers
//        System.out.println(""+ Tokens.checkIdentifier("ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("$ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("234ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("ce543va"));
//        System.out.println(""+ Tokens.checkIdentifier("ce$v63a"));
//        System.out.println(""+ Tokens.checkIdentifier("_ceva"));
//        System.out.println(""+ Tokens.checkIdentifier("ce_va"));
//        System.out.println(""+ Tokens.checkIdentifier("Ce va"));

// check int
//        System.out.println(Tokens.Literals.checkIntegerLiteral("100"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("100a"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("a100"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("1 00"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("0100"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("0105"));
//        System.out.println(Tokens.Literals.checkIntegerLiteral("0x108"));

// check boolean
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1E2"));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1E-2"));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1.00"));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1."));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1.E2"));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral("1.23E-2"));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral(".001"));
//        System.out.println(Tokens.Literals.checkFloatingPointLiteral(".001E5"));

// check boolean
//        System.out.println(Tokens.Literals.checkBooleanLiteral("true"));
//        System.out.println(Tokens.Literals.checkBooleanLiteral("false"));
//        System.out.println(Tokens.Literals.checkBooleanLiteral("truasdasa"));

// check char
//        System.out.println(Tokens.Literals.checkCharLiteral("'\n'")); // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'\t'")); // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'\\'")); // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'\''")); // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'a'"));  // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'as'")); // wrong
//        System.out.println(Tokens.Literals.checkCharLiteral("'\\n'"));// wrong
//        System.out.println(Tokens.Literals.checkCharLiteral("' '"));  // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'  '")); // wrong
//        System.out.println(Tokens.Literals.checkCharLiteral("'X'"));  // correct
//        System.out.println(Tokens.Literals.checkCharLiteral("'a"));   // wrong
//        System.out.println(Tokens.Literals.checkCharLiteral("''"));   // wrong

// check string
//        System.out.println(Tokens.Literals.checkStringLiteral("\"dasdsa\""));
//        System.out.println(Tokens.Literals.checkStringLiteral("\"\\n\\nEnter your SSN:\""));
//        System.out.println(Tokens.Literals.checkStringLiteral("\"X\""));
//        System.out.println(Tokens.Literals.checkStringLiteral("\"CMU\""));
//        System.out.println(Tokens.Literals.checkStringLiteral("\"dasdsa"));
//        System.out.println(Tokens.Literals.checkStringLiteral("\"dasds'a\""));
//        System.out.println(Tokens.Literals.checkStringLiteral("\"\""));

// check null
//        System.out.println(Tokens.Literals.checkNullLiteral("null"));
//        System.out.println(Tokens.Literals.checkNullLiteral("dasdsa"));
    }
}
