package pt.ipl.isel.gallows_game_bot.transversal;

public class APIException extends  Exception {

    public APIException(){ super(); }
    public APIException(String msg){ super(msg); }
    public APIException(String msg, Throwable cause){ super(msg, cause); }

}

