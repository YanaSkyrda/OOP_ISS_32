import model.Cat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.io.ObjectOutputStream;


@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    @Mock
    ClientSide clientSide;

    @Mock
    ObjectOutputStream oos;

    @Captor
    ArgumentCaptor<Cat> catCaptor;

    @Test
    public void checkWriteObject_RIGHTCAT() throws IOException {
        clientSide.run(new Cat("Tom", "Black", 10));
        Mockito.verify(clientSide).run(catCaptor.capture());
        Cat catCaptorValue = catCaptor.getValue();
        Cat expectedCat = new Cat("Tom", "Black", 10);
        if(!catCaptorValue.equals(expectedCat))
            Assert.fail();
    }

}
