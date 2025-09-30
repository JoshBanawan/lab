import java.util.*;

public class TranpositionCipherTable {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) { // loop para maka-create og new encryption kung gusto
            // ==== PLAINTEXT INPUT ====
            String text;
            while (true) {
                System.out.print("Enter plaintext : ");
                text = scan.nextLine().toUpperCase();

                if (!text.matches("[A-Za-z]+")) {
                    System.out.println("Error: Plaintext must only contain letters (A–Z).");
                } else {
                    break; // valid input
                }
            }

            // ==== KEY INPUT ====
            String keyStr;
            while (true) {
                System.out.print("Enter numeric key (e.g. 312): ");
                keyStr = scan.nextLine();

                if (!keyStr.matches("[0-9]+")) {
                    System.out.println("Error: Key must only contain numbers.");
                } else {
                    break; // valid input
                }
            }

            int cols = keyStr.length();
            int[] key = new int[cols];
            for (int i = 0; i < cols; i++) {
                key[i] = Character.getNumericValue(keyStr.charAt(i));
            }

            int rows = (int) Math.ceil((double) text.length() / cols);

            char[][] table = new char[rows][cols];
            int k = 0;
            for (int row = 0; row < rows; row++) {
                for (int c = 0; c < cols; c++) {
                    if (k < text.length()) {
                        table[row][c] = text.charAt(k++);
                    } else {
                        table[row][c] = 'X';
                    }
                }
            }

            // === ENCRYPTION ===
            System.out.println("\n=== ENCRYPTION ===");
            System.out.println("PT = " + text);
            System.out.println("K  = " + Arrays.toString(key) + " -> " + cols + " columns");
            System.out.println("Rows = " + rows);

            printTableWithKey(table, rows, cols, key);

            StringBuilder cipher = new StringBuilder();
            for (int colIndex = 1; colIndex <= cols; colIndex++) {
                int actualCol = indexOf(key, colIndex);
                for (int row = 0; row < rows; row++) {
                    cipher.append(table[row][actualCol]);
                }
            }

            String ciphertext = cipher.toString();
            System.out.println("CT = " + ciphertext);

            // Ask user kung gusto ba mo-proceed sa decryption
            System.out.print("\nDo you want to proceed to decryption? (yes/no): ");
            String choice = scan.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                // === DECRYPTION ===
                System.out.println("\n=== DECRYPTION ===");
                System.out.println("CT = " + ciphertext);
                System.out.println("K  = " + Arrays.toString(key) + " -> " + cols + " columns");
                System.out.println("Rows = " + rows);

                int colLen = rows;
                String[] ctDivision = new String[cols];
                k = 0;
                for (int colIndex = 1; colIndex <= cols; colIndex++) {
                    ctDivision[colIndex - 1] = ciphertext.substring(k, k + colLen);
                    k += colLen;
                }
                System.out.println("CT Division: " + String.join(" | ", ctDivision));

                char[][] decTable = new char[rows][cols];
                k = 0;
                for (int colIndex = 1; colIndex <= cols; colIndex++) {
                    int actualCol = indexOf(key, colIndex);
                    for (int row = 0; row < rows; row++) {
                        decTable[row][actualCol] = ciphertext.charAt(k++);
                    }
                }

                printTableWithKey(decTable, rows, cols, key);

                StringBuilder plain = new StringBuilder();
                for (int row = 0; row < rows; row++) {
                    for (int c = 0; c < cols; c++) {
                        plain.append(decTable[row][c]);
                    }
                }

                System.out.println("PT = " + plain.toString().replaceAll("X+$", ""));
            }

            // After decryption or skip, ask kung gusto mag-try balik
            System.out.print("\nDo you want to create a new encryption? (yes/no): ");
            String again = scan.nextLine().trim().toLowerCase();
            if (!again.equals("yes")) {
                System.out.println("Exiting program...");
                break;
            }
        }
    }

    static int indexOf(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) return i;
        }
        return -1;
    }

    static void printTableWithKey(char[][] table, int rows, int cols, int[] key) {
        for (int c = 0; c < cols; c++) 
        System.out.print("+---");
            System.out.println("+");
        }
    }
}