package pt.ipl.isel.gallows_game_bot.logic.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SentenceTest {

    public static Sentence createSentence(String sentence) {
        List<String> wordsStr = Arrays.asList(sentence.split(" "));
        List<Word> words = new LinkedList<>();

        for(String wordStr : wordsStr)
            words.add(WordTest.createWord(wordStr));

        return new Sentence(words);
    }



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullWords() throws Exception {
        new Sentence(null);
    }

    @Test
    public void constructorNonNullWords() throws Exception {
        new Sentence(new LinkedList<>());
    }

    @Test
    public void getWordsNonNullWords() throws Exception {
        List<Word> words = new LinkedList<>();
        words.add(new Word(new LinkedList<>()));
        words.add(new Word(new LinkedList<>()));

        Sentence sentence = new Sentence(words);

        assertSame(words, sentence.getWords());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWordsNullWords() throws Exception {
        Sentence sentence = new Sentence(new LinkedList<>());
        sentence.setWords(null);
    }

    @Test
    public void setWordsNonNullWords() throws Exception {
        List<Word> wordsA = new LinkedList<>();
        List<Word> wordsB = new LinkedList<>();

        Sentence sentence = new Sentence(wordsA);
        sentence.setWords(wordsB);

        assertSame(wordsB, sentence.getWords());
    }

    @Test(expected = IllegalArgumentException.class)
    public void matchesNullRegex() throws Exception {
        Sentence sentence = createSentence("Hello World");
        sentence.matches(null);
    }

    @Test
    public void matchesAnyRegex() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertTrue(sentence.matches("Hello Wor.."));
    }

    @Test
    public void matchesSpecificRegex() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertTrue(sentence.matches("Hello World"));
    }

    @Test
    public void matchesDifferentRegex() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertFalse(sentence.matches("Hello Friend"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsNullText() throws Exception {
        Sentence sentence = createSentence("Hello World");
        sentence.contains(null);
    }

    @Test
    public void containsSameText() throws Exception {
        String text = "Hello World";
        Sentence sentence = createSentence(text);

        assertTrue(sentence.contains(text));
    }

    @Test
    public void containsIncludedText() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertTrue(sentence.contains("llo W"));
    }

    @Test
    public void containsNotIncludedText() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertFalse(sentence.contains("llo Wa"));
    }

    @Test
    public void lengthEmptyWords() throws Exception {
        Sentence sentence = new Sentence(new LinkedList<>());
        assertSame(0, sentence.length());
    }

    @Test
    public void lengthNonEmptyWords() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertSame("Hello World".length(), sentence.length());

        List<Word> words = new LinkedList<>();
        words.add(new Word(new LinkedList<>()));
        words.add(new Word(new LinkedList<>()));
        sentence = new Sentence(words);

        assertEquals(1, sentence.length());
        assertEquals(" ", sentence.toString());
    }

    @Test
    public void getWordAtExistentPosition() throws Exception {
        Word word = WordTest.createWord("Hello");
        Sentence sentence = createSentence("Hello World");

        assertTrue(word.equals(sentence.getWordAt(0)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getWordAtNonExistentPosition() throws Exception {
        Sentence sentence = createSentence("Hello World");
        sentence.getWordAt(4);
    }

    @Test
    public void toStringEmptyWords() throws Exception {
        Sentence sentence = new Sentence(new LinkedList<>());
        assertTrue("".equals(sentence.toString()));
    }

    @Test
    public void toStringNonEmptyWords() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertTrue("Hello World".equals(sentence.toString()));
    }

    @Test
    public void equalsNullWord() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertFalse(sentence.equals(null));
    }

    @Test
    public void equalsDifferentWord() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertFalse(sentence.equals(createSentence("Hello")));
    }

    @Test
    public void equalsSameWord() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertTrue(sentence.equals(sentence));
    }

    @Test
    public void equalsEquivalentWord() throws Exception {
        Sentence sentence = createSentence("Hello World");
        assertTrue(sentence.equals(createSentence("Hello World")));
    }

}