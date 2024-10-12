package edu.mtisw.prestabank_backend.Controller;

import edu.mtisw.prestabank_backend.Entity.LoanEntity;
import edu.mtisw.prestabank_backend.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin("*")
public class LoanController {
    @Autowired
    LoanService loanService;

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
        changeLoan.calculateMonthlyPayment();
        return ResponseEntity.ok(updateLoan);
    }

    //URL borrar un prestamo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLoanById(@PathVariable Long id) throws Exception{
        var isDeleted = loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }



}
