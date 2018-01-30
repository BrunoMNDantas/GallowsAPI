package pt.ipl.isel.gallows_game_bot.logic.service;

import org.junit.Test;
import pt.ipl.isel.gallows_game_bot.dataAccess.WordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.Gallows;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;
import pt.ipl.isel.gallows_game_bot.transversal.Pair;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class GallowsServiceTest {

    private static final String TWENTY_WORDS_FILE_PATH = "/20words.txt";

    private static GallowsService createGallowsService() {
        return new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
    }

    private static Gallows createHelloWorldGallows() throws ServiceException {
        Collection<Integer> wordsLength = new LinkedList<>();
        wordsLength.add(5);
        wordsLength.add(5);

        return createGallowsService().createGallows(wordsLength);
    }



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullRepository() throws Exception {
        new GallowsService(null);
    }

    @Test
    public void constructorNormalRepository() throws Exception {
        new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGallowsNullWordsLength() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.createGallows(null);
    }

    @Test
    public void createGallowsEmptyWordsLength() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = gallowsService.createGallows(new LinkedList<>());

        assertEquals(0, gallows.getSentence().length());
    }

    @Test
    public void createGallowsNonEmptyWordsLength() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = createHelloWorldGallows();

        assertEquals(2, gallows.getSentence().getWords().size());
        assertFalse(new WordService().isDefined(gallows.getSentence().getWordAt(0)));
        assertFalse(new WordService().isDefined(gallows.getSentence().getWordAt(1)));
        assertEquals(5, gallows.getSentence().getWordAt(0).length());
        assertEquals(5, gallows.getSentence().getWordAt(1).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void includeNullGallows() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.include(null, 'a', new LinkedList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void includeNullLetter() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.include(createHelloWorldGallows(), null, new LinkedList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void includeNullPositions() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.include(createHelloWorldGallows(), 'a', null);
    }

    @Test
    public void includeContainingLetter() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = createHelloWorldGallows();
        Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
        positions.add(new Pair<>(0,2));
        positions.add(new Pair<>(0,3));
        positions.add(new Pair<>(1,3));
        gallowsService.include(gallows, 'l', positions);

        assertEquals(20, gallows.getDictionary().size());
        assertTrue(gallows.getIncluded().contains('l'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void excludeNullGallows() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.exclude(null, 'a');
    }

    @Test(expected = IllegalArgumentException.class)
    public void excludeNullLetter() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.exclude(createHelloWorldGallows(), null);
    }

    @Test
    public void excludeNonContainingLetter() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = createHelloWorldGallows();
        gallowsService.exclude(gallows, 'z');

        assertEquals(20, gallows.getDictionary().size());
        assertTrue(gallows.getExcluded().contains('z'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cleanDictionaryNullGallows() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.cleanDictionary(null);
    }

    @Test
    public void cleanDictionary() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = createHelloWorldGallows();

        gallowsService.cleanDictionary(gallows);

        assertEquals(7, gallows.getDictionary().size());

        for(Word word : gallows.getDictionary().getWords())
             assertTrue(word.length() == "Hello".length() || word.length() == "World".length());

        gallowsService.exclude(gallows, 'a');

        assertEquals(2, gallows.getDictionary().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void finishSentenceWordsWithOnlyOneDictionaryOptionNullGallows() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.finishSentenceWordsWithOnlyOneDictionaryOption(null);
    }

    @Test
    public void finishSentenceWordsWithOnlyOneDictionaryOption() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = createHelloWorldGallows();
        Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
        positions.add(new Pair<>(0,2));
        positions.add(new Pair<>(0,3));
        positions.add(new Pair<>(1,3));

        gallowsService.include(gallows, 'l', positions);

        Collection<Word> finishedWords = gallowsService.finishSentenceWordsWithOnlyOneDictionaryOption(gallows);

        assertEquals(1, finishedWords.size());
        assertTrue(finishedWords.contains(new WordService().createWord("hello")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMostProbableLetterNullGallows() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        gallowsService.getMostProbableLetter(null);
    }

    @Test
    public void getMostProbableLetter() throws Exception {
        GallowsService gallowsService = new GallowsService(new WordsRepository(TWENTY_WORDS_FILE_PATH));
        Gallows gallows = createHelloWorldGallows();
        Collection<Pair<Integer, Integer>> positions = new LinkedList<>();
        positions.add(new Pair<>(0,2));
        positions.add(new Pair<>(0,3));
        positions.add(new Pair<>(1,3));

        gallowsService.cleanDictionary(gallows);
        gallowsService.include(gallows, 'l', positions);
        gallowsService.finishSentenceWordsWithOnlyOneDictionaryOption(gallows);
        assertEquals(new Character('p'), gallowsService.getMostProbableLetter(gallows));
    }

}