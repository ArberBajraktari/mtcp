package server;

import client.Client;

import java.io.*;
import java.net.Socket;
import java.util.*;


public class Server extends ServerSecurity{
    private Client _client;
    private Battlefield _battlefield;
    private final PostGre _db = new PostGre();
    private BufferedReader _in;
    private BufferedWriter _out;
    private StringBuilder _messageSeparator = new StringBuilder();
    private Verb _myVerb = Verb.OTHER;
    private String _message;
    private String _version;
    private String _payload;
    private String[] _command;
    private int _messagesNumber;
    private List<String> _messagesSaved = new ArrayList<>();
    private Map<String, String> __header = new HashMap<>();
    private boolean _http_first_line = true;

    private String[] _allowedReq = {"users", "sessions", "packages", "transactions", "cards", "deck", "stats", "score", "battles", "tradings"};


    public Server(){
    }
//requestMode changed to one of the values below

//1 - create user
//2 - login user
//3 - create package
//4 - acquire(buy) package
//5 - show cards
//6 - show deck
//7 - configure deck
//8 - show deck; different format
//9 - edit user data
//10 - show user stats
//11- show user scoreboard
//12- trading
    private int requestMode;

    public Server(Socket clientSocket) throws IOException {
        this._in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this._out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }


    protected void setMyVerb(String myVerb) {
        switch (myVerb) {
            case "GET":
                _myVerb = Verb.GET;
                break;
            case "POST":
                _myVerb = Verb.POST;
                break;
            case "PUT":
                _myVerb = Verb.PUT;
                break;
            case "DELETE":
                _myVerb = Verb.DELETE;
                break;
            default:
                _myVerb = Verb.OTHER;
                break;
        }
    }

    private void separateMessage(){
        //separate message
        String[] request = _messageSeparator.toString().split(System.getProperty("line.separator"));
        _messageSeparator = new StringBuilder();
        boolean skip = true;
        for (String line: request){
            if(!line.isEmpty()){
                if (_http_first_line) {
                    //saving folder and version
                    String[] first_line = line.split(" ");
                    setMyVerb(first_line[0]);
                    _message = first_line[1];
                    _version = first_line[2];
                    _http_first_line = false;
                } else {

                    //saving the header
                    if(line.contains(": ")){
                        String[] other_lines = line.split(": ");
                        __header.put(other_lines[0], other_lines[1]);
                    }
                    //saving the payload
                    else{
                        if (skip){
                            skip = false;
                        }else{
                            _messageSeparator.append( line );
                            _messageSeparator.append( "\r\n" );
                        }

                    }

                }
            }
        }
        _payload = _messageSeparator.toString();
    }

    public void readRequest() throws IOException {
        //read and save request
        log("Reading request...");
        //save request
        while (_in.ready()) {
            _messageSeparator.append((char) _in.read());
        }
        separateMessage();
        int status = checkRequest();
        performRequest(status);

    }

    @SuppressWarnings("unused")
    private void showHeader(){
        System.out.println("header:");
        for (Map.Entry<String, String> entry : __header.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue() );
        }
    }


    //1 - wrong request
    private int checkRequest(){
        log("srv: Checking for errors...");

        //check if command is supported
        if(_myVerb == Verb.OTHER){
            System.out.println("srv: Request method not supported");
            return 0;
        }else {
            _command = _message.split("/");
            if(Arrays.asList(_allowedReq).contains(_command[1])){
                if(_command[1].equals("users")){
                    return 1;
                }
                if(_command.length == 3){
                    if(_command[1].equals("transactions") && _command[2].equals("packages")){
                        return 10;
                    }else{
                        return 0;
                    }
                }
                return 11;
            }
            else{
                return 0;
            }
        }
    }

    private void performRequest(int status) throws IOException {
        if(status == 0){
            _out.write("HTTP/1.1 400\r\n");
            _out.write("Content-Type: text/html\r\n");
            _out.write("\r\n");
            _out.write("Bad request!\r\n");
        }else{
            _out.write("HTTP/1.1 200 OK\r\n");
            _out.write("Content-Type: text/html\r\n");
            _out.write("\r\n");
        }
        switch (status) {
            case 1:
                createUser(_payload);
                break;
        }
        _out.flush();
    }
    //depending on requestMode
    private void createUser(String json) throws IOException {
        Client user = new Client(json);
        if(_db.register(user)==0){
            _out.write("New user is created\n");
            log("New user is created.");
        }else{
            _out.write("Username already exists\n");
        }
    }
    private void logInUser(){

    }
    private void createPackage(){

    }
    private void buyPackage(){

    }
    private void showStack(){
    }
    private void showDeck(){

    }
    private void showDeckOther(){

    }
    private void configureDeck(){

    }
    private void editUser(){

    }
    private void showStats(){

    }
    private void showScoreboard(){

    }
    private void trade(){

    }

    //check what the request wants to do
    public boolean toCreateUser(){


        //return either false or true

        return false;
    }

    public static void log(String msg){
        File file = new File("log.txt");

        // creates the file
        try {
            file.createNewFile();
            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file, true);

            // Writes the content to the file
            writer.write("logged: " + msg + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
