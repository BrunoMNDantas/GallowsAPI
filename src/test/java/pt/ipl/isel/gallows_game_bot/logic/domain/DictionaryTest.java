package pt.ipl.isel.gallows_game_bot.logic.domain;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class DictionaryTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullWords() throws Exception {
        new Dictionary(null);
    }

    @Test
    public void constructorNonNullWords() throws Exception {
        new Dictionary(new LinkedList<>());
    }

    @Test
    public void getWordsNonNullWords() throws Exception {
        List<Word> words = new LinkedList<>();
        Dictionary dictionary = new Dictionary(words);
        assertSame(words, dictionary.getWords());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWordNullWords() throws Exception {
        Dictionary dictionary = new Dictionary(new LinkedList<>());
        dictionary.setWords(null);
    }

    @Test
    public void setWordNonNullWords() throws Exception {
        List<Word> wordsA = new LinkedList<>();
        List<Word> wordsB = new LinkedList<>();

        Dictionary dictionary = new Dictionary(wordsA);
        dictionary.setWords(wordsB);

        assertSame(wordsB, dictionary.getWords());
    }

    @Test
    public void sizeEmptyWords() throws Exception {
        Dictionary dictionary = new Dictionary(new LinkedList<>());
        assertSame(0, dictionary.size());
    }

    @Test
    public void sizeNonEmptyWords() throws Exception {
        List<Word> words = new LinkedList<>();
        words.add(new Word(new LinkedList<>()));

        Dictionary dictionary = new Dictionary(words);

        assertSame(words.size(), dictionary.size());
    }

    @Test
    public void equalsNullDictionary() throws Exception {
        List<Word> words = new LinkedList<>();
        words.add(new Word(new LinkedList<>()));

        Dictionary dictionary = new Dictionary(words);

        assertFalse(dictionary.equals(null));
    }

    @Test
    public void equalsDifferentDictionary() throws Exception {
        List<Word> wordsA = new LinkedList<>();
        wordsA.add(new Word(new LinkedList<>()));

        Dictionary dictionaryA = new Dictionary(wordsA);

        List<Word> wordsB = new LinkedList<>();

        Dictionary dictionaryB = new Dictionary(wordsB);

        assertFalse(dictionaryA.equals(dictionaryB));
    }

    @Test
    public void equalsSameDictionary() throws Exception {
        List<Word> words = new LinkedList<>();
        words.add(new Word(new LinkedList<>()));

        Dictionary dictionary = new Dictionary(words);

        assertTrue(dictionary.equals(dictionary));
    }

    @Test
    public void equalsEquivalentDictionary() throws Exception {
        List<Word> wordsA = new LinkedList<>();
        wordsA.add(new Word(new LinkedList<>()));

        Dictionary dictionaryA = new Dictionary(wordsA);

        List<Word> wordsB = new LinkedList<>();
        wordsB.add(new Word(new LinkedList<>()));

        Dictionary dictionaryB = new Dictionary(wordsB);

        assertTrue(dictionaryA.equals(dictionaryB));
    }

}