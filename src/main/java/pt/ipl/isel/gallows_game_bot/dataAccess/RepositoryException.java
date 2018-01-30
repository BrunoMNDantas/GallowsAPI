package pt.ipl.isel.gallows_game_bot.dataAccess;

import pt.ipl.isel.gallows_game_bot.transversal.APIException;

public class RepositoryException extends APIException {

    public RepositoryException(){ super(); }
    public RepositoryException(String msg){ super(msg); }
    public RepositoryException(String msg, Throwable cause){ super(msg, cause); }

}

