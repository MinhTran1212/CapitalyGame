

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mycompany.mavenproject1.Player;
import com.mycompany.mavenproject1.GreedyPlayer;
import com.mycompany.mavenproject1.CarefulPlayer;
import com.mycompany.mavenproject1.TacticalPlayer;
import com.mycompany.mavenproject1.PropertyField;

public class PlayerTest {

    private GreedyPlayer greedy;
    private CarefulPlayer careful;
    private TacticalPlayer tactical;

    @BeforeEach
    public void setUp() {
        greedy = new GreedyPlayer("GreedyGuy");     // Initial balance: 10,000
        careful = new CarefulPlayer("CarefulGuy");   // Initial balance: 10,000
        tactical = new TacticalPlayer("TacticalGuy"); // Initial balance: 10,000
    }

    // --- Movement Tests ---

    @Test
    public void testPlayerMovementAndCircularWrap() {
        int boardSize = 6;
        assertEquals(0, greedy.getPosition(), "Initial position must be 0");

        greedy.move(4, boardSize);
        assertEquals(4, greedy.getPosition());

        // 4 + 3 = 7 -> 7 % 6 = 1
        greedy.move(3, boardSize);
        assertEquals(1, greedy.getPosition(), "Must wrap around circularly");

        // 1 + 5 = 6 -> 6 % 6 = 0
        greedy.move(5, boardSize);
        assertEquals(0, greedy.getPosition(), "Exact lap wraps back to 0");
    }

    // --- GreedyPlayer Strategy Tests ---

    @Test
    public void testGreedyPlayerStrategy() {
        assertTrue(greedy.shouldBuy(1000), "Greedy buys when balance > cost");
        assertTrue(greedy.shouldBuy(10000), "Greedy buys when balance == cost");
        assertFalse(greedy.shouldBuy(10001), "Greedy refuses when cost > balance");
    }

    // --- CarefulPlayer Strategy Tests ---

    @Test
    public void testCarefulPlayerStrategy() {
        // Balance = 10,000 -> 50% is 5,000
        assertTrue(careful.shouldBuy(1000), "Careful buys when cost <= 50% balance");
        assertTrue(careful.shouldBuy(5000), "Careful buys when cost == 50% balance");
        assertFalse(careful.shouldBuy(5001), "Careful refuses when cost > 50% balance");
    }

    // --- TacticalPlayer Strategy Tests ---

    @Test
    public void testTacticalPlayerStrategyAlternation() {
        // Turn 1: Active turn -> buys
        assertTrue(tactical.shouldBuy(1000), "Turn 1 is an active buying turn");
        // Turn 2: Skip turn -> refuses
        assertFalse(tactical.shouldBuy(1000), "Turn 2 must be skipped");
        // Turn 3: Active turn -> buys
        assertTrue(tactical.shouldBuy(1000), "Turn 3 is an active buying turn");
        // Turn 4: Skip turn -> refuses
        assertFalse(tactical.shouldBuy(1000), "Turn 4 must be skipped");
    }

    @Test
    public void testTacticalPlayerInsufficientBalanceTogglesFlag() {
        tactical.setBalance(500);

        // Turn 1: Active turn, but cannot afford 1000 -> false
        assertFalse(tactical.shouldBuy(1000), "Cannot afford cost");
        // Turn 2: Skip turn -> false
        assertFalse(tactical.shouldBuy(100), "Turn 2 is a skip turn");
        // Turn 3: Active turn, can afford 100 -> true
        assertTrue(tactical.shouldBuy(100), "Turn 3 is an active turn with sufficient balance");
    }

    // --- Bankruptcy and Field Clearing ---

    @Test
    public void testBankruptcyClearsFieldsAndResetsOwnership() {
        PropertyField prop = new PropertyField();
        prop.stepOn(greedy); // Buys property, sets owner, and adds to player's fields

        assertEquals(1, greedy.getFields().size());
        assertEquals(greedy, prop.getOwner());

        // Trigger bankruptcy
        greedy.setBalance(-100);
        greedy.bankrupt();

        assertTrue(greedy.getBankrupt(), "Player should be marked bankrupt");
        assertEquals(0, greedy.getFields().size(), "Owned fields list must be emptied");
        assertNull(prop.getOwner(), "Field owner must be reset to null");
        assertFalse(prop.hasHouse(), "Field house flag must be reset");
    }
}