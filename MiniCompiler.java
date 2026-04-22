import java.util.*;

public class MiniCompiler {

    static List<String> tokens = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter code (type END to finish):");

        String input = "", line;

        while (true) {
            line = sc.nextLine();
            if (line.equals("END"))
                break;
            input += line;
        }

        // ===== PHASE 1 =====
        lexicalAnalysis(input);

        // ===== PHASE 2 =====
        syntaxAnalysis();

        // ===== PHASE 3 =====
        semanticAnalysis();

        // ===== PHASE 4 =====
        intermediateCode();

        // ===== PHASE 5 =====
        codeOptimization();

        // ===== PHASE 6 =====
        codeGeneration();

        sc.close();
    }

    // ===============================
// PHASE 1: LEXICAL ANALYSIS (UPDATED)
// ===============================
public static void lexicalAnalysis(String input) {

    String current = "";

    System.out.println("\n--- LEXICAL ANALYSIS ---");

    for (int i = 0; i < input.length(); i++) {

        char ch = input.charAt(i);

        // Ignore spaces
        if (ch == ' ')
            continue;

        // Build identifiers/numbers
        if (Character.isLetterOrDigit(ch)) {
            current += ch;
        } else {

            // Print previous token
            if (!current.isEmpty()) {
                printTokenType(current);
                tokens.add(current);
                current = "";
            }

            // Print symbol
            String symbol = String.valueOf(ch);
            printTokenType(symbol);
            tokens.add(symbol);
        }
    }

    // Print last token
    if (!current.isEmpty()) {
        printTokenType(current);
        tokens.add(current);
    }

    System.out.println("Total Tokens: " + tokens.size());
}

// ===============================
// TOKEN TYPE IDENTIFICATION
// ===============================
public static void printTokenType(String token) {

    if (token.equals("while") || token.equals("for") || token.equals("do")) {
        System.out.println(token + " -> KEYWORD");
    }
    else if (token.matches("[0-9]+")) {
        System.out.println(token + " -> NUMBER");
    }
    else if (token.matches("[a-zA-Z]+")) {
        System.out.println(token + " -> IDENTIFIER");
    }
    else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
        System.out.println(token + " -> ARITHMETIC OPERATOR");
    }
    else if (token.equals("<") || token.equals(">")) {
        System.out.println(token + " -> RELATIONAL OPERATOR");
    }
    else if (token.equals("=")) {
        System.out.println(token + " -> ASSIGNMENT OPERATOR");
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
    else {
        System.out.println(token + " -> UNKNOWN");
    }
}

    // ===============================
// PHASE 2: SYNTAX ANALYSIS (DETAILED ERRORS)
// ===============================
public static void syntaxAnalysis() {

    System.out.println("\n--- SYNTAX ANALYSIS ---");

    // 1. Check loop keyword
    if (!(tokens.contains("while") || tokens.contains("for") || tokens.contains("do"))) {
        System.out.println("Syntax ERROR: Missing iteration keyword (while/for/do)");
        return;
    }

    // 2. WHILE LOOP CHECKS
    if (tokens.contains("while") && !tokens.contains("do")) {

        if (!tokens.contains("(")) {
            System.out.println("Syntax ERROR: Missing '(' after while");
            return;
        }

        if (!tokens.contains(")")) {
            System.out.println("Syntax ERROR: Missing ')' after condition");
            return;
        }

        if (!tokens.contains("{")) {
            System.out.println("Syntax ERROR: Missing '{' to start loop body");
            return;
        }

        if (!tokens.contains("}")) {
            System.out.println("Syntax ERROR: Missing '}' to end loop body");
            return;
        }

        System.out.println("Syntax is VALID (while loop)");
        return;
    }

    // 3. DO-WHILE LOOP CHECKS
    if (tokens.contains("do")) {

        if (!tokens.contains("{")) {
            System.out.println("Syntax ERROR: Missing '{' after do");
            return;
        }

        if (!tokens.contains("}")) {
            System.out.println("Syntax ERROR: Missing '}' before while");
            return;
        }

        if (!tokens.contains("while")) {
            System.out.println("Syntax ERROR: Missing 'while' in do-while");
            return;
        }

        if (!tokens.contains("(")) {
            System.out.println("Syntax ERROR: Missing '(' after while");
            return;
        }

        if (!tokens.contains(")")) {
            System.out.println("Syntax ERROR: Missing ')' in condition");
            return;
        }

        if (!tokens.contains(";")) {
            System.out.println("Syntax ERROR: Missing ';' in do-while");
            return;
        }

        System.out.println("Syntax is VALID (do-while loop)");
        return;
    }

    // 4. FOR LOOP CHECKS
    if (tokens.contains("for")) {

        if (!tokens.contains("(")) {
            System.out.println("Syntax ERROR: Missing '(' after for");
            return;
        }

        if (!tokens.contains(")")) {
            System.out.println("Syntax ERROR: Missing ')' in for loop");
            return;
        }

        int semicolonCount = 0;
        for (String t : tokens) {
            if (t.equals(";")) semicolonCount++;
        }

        if (semicolonCount < 2) {
            System.out.println("Syntax ERROR: Missing ';' inside for loop");
            return;
        }

        if (!tokens.contains("{")) {
            System.out.println("Syntax ERROR: Missing '{' to start loop body");
            return;
        }

        if (!tokens.contains("}")) {
            System.out.println("Syntax ERROR: Missing '}' to end loop body");
            return;
        }

        System.out.println("Syntax is VALID (for loop)");
        return;
    }
}

    // ===============================
    // PHASE 3: SEMANTIC ANALYSIS
    // ===============================
    public static void semanticAnalysis() {

        System.out.println("\n--- SEMANTIC ANALYSIS ---");

        for (String token : tokens) {

            if (token.matches("[a-zA-Z]+") || token.matches("[0-9]+"))
                continue;

            else if ("+-*/=<>(){};".contains(token))
                continue;

            else {
                System.out.println("Semantic ERROR at: " + token);
                return;
            }
        }

        System.out.println("Semantic check passed");
    }

    // ===============================
    // PHASE 4: INTERMEDIATE CODE
    // ===============================
    public static void intermediateCode() {

        System.out.println("\n--- INTERMEDIATE CODE (3-ADDRESS) ---");

        int temp = 1;

        for (int i = 0; i < tokens.size(); i++) {

            if (i + 4 < tokens.size()) {

                if (tokens.get(i + 1).equals("=") &&
                    tokens.get(i + 3).equals("+")) {

                    String t = "t" + temp++;

                    System.out.println(t + " = " + tokens.get(i + 2) + " + " + tokens.get(i + 4));
                    System.out.println(tokens.get(i) + " = " + t);
                }
            }
        }
    }

    // ===============================
    // PHASE 5: CODE OPTIMIZATION
    // ===============================
    public static void codeOptimization() {

        System.out.println("\n--- CODE OPTIMIZATION ---");

        // simple constant folding example
        for (int i = 0; i < tokens.size(); i++) {

            if (i + 4 < tokens.size()) {

                if (tokens.get(i + 1).equals("=") &&
                    tokens.get(i + 3).equals("+") &&
                    tokens.get(i + 2).matches("[0-9]+") &&
                    tokens.get(i + 4).matches("[0-9]+")) {

                    int result = Integer.parseInt(tokens.get(i + 2)) +
                                 Integer.parseInt(tokens.get(i + 4));

                    System.out.println(tokens.get(i) + " = " + result + "  (Optimized)");
                }
            }
        }
    }

    // ===============================
    // PHASE 6: CODE GENERATION
    // ===============================
    public static void codeGeneration() {

        System.out.println("\n--- FINAL CODE GENERATION (ASSEMBLY) ---");

        if (tokens.contains("while")) {

            String var = "", op = "", val = "";

            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("(")) {
                    var = tokens.get(i + 1);
                    op = tokens.get(i + 2);
                    val = tokens.get(i + 3);
                }
            }

            System.out.println("L1:");
            System.out.println("MOV R1, " + var);
            System.out.println("CMP R1, " + val);

            if (op.equals("<")) System.out.println("JGE L2");
            else if (op.equals(">")) System.out.println("JLE L2");

            for (int i = 0; i < tokens.size(); i++) {

                if (i + 4 < tokens.size()) {

                    if (tokens.get(i + 1).equals("=") &&
                        tokens.get(i + 3).equals("+")) {

                        System.out.println("MOV R1, " + tokens.get(i + 2));
                        System.out.println("ADD R1, " + tokens.get(i + 4));
                        System.out.println("MOV " + tokens.get(i) + ", R1");
                    }
                }
            }

            System.out.println("JMP L1");
            System.out.println("L2:");
        }

        else if (tokens.contains("for")) {

            String var = tokens.get(2);
            String start = tokens.get(4);
            String end = tokens.get(8);

            System.out.println("MOV " + var + ", " + start);

            System.out.println("L1:");
            System.out.println("MOV R1, " + var);
            System.out.println("CMP R1, " + end);
            System.out.println("JGE L2");

            System.out.println("MOV R1, x");
            System.out.println("ADD R1, 1");
            System.out.println("MOV x, R1");

            System.out.println("MOV R1, " + var);
            System.out.println("ADD R1, 1");
            System.out.println("MOV " + var + ", R1");

            System.out.println("JMP L1");
            System.out.println("L2:");
        }

        else if (tokens.contains("do")) {

            System.out.println("L1:");

            System.out.println("MOV R1, i");
            System.out.println("ADD R1, 1");
            System.out.println("MOV i, R1");

            System.out.println("MOV R1, i");
            System.out.println("CMP R1, 5");
            System.out.println("JL L1");
        }

        else {
            System.out.println("No valid loop found");
        }
    }
}