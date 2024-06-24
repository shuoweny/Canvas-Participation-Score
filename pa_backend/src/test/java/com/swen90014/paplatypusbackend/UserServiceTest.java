package com.swen90014.paplatypusbackend;
import com.swen90014.paplatypusbackend.dao.UserDao;
import com.swen90014.paplatypusbackend.domain.User;
import com.swen90014.paplatypusbackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByName() {
        String userName = "John Doe";

        List<User> mockUsers = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setName(userName);
        user.setToken("token123");
        mockUsers.add(user);

        when(userDao.getUserByName(userName)).thenReturn(mockUsers);

        List<User> result = userService.getUserByName(userName);

        assertEquals(mockUsers, result);
    }

    @Test
    void testGetUserByToken() {
        String token = "token123";

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");
        mockUser.setToken(token);

        when(userDao.getUserByToken(token)).thenReturn(mockUser);

        User result = userService.getUserByToken(token);

        assertEquals(mockUser, result);
    }

    @Test
    void testInsertOrUpdateBatch() {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setToken("token123");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setToken("token456");

        users.add(user1);
        users.add(user2);

        doNothing().when(userDao).insertOrUpdateBatch(users);

        userService.insertOrUpdateBatch(users);

        verify(userDao, times(1)).insertOrUpdateBatch(users);
    }

    @Test
    void testInsertOrUpdate() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setToken("token123");

        doNothing().when(userDao).insertOrUpdate(user);

        userService.insertOrUpdate(user);

        verify(userDao, times(1)).insertOrUpdate(user);
    }
}