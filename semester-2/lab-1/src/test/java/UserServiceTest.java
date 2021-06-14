import dao.FactoryDao;
import dao.UserDao;
import model.user.User;
import model.user.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import service.UserService;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private final FactoryDao factoryDao = mock(FactoryDao.class);
    private final UserDao userDao = mock(UserDao.class);
    private User user;

    @Before
    public void init() {
        userService = new UserService();
        userService.setFactoryDao(factoryDao);

        user = new UserBuilder()
                .setId(1L)
                .setName("name")
                .setPassword("pass")
                .setEmail("email@gmail.com")
                .build();
    }

    @Test
    public void shouldLoginUser() throws Exception {
        when(factoryDao.createUserDao()).thenReturn(userDao);
        when(userDao.findByUserName("name")).thenReturn(Optional.of(user));
        userService.loginUser("name", "1");
        verify(factoryDao, times(1)).createUserDao();
        verify(userDao, times(1)).findByUserName("name");
    }
}



