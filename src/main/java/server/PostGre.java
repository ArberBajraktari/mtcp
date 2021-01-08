package server;

import card_packs.Deck;
import card_packs.Stack;
import card_packs.card.Card;
import client.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Iterator;

public class PostGre {

    Connection connection;
    public PostGre(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kinaski", "kinaski", "postgres");
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    public int registerUser(Client user){
        try {
            PreparedStatement user_exst = connection.prepareStatement( "SELECT * FROM users where username = ?" );
            user_exst.setString(1, user.getUsername());
            ResultSet rs = user_exst.executeQuery();
            if(rs.next()){
                System.out.println("Already exists!");
                return 0;
            }else{
                PreparedStatement st = connection.prepareStatement("INSERT INTO users (username, password, coins, elorating, logged, bio, img) VALUES (?, ?, ?, ?, ?, ?, ?)");
                st.setString(1, user.getUsername());
                st.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                st.setInt(3, user.getCoins());
                st.setInt(4, user.getEloRating());
                st.setBoolean(5, user.isLogged());
                st.setString(6, user.getBio());
                st.setString(7, user.getImg());
                st.executeUpdate();
                st.close();
                return 1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int deleteUser(String username){
        PreparedStatement st;
        try {
            PreparedStatement user_exst = connection.prepareStatement( "SELECT * FROM users where username = ?" );
            user_exst.setString(1, username);
            ResultSet rs = user_exst.executeQuery();
            if(rs.next()){
                st = connection.prepareStatement("DELETE FROM users WHERE USERNAME = ?");
                st.setString(1, username);
                st.executeUpdate();
                st.close();
                return 1;
            }else{
                return 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }


    public boolean isLogged(String username){
        PreparedStatement user_exst = null;
        try {
            user_exst = connection.prepareStatement( "SELECT logged FROM users where username = ?" );
            user_exst.setString(1, username);
            ResultSet rs = user_exst.executeQuery();
            if(rs.next()){
                if(rs.getBoolean("logged")){
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int deleteUser(Client user){
        return deleteUser(user.getUsername());
    }

    public int logInUser(Client user) {
        PreparedStatement stm = null;
        try {
            PreparedStatement user_exst = connection.prepareStatement( "SELECT password FROM users where username = ?" );
            user_exst.setString(1, user.getUsername());
            ResultSet rs = user_exst.executeQuery();
            if(rs.next()){
                if(BCrypt.checkpw(user.getPassword(), rs.getString("password"))){
                    stm = connection.prepareStatement( "UPDATE users set logged = ? WHERE username = ?" );
                    stm.setBoolean(1, true);
                    stm.setString(2, user.getUsername());
                    int count = stm.executeUpdate();
                    stm.close();
                    if(count > 0) {
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }else{
                return 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int getIdFromUsername(String username) {
        PreparedStatement user_exst;
        try {
            user_exst = connection.prepareStatement( "SELECT user_id FROM users where username = ?" );
            user_exst.setString(1, username);
            ResultSet rs = user_exst.executeQuery();
            if(rs.next()){
                return rs.getInt("user_id");
            }
            return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void insertCardToPackage(int id, Card card) {
        //get biggest ID
        //increment with 1
        //add 4 cards
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO packages (package_id, card_id, name, damage) VALUES (?, ?, ?, ?)");
            st.setInt(1, id);
            st.setString(2, card.getId());
            st.setString(3, card.getName());
            st.setDouble(4, card.getDamage());
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getMaxId() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT package_id FROM packages ORDER BY package_id DESC LIMIT 1" );
            while (rs.next())
            {
                return rs.getInt("package_id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int getMinId() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT package_id FROM packages ORDER BY package_id ASC LIMIT 1" );
            while (rs.next())
            {
                return rs.getInt("package_id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int buyPackage(String username){
        int id = getIdFromUsername(username);
        int coins = getCoinsFromUsername(username);
        int pck_id = getMinId();
        if(pck_id != 0 && coins >= 5){
            Card[] cards = getCardsFromPackage(pck_id);
            deleteCardsFromPackage(pck_id);
            if(saveCardsWithUser(cards, id) == 1 && decreaseCoinsFromUser(username) == 1){
                return 1;
            }
        }else{
            return 0;
        }
        return 0;
    }

    public int decreaseCoinsFromUser(String username) {
        PreparedStatement stm ;
        try {
            stm = connection.prepareStatement( "UPDATE users set coins = coins - 5 WHERE username = ?" );
            stm.setString(1, username);
            if(stm.executeUpdate() > 0) {
                stm.close();
                return 1;
            }else{
                stm.close();
                return 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  0;

    }

    private int getCoinsFromUsername(String username) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement( "SELECT coins FROM users where username = ?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                return rs.getInt("coins");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    private Card[] getCardsFromPackage(int id){
        PreparedStatement stmt;
        Card[] cards = new Card[5];
        try {
            stmt = connection.prepareStatement( "SELECT * FROM packages where package_id = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next())
            {
                cards[count] = new Card(rs.getString("card_id"), rs.getString("name"), rs.getDouble("damage"));
                count++;
            }
            return cards;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int deleteCardsFromPackage(int id){
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement( "Delete FROM packages where package_id = ?");
            stmt.setInt(1,id);
            stmt.executeUpdate();
            stmt.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    //test scenario buyPackageTest()
    public int deleteUsersFromTickets(String username){
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement( "Delete FROM users_ticket where user_id = ?");
            stmt.setInt(1,getIdFromUsername(username));
            stmt.executeUpdate();
            stmt.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private int saveCardsWithUser(Card cards[], int user_id){
        try {
            for(Card card: cards){
                PreparedStatement st = connection.prepareStatement("INSERT INTO cards (card_id, name, damage, deck) VALUES (?, ?, ?, ?)");
                st.setString(1, card.getId());
                st.setString(2, card.getName());
                st.setDouble(3, card.getDamage());
                st.setInt(4, 0);
                st.executeUpdate();

                st = connection.prepareStatement("INSERT INTO users_ticket values(?, ?)  ");
                st.setInt(1, user_id);
                st.setString(2, card.getId());
                st.executeUpdate();
                st.close();
            }
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public String getStack(String username){
        try {
            PreparedStatement st = connection.prepareStatement("select c.card_id, c.name, damage from users as u join users_ticket as ut on u.user_id = ut.user_id join cards as c on ut.card_id = c.card_id where u.username = ?;");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            Stack stack = new Stack();
            while (rs.next())
            {
                stack.addCard(rs.getString("card_id"), rs.getString("name"), rs.getDouble("damage"));
            }
            return stack.getStack();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public String getDeck(String username) {
        try {
            PreparedStatement st = connection.prepareStatement("select c.card_id, c.name, damage from users as u join users_ticket as ut on u.user_id = ut.user_id join cards as c on ut.card_id = c.card_id where u.username = ? and deck = u.user_id;");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            Deck deck = new Deck();
            while(rs.next())
            {
                Card card = new Card(rs.getString("card_id"), rs.getString("name"), rs.getDouble("damage"));
                deck.append(card);
            }
            if(deck.showDeck() != null){
                return deck.showDeck();
            }else{
                return "Deck is empty (not configured)";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "Something went wrong";
    }

    //test scenario buyPackageTest()
    public int deleteCardsFromUser(String username) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement( "Delete FROM cards as c using users_ticket as ut where c.card_id = ut.card_id and ut.user_id = ?");
            stmt.setInt(1,getIdFromUsername(username));
            stmt.executeUpdate();
            stmt.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public boolean setDeck(String cardsJson, String username) {
        Deck deck = getCardsForDeck(cardsJson, username);
        if(deck != null){
            return setDeckForUsername(deck, username);
        }else{
            return false;
        }

    }

    private boolean setDeckForUsername(Deck deck, String username) {
        try {
            int id = getIdFromUsername(username);
            PreparedStatement st = connection.prepareStatement("UPDATE cards set deck = ? WHERE card_id in (?, ?, ?, ?)");
            st.setInt(1, id);
            st.setString(2, deck.getCardId(0));
            st.setString(3, deck.getCardId(1));
            st.setString(4, deck.getCardId(2));
            st.setString(5, deck.getCardId(3));
            int count = st.executeUpdate();
            st.close();
            if(count > 0){
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Deck getCardsForDeck(String cardsJson, String username) {
        String[] cards = new String[4];
        Deck deck = new Deck();
        JSONArray jsonCardArray = new JSONArray(cardsJson);
        //Iterating the contents of the array
        Iterator<Object> iterator = jsonCardArray.iterator();
        int count = 0;
        while(iterator.hasNext()) {
            cards[count] = (String) iterator.next();
            count++;
        }
        if(count != 4){
            return null;
        }

        try {
            PreparedStatement st = connection.prepareStatement("select c.card_id, c.name, damage from users as u join users_ticket as ut on u.user_id = ut.user_id join cards as c on ut.card_id = c.card_id where u.username = ? and c.card_id in (?, ?, ?, ?)");
            st.setString(1, username);
            st.setString(2, cards[0]);
            st.setString(3, cards[1]);
            st.setString(4, cards[2]);
            st.setString(5, cards[3]);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                Card card = new Card(rs.getString("card_id"), rs.getString("name"), rs.getDouble("damage"));
                deck.append(card);
            }
            if(deck.isFull()){
                return deck;
            }else{
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public void deleteAll(){
        try {
            PreparedStatement st = connection.prepareStatement("DELETE FROM users_ticket");
            st.executeUpdate();
            st = connection.prepareStatement("DELETE FROM users");
            st.executeUpdate();
            st = connection.prepareStatement("DELETE FROM cards");
            st.executeUpdate();
            st = connection.prepareStatement("DELETE FROM packages");
            st.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
