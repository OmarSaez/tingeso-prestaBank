package edu.mtisw.prestabank_backend.serviceTest;

import edu.mtisw.prestabank_backend.Entity.LoanEntity;
import edu.mtisw.prestabank_backend.Entity.UserEntity;
import edu.mtisw.prestabank_backend.Repository.LoanRepository;
import edu.mtisw.prestabank_backend.Repository.UserRepository;
import edu.mtisw.prestabank_backend.Service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLoans() {
        // Arrange
        LoanEntity loan = new LoanEntity();
        ArrayList<LoanEntity> loans = new ArrayList<>();
        loans.add(loan);
        when(loanRepository.findAll()).thenReturn(loans);

        // Act
        ArrayList<LoanEntity> result = loanService.getLoans();

        // Assert
        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void testGetLoansByIdUser() {
        // Arrange
        Long userId = 1L;
        LoanEntity loan = new LoanEntity();
        ArrayList<LoanEntity> loans = new ArrayList<>();
        loans.add(loan);
        when(loanRepository.findByIdUser(userId)).thenReturn(loans);

        // Act
        ArrayList<LoanEntity> result = loanService.getLoansByIdUser(userId);

        // Assert
        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findByIdUser(userId);
    }

    //Test donde se cumplen todos los requisitos que se calculen automaticamente, es decir r1, r3, r4, r6, y se usa save
    @Test
    void testSaveLoan() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1990);
        user.setPassword("securePassword");

        // Suponiendo que tienes un repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(25);
        loan.setIncome(5000000);
        loan.setVeteran(3);
        loan.setTotaldebt(240000);
        loan.setLoanAmount(10000);

        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.saveLoan(loan);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame el metodo save una vez

        // Verifica que se haya llamado el metodo de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió
        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(1, result.getEvalue().get(0), "El requisito R1 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(1, result.getEvalue().get(2), "El requisito R3 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(3), "El requisito R4 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(5), "El requisito R6 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }


    //Test donde no se cumple ningun requisito de los cuales se calculen automaticamente, es decir r1, r3, r4, r6 y se usa save
    @Test
    void testSaveLoan1() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1949);
        user.setPassword("securePassword");

        // repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(5);
        loan.setIncome(100000);
        loan.setVeteran(1);
        loan.setTotaldebt(60000);
        loan.setLoanAmount(2500000);

        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.saveLoan(loan);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame a la funcion save una vez

        // Verifica que se haya llamado la funcion de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió

        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(0, result.getEvalue().get(0), "El requisito R1 debería NO haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(0, result.getEvalue().get(2), "El requisito R3 debería NO haberse cumplido.");
        assertEquals(0, result.getEvalue().get(3), "El requisito R4 debería NO haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(0, result.getEvalue().get(5), "El requisito R6 debería NO haberse cumplido.");
        assertEquals(2, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }


    @Test
    void testGetLoanById() {
        // Arrange
        Long loanId = 1L;
        LoanEntity loan = new LoanEntity();
        loan.setId(loanId); // Asegúrate de establecer el ID del préstamo
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // Act
        LoanEntity result = loanService.getLoanById(loanId);

        // Assert
        assertNotNull(result, "El préstamo recuperado no debe ser nulo");
        assertEquals(loanId, result.getId(), "El ID del préstamo recuperado debe coincidir con el ID solicitado");
        verify(loanRepository, times(1)).findById(loanId);
    }


    @Test
    void testDeleteLoan() throws Exception {
        // Arrange
        Long loanId = 1L;
        when(loanRepository.existsById(loanId)).thenReturn(true);

        // Act
        boolean result = loanService.deleteLoan(loanId);

        // Assert
        assertTrue(result);
        verify(loanRepository, times(1)).deleteById(loanId);
    }

    //Test donde se cumplen todos los requisitos que se calculen automaticamente, es decir r1, r3, r4, r6, y se use update
    @Test
    void testUpdateLoan() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1990);
        user.setPassword("securePassword");

        // Suponiendo que tienes un repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(25);
        loan.setIncome(5000000);
        loan.setVeteran(3);
        loan.setTotaldebt(240000);
        loan.setLoanAmount(10000);
        loan.setEvalue(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 1, 1, 2)));

        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoan(loan);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame el metodo save una vez

        // Verifica que se haya llamado el metodo de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió
        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(1, result.getEvalue().get(0), "El requisito R1 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(1, result.getEvalue().get(2), "El requisito R3 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(3), "El requisito R4 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(5), "El requisito R6 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }

    //Test donde no se cumple ningun requisito de los cuales se calculen automaticamente, es decir r1, r3, r4, r6 y se usa update
    @Test
    void testUpdateLoan1() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1949);
        user.setPassword("securePassword");

        // repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(5);
        loan.setIncome(100000);
        loan.setVeteran(1);
        loan.setTotaldebt(60000);
        loan.setLoanAmount(2500000);
        loan.setEvalue(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 1, 1, 2)));

        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoan(loan);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame a la funcion save una vez

        // Verifica que se haya llamado la funcion de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió

        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(0, result.getEvalue().get(0), "El requisito R1 debería NO haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(0, result.getEvalue().get(2), "El requisito R3 debería NO haberse cumplido.");
        assertEquals(0, result.getEvalue().get(3), "El requisito R4 debería NO haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(0, result.getEvalue().get(5), "El requisito R6 debería NO haberse cumplido.");
        assertEquals(2, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }

    //Test donde se cumplen todos los requisitos que se calculen automaticamente, es decir r1, r2, r3, r4, r6, r7 y se use update
    //Se espera que el requisito 7 retorne 3 indicando que falta otra revision a los puntos de ahorro
    @Test
    void testUpdateLoanWithExcutive() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1990);
        user.setPassword("securePassword");

        // Suponiendo que tienes un repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(25);
        loan.setIncome(1500000);
        loan.setVeteran(3);
        loan.setTotaldebt(240000);
        loan.setLoanAmount(10000);
        loan.setEvalue(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 1, 1, 2)));

        ArrayList<Integer> balance12 = new ArrayList<>(Arrays.asList(
                10000,  // Mes 1
                12000,  // Mes 2 - ingreso
                15000,  // Mes 3 - ingreso
                14500,  // Mes 4 - retiro
                16000,  // Mes 5 - ingreso
                18000,  // Mes 6 - ingreso
                17500,  // Mes 7 - retiro
                20000,  // Mes 8 - ingreso
                22000,  // Mes 9 - ingreso
                21500,  // Mes 10 - retiro
                24000,  // Mes 11 - ingreso
                26000   // Mes 12 - ingreso
        ));


        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoanWithExcutive(loan, 12, balance12);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame el metodo save una vez

        // Verifica que se haya llamado el metodo de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió
        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(1, result.getEvalue().get(0), "El requisito R1 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(1, result.getEvalue().get(2), "El requisito R3 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(3), "El requisito R4 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(5), "El requisito R6 debería haberse cumplido.");
        assertEquals(3, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }


    //Test donde se cumplen todos los requisitos que se calculen automaticamente, es decir r1, r2, r3, r4, r6, r7 y se use update
    //Se espera que el requisito 7 retorne 1 indicando que se aprobo
    @Test
    void testUpdateLoanWithExcutive1() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1990);
        user.setPassword("securePassword");

        // Suponiendo que tienes un repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(25);
        loan.setIncome(1500000);
        loan.setVeteran(3);
        loan.setTotaldebt(240000);
        loan.setLoanAmount(10000);
        loan.setEvalue(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 1, 1, 2)));

        ArrayList<Integer> balance12 = new ArrayList<>(Arrays.asList(
                1000000,  // Mes 1
                1200000,  // Mes 2 - ingreso
                1500000,  // Mes 3 - ingreso
                1450000,  // Mes 4 - retiro
                1600000,  // Mes 5 - ingreso
                1800000,  // Mes 6 - ingreso
                1750000,  // Mes 7 - retiro
                2000000,  // Mes 8 - ingreso
                2200000,  // Mes 9 - ingreso
                2150000,  // Mes 10 - retiro
                2400000,  // Mes 11 - ingreso
                2600000   // Mes 12 - ingreso
        ));


        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoanWithExcutive(loan, 12, balance12);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame el metodo save una vez

        // Verifica que se haya llamado el metodo de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió
        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(1, result.getEvalue().get(0), "El requisito R1 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(1, result.getEvalue().get(2), "El requisito R3 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(3), "El requisito R4 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(5), "El requisito R6 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }

    //Test donde se cumplen todos los requisitos que se calculen automaticamente, es decir r1, r2, r3, r4, r6, r7 y se use update
    //Se espera que el requisito 7 retorne 0 indicando que se rechazo
    @Test
    void testUpdateLoanWithExcutive2() {
        // Arrange
        // Crear y configurar el usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRut("12345678-9");
        user.setBirthDay(15);
        user.setBirthMonth(6);
        user.setBirthYear(1990);
        user.setPassword("securePassword");

        // Suponiendo que tienes un repositorio de usuarios (userRepository), simula el guardado del usuario y devuelve el usuario de prueba
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simulación para obtener el usuario por su ID

        // Crear y configurar el préstamo (Loan) de prueba
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L); // Configura el ID del usuario para que coincida con el usuario de prueba
        loan.setType(1);
        loan.setYearInterest(4.0);
        loan.setMaxDuration(25);
        loan.setIncome(1500000);
        loan.setVeteran(1);
        loan.setTotaldebt(240000);
        loan.setLoanAmount(1000000);
        loan.setEvalue(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 1, 1, 2)));

        ArrayList<Integer> balance12 = new ArrayList<>(Arrays.asList(
                200000,  // Mes 1
                1200,  // Mes 2 - ingreso
                6500,  // Mes 3 - ingreso
                14500,  // Mes 4 - retiro
                16000,  // Mes 5 - ingreso
                18000,  // Mes 6 - ingreso
                1750,  // Mes 7 - retiro
                2000,  // Mes 8 - ingreso
                2200,  // Mes 9 - ingreso
                2150,  // Mes 10 - retiro
                1400,  // Mes 11 - ingreso
                2600   // Mes 12 - ingreso
        ));


        // Configura el comportamiento del repositorio de préstamos para devolver el préstamo simulado
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoanWithExcutive(loan, 1, balance12);

        // Assert
        assertNotNull(result); // Asegúrate de que el resultado no sea nulo
        assertEquals(loan.getIdUser(), result.getIdUser()); // Verifica que los valores sean correctos
        assertEquals(loan.getType(), result.getType());
        assertEquals(loan.getYearInterest(), result.getYearInterest());
        assertEquals(loan.getMaxDuration(), result.getMaxDuration());
        assertEquals(loan.getIncome(), result.getIncome());
        assertEquals(loan.getVeteran(), result.getVeteran());
        assertEquals(loan.getTotaldebt(), result.getTotaldebt());
        assertEquals(loan.getLoanAmount(), result.getLoanAmount());
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame el metodo save una vez

        // Verifica que se haya llamado el metodo de encontrar usuario por ID en el repositorio de usuarios
        verify(userRepository, times(1)).findById(1L);

        // Aserción para verificar que el primer requisito (R1) se cumplió
        assertNotNull(result.getEvalue()); // Asegúrate de que la lista evalue no sea nula
        assertTrue(result.getEvalue().size() > 0, "La lista evalue debería tener al menos un elemento.");
        assertEquals(1, result.getEvalue().get(0), "El requisito R1 debería haberse cumplido.");
        assertEquals(2, result.getEvalue().get(1), "El requisito R2 debería haberse pendiente.");
        assertEquals(0, result.getEvalue().get(2), "El requisito R3 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(3), "El requisito R4 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(4), "El requisito R5 debería haberse cumplido.");
        assertEquals(1, result.getEvalue().get(5), "El requisito R6 debería haberse cumplido.");
        assertEquals(0, result.getEvalue().get(6), "El requisito R7 debería haberse pendiente.");
    }

    //Se verifica que la simulacion de prestamo para una cuota mensual calcule bien
    @Test
    public void testSimulateLoan() {
        double loanAmount = 100000;
        double yearInterestRate = 5.0;
        int yearPayments = 15;

        double result = loanService.simulateLoan(loanAmount, yearInterestRate, yearPayments);

        // Cálculo manual esperado para verificar el resultado
        double monthlyInterestRate = yearInterestRate / 12 / 100;
        int totalPayments = yearPayments * 12;
        double upPart = Math.pow(1 + monthlyInterestRate, totalPayments) * monthlyInterestRate;
        double downPart = Math.pow(1 + monthlyInterestRate, totalPayments) - 1;
        double expectedMonthlyPayment = (upPart / downPart) * loanAmount;

        assertEquals(expectedMonthlyPayment, result, 0.01);

    }

}

