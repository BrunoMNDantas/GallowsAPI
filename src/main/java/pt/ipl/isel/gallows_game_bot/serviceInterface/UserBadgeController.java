package pt.ipl.isel.gallows_game_bot.serviceInterface;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserBadgeController {
/*
    private static final SentenceService SENTENCE_SERVICE = new SentenceService();
    private static final GallowsService GALLOWS_SERVICE = new GallowsService();



    private static void fill(Sentence sentence) throws Exception {
        Gallows gallows = GALLOWS_SERVICE.createGallows(sentence);

        while (!SENTENCE_SERVICE.isDefined(sentence) && gallows.getDictionary().size()!=0) {
            letter = GALLOWS_SERVICE.getMostProbableLetter(gallows);

            GALLOWS_SERVICE.include(gallows, letter, getLetterPositions());
            GALLOWS_SERVICE.exclude(gallows, letter);

            foundWords = GALLOWS_SERVICE.checkForWordsWithOnlyOneDictionaryOption(gallows);

            GALLOWS_SERVICE.finishWords(gallows, foundWords);

            System.out.println(sentence.toString().replace(LetterService.UNDEFINED_CHAR, UNKNOWN_CHAR));
        }

        Sentence sentence = SENTENCE_SERVICE.createSentence(mask);

        if(!SENTENCE_SERVICE.isDefined(sentence))
    }



    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createUserBadge(@RequestBody UserBadge userBadge) throws Exception {

    }
*/

}
