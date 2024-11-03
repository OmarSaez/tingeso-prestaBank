package edu.mtisw.prestabank_backend.serviceTest;;

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

    @Test
    void testSaveLoan() {
        // Arrange
        LoanEntity loan = new LoanEntity();
        loan.setIdUser(1L);
        loan.setType(1);
        loan.setYearInterest(5.0);
        loan.setMaxDuration(15);
        loan.setIncome(50000);
        loan.setVeteran(3);
        loan.setTotaldebt(30000);
        loan.setLoanAmount(100000);

        // Configura el comportamiento del repositorio para devolver el préstamo simulado
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
        verify(loanRepository, times(1)).save(loan); // Verifica que se llame al método save una vez
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

    @Test
    void testUpdateLoan() {
        // Arrange
        LoanEntity loan = new LoanEntity();
        loan.setLoanAmount(100000.0);
        loan.setYearInterest(5.0);
        loan.setMaxDuration(15);
        loan.setId(1L); // Asegúrate de establecer un ID si es necesario

        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoan(loan);

        // Assert
        assertNotNull(result, "El préstamo actualizado no debe ser nulo");
        assertEquals(loan.getLoanAmount(), result.getLoanAmount(), "El monto del préstamo debe coincidir");
        assertEquals(loan.getYearInterest(), result.getYearInterest(), "La tasa de interés debe coincidir");
        assertEquals(loan.getMaxDuration(), result.getMaxDuration(), "La duración máxima debe coincidir");
        verify(loanRepository, times(1)).save(loan);
    }


    @Test
    void testUpdateLoanWithExecutive() {
        // Arrange
        LoanEntity loan = new LoanEntity();
        loan.setLoanAmount(100000.0);
        loan.setYearInterest(5.0);
        loan.setMaxDuration(15);
        loan.setId(1L); // Establecer un ID si es necesario

        ArrayList<Integer> balanceLast12 = new ArrayList<>();
        balanceLast12.add(1000);
        balanceLast12.add(2000);
        // Agregar más balances de prueba según sea necesario...

        // Simular el comportamiento del repositorio
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        // Act
        LoanEntity result = loanService.updateLoanWithExcutive(loan, 2, balanceLast12);

        // Assert
        assertNotNull(result, "El préstamo actualizado no debe ser nulo");
        assertEquals(loan.getLoanAmount(), result.getLoanAmount(), "El monto del préstamo debe coincidir");
        assertEquals(loan.getYearInterest(), result.getYearInterest(), "La tasa de interés debe coincidir");
        assertEquals(loan.getMaxDuration(), result.getMaxDuration(), "La duración máxima debe coincidir");
        // Si tu método hace algo específico con el balance, también puedes verificar eso aquí

        verify(loanRepository, times(1)).save(loan);
    }


    // Puedes agregar más pruebas unitarias para otros métodos en LoanService.
}

