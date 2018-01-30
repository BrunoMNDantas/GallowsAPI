package pt.ipl.isel.gallows_game_bot.logic.domain;

import pt.ipl.isel.gallows_game_bot.transversal.Utils;

import java.util.List;

public class Sentence {

    private List<Word> words;
    public List<Word> getWords() { return this.words; }
    public void setWords(List<Word> words){
        if(words == null)
            throw new IllegalArgumentException("Words cannot be null!");

        this.words = words;
    }



    public Sentence(List<Word> words) {
        if(words == null)
            throw new IllegalArgumentException("Words cannot be null!");

        this.words = words;
    }



    public boolean matches(String regex) {
        if(regex == null)
            throw new IllegalArgumentException("Regex cannot be null!");

        return toString().matches(regex);
    }

    public boolean contains(String text) {
        if(text == null)
            throw new IllegalArgumentException("Text cannot be null!");

        return toString().contains(text);
    }

    public int length(){
        return toString().length();
    }

    public Word getWordAt(int position) {
        return words.get(position);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<words.size(); ++i){
            sb.append(words.get(i));

            if(i != words.size()-1)
                sb.append(" ");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Sentence))
            return false;

        if(o == this)
            return true;

        Sentence other = (Sentence) o;

        return Utils.equalsCollections(this.words, other.words);
    }

}
