package pt.ipl.isel.gallows_game_bot.logic.domain;

import java.util.Collection;

public class Gallows {

    private Dictionary dictionary;
    public Dictionary getDictionary() { return dictionary; }
    public void setDictionary(Dictionary dictionary) {
        if(dictionary == null)
            throw new IllegalArgumentException("Dictionary cannot be null!");

        this.dictionary = dictionary;
    }

    private Sentence sentence;
    public Sentence getSentence() { return sentence; }
    public void setSentence(Sentence sentence) {
        if(sentence == null)
            throw new IllegalArgumentException("Sentence cannot be null!");

        this.sentence = sentence;
    }

    private Collection<Character> included;
    public Collection<Character> getIncluded() { return included; }
    public void setIncluded(Collection<Character> included) {
        if(included == null)
            throw new IllegalArgumentException("Included cannot be null!");

        this.included = included;
    }

    private Collection<Character> excluded;
    public Collection<Character> getExcluded() { return excluded; }
    public void setExcluded(Collection<Character> excluded) {
        if(excluded == null)
            throw new IllegalArgumentException("Excluded cannot be null!");

        this.excluded = excluded;
    }



    public Gallows(Dictionary dictionary, Sentence sentence, Collection<Character> included, Collection<Character> excluded) {
        if(dictionary == null)
            throw new IllegalArgumentException("Dictionary cannot be null!");

        if(sentence == null)
            throw new IllegalArgumentException("Sentence cannot be null!");


        if(included == null)
            throw new IllegalArgumentException("Included cannot be null!");


        if(excluded == null)
            throw new IllegalArgumentException("Excluded cannot be null!");

        this.dictionary = dictionary;
        this.sentence = sentence;
        this.included = included;
        this.excluded = excluded;
    }

}
