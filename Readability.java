package readability;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Readability {
    private String text;
    private List<String> sentences;
    private int  countWords, countSentences;
    private int countLetters;
    private List<String> words;
    private Map<String,Double> scores;
    public static String pattern = "(\\b|\\s)[A-Z][^\\s\\.]*(\\s[^\\s\\.]+){8,}\\s[^\\s\\.]+[!?\\.]" ;
    private long countSyllabe;
    private long countPolySyllabe;

    public Readability(){
        scores = new HashMap<>();
    }
    public double averageWordBySentence(){
        String[] sentences = text.replaceAll("[,]","").split("[\\.!?]");

        OptionalDouble average = Arrays.stream(sentences)
                .map(s -> s.trim().split("\\s+"))
                .mapToInt(s -> s.length).average();
        return average.getAsDouble();
    }

    public  void setData(String nameFile) throws IOException {
         String dir = "C:\\Users\\nique\\IdeaProjects\\Readability Score\\";
        text = Files.readString(Path.of("./" +  nameFile)).toLowerCase();
        getCountSentences();
        getCountWords();
        getCountLetters();
        getSyllabe();
        computeIndex();
    }

    private void getSentences(){
        sentences =  Arrays.asList(text.trim().split("[!\\.?]")) ;
    }
    private void getCountSentences(){
        getSentences();
        countSentences= sentences.size();
    }
    private void  getWords(){
         words = new ArrayList<>();
        for(String sentence : sentences){
             words.addAll(Arrays.asList(sentence.trim()
                    .replaceAll(",", "")
                    .split("([^\\w]+)")));
        }
    }
    private void   getCountWords(){
        int count = 0;
        getWords();
        countWords = words.size();
     }
     private void getCountLetters(){

       countLetters= text.replaceAll("[\\n\\t\\s]","").length();
     }

     private void getSyllabe(){

        for (String word : words){
         long countVowel =   Arrays.stream(word.split("[^aeiouy]"))
                    .filter(t -> !t.isEmpty())
                    .count();
         if(word.endsWith("e") && countVowel >1){
             countVowel--;
         }
          if (countVowel == 0){
             countVowel++;

         }
          countSyllabe += countVowel;
          countPolySyllabe += countVowel > 2 ? 1 : 0;
        }

     }
    public String getText() {
        return text;
    }
    private void computeIndex(){
        getAutomatedScore();
        getColemanLiauScore();
        getFleshKincaidScore();
        getSMOGScore();
    }
    private  void getAutomatedScore(){
        double score= 4.71 * countLetters / countWords + 0.5* countWords / countSentences - 21.43;
        scores.put("ARI",score);
    }

    private  void  getFleshKincaidScore(){
        double score=  0.39 * countWords / countSentences + 11.8 * countSyllabe / countWords - 15.59;
        scores.put("FK",score);
    }

    private void  getSMOGScore(){
        double score =  1.043 * Math.sqrt(countPolySyllabe * 30 / countSentences) + 3.1291;
        scores.put("SMOG",score);
    }
    private void getColemanLiauScore(){
        double score =  0.0588 * 100 * countLetters / countWords  - 0.296 * 100 * countSentences/countWords -15.8;
        scores.put("CL",score);
    }


    public void analyse(){
        System.out.println("Words: "+countWords);
        System.out.println("Sentences: "+countSentences);
        System.out.println("Characters: "+countLetters );
        System.out.println("Syllables: "+countSyllabe);
        System.out.println("PolySyllables: "+countPolySyllabe);

    }

    private String getLabel(String tag){
        switch(tag){
            case "ARI":
                return "Automated Readability Index";
            case "FK":
                return "Flesch–Kincaid readability tests";
            case "SMOG":
                return "Simple Measure of Gobbledygook";
            case "CL":
                return "Coleman–Liau index";
            default:
                return "";
        }
    }

    private String formatIndex(String tag){

        double score = scores.get(tag);

        return String.format("%s: %.2f (about %s year olds).",getLabel(tag),score,getAge(score));
    }

    private String getAge(double value) {
        int score= (int) Math.floor(value);
        if (score >= 14){
            return "24";
        }
        switch (score){
            case 1:
                return "6";
            case 2:
                return "7";

            case 13:
                return "24";

            default:
                return ""+(score+6);

        }
    }

    public void printIndex(String selection) {
        if (selection.equals("all")){
            List.of("ARI","FK","SMOG","CL").stream()
                    .forEach(tag -> System.out.println(formatIndex(tag)) );
            double average = List.of("ARI","FK","SMOG","CL").stream()
                    .mapToDouble(tag -> Integer.parseInt(getAge(scores.get(tag))))
                    .average().getAsDouble();
            System.out.println();
            System.out.printf("This text should be understood in average by %.2f year olds.\n",average);

        }
        else {
            System.out.println(formatIndex(selection));
        }
    }
}
