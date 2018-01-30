package pt.ipl.isel.gallows_game_bot.logic.domain;

import pt.ipl.isel.gallows_game_bot.transversal.Utils;

import java.util.List;

public class Word {

    private List<Letter> letters;
    public List<Letter> getLetters() { return this.letters; }
    public void setLetters(List<Letter> letters) {
        if(letters == null)
            throw new IllegalArgumentException("Letters cannot be null!");

        this.letters = letters;
    }



    public Word(List<Letter> letters) {
        if(letters == null)
            throw new IllegalArgumentException("Letters cannot be null!");

        this.letters = letters;
    }



    public boolean matches(String regex) {
        if(regex == null)
            throw new IllegalArgumentException("Regex cannot be null!");

        return toString().matches(regex);
    }

    public boolean contains(String text) {
        if(text == null)
            throw new IllegalArgumentException("Letters text be null!");

        return toString().contains(text);
    }

    public int length(){
        return toString().length();
    }

    public Letter getLetterAt(int position){
        return letters.get(position);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Letter letter : letters)
            sb.append(letter.toString());

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Word))
            return false;

        if(o == this)
            return true;

        Word other = (Word) o;

        return Utils.equalsCollections(this.letters, other.letters);
    }

}
