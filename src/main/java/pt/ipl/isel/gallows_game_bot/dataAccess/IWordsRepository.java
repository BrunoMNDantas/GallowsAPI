package pt.ipl.isel.gallows_game_bot.dataAccess;

import java.util.Collection;

public interface IWordsRepository {

    Collection<String> getAll() throws RepositoryException;

    boolean insert(String word) throws RepositoryException;

    boolean delete(String word) throws RepositoryException;

}
