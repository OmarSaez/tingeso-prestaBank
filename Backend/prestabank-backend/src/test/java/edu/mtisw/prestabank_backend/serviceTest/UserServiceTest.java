package edu.mtisw.prestabank_backend.serviceTest;

import edu.mtisw.prestabank_backend.Entity.UserEntity;
import edu.mtisw.prestabank_backend.Repository.UserRepository;
import edu.mtisw.prestabank_backend.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        when(userRepository.findAll()).thenReturn(users);

        ArrayList<UserEntity> result = userService.getUsers();
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setRut("12345678-9");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByRut(user.getRut())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.saveUser(user);
        assertNotNull(result);
        verify(userRepository, times(1)).findUserByEmail(user.getEmail());
        verify(userRepository, times(1)).findByRut(user.getRut());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSaveUserAlreadyExists() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setRut("12345678-9");

        // Simulamos que el usuario ya existe
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findByRut(user.getRut())).thenReturn(user);

        UserEntity result = userService.saveUser(user);
        // Debe retornar null si el usuario ya existe
        assertNull(result);
        verify(userRepository, times(1)).findUserByEmail(user.getEmail());
        verify(userRepository, times(1)).findByRut(user.getRut());
        verify(userRepository, never()).save(user);
    }



    @Test
    public void testGetUserById() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);
        assertNotNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserByRut() {
        UserEntity user = new UserEntity();
        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        UserEntity result = userService.getUserByRut("12345678-9");
        assertNotNull(result);
        verify(userRepository, times(1)).findByRut("12345678-9");
    }

    @Test
    public void testGetUserByEmail() {
        UserEntity user = new UserEntity();
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);

        UserEntity result = userService.getUserByEmail("test@example.com");
        assertNotNull(result);
        verify(userRepository, times(1)).findUserByEmail("test@example.com");
    }

    @Test
    public void testUpdateUser() {
        UserEntity user = new UserEntity();
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.updateUser(user);
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUserException() throws Exception {
        Long userId = 1L;

        // Simulamos que el método deleteById lanza una excepción
        doThrow(new RuntimeException("Error eliminando usuario")).when(userRepository).deleteById(userId);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("Error eliminando usuario", exception.getMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }

}
