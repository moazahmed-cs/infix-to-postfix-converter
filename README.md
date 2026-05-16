# 🔢 Infix to Postfix Converter

A Java implementation of the classic stack-based algorithm that converts mathematical expressions from infix notation to postfix (Reverse Polish Notation).

## 📌 About

Mathematical expressions are normally written in **infix notation** (e.g. `3 + 4 * 2`), where operators sit between operands. Computers, however, process expressions more efficiently in **postfix notation** (e.g. `3 4 2 * +`), where operators follow their operands — eliminating the need for parentheses and precedence rules at evaluation time.

This project implements the **Shunting Yard Algorithm** using a stack data structure to perform this conversion correctly.

## 🎯 Features

- Handles all basic operators: `+`, `-`, `*`, `/`, `^`
- Correctly applies **operator precedence** (`*` and `/` before `+` and `-`)
- Handles **parentheses** for grouping expressions
- Supports multi-digit numbers

## 🛠️ Built With

- **Java** — Core language
- **Stack** — Core data structure used for conversion
- **Data Structures & Algorithms** — Shunting Yard Algorithm

## 💡 Example

```
Input  (Infix):   3 + 4 * 2
Output (Postfix): 3 4 2 * +

Input  (Infix):   ( 3 + 4 ) * 2
Output (Postfix): 3 4 + 2 *
```

## 🚀 How to Run

```bash
# Clone the repository
git clone https://github.com/moaz-ahmed/infix-to-postfix-converter.git

# Navigate to the project directory
cd infix-to-postfix-converter

# Compile
javac InfixToPostfix.java

# Run
java InfixToPostfix
```

## 📚 What I Learned

- Practical application of the Stack data structure
- How compilers and calculators parse and evaluate expressions internally
- Operator precedence and associativity rules
- Implementing classic CS algorithms from scratch in Java

---

> **Course:** Data Structures — Alexandria University
