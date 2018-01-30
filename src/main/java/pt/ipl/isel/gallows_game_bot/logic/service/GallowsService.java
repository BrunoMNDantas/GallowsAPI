package pt.ipl.isel.gallows_game_bot.logic.service;

import pt.ipl.isel.gallows_game_bot.dataAccess.IWordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GallowsService {

    private LetterService letterService = new LetterService();
    private WordService wordService = new WordService();
    private SentenceService sentenceService = new SentenceService();
    private DictionaryService dictionaryService;



    public GallowsService(IWordsRepository repository) {
        if(repository == null)
            throw new IllegalArgumentException("Repository cannot be null!");

        this.dictionaryService = new DictionaryService(repository);
    }



    public Gallows createGallows(Collection<Integer> wordsLengths) throws ServiceException {
        Sentence sentence = sentenceService.createSentence(wordsLengths);
        Dictionary dictionary = dictionaryService.getDictionary();

        return new Gallows(dictionary, sentence, new LinkedList<>(), new LinkedList<>());
    }


    public void include(Gallows gallows, Character letter, Collection<Map.Entry<Integer, Integer>> positions) {
        if(gallows == null)
            throw new IllegalArgumentException("Gallows cannot be null!");

        if(letter == null)
            throw new IllegalArgumentException("Letter cannot be null!");

        if(positions == null)
            throw new IllegalArgumentException("Positions cannot be null!");

        Sentence sentence = gallows.getSentence();

        Word sentenceWord;
        Letter sentenceWordLetter;
        for(Map.Entry<Integer, Integer> position : positions) {
            sentenceWord = sentence.getWordAt(position.getKey());
            sentenceWordLetter = sentenceWord.getLetterAt(position.getValue());
            sentenceWordLetter.setCharacter(letter);
        }

        if(sentence.getWords().size() == 1) // cannot remove words without letter it may be possible to exist words without that letter
            dictionaryService.filterWordsWithLetter(gallows.getDictionary(), letter);

        gallows.getIncluded().add(letter);
    }

    public void exclude(Gallows gallows, Character letter) {
        if(gallows == null)
            throw new IllegalArgumentException("Gallows cannot be null!");

        if(letter == null)
            throw new IllegalArgumentException("Letter cannot be null!");

        dictionaryService.filterWordsWithoutLetter(gallows.getDictionary(), letter);

        gallows.getExcluded().add(letter);
    }


    public void cleanDictionary(Gallows gallows) {
        if(gallows == null)
            throw new IllegalArgumentException("Gallows cannot be null!");

        Predicate<Word> lengthPredicate = createLengthPredicate(gallows);
        Predicate<Word> patternPredicate = createPatternPredicate(gallows);
        Predicate<Word> excludeLetterPredicate = createExcludeLetterPredicate(gallows);

        Dictionary dictionary = gallows.getDictionary();

        dictionary.setWords(
                dictionary.getWords()
                        .stream()
                        .filter((word) ->
                                lengthPredicate.test(word) &&
                                patternPredicate.test(word) &&
                                excludeLetterPredicate.test(word))
                        .collect(Collectors.toList()));
    }

    private Predicate<Word> createLengthPredicate(Gallows gallows) {
        Collection<Word> undefinedWords = getUndefinedWords(gallows.getSentence());

        Collection<Integer> acceptedLengths = new LinkedList<>();

        for(Word word : undefinedWords)
            if(!acceptedLengths.contains(word.length()))
                acceptedLengths.add(word.length());

        return (word) -> acceptedLengths.contains(word.length());
    }

    private Predicate<Word> createPatternPredicate(Gallows gallows) {
        Collection<Word> undefinedWords = getUndefinedWords(gallows.getSentence());

        Collection<String> patterns = new LinkedList<>();

        for(Word word : undefinedWords)
            patterns.add(wordService.createRegex(word));

        return (word) -> {
            for(String regex : patterns)
                if(word.matches(regex))
                    return true;

            return false;
        };
    }

    private Predicate<Word> createExcludeLetterPredicate(Gallows gallows) {
        return (word) -> {
            for(Character character : gallows.getExcluded())
                if(word.contains(Character.toString(character)))
                    return false;

            return true;
        };
    }

    private Collection<Word> getUndefinedWords(Sentence sentence) {
        return sentence.getWords()
                .stream()
                .filter(word -> !wordService.isDefined(word))
                .collect(Collectors.toList());
    }


    public Collection<Word> finishSentenceWordsWithOnlyOneDictionaryOption(Gallows gallows) {
        if(gallows == null)
            throw new IllegalArgumentException("Gallows cannot be null!");

        Sentence sentence = gallows.getSentence();
        Dictionary dictionary = gallows.getDictionary();

        Collection<Word> undefinedWords = getUndefinedWords(sentence);
        Collection<Word> uniquePossibilities = new LinkedList<>();

        Word uniquePossibility;
        for(Word sentenceWord : undefinedWords) {
            uniquePossibility = getUniquePossibility(sentenceWord, dictionary);

            if(uniquePossibility != null){
                finishWord(sentenceWord, uniquePossibility);
                uniquePossibilities.add(uniquePossibility);
            }
        }

        return uniquePossibilities;
    }

    private Word getUniquePossibility(Word sentenceWord, Dictionary dictionary) {
        Word uniquePossibility = null;

        for(Word dictionaryWord : dictionary.getWords()) {
            if(canBeSameWord(sentenceWord, dictionaryWord)){
                if(uniquePossibility == null)
                    uniquePossibility = dictionaryWord;
                else
                    return null;
            }
        }

        return uniquePossibility;
    }

    private boolean canBeSameWord(Word sentenceWord, Word dictionaryWord){
        if(sentenceWord.length() != dictionaryWord.length())
            return false;

        for(int i=0; i<sentenceWord.length(); ++i)
            if( letterService.isDefined(sentenceWord.getLetterAt(i)) &&
                !dictionaryWord.getLetterAt(i).equals(sentenceWord.getLetterAt(i)))
                return false;

        return true;
    }

    private void finishWord(Word sentenceWord, Word dictionaryWord) {
        Letter dictionaryWordLetter;
        Letter sentenceWordLetter;

        for(int i=0; i<sentenceWord.length(); ++i) {
            sentenceWordLetter = sentenceWord.getLetterAt(i);
            dictionaryWordLetter = dictionaryWord.getLetterAt(i);

            sentenceWordLetter.setCharacter(dictionaryWordLetter.getCharacter());
        }
    }


    public Character getMostProbableLetter(Gallows gallows) {
        if(gallows == null)
            throw new IllegalArgumentException("Gallows cannot be null!");

        Map<Character, Integer> counter = getNonTestedLettersCount(gallows);

        Character mostFrequent = 0;
        for(Character c : counter.keySet())
            if(mostFrequent == 0 || counter.get(mostFrequent) < counter.get(c))
                mostFrequent = c;

        return mostFrequent;
    }

    private Map<Character, Integer> getNonTestedLettersCount(Gallows gallows) {
        Sentence sentence = gallows.getSentence();
        Collection<Word> undefinedWords = getUndefinedWords(sentence);

        Map<Character, Integer> counter = new HashMap<>();

        Map<Character, Integer> wordCounter;
        for(Word sentenceWord : undefinedWords){
            wordCounter = getNonTestedLettersCount(sentenceWord, gallows);

            for(Character c : wordCounter.keySet())
                if(!counter.containsKey(c))
                    counter.put(c, wordCounter.get(c));
                else
                    counter.put(c, counter.get(c) + wordCounter.get(c));
        }

        return counter;
    }

    private Map<Character, Integer> getNonTestedLettersCount(Word sentenceWord, Gallows gallows) {
        Dictionary dictionary = gallows.getDictionary();
        Collection<Word> possibleWords = findPossibleWords(sentenceWord, dictionary);

        Map<Character, Integer> counter = new HashMap<>();

        Character dictionaryWordLetter;
        for(int i=0; i<sentenceWord.length(); ++i){
            if(letterService.isDefined(sentenceWord.getLetterAt(i)))
                continue;

            for(Word dictionaryWord : possibleWords) {
                dictionaryWordLetter = dictionaryWord.getLetterAt(i).getCharacter();

                if(gallows.getIncluded().contains(dictionaryWordLetter) || gallows.getExcluded().contains(dictionaryWordLetter))
                    continue;

                if(!counter.containsKey(dictionaryWordLetter))
                    counter.put(dictionaryWordLetter, 0);

                counter.put(dictionaryWordLetter, counter.get(dictionaryWordLetter)+1);
            }
        }

        return counter;
    }

    private Collection<Word> findPossibleWords(Word sentenceWord, Dictionary dictionary) {
        Collection<Word> possibleWords = new LinkedList<>();

        for(Word dictionaryWord : dictionary.getWords())
            if(canBeSameWord(sentenceWord, dictionaryWord))
                possibleWords.add(dictionaryWord);

        return possibleWords;
    }

}
