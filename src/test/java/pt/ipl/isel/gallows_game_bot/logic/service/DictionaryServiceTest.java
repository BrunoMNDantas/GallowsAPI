package pt.ipl.isel.gallows_game_bot.logic.service;

import org.junit.Test;
import pt.ipl.isel.gallows_game_bot.dataAccess.IWordsRepository;
import pt.ipl.isel.gallows_game_bot.dataAccess.RepositoryException;
import pt.ipl.isel.gallows_game_bot.dataAccess.WordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.Dictionary;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DictionaryServiceTest {

    private static final String TEN_WORDS_FILE_PATH = "/10words.txt";
    private static final String WORDS_EMPTY_FILE_PATH = "/wordsEmpty.txt";
    private static final String WORDS_TO_SANITIZE_FILE_PATH = "/wordsToSanitize.txt";



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullRepository() throws Exception {
        new DictionaryService(null);
    }

    @Test
    public void constructorNonNullRepository() throws Exception {
        new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
    }

    @Test
    public void getDictionaryTenWords() throws Exception {
        WordService wordService = new WordService();
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        assertEquals(10, dictionary.size());

        String[] words = new String[]{"i", "at", "put", "car", "home", "take", "window", "computer", "building", "television"};
        for(String word : words)
            assertTrue(dictionary.getWords().contains(wordService.createWord(word)));
    }

    @Test
    public void getDictionaryEmptyWords() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(WORDS_EMPTY_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        assertEquals(0, dictionary.size());
    }

    @Test(expected = ServiceException.class)
    public void getDictionaryRepositoryException() throws Exception {
        IWordsRepository repository = new IWordsRepository() {
            @Override
            public Collection<String> getAll() throws RepositoryException {
                throw new RepositoryException();
            }

            @Override
            public boolean insert(String word) throws RepositoryException {
                throw new RepositoryException();
            }

            @Override
            public boolean delete(String word) throws RepositoryException {
                throw new RepositoryException();
            }
        };
        DictionaryService dictionaryService = new DictionaryService(repository);

        dictionaryService.getDictionary();
    }

    @Test
    public void getDictionarySanitizeWords() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(WORDS_TO_SANITIZE_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        assertEquals(2, dictionary.size());
        assertEquals("at", dictionary.getWords().get(0).toString());
        assertEquals("car", dictionary.getWords().get(1).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsByLengthNullDictionary() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsByLength(null, 1,2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsByLengthNegativeMax() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsByLength(new Dictionary(new LinkedList<>()), 1,-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsByLengthNegativeMin() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsByLength(new Dictionary(new LinkedList<>()), -1,2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsByLengthMinGreaterThenMax() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsByLength(new Dictionary(new LinkedList<>()), 2,1);
    }

    @Test
    public void filterWordsByLengthEmptyResult() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        dictionaryService.filterWordsByLength(dictionary, 50,51);

        assertEquals(0, dictionary.size());
    }

    @Test
    public void filterWordsByLengthNonEmptyResult() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        dictionaryService.filterWordsByLength(dictionary, 2,2);

        assertEquals(1, dictionary.size());
    }

    @Test
    public void filterWordsByLengthNoRemovals() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        dictionaryService.filterWordsByLength(dictionary, 1,20);

        assertEquals(10, dictionary.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsByAcceptedLengthNullDictionary() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsByAcceptedLength(null, new LinkedList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsByAcceptedLengthNullAcceptedLength() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsByAcceptedLength(new Dictionary(new LinkedList<>()), null);
    }

    @Test
    public void filterWordsByAcceptedLengthEmptyAcceptedLength() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        dictionaryService.filterWordsByAcceptedLength(dictionary, new LinkedList<>());

        assertEquals(0, dictionary.size());
    }

    @Test
    public void filterWordsByAcceptedLengthEmptyResult() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        Collection<Integer> acceptedLengths = new LinkedList<>();
        acceptedLengths.add(50);
        dictionaryService.filterWordsByAcceptedLength(dictionary, acceptedLengths);

        assertEquals(0, dictionary.size());
    }

    @Test
    public void filterWordsByAcceptedLengthNonEmptyResult() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        Collection<Integer> acceptedLengths = new LinkedList<>();
        acceptedLengths.add(2);
        dictionaryService.filterWordsByAcceptedLength(dictionary, acceptedLengths);

        assertEquals(1, dictionary.size());
    }

    @Test
    public void filterWordsByAcceptedLengthNoRemovals() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();
        Collection<Integer> acceptedLengths = new LinkedList<>();
        acceptedLengths.add(1);
        acceptedLengths.add(2);
        acceptedLengths.add(3);
        acceptedLengths.add(4);
        acceptedLengths.add(6);
        acceptedLengths.add(8);
        acceptedLengths.add(10);
        dictionaryService.filterWordsByAcceptedLength(dictionary, acceptedLengths);

        assertEquals(10, dictionary.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsWithLetterNullDictionary() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsWithLetter(null, 'A');
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsWithLetterNullCharacter() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsWithLetter(dictionaryService.getDictionary(), null);
    }

    @Test
    public void filterWordsWithLetterExistentCharacter() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        dictionaryService.filterWordsWithLetter(dictionary, 'b');

        assertEquals(1, dictionary.size());
    }

    @Test
    public void filterWordsWithLetterNonExistentCharacter() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        dictionaryService.filterWordsWithLetter(dictionary, 'z');

        assertEquals(0, dictionary.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsWithoutLetterNullDictionary() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsWithoutLetter(null, 'A');
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWordsWithoutLetterNullCharacter() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        dictionaryService.filterWordsWithoutLetter(dictionaryService.getDictionary(), null);
    }

    @Test
    public void filterWordsByWithoutLetterExistentLetter() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        dictionaryService.filterWordsWithoutLetter(dictionary, 'z');

        assertEquals(10, dictionary.size());
    }

    @Test
    public void filterWordsWithoutLetterNonExistentLetter() throws Exception {
        DictionaryService dictionaryService = new DictionaryService(new WordsRepository(TEN_WORDS_FILE_PATH));
        Dictionary dictionary = dictionaryService.getDictionary();

        dictionaryService.filterWordsWithoutLetter(dictionary, 'b');

        assertEquals(9, dictionary.size());
    }

}