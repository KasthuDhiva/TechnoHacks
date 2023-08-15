import java.util.Random;
import java.util.Scanner;

public class RandomPasswordGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the desired length of the password: ");
        int length = scanner.nextInt();

        System.out.print("Enter complexity level (1 for simple, 2 for moderate, 3 for strong): ");
        int complexity = scanner.nextInt();

        String password = generateRandomPassword(length, complexity);
        System.out.println("Generated password: " + password);

        scanner.close();
    }

    public static String generateRandomPassword(int length, int complexity) {
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        String validChars = lowercase;

        if (complexity >= 2) {
            validChars += uppercase;
            validChars += numbers;
        }

        if (complexity == 3) {
            validChars += specialChars;
        }

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(validChars.length());
            char randomChar = validChars.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}
