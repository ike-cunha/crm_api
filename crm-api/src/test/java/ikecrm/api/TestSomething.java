package ikecrm.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestSomething {
    static Integer heavy;

    @BeforeAll
    public static void beforeAll(){
        heavy = 1;
    }

    @Test
    public void testCaseA(){
         var actual = 1+heavy;
         var expected = 2;
         assertEquals(expected, actual);
     }
}
