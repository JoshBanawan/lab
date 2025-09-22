import java.util.Scanner;
import java.util.Random;

public class TabularCipherr {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine().toUpperCase().replaceAll("\\s+", "");

        System.out.print("Enter number of columns: ");
        int columns = sc.nextInt();

        int rows = (int) Math.ceil((double) plaintext.length() / columns);

        char[][] table = new char[rows][columns];
        int index = 0;

        
  for (int r = 0; r < rows; r++) {
    for (int c = 0; c < columns; c++) {
  if (index < plaintext.length()) {
       table[r][c] = plaintext.charAt(index++);
         } else {
                   
      table[r][c] = (char) ('A' + rand.nextInt(26));
         }
         }
        }
        System.out.println("\nTable:");
        for (int r = 0; r < rows; r++) {
            System.out.print("[");
            for (int c = 0; c < columns; c++) {
                System.out.print(table[r][c]);
                if (c < columns - 1) System.out.print(", ");
            }
            System.out.println("]");
        }

        
        StringBuilder ciphertext = new StringBuilder();
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                ciphertext.append(table[r][c]);
            }
        }

        System.out.println("\nCiphertext: " + ciphertext.toString());

        sc.close();
    }
}
