package pt.ipl.isel.gallows_game_bot.logic.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class LetterTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullCharacter() throws Exception {
        new Letter(null);
    }

    @Test
    public void constructorWithNonNullCharacter() throws Exception {
        new Letter('a');
    }

    @Test
    public void getCharacterNonNull() throws Exception {
        Letter letter = new Letter('a');
        assertSame('a', letter.getCharacter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCharacterNull() throws Exception {
        Letter letter = new Letter('a');

        letter.setCharacter(null);
        assertSame(null, letter.getCharacter());
    }

    @Test
    public void setCharacterNonNull() throws Exception {
        Letter letter = new Letter('b');

        letter.setCharacter('a');
        assertSame('a', letter.getCharacter());
    }

    @Test
    public void matchesAnyCharacter() throws Exception {
        Letter letter = new Letter('a');
        assertTrue(letter.matches("."));
    }

    @Test
    public void matchesSpecificCharacter() throws Exception {
        Letter letter = new Letter('a');
        assertTrue(letter.matches("a"));
    }

    @Test
    public void matchesDifferentCharacter() throws Exception {
        Letter letter = new Letter('a');
        assertFalse(letter.matches("b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void matchesNullCharacter() throws Exception {
        Letter letter = new Letter('a');
        assertFalse(letter.matches(null));
    }

    @Test
    public void toStringNonNullCharacter() throws Exception {
        Letter letter = new Letter('a');
        assertTrue(letter.toString().endsWith("a"));
    }

    @Test
    public void equalsWithSameLetter() throws Exception {
        Letter letter = new Letter('a');
        assertTrue(letter.equals(letter));
    }

    @Test
    public void equalsWithEquivalentLetter() throws Exception {
        Letter letter = new Letter('a');
        assertTrue(letter.equals(new Letter('a')));
    }

    @Test
    public void equalsWithNullLetter() throws Exception {
        Letter letter = new Letter('a');
        assertFalse(letter.equals(null));
    }

    @Test
    public void equalsWithDifferentLetter() throws Exception {
        Letter letter = new Letter('a');
        assertFalse(letter.equals(new Letter('b')));
    }

}