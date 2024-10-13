package edu.mtisw.prestabank_backend.Service;

import edu.mtisw.prestabank_backend.Entity.LoanEntity;
import edu.mtisw.prestabank_backend.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    //rescatar todos los prestamos
    public ArrayList<LoanEntity> getLoans() { return (ArrayList<LoanEntity>) loanRepository.findAll();}

    //Rescata un loan por el ID del user
    public ArrayList<LoanEntity> getLoansByIdUser(Long idUser){return (ArrayList<LoanEntity>) loanRepository.findByIdUser(idUser);}

    //Guardar un prestamo
    public LoanEntity saveLoan(LoanEntity saveLoan){
        saveLoan.calculateMonthlyPayment();
        return loanRepository.save(saveLoan);
    }
    //Buscar un prestamo por su ID
    public LoanEntity getLoanById(Long id){ return loanRepository.findById(id).get();}
    //Borrar un prestamo
    public boolean deleteLoan(Long id) throws Exception {
        try {
            if (!loanRepository.existsById(id)) {
                throw new Exception("Loan not found");
            }
            loanRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception("Error deleting loan: " + e.getMessage());
        }
    }


    //Modificar un prestamo
    public LoanEntity updateLoan(LoanEntity changeLoan) {
        changeLoan.calculateMonthlyPayment();
        return loanRepository.save(changeLoan);}

    public double Calculo(Long id) {
        LoanEntity loan = getLoanById(id);
        // Calcular la parte superior de la fórmula de amortización
        double upPart = 1 + loan.getMonthlyInteresRate(); // Tasa de interés mensual
        upPart = Math.pow(upPart, loan.getTotalPayments()); // (1 + tasa)^n
        upPart = upPart * loan.getMonthlyInteresRate(); // (1 + tasa)^n * tasa

        double downPart = 1 + loan.getMonthlyInteresRate(); // Tasa de interes mensual
        downPart = Math.pow(downPart, loan.getTotalPayments()); // (1 + tasa)^n
        downPart = downPart - 1; // (1 + tasa)^n - 1

        double result = upPart/downPart; // Division
        result = result * loan.getLoanAmount(); // Calculo * el monto del prestamo

        return result; // Devuelve el resultado
    }

    /*

    //Buscar un prestamo por estatus de aprobacion o rechazo
    public List<LoanEntity> getLoanByStatusloan(int status){

    }

     */



}
