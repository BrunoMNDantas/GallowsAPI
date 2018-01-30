package pt.ipl.isel.gallows_game_bot.logic.service;

import pt.ipl.isel.gallows_game_bot.logic.domain.Letter;

import java.util.LinkedList;
import java.util.List;

public class LetterService {

    public static final Character UNDEFINED_CHAR = 0;



    public boolean isDefined(Letter letter) {
        if(letter == null)
            throw new IllegalArgumentException("Letter cannot be null!");

        return letter.getCharacter() != UNDEFINED_CHAR;
    }

    public boolean areDefined(List<Letter> letters) {
        if(letters == null)
            throw new IllegalArgumentException("Letters cannot be null!");

        for(Letter letter : letters)
            if(!isDefined(letter))
                return false;

        return true;
    }

    public Letter createLetter() {
        return new Letter(UNDEFINED_CHAR);
    }

    public Letter createLetter(Character character) {
        if(character == null)
            throw new IllegalArgumentException("Character cannot be null!");

        return new Letter(character);
    }

    public List<Letter> createLetters(String characters) {
        if(characters == null)
            throw new IllegalArgumentException("Characters cannot be null!");

        List<Letter> letters = new LinkedList<>();

        for(int i=0; i<characters.length(); ++i)
            letters.add(createLetter(characters.charAt(i)));

        return letters;
    }

    public String createRegex(Letter letter) {
        if(letter == null)
            throw new IllegalArgumentException("Letter cannot be null!");

        if(letter.getCharacter() == UNDEFINED_CHAR)
            return ".";

        return Character.toString(letter.getCharacter());
    }

}
