import notsimcity.Job;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JobTest {
    @Test
    void TestConstructor(){
        final Job job = new Job(0,0,0,0,new ImageIcon("grass.png").getImage(),0);
        assertNotNull(job);
    }
}
