package pt.ipl.isel.gallows_game_bot.logic.domain;

import pt.ipl.isel.gallows_game_bot.transversal.Utils;

public class Letter {

    private Character character;
    public Character getCharacter() { return this.character; }
    public void setCharacter(Character character) {
        if(character == null)
            throw new IllegalArgumentException("Character cannot be null!");

        this.character = character;
    }



    public Letter(Character character) {
        if(character == null)
            throw new IllegalArgumentException("Character cannot be null!");

        this.character = character;
    }



    public boolean matches(String regex) {
        if(regex == null)
            throw new IllegalArgumentException("Regex cannot be null!");

        return toString().matches(regex);
    }

    @Override
    public String toString(){
        return Character.toString(character);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Letter))
            return false;

        if(o == this)
            return true;

        Letter other = (Letter) o;

        return Utils.equals(this.character, other.character);
    }

}
