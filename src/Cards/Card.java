package Cards;

public abstract class Card {
    private String __name;
    private int __damage;

    public Card() {
        this.__damage = 100;
        this.__name = "default";
    }

    public String get_name() {
        return __name;
    }

    public int get_damage() {
        return __damage;
    }

    public Card(String name, int damage) {
        this.__name = name;
        this.__damage = damage;
    }


    public abstract void method();
}
