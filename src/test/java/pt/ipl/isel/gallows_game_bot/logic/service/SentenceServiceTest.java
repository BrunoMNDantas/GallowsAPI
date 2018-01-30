package pt.ipl.isel.gallows_game_bot.logic.service;

import org.junit.Test;
import pt.ipl.isel.gallows_game_bot.logic.domain.Sentence;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SentenceServiceTest {

    public static Sentence createEmptySentence() {
        return new Sentence(new LinkedList<>());
    }

    public static Sentence createDefinedSentence() {
        return new Sentence(WordServiceTest.createDefinedWords());
    }

    public static Sentence createUndefinedSentence() {
        return new Sentence(WordServiceTest.createUndefinedWords());
    }

    public static List<Sentence> createDefinedSentences() {
        List<Sentence> sentences = new LinkedList<>();

        sentences.add(createDefinedSentence());
        sentences.add(createDefinedSentence());

        return sentences;
    }

    public static List<Sentence> createUndefinedSentences() {
        List<Sentence> sentences = new LinkedList<>();

        sentences.add(createDefinedSentence());
        sentences.add(createUndefinedSentence());

        return sentences;
    }



    @Test(expected = IllegalArgumentException.class)
    public void isDefinedNullSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        sentenceService.isDefined(null);
    }

    @Test
    public void isDefinedEmptySentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = createEmptySentence();

        assertTrue(sentenceService.isDefined(sentence));
    }

    @Test
    public void isDefinedDefinedSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = createDefinedSentence();

        assertTrue(sentenceService.isDefined(sentence));
    }

    @Test
    public void isDefinedUndefinedSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = createUndefinedSentence();

        assertFalse(sentenceService.isDefined(sentence));
    }

    @Test(expected = IllegalArgumentException.class)
    public void areDefinedNullSentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        sentenceService.areDefined(null);
    }

    @Test
    public void areDefinedEmptySentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        assertTrue(sentenceService.areDefined(new LinkedList<>()));
    }

    @Test
    public void areDefinedDefinedSentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        List<Sentence> sentences = createDefinedSentences();

        assertTrue(sentenceService.areDefined(sentences));
    }

    @Test
    public void areDefinedUndefinedSentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        List<Sentence> sentences = createUndefinedSentences();

        assertFalse(sentenceService.areDefined(sentences));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSentenceNullLengths() throws Exception {
        SentenceService sentenceService = new SentenceService();
        sentenceService.createSentence((Collection<Integer>) null);
    }

    @Test
    public void createSentenceEmptyLengths() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = sentenceService.createSentence(new LinkedList<>());

        assertEquals(0, sentence.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSentenceNegativeLengths() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Collection<Integer> lengths = new LinkedList<>();
        lengths.add(2);
        lengths.add(-1);

        sentenceService.createSentence(lengths);
    }

    @Test
    public void createSentenceZeroLengths() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Collection<Integer> lengths = new LinkedList<>();
        lengths.add(0);
        lengths.add(0);

        Sentence sentence = sentenceService.createSentence(lengths);

        assertEquals(2, sentence.getWords().size());
        assertEquals(0, sentence.getWordAt(0).length());
        assertEquals(0, sentence.getWordAt(1).length());
    }

    @Test
    public void createSentencePositiveLengths() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Collection<Integer> lengths = new LinkedList<>();
        lengths.add(1);
        lengths.add(2);

        Sentence sentence = sentenceService.createSentence(lengths);

        assertEquals(2, sentence.getWords().size());
        assertEquals(1, sentence.getWordAt(0).length());
        assertEquals(2, sentence.getWordAt(1).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSentenceNullSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        sentenceService.createSentence((String) null);
    }

    @Test
    public void createSentenceEmptySentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = sentenceService.createSentence("");

        assertEquals(0, sentence.getWords().size());
        assertEquals(0, sentence.length());
    }

    @Test
    public void createSentenceNormalSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = sentenceService.createSentence("Hello World");

        assertEquals(2, sentence.getWords().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSentencesNullSentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        sentenceService.createSentences(null);
    }

    @Test
    public void createSentencesEmptySentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        List<Sentence> sentences = sentenceService.createSentences(new LinkedList<>());

        assertEquals(0, sentences.size());
    }

    @Test
    public void createSentencesNormalSentences() throws Exception {
        SentenceService sentenceService = new SentenceService();
        List<String> sentencesStr = new LinkedList<>();
        sentencesStr.add("ABC DEF");
        sentencesStr.add("");

        List<Sentence> sentences = sentenceService.createSentences(sentencesStr);

        assertEquals(2, sentences.size());
        assertEquals("ABC DEF", sentences.get(0).toString());
        assertEquals("", sentences.get(1).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRegexNullSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        sentenceService.createRegex(null);
    }

    @Test
    public void createRegexEmptySentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = createEmptySentence();
        String regex = sentenceService.createRegex(sentence);

        assertEquals("", regex);
    }

    @Test
    public void createRegexDefinedSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = sentenceService.createSentence("ABC DEF");
        String regex = sentenceService.createRegex(sentence);

        assertEquals("ABC DEF", regex);
    }

    @Test
    public void createRegexUndefinedSentence() throws Exception {
        SentenceService sentenceService = new SentenceService();
        Sentence sentence = sentenceService.createSentence("ABC DEF" + LetterService.UNDEFINED_CHAR);
        String regex = sentenceService.createRegex(sentence);

        assertEquals("ABC DEF.", regex);
    }

}