package card_packs;

import card_packs.card.Card;
import server.Server;

import java.util.ArrayList;

public class Stack {
    private static ArrayList<Card> _stackCards = new ArrayList<>();
    private int _cardCount = 0;

    public Stack(){
    }

    public String getStack(){
        StringBuilder str = new StringBuilder("Stack: \n");
        for(int i = 0; i < _stackCards.size(); i++) {
            str.append(_stackCards.get(i).getId() + ": ");
            str.append(_stackCards.get(i).getName() + " - ");
            str.append(_stackCards.get(i).getDamage() + "dmg - ");
            str.append(_stackCards.get(i).getElementType() + "\n");
        }
        return str.toString();
    }

    public void addCard(String id, String name, double damage){
        Card card = new Card(id, name, damage);
        _stackCards.add(card);
    }

    public void appendPackage(Package p_package) {
        if( p_package.isCreated()) {
            for (int i = 0; i < p_package.getPackage().length; i++) {
                _stackCards.add(p_package.getPackage()[i]);
            }
            _cardCount += 5;
        }
    }

    public int getCardCount(){
        return _cardCount;
    }
}
