package pt.ipl.isel.gallows_game_bot.logic.service;

import org.junit.Test;
import pt.ipl.isel.gallows_game_bot.logic.domain.Letter;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class LetterServiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void isDefinedNullLetter() throws Exception {
        LetterService letterService = new LetterService();
        letterService.isDefined(null);
    }

    @Test
    public void isDefinedUndefinedLetter() throws Exception {
        LetterService letterService = new LetterService();
        assertFalse(letterService.isDefined(new Letter(LetterService.UNDEFINED_CHAR)));
    }

    @Test
    public void isDefinedDefinedLetter() throws Exception {
        LetterService letterService = new LetterService();
        assertTrue(letterService.isDefined(new Letter('A')));
    }

    @Test(expected = IllegalArgumentException.class)
    public void areDefinedNullLetters() throws Exception {
        LetterService letterService = new LetterService();
        letterService.areDefined(null);
    }

    @Test
    public void areDefinedEmptyLetters() throws Exception {
        LetterService letterService = new LetterService();
        assertTrue(letterService.areDefined(new LinkedList<>()));
    }

    @Test
    public void areDefinedDefinedLetters() throws Exception {
        LetterService letterService = new LetterService();

        List<Letter> letters = new LinkedList<>();
        letters.add(new Letter('A'));
        letters.add(new Letter('B'));

        assertTrue(letterService.areDefined(letters));
    }

    @Test
    public void areDefinedUnDefinedLetters() throws Exception {
        LetterService letterService = new LetterService();

        List<Letter> letters = new LinkedList<>();
        letters.add(new Letter('A'));
        letters.add(new Letter(LetterService.UNDEFINED_CHAR));

        assertFalse(letterService.areDefined(letters));
    }

    @Test
    public void createLetter() throws Exception {
        LetterService letterService = new LetterService();
        assertEquals(new Letter(LetterService.UNDEFINED_CHAR), letterService.createLetter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLetterNullCharacter() throws Exception {
        LetterService letterService = new LetterService();
        letterService.createLetter(null);
    }

    @Test
    public void createLetterNonNullCharacter() throws Exception {
        LetterService letterService = new LetterService();
        Letter letter = letterService.createLetter('A');

        assertEquals(new Character('A'), letter.getCharacter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLettersNullCharacters() throws Exception {
        LetterService letterService = new LetterService();
        letterService.createLetters(null);
    }

    @Test
    public void createLettersEmptyCharacters() throws Exception {
        LetterService letterService = new LetterService();
        List<Letter> letters = letterService.createLetters("");

        assertEquals(0, letters.size());
    }

    @Test
    public void createLettersNormalCharacters() throws Exception {
        LetterService letterService = new LetterService();
        List<Letter> letters = letterService.createLetters("ABC");

        assertEquals(3, letters.size());
    }

    @Test
    public void createLettersUndefinedCharacters() throws Exception {
        LetterService letterService = new LetterService();
        List<Letter> letters = letterService.createLetters("AB" + LetterService.UNDEFINED_CHAR);

        assertEquals(3, letters.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRegexNullLetter() throws Exception {
        LetterService letterService = new LetterService();
        letterService.createRegex(null);
    }

    @Test
    public void createRegexUndefinedLetter() throws Exception {
        LetterService letterService = new LetterService();
        String regex = letterService.createRegex(new Letter(LetterService.UNDEFINED_CHAR));

        assertEquals(".", regex);
    }

    @Test
    public void createRegexDefinedLetter() throws Exception {
        LetterService letterService = new LetterService();
        String regex = letterService.createRegex(new Letter('A'));

        assertEquals("A", regex);
    }

}