package pt.ipl.isel.gallows_game_bot.dataAccess;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ipl.isel.gallows_game_bot.serviceInterface.App;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

public class WordsRepository implements IWordsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordsRepository.class);



    private String wordsFilePath;



    public WordsRepository(String wordsFilePath) {
        if(wordsFilePath == null || wordsFilePath.isEmpty())
            throw new IllegalArgumentException("WordFilePath cannot be null neither empty!");

        this.wordsFilePath = wordsFilePath;
    }



    public Collection<String> getAll() throws RepositoryException {
        File wordsFile = getWordsFile();

        try {
            Collection<String> words = FileUtils.readLines(wordsFile, "UTF-8");
            return words;
        } catch (IOException e) {
            LOGGER.error("Error reading file '" + wordsFilePath + "'. Check if file exists or is locked.", e);
            throw new RepositoryException("Error reading words file.", e);
        }
    }

    private File getWordsFile() throws RepositoryException {
        URI uri;

        try{
            URL url = App.class.getResource(wordsFilePath);

            if(url == null) {
                LOGGER.error("Error finding file'" + wordsFilePath + "'. This can happen if file could not be found or due to malformed uri.");
                throw new RepositoryException("Error finding words File!");
            }

            uri = url.toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Error finding file'" + wordsFilePath + "'. This can happen if file could not be found or due to malformed uri.", e);
            throw new RepositoryException("Error finding words File!", e);
        }

         return new File(uri);
    }


    public boolean insert(String word) throws RepositoryException {
        if(word == null || word.isEmpty())
            throw new IllegalArgumentException("Word cannot be null neither empty!");

        Collection<String> words = getAll();

        if(!words.contains(word)){
            words.add(word);
            write(words);
            return true;
        }

        return false;
    }


    public boolean delete(String word) throws RepositoryException {
        if(word == null || word.isEmpty())
            throw new IllegalArgumentException("Word cannot be null neither empty!");

        Collection<String> words = getAll();

        if(words.contains(word)){
            words.remove(word);
            write(words);
            return true;
        }

        return false;
    }

    private void write(Collection<String> words) throws RepositoryException {
        File wordsFile = getWordsFile();

        try{
            FileUtils.writeLines(wordsFile, words, false);
        } catch (IOException e) {
            LOGGER.error("Error writing on file '" + wordsFilePath + "'. Check if you have permission to write.", e);
            throw new RepositoryException("Error writing words!", e);
        }
    }

}
