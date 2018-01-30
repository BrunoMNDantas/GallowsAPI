package pt.ipl.isel.gallows_game_bot.serviceInterface;

import pt.ipl.isel.gallows_game_bot.dataAccess.IWordsRepository;
import pt.ipl.isel.gallows_game_bot.dataAccess.WordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.Sentence;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;
import pt.ipl.isel.gallows_game_bot.logic.service.GallowsEngine;
import pt.ipl.isel.gallows_game_bot.logic.service.SentenceService;
import pt.ipl.isel.gallows_game_bot.transversal.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class App {

    private static final boolean AUTO = true;
    private static final String WORDS_FILE = "/words.txt";
    private static String SENTENCE;



    public static void main(String[] args) throws Exception {
        IWordsRepository wordsRepository = new WordsRepository(WORDS_FILE);
        Supplier<Collection<Integer>> wordsLengthSupplier = getWordsLengthSupplier();
        Predicate<Character> letterPredicate = getLetterPredicate();
        Function<Character, Collection<Pair<Integer, Integer>>> letterPositionsFunction = getLetterPositionsFunction();
        Predicate<Word> completeWordPredicate = getCompleteWordPredicate();
        Consumer<String> logger = System.out::println;

        GallowsEngine engine = new GallowsEngine(wordsRepository, wordsLengthSupplier, letterPredicate,
                                                letterPositionsFunction, completeWordPredicate,logger);

        String sentence = engine.run();

        if(sentence ==  null)
            System.out.println("Your sentence has words that I don't have on my dictionary!");
        else
            System.out.println("Sentence is: '" + sentence + "'");
    }



    public static Supplier<Collection<Integer>> getWordsLengthSupplier() {
        return () -> {
            try {
                SENTENCE = ask("Insert sentence");

                Collection<Integer> lengths = new LinkedList<>();
                for(String word : SENTENCE.split(" "))
                    lengths.add(word.length());

                return lengths;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Predicate<Character> getLetterPredicate() {
        return (letter) -> {
            if(AUTO) {
                boolean answer = SENTENCE.contains(Character.toString(letter));
                System.out.println(">" + (answer ? "+" : "-") + letter);
                return answer;
            } else {
                try {
                    return askIfContainsLetter(letter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static Function<Character,Collection<Pair<Integer,Integer>>> getLetterPositionsFunction() {
       return (letter) -> {
            try {
                if(AUTO)
                    return getLetterPositions(letter);
                else
                    return askForLetterPositions();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
       };
    }

    public static Predicate<Word> getCompleteWordPredicate() {
        return (word) -> {
            try {
                if(AUTO)
                    return SENTENCE.contains(word.toString());
                else
                    return askForFoundWord(word);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static String readLine() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private static String ask(String text) throws Exception {
        System.out.println(text);
        return ask();
    }

    private static String ask() throws Exception {
        System.out.print(">");
        return readLine();
    }

    private static boolean askIfContainsLetter(Character letter) throws Exception {
        String answer = ask("Does your sentence contains '" + letter + "'? [yes/no]");

        if(answer.equals("yes") || answer.equals("y"))
            return true;

        if(answer.equals("no") || answer.equals("n"))
            return false;

        System.out.println("Unknown answer. Please answer with [yes/no]");

        return askIfContainsLetter(letter);
    }

    private static Collection<Pair<Integer, Integer>> askForLetterPositions() throws Exception {
        System.out.println("Insert letter positions in format [(wordPosition) (letterPosition)]. To stop press enter.");

        Pair<Integer, Integer> position;
        Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
        while((position = getLetterPosition()) != null)
            positions.add(position);

        return positions;
    }

    private static Pair<Integer, Integer> getLetterPosition() throws Exception {
        String pos = ask();

        if(pos == null || pos.isEmpty())
            return null;

        String[] positions = pos.split(" ");

        if(positions.length != 2){
            System.out.println("Invalid format! Format [(wordPosition) (letterPosition)]. To stop press enter.");
            return getLetterPosition();
        }

        return new Pair<>(Integer.parseInt(positions[0])-1, Integer.parseInt(positions[1])-1);
    }

    private static boolean askForFoundWord(Word word) throws Exception {
        String answer = ask("Does your sentence contains word: '" + word.toString() + "'");

        if(answer.equals("yes") || answer.equals("y"))
            return true;

        if(answer.equals("no") || answer.equals("n"))
            return false;

        System.out.println("Unknown answer. Please answer with [yes/no]");

        return askForFoundWord(word);
    }

    private static Collection<Pair<Integer, Integer>> getLetterPositions(Character character) {
        Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
        Sentence sentence = new SentenceService().createSentence(SENTENCE);

        for(int wordIdx=0; wordIdx<sentence.getWords().size(); ++wordIdx) {
            for(int letterIdx = 0; letterIdx<sentence.getWordAt(wordIdx).length(); ++letterIdx){
                if(sentence.getWordAt(wordIdx).getLetterAt(letterIdx).getCharacter() == character)
                    positions.add(new Pair<>(wordIdx, letterIdx));
            }
        }

        return positions;
    }

}
