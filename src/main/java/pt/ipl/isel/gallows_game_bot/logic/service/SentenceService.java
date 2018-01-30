package pt.ipl.isel.gallows_game_bot.logic.service;

import pt.ipl.isel.gallows_game_bot.logic.domain.Sentence;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SentenceService {

    private WordService wordService = new WordService();



    public boolean isDefined(Sentence sentence) {
        if(sentence == null)
            throw new IllegalArgumentException("Sentence cannot be null!");

        return wordService.areDefined(sentence.getWords());
    }

    public boolean areDefined(List<Sentence> sentences) {
        if(sentences == null)
            throw new IllegalArgumentException("Sentences cannot be null!");

        for(Sentence sentence : sentences)
            if(!isDefined(sentence))
                return false;

        return true;
    }

    public Sentence createSentence(Collection<Integer> wordsLength) {
        if(wordsLength == null)
            throw new IllegalArgumentException("WordsLength cannot be null!");

        List<Word> words = new LinkedList<>();

        for(Integer length : wordsLength)
            words.add(wordService.createWord(length));

        return new Sentence(words);
    }

    public Sentence createSentence(String sentence) {
        if(sentence == null)
            throw new IllegalArgumentException("Sentence cannot be null!");

        if(sentence.isEmpty())
            return new Sentence(new LinkedList<>());

        List<String> wordsStr = Arrays.asList(sentence.split(" "));
        List<Word> words = wordService.createWords(wordsStr);
        return new Sentence(words);
    }

    public List<Sentence> createSentences(List<String> sentences) {
        if(sentences == null)
            throw new IllegalArgumentException("Sentences cannot be null!");

        List<Sentence> s = new LinkedList<>();

        for(String sentence : sentences)
            s.add(createSentence(sentence));

        return s;
    }

    public String createRegex(Sentence sentence){
        if(sentence == null)
            throw new IllegalArgumentException("Sentence cannot be null!");

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<sentence.getWords().size(); ++i) {
            sb.append(wordService.createRegex(sentence.getWordAt(i)));

            if(i != sentence.getWords().size()-1)
                sb.append(" ");
        }

        return sb.toString();
    }

}
