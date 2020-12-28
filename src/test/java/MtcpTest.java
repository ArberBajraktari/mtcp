import card_packs.card.CARDTYPE;
import card_packs.card.Card;
import card_packs.card.ELEMENT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtcpTest {
    public static Card monster = new Card("Dragon", 10.0);
    public static Card spell1 = new Card("FireSpell", 11.0);
    public static Card spell2 = new Card("WaterSpell", 11.0);
    public static Card spell3 = new Card("RegularSpell", 11.0);

    @Test
    void configureCardMonsterTest() {
        assertEquals(CARDTYPE.MONSTER.toString(), monster.isMonster());
        assertEquals(ELEMENT.NOT_SET.toString(), monster.getElementType());
    }

    @Test
    void configureCardSpellTest() {
        assertEquals(CARDTYPE.SPELL.toString(), spell1.isMonster());
        assertEquals(ELEMENT.FIRE.toString(), spell1.getElementType());
        assertEquals(CARDTYPE.SPELL.toString(), spell2.isMonster());
        assertEquals(ELEMENT.WATER.toString(), spell2.getElementType());
        assertEquals(CARDTYPE.SPELL.toString(), spell3.isMonster());
        assertEquals(ELEMENT.NORMAL.toString(), spell3.getElementType());
    }

}
