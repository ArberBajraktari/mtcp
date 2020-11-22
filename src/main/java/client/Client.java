package client;
import card_packs.Deck;
import card_packs.Stack;
import server.Server;


public class Client implements IPlayable{

    //clients info
    private String _username;
    private String _password;
    private int _coins;
    private int _eloRating;
    private boolean _logged;
    private MODE _stance = MODE.ATTACK;

    private Server _server;

    private Package _package;
    private Stack _stack;
    private Deck _deck;


    public Client(){
        //default client
        _username = "Max";
        _password = "password";
        _coins = 20;
        _eloRating = 0;
        _logged = false;
    }

    public Client(String username, String password){
        new Client();
        this._username = username;
        this._password = password;
    }




    //show client profile
    public void showProfile(){
        System.out.println("Users profile:");
        System.out.println("\r\tUsername: " + _username);
        System.out.println("\r\tCoins: " + _coins);
        System.out.println("\r\tElo Rating: " + _eloRating);
    }



}
