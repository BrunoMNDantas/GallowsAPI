package pt.ipl.isel.gallows_game_bot.logic.service;

import org.junit.Test;
import pt.ipl.isel.gallows_game_bot.dataAccess.WordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.Sentence;
import pt.ipl.isel.gallows_game_bot.transversal.Pair;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GallowsEngineTest {

    private static final String TEN_WORDS_FILE_PATH = "/10words.txt";



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullWordsRepository() throws Exception {
        new GallowsEngine(
                null,
                () -> new LinkedList<>(),
                (letter) -> false,
                (letter) -> new LinkedList<>(),
                (word) -> false,
                (text) -> System.out.println(text)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullWordsLengthSupplier() throws Exception {
        new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                null,
                (letter) -> false,
                (letter) -> new LinkedList<>(),
                (word) -> false,
                (text) -> System.out.println(text)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullLetterPredicate() throws Exception {
        new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> new LinkedList<>(),
                null,
                (letter) -> new LinkedList<>(),
                (word) -> false,
                (text) -> System.out.println(text)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullLetterPositionsFunction() throws Exception {
        new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> new LinkedList<>(),
                (letter) -> false,
                null,
                (word) -> false,
                (text) -> System.out.println(text)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullCompleteWordsPredicate() throws Exception {
        new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> new LinkedList<>(),
                (letter) -> false,
                (letter) -> new LinkedList<>(),
                null,
                (text) -> System.out.println(text)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullLogger() throws Exception {
        new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> new LinkedList<>(),
                (letter) -> false,
                (letter) -> new LinkedList<>(),
                (word) -> false,
                null
        );
    }

    @Test
    public void constructor() throws Exception {
        new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> new LinkedList<>(),
                (letter) -> false,
                (letter) -> new LinkedList<>(),
                (word) -> false,
                (text) -> System.out.println(text)
        );
    }

    @Test
    public void runKnownWords() throws Exception {
        GallowsEngine engine = new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> {
                    Collection<Integer> lengths = new LinkedList<>();

                    lengths.add("at".length());
                    lengths.add("window".length());

                    return lengths;
                },
                (letter) -> "at window".contains(Character.toString(letter)),
                (letter) -> {
                    Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
                    Sentence sentence = new SentenceService().createSentence("at window");

                    for(int wordIdx=0; wordIdx<sentence.getWords().size(); ++wordIdx) {
                        for(int letterIdx = 0; letterIdx<sentence.getWordAt(wordIdx).length(); ++letterIdx){
                            if(sentence.getWordAt(wordIdx).getLetterAt(letterIdx).getCharacter() == letter)
                                positions.add(new Pair<>(wordIdx, letterIdx));
                        }
                    }

                    return positions;
                },
                (word) -> "at window".contains(word.toString()),
                System.out::println
        );

        assertEquals("at window", engine.run());
    }

    @Test
    public void runUnknownWords() throws Exception {
        GallowsEngine engine = new GallowsEngine(
                new WordsRepository(TEN_WORDS_FILE_PATH),
                () -> {
                    Collection<Integer> lengths = new LinkedList<>();

                    lengths.add("at".length());
                    lengths.add("cinema".length());

                    return lengths;
                },
                (letter) -> "at cinema".contains(Character.toString(letter)),
                (letter) -> {
                    Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
                    Sentence sentence = new SentenceService().createSentence("at cinema");

                    for(int wordIdx=0; wordIdx<sentence.getWords().size(); ++wordIdx) {
                        for(int letterIdx = 0; letterIdx<sentence.getWordAt(wordIdx).length(); ++letterIdx){
                            if(sentence.getWordAt(wordIdx).getLetterAt(letterIdx).getCharacter() == letter)
                                positions.add(new Pair<>(wordIdx, letterIdx));
                        }
                    }

                    return positions;

                },
                (word) -> "at cinema".contains(word.toString()),
                System.out::println
        );

        assertNull(engine.run());
    }

}