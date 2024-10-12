package edu.mtisw.prestabank_backend.controllerTest;

import edu.mtisw.prestabank_backend.Controller.UserController;
import edu.mtisw.prestabank_backend.Entity.UserEntity;
import edu.mtisw.prestabank_backend.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    //TESTs que prueba el GET para la lista de usuarios
    @Test
    public void listUser_ShouldReturnUsers() throws Exception {
        // Se crean dos instancias de UserEntity para simular los usuarios.
        // 'null' se utiliza para el id porque se genera automáticamente en la base de datos.
        UserEntity user1 = new UserEntity(null,"pedro@gmail.com", "123", "Pedro", "Pérez", "19876543-2", 15, 6, 1995);
        UserEntity user2 = new UserEntity(null,"ana@gmail.com", "123", "Ana", "Gómez", "12345678-9", 23, 12, 1988);

        // Se crea una lista de usuarios para simular la respuesta del servicio.
        List<UserEntity> userList = new ArrayList<>(Arrays.asList(user1, user2));

        // Aquí se indica que cuando se llame al método getUsers() del userService,
        // debe devolver la lista de usuarios creada anteriormente.
        given(userService.getUsers()).willReturn((ArrayList<UserEntity>) userList);

        // Se realiza una solicitud GET al endpoint "/api/user/" para obtener la lista de usuarios.
        // Se utiliza 'perform' para simular la solicitud HTTP y se le pasa el método de la solicitud y la URL.
        mockMvc.perform(get("/api/user/"))
                // Se espera un código de estado HTTP 200 OK como respuesta, indicando que la solicitud fue exitosa.
                .andExpect(status().isOk())
                // Se espera que el tipo de contenido de la respuesta sea JSON.
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Se comprueba que el nombre del primer usuario en la lista devuelta es "Pedro".
                // 'jsonPath' se usa para acceder a los elementos en el JSON de la respuesta.
                .andExpect(jsonPath("$[0].name", is("Pedro")))
                // Se comprueba que el nombre del segundo usuario en la lista devuelta es "Ana".
                .andExpect(jsonPath("$[1].name", is("Ana")));
    }

    @Test
    public void listUser_ShouldReturnUser2() throws Exception{
        UserEntity user1 = new UserEntity(null,"pedro@gmail.com", "123", "Pedro", "Pérez", "19876543-2", 15, 6, 1995);
        UserEntity user2 = new UserEntity(null,"pedro@gmail.com", "123", "Pedro", "Pérez", "12345678-9", 23, 12, 1988);
        UserEntity user3 = new UserEntity(null,"ana@gmail.com", "123", "Ana", "Gómez", "12345678-9", 23, 12, 1988);

        List<UserEntity> userList = new ArrayList<>(Arrays.asList(user1, user2, user3));

        given(userService.getUsers()).willReturn((ArrayList<UserEntity>) userList);

        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Pedro")))
                .andExpect(jsonPath("$[1].name", is("Pedro")))
                .andExpect(jsonPath("$[2].name", is("Ana")))
                .andExpect(jsonPath("$[0].rut", is("19876543-2")))
                .andExpect(jsonPath("$[1].rut", is("12345678-9")));
    }

    //Test para probar el buscar un user por su ID
    @Test
    public void getUserById_ShouldReturnUser() throws Exception{
        UserEntity user = new UserEntity(55L,"lucas@gmail.com", "123", "Lucas", "Martínez", "98765432-1", 5, 11, 1990);

        given(userService.getUserById(55L)).willReturn(user);

        mockMvc.perform(get("/api/user/{id}", 55L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Lucas")))
                .andExpect(jsonPath( "$.lastName", is("Martínez")));
    }

    //Test para guardar un usuario
    @Test
    public void saveUser_ShouldRetunrSavedUser() throws Exception{
        UserEntity savedUser = new UserEntity(15L, "sofia@gmail.com", "123", "Sofía", "López", "12345679-0", 20, 3, 1998);

        given(userService.saveUser(Mockito.any(UserEntity.class))).willReturn(savedUser);

        String userJSON = """
        {   
                "email": "sofia@gmail.com"
                "pasword": "123"
                "name": "Sofía",
                "lastName": "López",
                "rut": "12345679-0",
                "day": 20,
                "month": 3,
                "year": 1998
        }
        """;

        mockMvc.perform(post("/api/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut", is("12345679-0")));

    }

    //Test para probar delete de un usuario en base a su ID
    @Test
    public void deleteUserById_ShouldReturn204() throws Exception{
        when(userService.deleteUser(80L)).thenReturn(true);

        mockMvc.perform(delete("/api/user/{id}", 80L))
                .andExpect(status().isNoContent());
    }

}
