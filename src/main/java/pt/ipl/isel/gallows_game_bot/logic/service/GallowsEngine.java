package pt.ipl.isel.gallows_game_bot.logic.service;

import pt.ipl.isel.gallows_game_bot.dataAccess.IWordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.Gallows;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GallowsEngine {

    private static final char UNKNOWN_CHAR = '-';



    private Supplier<Collection<Integer>> wordsLengthSupplier;
    private Predicate<Character> letterPredicate;
    private Function<Character, Collection<Map.Entry<Integer, Integer>>> letterPositionsFunction;
    private Predicate<Word> completeWordPredicate;
    private Consumer<String> logger;
    private GallowsService gallowsService;
    private SentenceService sentenceService;



    public GallowsEngine(IWordsRepository wordsRepository,
                        Supplier<Collection<Integer>> wordsLengthSupplier,
                        Predicate<Character> letterPredicate,
                        Function<Character, Collection<Map.Entry<Integer, Integer>>> letterPositionsFunction,
                        Predicate<Word> completeWordPredicate,
                        Consumer<String> logger) {

        if(wordsRepository == null)
            throw new IllegalArgumentException("WordsRepository cannot be null!");

        if(wordsLengthSupplier == null)
            throw new IllegalArgumentException("WordLengthSupplier cannot be null!");

        if(letterPredicate == null)
            throw new IllegalArgumentException("Letter cannot be null!");

        if(letterPositionsFunction == null)
            throw new IllegalArgumentException("LetterPositionsFunction cannot be null!");

        if(completeWordPredicate == null)
            throw new IllegalArgumentException("CompleteWordPredicate cannot be null!");

        if(logger == null)
            throw new IllegalArgumentException("Logger cannot be null!");

        this.wordsLengthSupplier = wordsLengthSupplier;
        this.letterPredicate = letterPredicate;
        this.letterPositionsFunction = letterPositionsFunction;
        this.completeWordPredicate = completeWordPredicate;
        this.logger = logger;
        this.gallowsService = new GallowsService(wordsRepository);
        this.sentenceService = new SentenceService();
    }



    public String run() throws ServiceException {
        Gallows gallows = gallowsService.createGallows(wordsLengthSupplier.get());

        Character letter;
        while (!sentenceService.isDefined(gallows.getSentence()) && gallows.getDictionary().size()!=0) {
            letter = gallowsService.getMostProbableLetter(gallows);

            testLetter(gallows, letter);

            gallowsService.cleanDictionary(gallows);

            if(!checkForFoundWords(gallows))
                return null;

            logger.accept(gallows.getSentence().toString().replace(LetterService.UNDEFINED_CHAR, UNKNOWN_CHAR));
        }

        if(gallows.getDictionary().size() == 0 && !sentenceService.isDefined(gallows.getSentence()))
            return null;
        else
            return gallows.getSentence().toString();
    }

    private void testLetter(Gallows gallows, Character letter) {
        if (letterPredicate.test(letter))
            gallowsService.include(gallows, letter, letterPositionsFunction.apply(letter));
        else
            gallowsService.exclude(gallows, letter);
    }

    private boolean checkForFoundWords(Gallows gallows) { //returns true if all found words are in sentence
        Collection<Word> finishedWords = gallowsService.finishSentenceWordsWithOnlyOneDictionaryOption(gallows);

        if(finishedWords.isEmpty())
            return true;

        for(Word finishedWord : finishedWords)
            if(!completeWordPredicate.test(finishedWord))
                return false;

        return checkForFoundWords(gallows);
    }

}
