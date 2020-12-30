import card_packs.Deck;
import card_packs.Stack;
import card_packs.card.CARDTYPE;
import card_packs.card.Card;
import card_packs.card.ELEMENT;
import card_packs.Package;
import client.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(ok.isCreated());
        assertFalse(nok.isCreated());
    }

    @Test
    void deckTest() {
        //3 cards inserted, should not work -> return 1
        Deck deck = new Deck();
        deck.append(monster);
        deck.append(spell1);
        deck.append(spell2);
        assertEquals(1, deck.checkDeckCreated());

        //4 cards inserted, should work -> return 0
        Deck deck2 = new Deck();
        deck2.append(monster);
        deck2.append(spell1);
        deck2.append(spell2);
        deck2.append(spell3);
        assertEquals(0, deck2.checkDeckCreated());
//        deck.showDeck();
    }

    @Test
    void stackTest() {
        Package p_works = new Package("[{\\\"Id\\\":\\\"845f0dc7-37d0-426e-994e-43fc3ac83c08\\\", \\\"Name\\\":\\\"WaterGoblin\\\", \\\"Damage\\\": 10.0}, {\\\"Id\\\":\\\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\\\", \\\"Name\\\":\\\"Dragon\\\", \\\"Damage\\\": 50.0}, {\\\"Id\\\":\\\"e85e3976-7c86-4d06-9a80-641c2019a79f\\\", \\\"Name\\\":\\\"WaterSpell\\\", \\\"Damage\\\": 20.0}, {\\\"Id\\\":\\\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\\\", \\\"Name\\\":\\\"Ork\\\", \\\"Damage\\\": 45.0}, {\\\"Id\\\":\\\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\\\", \\\"Name\\\":\\\"FireSpell\\\",    \\\"Damage\\\": 25.0}]");
        Package p_n_works = new Package("[{\\\"Id\\\":\\\"4a2757d6-b1c3-47ac-b9a3-91deab093531\\\", \\\"Name\\\":\\\"Dragon\\\", \\\"Damage\\\": 55.0}, {\\\"Id\\\":\\\"4a2757d6-b1c3-47ac-b9a3-91deab093531\\\", \\\"Name\\\":\\\"Dragon\\\", \\\"Damage\\\": 55.0}, {\\\"Id\\\":\\\"91a6471b-1426-43f6-ad65-6fc473e16f9f\\\", \\\"Name\\\":\\\"WaterSpell\\\", \\\"Damage\\\": 21.0}, {\\\"Id\\\":\\\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\\\", \\\"Name\\\":\\\"Ork\\\", \\\"Damage\\\": 55.0}]");

        Stack stc = new Stack();
        stc.appendPackage(p_works);
        stc.appendPackage(p_n_works);
        assertEquals(5, stc.getCardCount());

        Stack stc2 = new Stack();
        stc2.appendPackage(p_works);
        stc2.appendPackage(p_works);
        assertEquals(10, stc2.getCardCount());
    }

    @Test
    void clientBuyPackageTest() {
        Client user = new Client();
        user.buyPackage("[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]");
        assertEquals(15, user.getCoins());
        user.buyPackage("[{\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]");
        assertEquals(15, user.getCoins());
        user.buyPackage("[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]");
        assertEquals(10, user.getCoins());
    }

}
