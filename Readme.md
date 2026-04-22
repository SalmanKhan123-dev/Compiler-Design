# Compiler Design - Mini Compiler Simulator 🚀

## 📖 Project Overview

This is an **educational Java implementation of a mini compiler** that demonstrates all major phases of compilation for basic **iteration statements** (while, for, do-while loops).

The project includes:

- **`MainCompiler.java`**: Simple lexical analyzer (tokenizer).
- **`MiniCompiler.java`**: **Full 6-phase compiler pipeline** for loop constructs.

Perfect for **Compiler Design courses** to understand lexical → code generation flow.

## ✨ Features

- **Lexical Analysis**: Token classification (keywords, operators, identifiers, numbers).
- **Syntax Validation**: Detailed error checking for loop structures.
- **Semantic Checks**: Basic symbol validation.
- **3-Address Intermediate Code**.
- **Optimization** (constant folding).
- **Assembly-like Code Generation**.

**Supported Constructs**:

```
while (condition) { body }
for (init; cond; incr) { body }
do { body } while (condition);
```

## 🛠️ Prerequisites

- **JDK 8+** (OpenJDK recommended).
- Java compiler (`javac`) & runtime (`java`).

## 📋 Compilation & Usage

### 1. Compile

```bash
cd \"c:/Users/SALMAN KHAN/OneDrive/Desktop/Compiler Design\"
javac *.java
```

### 2. Run MiniCompiler (Full Pipeline)

```bash
java MiniCompiler
```

**Sample Input**:

```
while (i < 5) { i = i + 1; }
END
```

**Expected Output**:

```
--- LEXICAL ANALYSIS ---
while -> KEYWORD
( -> PARENTHESIS
i -> IDENTIFIER
< -> RELATIONAL OPERATOR
5 -> NUMBER
) -> PARENTHESIS
{ -> BRACE
i -> IDENTIFIER
= -> ASSIGNMENT OPERATOR
i -> IDENTIFIER
+ -> ARITHMETIC OPERATOR
1 -> NUMBER
; -> SEMICOLON
} -> BRACE
Total Tokens: 14

--- SYNTAX ANALYSIS ---
Syntax is VALID (while loop)

--- SEMANTIC ANALYSIS ---
Semantic check passed

--- INTERMEDIATE CODE (3-ADDRESS) ---
t1 = i + 1
i = t1

--- CODE OPTIMIZATION ---
i = 2  (Optimized)  [if constants]

--- FINAL CODE GENERATION (ASSEMBLY) ---
L1:
MOV R1, i
CMP R1, 5
JGE L2
MOV R1, i
ADD R1, 1
MOV i, R1
JMP L1
L2:
```

### 3. Run MainCompiler (Lexer Only)

```bash
java MainCompiler
```

## 🔄 Compiler Phases Explained

### Phase 1: Lexical Analysis

Breaks input into tokens:

```java
// Keywords: while, for, do
// Identifiers: i, x, count
// Numbers: 5, 10
// Operators: +, -, *, /, =, <, >
// Symbols: (), {}, ;
```

### Phase 2: Syntax Analysis

Validates structures:

- Missing `{` → \"Syntax ERROR: Missing '{' ...\"
- No loop keyword → Error.

### Phase 3: Semantic Analysis

Ensures valid symbols.

### Phase 4: Intermediate Code

```
t1 = a + b
x = t1
```

### Phase 5: Optimization

```
2 + 3 → 5  (constant folding)
```

### Phase 6: Code Generation

Assembly for loops:

```
L1: MOV R1, i
    CMP R1, 5
    JGE L2
    ; loop body
    JMP L1
L2:
```

## 📁 Project Structure

```
Compiler Design/
├── MainCompiler.java     # Simple lexer
├── MiniCompiler.java     # Full compiler
├── input.txt             # Sample test input
├── *.class               # Compiled
└── README.md             # This file
```

## 🧪 Sample Input File (input.txt)

```
for (i = 0; i < 10; i = i + 1) {
  x = x + 1;
}
```

## 🔮 Future Improvements

- Parse expressions fully.
- Symbol table.
- More optimizations.
- Real assembly output.
- GUI interface.

## 📂 Repository

[GitHub Repo](https://github.com/SalmanKhan123-dev/Compiler-Design)

## 🙌 Contributing

1. Fork repo.
2. Add features (e.g., if/else support).
3. PR to `main`.

**Built with ❤️ for Compiler Design learning!**
