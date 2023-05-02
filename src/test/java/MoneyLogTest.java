import notsimcity.MoneyLog;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoneyLogTest {
    @Test
    void TestConstructor(){
        final MoneyLog ml = new MoneyLog(0,0,"",0);
        assertNotNull(ml);
    }
}
