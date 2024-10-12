package edu.mtisw.prestabank_backend.repositoryTest;

import edu.mtisw.prestabank_backend.Entity.UserEntity;
import edu.mtisw.prestabank_backend.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    //------------TESTs buscar un usuario por su rut, son 5 pruebas en total de este tipo--------------
    @Test
    public void whenFindByRut_thenReturnUser1(){
        // given
        UserEntity user = new UserEntity(null,"omar@gmail.com", "123", "Omar", "Saez", "21091258-4", 18, 8, 2002);
        entityManager.persistAndFlush(user);

        //when
        UserEntity found = userRepository.findByRut(user.getRut());

        //then
        assertThat(found.getRut()).isEqualTo(user.getRut());
    }

    @Test
    public void whenFindByRut_thenReturnUser2(){
        // given
        UserEntity user = new UserEntity(null,"laura@gmail.com", "123", "Laura", "Gómez", "18365479-1", 22, 5, 1995);
        entityManager.persistAndFlush(user);

        //when
        UserEntity found = userRepository.findByRut(user.getRut());

        //then
        assertThat(found.getRut()).isEqualTo(user.getRut());
    }

    @Test
    public void whenFindByRut_thenReturnUser3(){
        // given
        UserEntity user = new UserEntity(null,"carlos@gmail.com", "123", "Carlos", "Fernández", "16234567-3", 11, 12, 1980);
        entityManager.persistAndFlush(user);

        //when
        UserEntity found = userRepository.findByRut(user.getRut());

        //then
        assertThat(found.getRut()).isEqualTo(user.getRut());
    }

    @Test
    public void whenFindByRut_thenReturnUser4(){
        // given
        UserEntity user = new UserEntity(null,"ana@gmail.com", "123", "Ana", "Martínez", "20785643-6", 9, 3, 1998);
        entityManager.persistAndFlush(user);

        //when
        UserEntity found = userRepository.findByRut(user.getRut());

        //then
        assertThat(found.getRut()).isEqualTo(user.getRut());
    }

    @Test
    public void whenFindByRut_thenReturnUser5(){
        // given
        UserEntity user = new UserEntity(null,"mario@gmail.com", "123", "Mario", "Rojas", "19456783-2", 30, 6, 1975);
        entityManager.persistAndFlush(user);

        //when
        UserEntity found = userRepository.findByRut(user.getRut());

        //then
        assertThat(found.getRut()).isEqualTo(user.getRut());
    }




}
