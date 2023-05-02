import notsimcity.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
    @Test
    void TestConstructor(){
        final Game game = new Game();
        assertNotNull(game);
    }
}
