package readability;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Readability {
    private String text;
    public static String pattern = "(\\b|\\s)[A-Z][^\\s\\.]*(\\s[^\\s\\.]+){8,}\\s[^\\s\\.]+[!?\\.]" ;


    public double averageWordBySentence(){
        String[] sentences = text.replaceAll("[,]","").split("[\\.!?]");

        OptionalDouble average = Arrays.stream(sentences)
                .map(s -> s.trim().split("\\s+"))
                .mapToInt(s -> s.length).average();
        return average.getAsDouble();
    }

    public  void setData(String nameFile) throws IOException {
        text = Files.readString(Path.of("./" + nameFile));
    }

    private List<String>  sentences(){
        return Arrays.asList(text.split("[!\\.?]")) ;
    }
    private double countSentences(){
        return sentences().size();
    }
    private int   countWords(){
        int count = 0;
        for(String sentence : sentences()){
            count += sentence.trim()
                    .replaceAll(",", "")
                    .split("([^\\w]+)").length;
        }
        return count;
     }
     private int countLetters(){

        return (int) text.replaceAll("[\\n\\t\\s]","").length();
     }
    public String getText() {
        return text;
    }

    private double  getScore(){
        double score = 4.71 * countLetters() / countWords() + 0.5* countWords() / countSentences() - 21.43;
        return  score;
    }



    public void analyse(){
        System.out.println("Words: "+countWords());
        System.out.println("Sentences: "+countSentences());
        System.out.println("Characters: "+countLetters() );
        System.out.println("The score is: "+getScore());
        System.out.println("This text should be understood by "+comprehensibleBy()+" year olds.");

    }

    private String comprehensibleBy() {
        int score= (int) Math.ceil(getScore());
        if (score >= 14){
            return "24+";
        }
        switch (score){
            case 1:
                return "5-6";
            case 2:
                return "6-7";

            case 13:
                return "18-24";

            default:
                return (score+5)+"-"+(score+6);

        }
    }
}
