package card_packs;

import card_packs.card.Card;
import server.Server;

import java.util.Arrays;

public class Deck {
    private final Card[] _deckCards = new Card[4];

    public Deck(){
        Arrays.fill(_deckCards, null);
    }

    public void append(Card card){
        for( int i=0; i<_deckCards.length; i++){
            if( _deckCards[i] == null){
                _deckCards[i] = card;
                break;
            }
        }
    }

    //error check
    public int checkDeckCreated(){
        for (Card deckCard : _deckCards) {
            if (deckCard == null) {
                return 1;
            }
        }
        return 0;
    }

    public String showDeck(){
        Server.log("Deck:");
        if(checkDeckCreated() == 0){
            return Package.showCards(_deckCards);

        }else{
            Server.log("Cannot show Deck because it is not created!");
            System.out.println("Cannot show Deck because it is not created!");
            return null;
        }
    }
    public boolean isFull(){
        for (Card deckCard : _deckCards) {
            if (deckCard == null) {
                return false;
            }
        }
        return true;
    }

    public String showDeckPlain(){
        Server.log("Deck:");
        StringBuilder deck = new StringBuilder();
        deck.append("Deck:\n");
        if(checkDeckCreated() == 0){
            for( int i=0; i<4; i++){
                deck.append(_deckCards[i].getName() + "is of ");
                deck.append(_deckCards[i].getElementType() + " type \n");
            }
            return deck.toString();
        }else{
            Server.log("Cannot show Deck because it is not created!");
            System.out.println("Cannot show Deck because it is not created!");
            return null;
        }
    }

    public String getCardId(int cardId){
        return _deckCards[cardId].getId();
    }


}
