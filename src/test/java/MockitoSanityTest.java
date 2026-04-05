

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class MockitoSanityTest {

    @Test
    void pruebaMockBasica() {
        Object objeto = mock(Object.class);
        assertNotNull(objeto);
    }
}