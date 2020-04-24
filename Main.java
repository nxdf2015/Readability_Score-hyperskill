package readability;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * compute readability of a text
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String nameFile = args[0];
        Scanner scanner = new Scanner(System.in);

        Readability readability = new Readability();
        readability.setData(nameFile);
        readability.analyse();
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String selection = scanner.nextLine();
        System.out.println();
        readability.printIndex(selection);
    }
}
