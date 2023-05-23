import notsimcity.MoneyLog;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoneyLogTest {
    @Test
    void TestConstructor(){
        final MoneyLog ml = new MoneyLog(1,1,"alma","alma");
        assertNotNull(ml);
    }
}
