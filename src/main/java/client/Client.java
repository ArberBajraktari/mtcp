package client;
import card_packs.Deck;
import card_packs.Package;
import card_packs.Stack;
import server.PostGre;
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
    private PostGre _db = new PostGre();

    private Deck _deck = new Deck();
    private Stack _stack = new Stack();


    public Client(){
        //default client
        _username = "Max";
        _password = "password";
        _coins = 20;
        _eloRating = 0;
        _logged = false;
    }

    public Client(String username, String password){
        this._username = username;
        this._password = password;
    }

    //show client profile
    @SuppressWarnings("unused")
    public void showProfile(){
        System.out.println("Users profile:");
        System.out.println("\r\tUsername: " + _username);
        System.out.println("\r\tCoins: " + _coins);
        System.out.println("\r\tElo Rating: " + _eloRating);
    }

    public boolean signUp(String username, String password) {
        //save to DB
        return true;
    }

    public boolean logIn(String username, String password){
        //get from DB
        return true;
    }


    public void buyPackage(){
        if(isCoinsValid()){
            //TODO: buy package, change database rows.
            _db.buyPackage(_username);
        }else{
            System.out.println("Not enough money!");
        }
    }

    public boolean isCoinsValid(){
        if(_coins >= 5){
            return true;
        }
        return false;
    }

    public int getCoins(){
        return _coins;
    }

}
