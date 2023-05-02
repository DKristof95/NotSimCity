import notsimcity.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerTest {
    @Test
    void TestConstructor(){
        final Player player = new Player();
        assertNotNull(player);
    }
}
