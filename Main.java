package readability;

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
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String text = in.nextLine();
        //match sentences with more than 10 words
        String  textPattern = "(\\b|\\s)[A-Z][^\\s\\.]*(\\s[^\\s\\.]+){8,}\\s[^\\s\\.]+[!?\\.]" ;


        String[] sentences = text.replaceAll("[,]","").split("[\\.!?]");

        OptionalDouble average =Arrays.stream(sentences).map(s -> s.trim().split("\\s+")).mapToInt(s -> s.length).average();
        if (average.getAsDouble() > 10   ){
            System.out.println("HARD");
        }
        else {
            System.out.println("EASY");
        }


    }
}
