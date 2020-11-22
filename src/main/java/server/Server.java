package server;

import client.Client;


public class Server extends ServerSecurity{
    private Client _client;
    private Battlefield _battlefield;
//requestMode changed to one of the values below
//0 - create user
//1 - login user
//2 - create package
//3 - acquire(buy) package
//4 - show cards
//5 - show deck
//6 - configure deck
//7 - show deck; different format
//8 - edit user data
//9 - show user stats
//10- show user scoreboard
//11- trading
    private int requestMode;

    public void readRequest(){

    }
    private void checkRequest(){

    }
    private void saveRequest(){

    }
    private void performRequest(){

    }

    //depending on requestMode
    private void createUser(){

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


}
