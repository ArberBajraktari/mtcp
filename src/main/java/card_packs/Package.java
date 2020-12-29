package card_packs;

import card_packs.card.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import server.Server;

public class Package {

    private final Card[] _packageCards = new Card[5];
    private final JSONArray _jsonCardArray;

    public Package(String jsonText) {
        System.out.println(jsonText);
        jsonText = tripleToSingle(jsonText);
        _jsonCardArray = new JSONArray(jsonText);
        if(checkPackageCreation() == 0){
            for(int i=0; i < _jsonCardArray.length(); i++)
            {
                JSONObject tempCard = _jsonCardArray.getJSONObject(i);
                _packageCards[i] = new Card(tempCard.getString("Id"), tempCard.getString("Name"), tempCard.getDouble("Damage"));
            }
        }
    }

    //error check
    //0 - okay
    //1 - number of cards inserted is not 5
    //2 - package received empty input and not created
    public int checkPackageCreation(){
        if(_jsonCardArray.length() != 5){
            Server.log("Could not create Package. Package need 5 cards to be created, and there were either more or less inserted!");
            return 1;
        }
        Server.log("Package is created successfully!");
        return 0;
    }
    @SuppressWarnings("unused")
    public void showPackage(){
        Server.log("Package:");
        for(int i=0; i<5; i++){
            Server.log(_packageCards[i].getId() + ": " + _packageCards[i].getName() + " - " + _packageCards[i].getDamage() + "dmg - " + _packageCards[i].getElementType());
            System.out.print(_packageCards[i].getId() + ": ");
            System.out.print(_packageCards[i].getName() + " - ");
            System.out.print(_packageCards[i].getDamage() + "dmg - ");
            System.out.println(_packageCards[i].getElementType());
        }
    }

    private String tripleToSingle(String jsonText){
        return jsonText.replace("\\\"" , "\"");
    }

}
