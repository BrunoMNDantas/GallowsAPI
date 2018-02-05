package pt.ipl.isel.gallows_game_bot.serviceInterface;

import org.springframework.web.bind.annotation.*;
import pt.ipl.isel.gallows_game_bot.dataAccess.WordsRepository;
import pt.ipl.isel.gallows_game_bot.logic.domain.Gallows;
import pt.ipl.isel.gallows_game_bot.logic.domain.Sentence;
import pt.ipl.isel.gallows_game_bot.logic.domain.Word;
import pt.ipl.isel.gallows_game_bot.logic.service.GallowsService;
import pt.ipl.isel.gallows_game_bot.logic.service.SentenceService;
import pt.ipl.isel.gallows_game_bot.logic.service.ServiceException;
import pt.ipl.isel.gallows_game_bot.transversal.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class GallowsController {

    private static final String WORDS_FILE = "/words.txt";
    private static final Map<UUID, Gallows> GALLOWS_BY_ID = new HashMap<>();



    private void insert(UUID id, Gallows gallows) {
        synchronized (GALLOWS_BY_ID){
            GALLOWS_BY_ID.put(id, gallows);
        }
    }

    private void delete(UUID id) {
        synchronized (GALLOWS_BY_ID){
            GALLOWS_BY_ID.remove(id);
        }
    }

    private Gallows get(UUID id) {
        synchronized (GALLOWS_BY_ID){
            return GALLOWS_BY_ID.get(id);
        }
    }



    private GallowsService gallowsService = new GallowsService(new WordsRepository(WORDS_FILE));
    private SentenceService sentenceService = new SentenceService();



    @RequestMapping(value = "create", method = RequestMethod.POST)
    public UUID createGallows(@RequestBody Collection<Integer> lengths) throws ServiceException {
        UUID id = UUID.randomUUID();
        Gallows gallows = gallowsService.createGallows(lengths);

        insert(id, gallows);

        return id;
    }

    @RequestMapping(value = "letter", method = RequestMethod.GET)
    public char getLetter(@RequestParam UUID gallowsId) {
        Gallows gallows = get(gallowsId);
        return gallowsService.getMostProbableLetter(gallows);
    }

    @RequestMapping(value = "include", method = RequestMethod.POST)
    public void includeLetter(@RequestParam UUID gallowsId, @RequestParam char letter, @RequestBody Collection<Pair<Integer, Integer>> positions) {
        Gallows gallows = get(gallowsId);
        gallowsService.include(gallows, letter, positions);
    }

    @RequestMapping(value = "exclude", method = RequestMethod.POST)
    public void excludeLetter(@RequestParam UUID gallowsId, @RequestParam char letter) {
        Gallows gallows = get(gallowsId);
        gallowsService.exclude(gallows, letter);
    }

    @RequestMapping(value = "clean", method = RequestMethod.POST)
    public void cleanDictionary(@RequestParam UUID gallowsId) {
        Gallows gallows = get(gallowsId);
        gallowsService.cleanDictionary(gallows);
    }

    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public Collection<String> finishWords(@RequestParam UUID gallowsId) {
        Gallows gallows = get(gallowsId);
        Collection<Word> finishedWords = gallowsService.finishSentenceWordsWithOnlyOneDictionaryOption(gallows);
        return finishedWords.stream().map(Word::toString).collect(Collectors.toList());
    }

    @RequestMapping(value = "isfinished", method = RequestMethod.GET)
    public boolean isFinished(@RequestParam UUID gallowsId) {
        Gallows gallows = get(gallowsId);
        return sentenceService.isDefined(gallows.getSentence()) || gallows.getDictionary().size()==0;
    }

    @RequestMapping(value = "sentence", method = RequestMethod.GET)
    public String getSentente(@RequestParam UUID gallowsId) {
        Gallows gallows = get(gallowsId);
        Sentence sentence = gallows.getSentence();
        return sentence.toString();
    }

}
