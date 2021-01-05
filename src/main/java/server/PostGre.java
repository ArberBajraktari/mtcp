package server;

import card_packs.card.Card;
import client.Client;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

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

    public int getIdFromUsername(String username) throws SQLException {
        PreparedStatement user_exst = connection.prepareStatement( "SELECT user_id FROM users where username = ?" );
        user_exst.setString(1, username);
        ResultSet rs = user_exst.executeQuery();
        if(rs.next()){
            return rs.getInt("user_id");
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

    public void buyPackage(String username){
        //TODO: merr prej te 'packages' edhe kaloji te users, cards edhe user_tickets

    }

}
