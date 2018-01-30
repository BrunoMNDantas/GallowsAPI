package pt.ipl.isel.gallows_game_bot.logic.domain;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertSame;

public class GallowsTest {

    private static Dictionary createEmptyDictionary(){
        return new Dictionary(new LinkedList<>());
    }

    private static Sentence createEmptySentence(){
        return new Sentence(new LinkedList<>());
    }

    private static <T> List<T> createEmptyList(){
        return new LinkedList<>();
    }

    private static Gallows createEmptyGallows(){
        return new Gallows(createEmptyDictionary(), createEmptySentence(), createEmptyList(), createEmptyList());
    }



    @Test(expected = IllegalArgumentException.class)
    public void constructorNullDictionary() throws Exception {
        new Gallows(null, createEmptySentence(), createEmptyList(), createEmptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullSentence() throws Exception {
        new Gallows(createEmptyDictionary(), null, createEmptyList(), createEmptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullIncluded() throws Exception {
        new Gallows(createEmptyDictionary(), createEmptySentence(), null, createEmptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullExcluded() throws Exception {
        new Gallows(createEmptyDictionary(), createEmptySentence(), createEmptyList(), null);
    }

    @Test
    public void constructorNoNulls() throws Exception {
        new Gallows(createEmptyDictionary(), createEmptySentence(), createEmptyList(), createEmptyList());
    }

    @Test
    public void getDictionaryNonNullDictionary() throws Exception {
        Dictionary dictionary = createEmptyDictionary();
        Gallows gallows = new Gallows(dictionary, createEmptySentence(), createEmptyList(), createEmptyList());

        assertSame(dictionary, gallows.getDictionary());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDictionaryNullDictionary() throws Exception {
        Gallows gallows = createEmptyGallows();
        gallows.setDictionary(null);
    }

    @Test
    public void setDictionaryNonNullDictionary() throws Exception {
        Dictionary dictionary = createEmptyDictionary();
        Gallows gallows = createEmptyGallows();

        gallows.setDictionary(dictionary);

        assertSame(dictionary, gallows.getDictionary());
    }

    @Test
    public void getSentenceNonNullSentence() throws Exception {
        Sentence sentence = createEmptySentence();
        Gallows gallows = new Gallows(createEmptyDictionary(), sentence, createEmptyList(), createEmptyList());

        assertSame(sentence, gallows.getSentence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSentenceNullSentence() throws Exception {
        Gallows gallows = createEmptyGallows();
        gallows.setSentence(null);
    }

    @Test
    public void setSentenceNonNullSentence() throws Exception {
        Sentence sentence = createEmptySentence();
        Gallows gallows = createEmptyGallows();

        gallows.setSentence(sentence);

        assertSame(sentence, gallows.getSentence());
    }

    @Test
    public void getIncludedNonNullIncluded() throws Exception {
        List<Character> included = createEmptyList();
        Gallows gallows = new Gallows(createEmptyDictionary(), createEmptySentence(), included, createEmptyList());

        assertSame(included, gallows.getIncluded());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIncludedNullIncluded() throws Exception {
        Gallows gallows = createEmptyGallows();
        gallows.setIncluded(null);
    }

    @Test
    public void setIncludedNunNullIncluded() throws Exception {
        List<Character> included = createEmptyList();
        Gallows gallows = createEmptyGallows();

        gallows.setIncluded(included);

        assertSame(included, gallows.getIncluded());
    }

    @Test
    public void getExcludedNonNullExcluded() throws Exception {
        List<Character> excluded = createEmptyList();
        Gallows gallows = new Gallows(createEmptyDictionary(), createEmptySentence(), createEmptyList(), excluded);

        assertSame(excluded, gallows.getExcluded());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setExcludedNullExcluded() throws Exception {
        Gallows gallows = createEmptyGallows();
        gallows.setExcluded(null);
    }

    @Test
    public void setExcludedNonNullExcluded() throws Exception {
        List<Character> excluded = createEmptyList();
        Gallows gallows = createEmptyGallows();

        gallows.setExcluded(excluded);

        assertSame(excluded, gallows.getExcluded());
    }

}