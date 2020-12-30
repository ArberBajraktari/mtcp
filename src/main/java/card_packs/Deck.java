package card_packs;

import card_packs.card.Card;
import server.Server;

import java.util.Arrays;

public class Deck {
    private final Card[] _deckCards = new Card[4];
    private boolean _configured = false;

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

    @SuppressWarnings("unused")
    public void showDeck(){
        Server.log("Deck:");
        if(checkDeckCreated() == 0){
            Package.showCards(_deckCards);
        }else{
            Server.log("Cannot show Deck because it is not created!");
            System.out.println("Cannot show Deck because it is not created!");
        }
    }

    private void configure(){

    }


}
