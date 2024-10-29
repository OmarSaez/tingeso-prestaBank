package edu.mtisw.prestabank_backend.Controller;

import edu.mtisw.prestabank_backend.Entity.LoanEntity;
import edu.mtisw.prestabank_backend.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin("*")
public class LoanController {
    @Autowired
    LoanService loanService;

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class); //MEnsaje por cosnla

    //URL rescatar todas los prestamos
    @GetMapping("/")
    public ResponseEntity<List<LoanEntity>> listLoan(){
        List<LoanEntity> loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }

    //URL rescatar un loan por su ID
    @GetMapping("/{id}")
    public ResponseEntity<LoanEntity> getLoan(@PathVariable Long id){
        LoanEntity loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    //URl rescatar un loan por el ID del USER
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<LoanEntity>> getLoansByUser(@PathVariable Long idUser){
        List<LoanEntity> loans = loanService.getLoansByIdUser(idUser);
        return ResponseEntity.ok(loans);
    }

    //URL guardar un prestamo
    @PostMapping("/")
    public ResponseEntity<LoanEntity> saveLoan(@RequestBody LoanEntity loan){
        LoanEntity newLoan = loanService.saveLoan(loan);
        return ResponseEntity.ok(newLoan);
    }

    //URL actualizar un prestamo
    @PutMapping("/")
    public ResponseEntity<LoanEntity> updateLoan(@RequestBody LoanEntity changeLoan) {
        LoanEntity updateLoan = loanService.updateLoan(changeLoan);
        return ResponseEntity.ok(updateLoan);
    }

    //URL borrar un prestamo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLoanById(@PathVariable Long id) throws Exception{
        var isDeleted = loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    //URL buscar todos los prestamos por rechazo o apruebo
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanEntity>> listStatusLoans(@PathVariable int status){
        List<LoanEntity> loans = loanService.getLoanByStatus(status);
        return ResponseEntity.ok(loans);
    }


    //URL buscar todos los prestamos por tipo
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LoanEntity>> listTypeLoans(@PathVariable int type){
        List<LoanEntity> loans = loanService.getLoanByType(type);
        return ResponseEntity.ok(loans);
    }

    //URL update para un Ejecutivo
    @PutMapping("/executive")
    public ResponseEntity<LoanEntity> updateLoanWithExecutive(@RequestBody LoanEntity changeLoan, @RequestParam int acountYears, @RequestParam ArrayList<Integer> balanceLast12){
        LoanEntity updateLoan = loanService.updateLoanWithExcutive(changeLoan, acountYears, balanceLast12);
        return ResponseEntity.ok(updateLoan);
    }

    @PostMapping("/simulate")
    public ResponseEntity<Double> simulateLoan(@RequestBody LoanSimulationRequest request) {
        double monthlyPayment = loanService.simulateLoan(
                request.getLoanAmount(),
                request.getYearInterestRate(),
                request.getYearPayments()
        );
        logger.info("---Valor devuelto al front: {}", monthlyPayment);
        return ResponseEntity.ok(monthlyPayment);
    }
}
