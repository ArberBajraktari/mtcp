package card_packs.card;

//TODO: Configure Card

public class Card {

    protected String __name;
    protected double __damage;
    protected ELEMENT __elementType;
    protected CARDTYPE __cardType;

    public Card() {
        this.__damage = 10.0;
        this.__name = "defaultCard";
        this.__elementType = ELEMENT.NOT_SET;
        __cardType = CARDTYPE.NOT_SET;
    }

    public Card(String name, double damage) {
            this.__name = name;
            this.__damage = damage;
            configureCard(name);
    }

    private void configureCard(String name){
        if(name.contains("Fire") || name.contains("Water") || name.contains("Regular")){
            if(name.contains("Spell")){
                __cardType = CARDTYPE.SPELL;
                if(name.contains("Fire")){
                    __elementType = ELEMENT.FIRE;
                }else if(name.contains("Water")){
                    __elementType = ELEMENT.WATER;
                }else{
                    __elementType = ELEMENT.NORMAL;
                }
            }else{
                __cardType = CARDTYPE.MONSTER;
            }
        }else{
            __cardType = CARDTYPE.MONSTER;
            __elementType = ELEMENT.NOT_SET;
        }
    }

    public String get_name() {
        return __name;
    }

    public String getElementType(){
        return __elementType.toString();
    }

    public String isMonster(){
        return __cardType.toString();
    }
}
