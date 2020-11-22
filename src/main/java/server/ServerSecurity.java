package server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerSecurity {

    static private Map<String, String> usersLogged = new HashMap<>();
    static private List<String> usersTokens = new ArrayList<>();

    static protected void addLoggedUser(String username, String password){
        usersLogged.put(username, password);
        usersTokens.add(username + "-mtcgToken");
    }

    static protected boolean isUserLogged(String username, String password){
        for (Map.Entry<String, String> entry : usersLogged.entrySet()) {
            String uname = entry.getKey();
            Object pwd = entry.getValue();
            if(username.equals(uname) && password.equals(pwd)){
                return true;
            }
        }
        return false;
    }

    static protected boolean isTokenCorrect(String token){
        for (String tkn : usersTokens) {
            if(token.equals(tkn)){
                return true;
            }
        }
        return false;
    }

}
