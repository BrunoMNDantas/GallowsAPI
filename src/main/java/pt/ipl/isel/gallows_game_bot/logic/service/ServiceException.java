package pt.ipl.isel.gallows_game_bot.logic.service;

import pt.ipl.isel.gallows_game_bot.transversal.APIException;

public class ServiceException extends APIException {

    public ServiceException(){ super(); }
    public ServiceException(String msg){ super(msg); }
    public ServiceException(String msg, Throwable cause){ super(msg, cause); }

}

