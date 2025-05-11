import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testequalChars() {
        OffByN offby5 = new OffByN(5);
        assertTrue(offby5.equalChars('a', 'f'));
        assertFalse(offby5.equalChars('a', 'h'));
    }
}
