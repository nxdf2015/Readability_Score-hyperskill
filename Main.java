package readability;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * compute readability of a text
 * text is 'hard' to read if text has an average of more than ten words by sentences
 * otherwise text is 'easy' to read
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String nameFile = args[0];

        Readability readability = new Readability();
        readability.setData(nameFile);

       readability.analyse();




    }
}
