package client;
import card_packs.Deck;
import card_packs.Package;
import card_packs.Stack;
import org.json.JSONArray;
import org.json.JSONObject;
import server.PostGre;
import server.Server;


public class Client implements IPlayable{

    //clients info
    private String _username;
    private String _password;
    private int _coins;
    private int _eloRating;
    private boolean _logged;
    private String _bio;
    private String _img;

    private MODE _stance = MODE.ATTACK;

    private Server _server;
    private PostGre _db;

    private Deck _deck = new Deck();
    private Stack _stack = new Stack();


    public Client(){
        _coins = 20;
        _eloRating = 0;
        _logged = false;
        _bio = "hello";
        _img = ":)";
    }

    public Client(String json){
        this();
        JSONObject _jsonUser = new JSONObject(json);
        _username = _jsonUser.getString("Username");
        _password = _jsonUser.getString("Password");
    }


    public int getEloRating() {
        return _eloRating;
    }

    public boolean isLogged() {
        return _logged;
    }

    public String getUsername() {
        return _username;
    }

    public String getPassword() {
        return _password;
    }

    public String getBio() {
        return _bio;
    }

    public String getImg() {
        return _img;
    }

    //show client profile
    @SuppressWarnings("unused")
    public void showProfile(){
        System.out.println("Users profile:");
        System.out.println("\r\tUsername: " + _username);
        System.out.println("\r\tCoins: " + _coins);
        System.out.println("\r\tElo Rating: " + _eloRating);
    }
    public int getCoins(){
        return _coins;
    }

    public void showStack(){

    }

}
