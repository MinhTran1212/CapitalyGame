

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mycompany.mavenproject1.Field;
import com.mycompany.mavenproject1.PropertyField;
import com.mycompany.mavenproject1.ServiceField;
import com.mycompany.mavenproject1.LuckyField;
import com.mycompany.mavenproject1.Player;
import com.mycompany.mavenproject1.GreedyPlayer;
import com.mycompany.mavenproject1.CarefulPlayer;
import com.mycompany.mavenproject1.TacticalPlayer;

public class FieldTest {

    private GreedyPlayer player1;
    private GreedyPlayer player2;
    private CarefulPlayer carefulPlayer;

    @BeforeEach
    public void setUp() {
        player1 = new GreedyPlayer("Player1");         // 10,000
        player2 = new GreedyPlayer("Player2");         // 10,000
        carefulPlayer = new CarefulPlayer("Careful");   // 10,000
    }

    // ==========================================
    // 1. LuckyField Tests
    // ==========================================

    @Test
    public void testLuckyFieldReward() {
        LuckyField lucky = new LuckyField(2000);
        lucky.stepOn(player1);

        assertEquals(12000, player1.getBalance(), "Lucky field should add 2000 to balance");
        assertFalse(player1.getBankrupt());
    }

    // ==========================================
    // 2. ServiceField Tests
    // ==========================================

    @Test
    public void testServiceFieldStandardFee() {
        ServiceField service = new ServiceField(1500);
        service.stepOn(player1);

        assertEquals(8500, player1.getBalance(), "Service field should deduct 1500 from balance");
        assertFalse(player1.getBankrupt());
    }

    @Test
    public void testServiceFieldExactZeroBalanceIsNotBankrupt() {
        player1.setBalance(3000);
        ServiceField service = new ServiceField(3000);
        service.stepOn(player1);

        assertEquals(0, player1.getBalance(), "Balance should become exactly 0");
        assertFalse(player1.getBankrupt(), "Balance of 0 is still solvent");
    }

    @Test
    public void testServiceFieldCausesBankruptcy() {
        player1.setBalance(1000);
        ServiceField service = new ServiceField(1500);
        service.stepOn(player1);

        assertTrue(player1.getBankrupt(), "Balance dropping below 0 triggers bankruptcy");
    }

    // ==========================================
    // 3. PropertyField Tests
    // ==========================================

    @Test
    public void testPropertyPurchaseAndHouseBuilding() {
        PropertyField property = new PropertyField();

        // Step 1: Buy unowned land (1000)
        property.stepOn(player1);
        assertEquals(9000, player1.getBalance());
        assertEquals(player1, property.getOwner(), "Player1 becomes the owner");
        assertFalse(property.hasHouse());
        assertEquals(1, player1.getFields().size());

        // Step 2: Land on own property -> build house (4000)
        property.stepOn(player1);
        assertEquals(5000, player1.getBalance());
        assertTrue(property.hasHouse(), "House should be built");
    }

    @Test
    public void testPropertyRentCollectionWithAndWithoutHouse() {
        PropertyField property = new PropertyField();

        // Player1 buys property
        property.stepOn(player1); // Player1 balance = 9000

        // Player2 lands on unbuilt property -> pays 500 rent
        property.stepOn(player2);
        assertEquals(9500, player2.getBalance(), "Player2 pays 500 rent");
        assertEquals(9500, player1.getBalance(), "Owner receives 500 rent");

        // Player1 builds house (costs 4000)
        property.stepOn(player1); // Player1 balance = 5500

        // Player2 lands on property with house -> pays 2000 rent
        property.stepOn(player2);
        assertEquals(7500, player2.getBalance(), "Player2 pays 2000 rent");
        assertEquals(7500, player1.getBalance(), "Owner receives 2000 rent");
    }

    @Test
    public void testPropertyRespectsCarefulPlayerBudget() {
        PropertyField property = new PropertyField();

        // Step 1: Careful lands on unowned property and buys it (10,000 -> 9,000)
        property.stepOn(carefulPlayer);
        assertEquals(carefulPlayer, property.getOwner());
        assertEquals(9000, carefulPlayer.getBalance());

        // Step 2: Set balance to 6,000 (half is 3,000). House costs 4,000 > 3,000
        carefulPlayer.setBalance(6000);

        // Careful lands on own property again -> refuses house
        property.stepOn(carefulPlayer);
        assertFalse(property.hasHouse(), "CarefulPlayer refuses house when cost > 50% balance");
        assertEquals(6000, carefulPlayer.getBalance(), "Balance remains unchanged");
    }

    @Test
    public void testRentCausesBankruptcyAndResetsProperty() {
        PropertyField ownedProp = new PropertyField();
        PropertyField victimsProp = new PropertyField();

        // Victim (player2) steps on and buys victimsProp
        victimsProp.stepOn(player2);

        // Player1 buys and builds house on ownedProp
        ownedProp.stepOn(player1);
        ownedProp.stepOn(player1);

        // Set victim's balance to 500 (rent is 2000)
        player2.setBalance(500);
        ownedProp.stepOn(player2);

        assertTrue(player2.getBankrupt(), "Victim should go bankrupt");
        assertNull(victimsProp.getOwner(), "Victim's property owner must reset to null");
        assertFalse(victimsProp.hasHouse(), "Victim's property house must reset");
    }
}