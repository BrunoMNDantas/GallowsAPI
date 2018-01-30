package pt.ipl.isel.gallows_game_bot.dataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class WordsRepositoryTest {

    private static final String WORDS_FILE_PATH = "/words.txt";
    private static final String WORDS_EMPTY_FILE_PATH = "/wordsEmpty.txt";



    private static IWordsRepository getWordsRepository() {
        return new WordsRepository(WORDS_FILE_PATH);
    }



    @Before
    public void before() throws Exception {
        clean();
    }

    @After
    public void after() throws Exception {
        clean();
    }

    private void clean() throws  Exception {
        IWordsRepository repository = getWordsRepository();

        Collection<String> words = repository.getAll();
        for(String word : words)
            repository.delete(word);

        repository.insert("at");
        repository.insert("put");
        repository.insert("take");
    }



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullWordFilePath() throws Exception {
        new WordsRepository(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorEmptyWordFilePath() throws Exception {
        new WordsRepository("");
    }

    @Test
    public void constructorValidWordFilePath() throws Exception {
        new WordsRepository(WORDS_FILE_PATH);
    }


    @Test(expected = RepositoryException.class)
    public void getAllWithNonExistentFile() throws Exception {
        IWordsRepository repository = new WordsRepository("/nonExistentFile.txt");
        repository.getAll();
    }

    @Test
    public void getAllNormalFile() throws Exception {
        IWordsRepository repository = getWordsRepository();

        assertNotNull(repository.getAll());
        assertSame(3, repository.getAll().size());
        assertTrue(repository.getAll().contains("at"));
        assertTrue(repository.getAll().contains("put"));
        assertTrue(repository.getAll().contains("take"));
    }

    @Test
    public void getAllEmptyFile() throws Exception {
        IWordsRepository repository = new WordsRepository(WORDS_EMPTY_FILE_PATH);

        assertNotNull(repository.getAll());
        assertTrue(repository.getAll().size() == 0);
    }


    @Test(expected = RepositoryException.class)
    public void insertWithNonExistentFile() throws Exception {
        IWordsRepository repository = new WordsRepository("/nonExistentFile.txt");
        repository.insert("word");
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNullWord() throws Exception {
        IWordsRepository repository = getWordsRepository();
        repository.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertEmptyWord() throws Exception {
        IWordsRepository repository = getWordsRepository();
        repository.insert("");
    }

    @Test
    public void insertExistentWord() throws Exception {
        IWordsRepository repository = getWordsRepository();

        assertFalse(repository.insert("at"));
        assertSame(3, repository.getAll().size());
    }

    @Test
    public void insertNonExistentWord() throws Exception {
        IWordsRepository repository = getWordsRepository();

        assertTrue(repository.insert("no"));
        assertTrue(repository.getAll().contains("no"));
        assertSame(4, repository.getAll().size());
    }


    @Test(expected = RepositoryException.class)
    public void deleteWithNonExistentFile() throws Exception {
        IWordsRepository repository = new WordsRepository("/nonExistentFile.txt");
        repository.delete("at");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNullWord() throws Exception {
        IWordsRepository repository = getWordsRepository();
        repository.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteEmptyWord() throws Exception {
        IWordsRepository repository = getWordsRepository();
        repository.delete("");
    }

    @Test
    public void deleteExistentWord() throws Exception {
        IWordsRepository repository = getWordsRepository();

        assertTrue(repository.delete("at"));
        assertFalse(repository.getAll().contains("at"));
        assertSame(2, repository.getAll().size());
    }

    @Test
    public void deleteNonExistentWord() throws Exception {
        IWordsRepository repository = getWordsRepository();

        assertFalse(repository.delete("no"));
        assertFalse(repository.getAll().contains("no"));
        assertSame(3, repository.getAll().size());
    }

}