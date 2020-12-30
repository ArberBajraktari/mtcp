package card_packs;

import card_packs.card.Card;
import server.Server;

import java.util.ArrayList;

public class Stack {
    private static ArrayList<Card> _stackCards = new ArrayList<>();
    private int _cardCount = 0;

    public Stack(){
    }

    @SuppressWarnings("unused")
    public void showStack(){
        for(int i = 0; i < _stackCards.size(); i++) {
            Server.log(_stackCards.get(i).getId() + ": " + _stackCards.get(i).getName() + " - " + _stackCards.get(i).getDamage() + "dmg - " + _stackCards.get(i).getElementType());
            System.out.print(_stackCards.get(i).getId() + ": ");
            System.out.print(_stackCards.get(i).getName() + " - ");
            System.out.print(_stackCards.get(i).getDamage() + "dmg - ");
            System.out.println(_stackCards.get(i).getElementType());
        }
    }

    public void tradeCard(Card card){

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
