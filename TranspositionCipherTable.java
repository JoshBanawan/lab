import java.util.*;

public class TranspositionCipherTable {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        while (true) {
            // ==== PLAINTEXT INPUT ====
            String text;
            while (true) {
                System.out.print("Enter plaintext : ");
                text = scan.nextLine().toUpperCase();

                if (!text.matches("[A-Za-z]+")) {
                    System.out.println("Error: Letters only (A–Z).");
                } else {
                    break; // valid input
                }
            }

            // ==== KEY INPUT ====
            String keyStr;
            int[] key;
            while (true) {
                System.out.print("Enter numeric key (ex: 2314): ");
                keyStr = scan.nextLine();

                if (!keyStr.matches("[0-9]+")) {
                    System.out.println("Error: Numbers only (0-9).");
                    continue;
                }

                int cols = keyStr.length();
                key = new int[cols];
                boolean[] seen = new boolean[cols + 1];
                boolean valid = true;

                for (int i = 0; i < cols; i++) {
                    key[i] = Character.getNumericValue(keyStr.charAt(i));
                    if (key[i] < 1 || key[i] > cols || seen[key[i]]) {
                        valid = false;
                        break;
                    }
                    seen[key[i]] = true;
                }

                if (!valid) {
                    System.out.println("Error: Key must be a permutation of 1.." + keyStr.length());
                } else {
                    break; // valid key
                }
            }

            int cols = key.length;
            int rows = (int) Math.ceil((double) text.length() / cols);

            char[][] table = new char[rows][cols];
            int k = 0;
            int padCount = rows * cols - text.length();

            for (int row = 0; row < rows; row++) {
                for (int c = 0; c < cols; c++) {
                    if (k < text.length()) {
                        table[row][c] = text.charAt(k++);
                    } else {
                        // random padding (A–Z)
                        table[row][c] = (char) ('A' + rand.nextInt(26));
                    }
                }
            }

            // === ENCRYPTION ===
            System.out.println("\n ENCRYPTION ");
            System.out.println("Plaintext = " + text);
            System.out.println("Key  = " + Arrays.toString(key) + " -> " + cols + " columns");
            System.out.println("Rows = " + rows);

            printTableWithKey(table, rows, cols, key);

            // build mapping order -> actualCol
            int[] orderToIndex = new int[cols + 1];
            for (int i = 0; i < cols; i++) orderToIndex[key[i]] = i;

            StringBuilder cipher = new StringBuilder();
            for (int order = 1; order <= cols; order++) {
                int actualCol = orderToIndex[order];
                for (int row = 0; row < rows; row++) {
                    cipher.append(table[row][actualCol]);
                }
            }

            String ciphertext = cipher.toString();
            System.out.println("CT = " + ciphertext);

            // Ask user for decryption
            System.out.print("\nDo you want to proceed to decryption? (yes/no): ");
            String choice = scan.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                // === DECRYPTION ===
                System.out.println("\n DECRYPTION ");
                System.out.println("Cyphertext = " + ciphertext);
                System.out.println("Key  = " + Arrays.toString(key) + " -> " + cols + " columns");
                System.out.println("Rows = " + rows);

                int colLen = rows;
                String[] ctDivision = new String[cols];
                k = 0;
                for (int order = 1; order <= cols; order++) {
                    ctDivision[order - 1] = ciphertext.substring(k, k + colLen);
                    k += colLen;
                }
                System.out.println("Cypher Text Division: " + String.join(" | ", ctDivision));

                char[][] decTable = new char[rows][cols];
                k = 0;
                for (int order = 1; order <= cols; order++) {
                    int actualCol = orderToIndex[order];
                    for (int row = 0; row < rows; row++) {
                        decTable[row][actualCol] = ctDivision[order - 1].charAt(row);
                    }
                }

                printTableWithKey(decTable, rows, cols, key);

                StringBuilder plain = new StringBuilder();
                for (int row = 0; row < rows; row++) {
                    for (int c = 0; c < cols; c++) {
                        plain.append(decTable[row][c]);
                    }
                }
                // remove *exactly* the padding chars we added
                String decrypted = plain.substring(0, plain.length() - padCount);
                System.out.println("Plaintext = " + decrypted);
            }

            // Ask to repeat
            System.out.print("\nDo you want to create a new encryption? (yes/no): ");
            String again = scan.nextLine().trim().toLowerCase();
            if (!again.equals("yes")) {
                System.out.println("Exiting program...");
                break;
            }
        }
    }

    static void printTableWithKey(char[][] table, int rows, int cols, int[] key) {
        for (int c = 0; c < cols; c++) System.out.print("+---");
        System.out.println("+");
        for (int c = 0; c < cols; c++) System.out.print("| " + key[c] + " ");
        System.out.println("|");
        for (int c = 0; c < cols; c++) System.out.print("+---");
        System.out.println("+");

        for (int r = 0; r < rows; r++) {
            System.out.print((r + 1) + " ");
            for (int c = 0; c < cols; c++) {
                System.out.print("| " + table[r][c] + " ");
            }
            System.out.println("|");
            for (int c = 0; c < cols; c++) System.out.print("+---");
            System.out.println("+");
        }
    }
}
