package pt.ipl.isel.gallows_game_bot.logic.domain;

import pt.ipl.isel.gallows_game_bot.transversal.Utils;

import java.util.List;

public class Dictionary {

    private List<Word> words;
    public List<Word> getWords() { return this.words; }
    public void setWords(List<Word> words) {
        if(words == null)
            throw new IllegalArgumentException("Words cannot be null!");

        this.words = words;
    }



    public Dictionary(List<Word> words) {
        if(words == null)
            throw new IllegalArgumentException("Words cannot be null!");

        this.words = words;
    }



    public int size(){
        return words.size();
    }


    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Dictionary))
            return false;

        if(o == this)
            return true;

        Dictionary other = (Dictionary)o;

        return Utils.equalsCollections(this.words, other.words);
    }

}
