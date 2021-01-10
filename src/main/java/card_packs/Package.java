package card_packs;

import card_packs.card.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import server.PostGre;
import server.Server;

public class Package {

    private final Card[] _packageCards = new Card[5];
    private final JSONArray _jsonCardArray;

    public Package(String jsonText) {
        jsonText = tripleToSingle(jsonText);
        _jsonCardArray = new JSONArray(jsonText);
        if(isCreated()){
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
    public boolean isCreated(){
        if(_jsonCardArray.length() != 5){
            return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    public void showPackage(){
        if(isCreated()){
            showCards(_packageCards);
        }else{
            System.out.println("Cannot show Package because it is empty!");
        }

    }

    public void savePackage(){
        PostGre db = new PostGre();
        int id = db.getMaxId();
        for(int i=0; i < _jsonCardArray.length(); i++) {
            db.insertCardToPackage(id+1, _packageCards[i]);
        }
        System.out.println("Package is saved in the Database!");
    }

    private String tripleToSingle(String jsonText){
        return jsonText.replace("\\\"" , "\"");
    }

    static String showCards(Card[] deckCards) {
        StringBuilder str = new StringBuilder("Deck: \n");
        for (Card deckCard : deckCards) {
            str.append(deckCard.getId() + ": ");
            str.append(deckCard.getName() + " - ");
            str.append(deckCard.getDamage() + "dmg - ");
            str.append(deckCard.getElementType() + "\n");
        }
        return str.toString();
    }


    public Card[] getPackage(){
        return _packageCards;
    }

}
