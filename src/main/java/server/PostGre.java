package server;

import card_packs.card.Card;
import client.Client;

import java.sql.*;

public class PostGre {

    Connection connection;
    public PostGre(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kinaski", "kinaski", "postgres");
            System.out.println("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    public int register(Client user){
        try {
            PreparedStatement st2 = connection.prepareStatement( "SELECT * FROM users where username = ?" );
            st2.setString(1, user.getUsername());
            ResultSet rs = st2.executeQuery();
            if(rs.next()){
                System.out.println("Already exists!");
                return 1;
            }else{
                PreparedStatement st = connection.prepareStatement("INSERT INTO users (username, password, coins, elorating, logged, bio, img) VALUES (?, ?, ?, ?, ?, ?, ?)");
                st.setString(1, user.getUsername());
                st.setString(2, user.getPassword());
                st.setInt(3, user.getCoins());
                st.setInt(4, user.getEloRating());
                st.setBoolean(5, user.isLogged());
                System.out.println(user.getBio());
                st.setString(6, user.getBio());
                st.setString(7, user.getImg());
                st.executeUpdate();
                st.close();
                return 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 1;
    }

    public void logIn(String username, String password){

    }

    public void insertCard(int id, Card card) {
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

    public void buyPackage(String username){
        //TODO: merr prej te 'packages' edhe kaloji te users, cards edhe user_tickets

    }

}
