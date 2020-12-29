import card_packs.card.CARDTYPE;
import card_packs.card.Card;
import card_packs.card.ELEMENT;
import card_packs.Package;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtcpTest {
    public static Card monster = new Card("id1:", "Dragon", 10.0);
    public static Card spell1 = new Card("id2:", "FireSpell", 11.0);
    public static Card spell2 = new Card("id3:", "WaterSpell", 11.0);
    public static Card spell3 = new Card("id4:", "RegularSpell", 11.0);

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

    //check if only Packages with 5 Cards can be created
    @Test
    void packageTest() {
        Package ok = new Package("[{\\\"Id\\\":\\\"845f0dc7-37d0-426e-994e-43fc3ac83c08\\\", \\\"Name\\\":\\\"WaterGoblin\\\", \\\"Damage\\\": 10.0}, {\\\"Id\\\":\\\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\\\", \\\"Name\\\":\\\"Dragon\\\", \\\"Damage\\\": 50.0}, {\\\"Id\\\":\\\"e85e3976-7c86-4d06-9a80-641c2019a79f\\\", \\\"Name\\\":\\\"WaterSpell\\\", \\\"Damage\\\": 20.0}, {\\\"Id\\\":\\\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\\\", \\\"Name\\\":\\\"Ork\\\", \\\"Damage\\\": 45.0}, {\\\"Id\\\":\\\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\\\", \\\"Name\\\":\\\"FireSpell\\\",    \\\"Damage\\\": 25.0}]");
        Package nok = new Package("[{\\\"Id\\\":\\\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\\\", \\\"Name\\\":\\\"Dragon\\\", \\\"Damage\\\": 50.0}, {\\\"Id\\\":\\\"e85e3976-7c86-4d06-9a80-641c2019a79f\\\", \\\"Name\\\":\\\"WaterSpell\\\", \\\"Damage\\\": 20.0}, {\\\"Id\\\":\\\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\\\", \\\"Name\\\":\\\"Ork\\\", \\\"Damage\\\": 45.0}, {\\\"Id\\\":\\\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\\\", \\\"Name\\\":\\\"FireSpell\\\",    \\\"Damage\\\": 25.0}]");
        ok.showPackage();
        assertEquals(0, ok.checkPackageCreation());
        assertEquals(1, nok.checkPackageCreation());
    }

}
