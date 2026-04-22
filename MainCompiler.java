import java.util.Scanner;

public class MainCompiler {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter iteration statement and type END to finish:");

        String input = "";
        String line;

        // Keep taking input until END is entered
        while (true) {

            line = sc.nextLine();

            if (line.equals("END")) {
                break;
            }

            input = input + line;
        }

        analyze(input);

        sc.close();
    }

    public static void analyze(String input) {

        int count = 0;
        String current = "";

        System.out.println("\nTokens:");

        for (int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);

            // Ignore spaces
            if (ch == ' ')
                continue;

            if (Character.isLetterOrDigit(ch)) {
                current += ch;
            }
            else {

                if (!current.isEmpty()) {
                    printToken(current);
                    count++;
                    current = "";
                }

                printToken(String.valueOf(ch));
                count++;
            }
        }

        if (!current.isEmpty()) {
            printToken(current);
            count++;
        }

        System.out.println("\nTotal Tokens: " + count);
    }

    public static void printToken(String token) {

        if (token.equals("while") || token.equals("for") || token.equals("do")) {
            System.out.println(token + " -> KEYWORD");
        }
        else if (token.equals("(") || token.equals(")")) {
            System.out.println(token + " -> PARENTHESIS");
        }
        else if (token.equals("{") || token.equals("}")) {
            System.out.println(token + " -> BRACE");
        }
        else if (token.equals(";")) {
            System.out.println(token + " -> SEMICOLON");
        }
        else if (token.equals("<") || token.equals(">")) {
            System.out.println(token + " -> RELATIONAL OPERATOR");
        }
        else if (token.equals("=")) {
            System.out.println(token + " -> ASSIGNMENT OPERATOR");
        }
        else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            System.out.println(token + " -> ARITHMETIC OPERATOR");
        }
        else if (Character.isDigit(token.charAt(0))) {
            System.out.println(token + " -> NUMBER");
        }
        else {
            System.out.println(token + " -> IDENTIFIER");
        }
    }
}
