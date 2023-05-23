import notsimcity.Job;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JobTest {
    @Test
    void TestConstructor(){
        final Job job = new Job(1,1,1,1,new ImageIcon("grass.png").getImage(),1);
        assertNotNull(job);
    }
}
