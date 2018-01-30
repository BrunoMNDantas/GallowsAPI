package pt.ipl.isel.gallows_game_bot.logic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ipl.isel.gallows_game_bot.dataAccess.IWordsRepository;
import pt.ipl.isel.gallows_game_bot.dataAccess.RepositoryException;
import pt.ipl.isel.gallows_game_bot.logic.domain.Dictionary;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DictionaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryService.class);



    private static Collection<String> sanitize(Collection<String> words) {
        Collection<String> finalWords = new LinkedList<>();

        String w;
        for(String word : words) {
            w = sanitize(word);

            if(w != null)
                finalWords.add(w);
        }

        return finalWords;
    }

    private static String sanitize(String word) {
        if(word == null || word.isEmpty())
            return null;

        word = word.trim();

        if(word.isEmpty())
            return null;

        word = word.toLowerCase();

        return word;
    }



    private WordService wordService = new WordService();
    private IWordsRepository repository;



    public DictionaryService(IWordsRepository repository) {
        if(repository == null)
            throw new IllegalArgumentException("Repository cannot be null!");

        this.repository = repository;
    }



    public Dictionary getDictionary() throws ServiceException {
        Collection<String> wordsStr;
        try{
            wordsStr = new LinkedList<>(repository.getAll());
            wordsStr = sanitize(wordsStr);
        } catch (RepositoryException e) {
            LOGGER.error("Error getting all words from repository!", e);
            throw new ServiceException("Error getting all words!", e);
        }

        List<Word> words = wordService.createWords(new LinkedList<>(wordsStr));
        return new Dictionary(words);
    }

    public void filterWordsByLength(Dictionary dictionary, int min, int max) {
        if(dictionary == null)
            throw new IllegalArgumentException("Dictionary cannot be null!");

        if(min < 0 || max < 0 || min > max)
            throw new IllegalArgumentException("Invalid min or max!");

        Predicate<Word> filter = (dictionaryWord) -> dictionaryWord.length() >= min && dictionaryWord.length() <= max;
        filter(dictionary, filter);
    }

    public void filterWordsByAcceptedLength(Dictionary dictionary, Collection<Integer> acceptedLengths) {
        if(dictionary == null)
            throw new IllegalArgumentException("Dictionary cannot be null!");

        if(acceptedLengths == null)
            throw new IllegalArgumentException("AcceptedLengths cannot be null!");

        Predicate<Word> filter = (dictionaryWord) -> acceptedLengths.contains(dictionaryWord.length());
        filter(dictionary, filter);
    }

    public void filterWordsWithLetter(Dictionary dictionary, Character character) {
        if(dictionary == null)
            throw new IllegalArgumentException("Dictionary cannot be null!");

        if(character == null)
            throw new IllegalArgumentException("Character cannot be null!");

        Predicate<Word> filter = (dictionaryWord) -> dictionaryWord.contains(Character.toString(character));
        filter(dictionary, filter);
    }

    public void filterWordsWithoutLetter(Dictionary dictionary, Character character) {
        if(dictionary == null)
            throw new IllegalArgumentException("Dictionary cannot be null!");

        if(character == null)
            throw new IllegalArgumentException("Character cannot be null!");

        Predicate<Word> filter = (dictionaryWord) -> !dictionaryWord.contains(Character.toString(character));
        filter(dictionary, filter);
    }

    private void filter(Dictionary dictionary, Predicate<Word> filter) {
        List<Word> words = dictionary.getWords();
        words = words.stream().filter(filter).collect(Collectors.toList());

        dictionary.setWords(words);
    }

}
