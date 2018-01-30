package pt.ipl.isel.gallows_game_bot.logic.service;

import pt.ipl.isel.gallows_game_bot.logic.domain.Letter;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;

import java.util.LinkedList;
import java.util.List;

public class WordService {

    private LetterService letterService = new LetterService();



    public boolean isDefined(Word word) {
        if(word == null)
            throw new IllegalArgumentException("Word cannot be null!");

        return letterService.areDefined(word.getLetters());
    }

    public boolean areDefined(List<Word> words) {
        if(words == null)
            throw new IllegalArgumentException("Words cannot be null!");

        for(Word word : words)
            if(!isDefined(word))
                return false;

        return true;
    }

    public Word createWord(int length){
        if(length < 0)
            throw new IllegalArgumentException("Length must be greater then 0!");

        List<Letter> letters = new LinkedList<>();

        while(length-->0)
            letters.add(letterService.createLetter());

        return new Word(letters);
    }

    public Word createWord(String word) {
        if(word == null)
            throw new IllegalArgumentException("Word cannot be null!");

        List<Letter> letters = letterService.createLetters(word);
        return new Word(letters);
    }

    public List<Word> createWords(List<String> words) {
        if(words == null)
            throw new IllegalArgumentException("Words cannot be null!");

        List<Word> w = new LinkedList<>();

        for(String word : words)
            w.add(createWord(word));

        return w;
    }

    public String createRegex(Word word) {
        if(word == null)
            throw new IllegalArgumentException("Word cannot be null!");

        StringBuilder sb = new StringBuilder();

        for(Letter letter : word.getLetters())
            sb.append(letterService.createRegex(letter));

        return sb.toString();
    }

}
