package pt.ipl.isel.gallows_game_bot.logic.service;

import org.junit.Test;
import pt.ipl.isel.gallows_game_bot.logic.domain.Letter;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class WordServiceTest {

    private static final LetterService letterService = new LetterService();



    public static Word createDefinedWord() {
        return new Word(letterService.createLetters("ABC"));
    }

    public static Word createUndefinedWord() {
        return new Word(letterService.createLetters("ABC" + LetterService.UNDEFINED_CHAR));
    }

    public static List<Word> createDefinedWords(){
        List<Word> words = new LinkedList<>();

        words.add(createDefinedWord());
        words.add(createDefinedWord());

        return words;
    }

    public static List<Word> createUndefinedWords(){
        List<Word> words = new LinkedList<>();

        words.add(createDefinedWord());
        words.add(createUndefinedWord());

        return words;
    }



    @Test(expected = IllegalArgumentException.class)
    public void isDefinedNullWord() throws Exception {
        WordService wordService = new WordService();
        wordService.isDefined(null);
    }

    @Test
    public void isDefinedUndefinedWord() throws Exception {
        WordService wordService = new WordService();
        Word word = createUndefinedWord();

        assertFalse(wordService.isDefined(word));
    }

    @Test
    public void isDefinedDefinedWord() throws Exception {
        WordService wordService = new WordService();
        Word word = createDefinedWord();

        assertTrue(wordService.isDefined(word));
    }

    @Test
    public void isDefinedEmptyWord() throws Exception {
        WordService wordService = new WordService();
        Word word = new Word(new LinkedList<>());

        assertTrue(wordService.isDefined(word));
    }

    @Test(expected = IllegalArgumentException.class)
    public void areDefinedNullWords() throws Exception {
        WordService wordService = new WordService();
        wordService.areDefined(null);
    }

    @Test
    public void areDefinedEmptyWords() throws Exception {
        WordService wordService = new WordService();
        assertTrue(wordService.areDefined(new LinkedList<>()));
    }

    @Test
    public void areDefinedDefinedWords() throws Exception {
        WordService wordService = new WordService();
        List<Word> words = createDefinedWords();

        assertTrue(wordService.areDefined(words));
    }

    @Test
    public void areDefinedUndefinedWords() throws Exception {
        WordService wordService = new WordService();
        List<Word> words = createUndefinedWords();

        assertFalse(wordService.areDefined(words));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordNegativeLength() throws Exception {
        WordService wordService = new WordService();
        wordService.createWord(-1);
    }

    @Test
    public void createWordZeroLength() throws Exception {
        WordService wordService = new WordService();
        Word word = wordService.createWord(0);

        assertEquals(0, word.length());
    }

    @Test
    public void createWordPositiveLength() throws Exception {
        WordService wordService = new WordService();
        Word word = wordService.createWord(2);

        assertEquals(2, word.length());

        for(Letter letter : word.getLetters())
            assertEquals(LetterService.UNDEFINED_CHAR, letter.getCharacter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordNullWord() throws Exception {
        WordService wordService = new WordService();
        wordService.createWord(null);
    }

    @Test
    public void createWordEmptyWord() throws Exception {
        WordService wordService = new WordService();
        Word word = wordService.createWord("");

        assertEquals(0, word.length());
    }

    @Test
    public void createWordNormalWord() throws Exception {
        WordService wordService = new WordService();
        Word word = wordService.createWord("ABC");

        assertEquals("ABC", word.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordsNullWords() throws Exception {
        WordService wordService = new WordService();
        wordService.createWords(null);
    }

    @Test
    public void createWordsEmptyWords() throws Exception {
        WordService wordService = new WordService();
        List<Word> words = wordService.createWords(new LinkedList<>());

        assertEquals(0, words.size());
    }

    @Test
    public void createWordsNormalWords() throws Exception {
        WordService wordService = new WordService();
        List<String> wordsStr = new LinkedList<>();
        wordsStr.add("ABC");
        wordsStr.add("DEF");
        List<Word> words = wordService.createWords(wordsStr);

        assertEquals(2, words.size());
        assertEquals("ABC", words.get(0).toString());
        assertEquals("DEF", words.get(1).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRegexNullWord() throws Exception {
        WordService wordService = new WordService();
        wordService.createRegex(null);
    }

    @Test
    public void createRegexEmptyWord() throws Exception {
        WordService wordService = new WordService();
        Word word = new Word(new LinkedList<>());
        String regex = wordService.createRegex(word);

        assertEquals("", regex);
    }

    @Test
    public void createRegexDefinedWord() throws Exception {
        WordService wordService = new WordService();
        Word word = wordService.createWord("ABC");
        String regex = wordService.createRegex(word);

        assertEquals("^ABC", regex);
    }

    @Test
    public void createRegexUndefinedWord() throws Exception {
        WordService wordService = new WordService();
        Word word = wordService.createWord("ABC" + LetterService.UNDEFINED_CHAR);
        String regex = wordService.createRegex(word);

        assertEquals("^ABC.", regex);
    }

}