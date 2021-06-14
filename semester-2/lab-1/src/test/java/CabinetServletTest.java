import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import service.TicketsService;
import servlets.CabinetServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TicketsService.class})
public class CabinetServletTest {

    private CabinetServlet servlet;
    private TicketsService ticketsService;
    private HttpServletResponse response;
    private HttpServletRequest request;


    @Before
    public void init() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        ticketsService = PowerMockito.mock(TicketsService.class);
        servlet = new CabinetServlet();
    }

    @Test
    public void whenCallDoGetThenReturnJson() throws Exception {
        when(request.getParameter("user")).thenReturn("1");
        PowerMockito.verifyNew(TicketsService.class).withNoArguments();
        PowerMockito.when(ticketsService.findAllTicketsByUser("user"))
                .thenReturn(new ArrayList<>());
        servlet.doGet(request, response);
        verify(request, times(1)).getParameter("user");
    }
}
