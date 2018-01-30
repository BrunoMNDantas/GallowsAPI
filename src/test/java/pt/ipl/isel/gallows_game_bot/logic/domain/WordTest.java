package pt.ipl.isel.gallows_game_bot.logic.domain;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class WordTest {

    public static Word createWord(String word) {
        List<Letter> letters = new LinkedList<>();

        for(int i=0; i<word.length(); ++i)
            letters.add(new Letter(word.charAt(i)));

        return new Word(letters);
    }



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullLetters() throws Exception {
        new Word(null);
    }

    @Test
    public void constructorNonNullLetters() throws Exception {
        new Word(new LinkedList<>());
    }

    @Test
    public void getLettersNonNullLetters() throws Exception {
        List<Letter> letters = new LinkedList<>();
        Word word = new Word(letters);

        assertSame(letters, word.getLetters());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLettersNullLetters() throws Exception {
        Word word = new Word(new LinkedList<>());
        word.setLetters(null);
    }

    @Test
    public void setLettersNonNullLetters() throws Exception {
        List<Letter> lettersA = new LinkedList<>();
        List<Letter> lettersB = new LinkedList<>();
        Word word = new Word(lettersA);

        word.setLetters(lettersB);
        assertSame(lettersB, word.getLetters());
    }

    @Test(expected = IllegalArgumentException.class)
    public void matchesNullRegex() throws Exception {
        Word word = createWord("pc");
        word.matches(null);
    }

    @Test
    public void matchesAnyCharacters() throws Exception {
        Word word = createWord("pc");
        assertTrue(word.matches(".."));
    }

    @Test
    public void matchesSpecificCharacters() throws Exception {
        Word word = createWord("pc");
        assertTrue(word.matches("pc"));
    }

    @Test
    public void matchesDifferentCharacters() throws Exception {
        Word word = createWord("pc");
        assertFalse(word.matches("pf"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsNullText() throws Exception {
        Word word = createWord("computer");
        word.contains(null);
    }

    @Test
    public void containsSameText() throws Exception {
        Word word = createWord("computer");
        assertTrue(word.contains("computer"));
    }

    @Test
    public void containsContainingText() throws Exception {
        Word word = createWord("computer");
        assertTrue(word.contains("ter"));
    }

    @Test
    public void containsDifferentText() throws Exception {
        Word word = createWord("computer");
        assertFalse(word.contains("pc"));
    }

    @Test
    public void lengthEmptyLetters() throws Exception {
        Word word = new Word(new LinkedList<>());
        assertSame(0, word.length());
    }

    @Test
    public void lengthNormalLetters() throws Exception {
        String text = "text";
        Word word = createWord(text);
        assertSame(text.length(), word.length());
    }

    @Test
    public void getLetterAtContainingPosition() throws Exception {
        Word word = createWord("pc");
        assertSame('p', word.getLetterAt(0).getCharacter());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLetterAtNonExistentPosition() throws Exception {
        Word word = createWord("pc");
        word.getLetterAt(5);
    }

    @Test
    public void toStringEmptyLetters() throws Exception {
        Word word = new Word(new LinkedList<>());
        assertEquals("", word.toString());
    }

    @Test
    public void toStringNormalLetters() throws Exception {
        Word word = createWord("computer");
        assertEquals("computer", word.toString());
    }

    @Test
    public void equalsNullWord() throws Exception {
        Word word = createWord("pc");
        assertFalse(word.equals(null));
    }

    @Test
    public void equalsSameWord() throws Exception {
        Word word = createWord("pc");
        assertTrue(word.equals(word));
    }

    @Test
    public void equalsDifferentWord() throws Exception {
        Word word = createWord("pc");
        assertFalse(word.equals(createWord("p")));
    }

    @Test
    public void equalsEquivalentWord() throws Exception {
        Word word = createWord("pc");
        assertTrue(word.equals(createWord("pc")));
    }

}