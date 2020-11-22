package card_packs.card;

//TODO: Configure Card

public abstract class Card {

    protected String __name;
    protected int __damage;
    protected ELEMENT __elementType;

    public Card() {
        this.__damage = 100;
        this.__name = "default";
        this.__elementType = ELEMENT.NOT_SET;
    }

    public Card(String name, int damage) {
        this.__name = name;
        this.__damage = damage;
    }

    public String get_name() {
        return __name;
    }

    public int get_damage() {
        return __damage;
    }

    public void show(){
        System.out.println("hey");
    }

    public abstract void method();
}
