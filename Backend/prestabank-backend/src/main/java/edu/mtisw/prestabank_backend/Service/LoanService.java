package edu.mtisw.prestabank_backend.Service;

import edu.mtisw.prestabank_backend.Entity.LoanEntity;
import edu.mtisw.prestabank_backend.Entity.UserEntity;
import edu.mtisw.prestabank_backend.Repository.LoanRepository;
import edu.mtisw.prestabank_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    UserRepository userRepository;

    //rescatar todos los prestamos
    public ArrayList<LoanEntity> getLoans() {
        return (ArrayList<LoanEntity>) loanRepository.findAll();
    }

    //Rescata todos los loan por el ID de un user
    public ArrayList<LoanEntity> getLoansByIdUser(Long idUser) {
        return (ArrayList<LoanEntity>) loanRepository.findByIdUser(idUser);
    }

    //Guardar un prestamo
    public LoanEntity saveLoan(LoanEntity saveLoan) {

        Double CalculeMonthlyPayment = Calculo(saveLoan);
        saveLoan.setMonthlyPayment(CalculeMonthlyPayment);

        Integer EvalueWithR1 = paymentToIncome(saveLoan);
        //R2 no se puede evaluar automaticamente
        Integer EvalueWithR3 = seniority(saveLoan);
        Integer EvalueWithR4 = debtToIncome(saveLoan);
        //R5 es tabla, no se verifica aca
        Integer EvalueWithR6 = yearOld(saveLoan);
        ArrayList<Integer> EvalueWithR7 =
                ArrayList < Integer > newEvalue = new ArrayList<>(Arrays.asList(EvalueWithR1, 2, EvalueWithR3, EvalueWithR4));
        saveLoan.setEvalue(newEvalue);

        return loanRepository.save(saveLoan);
    }

    //Buscar un prestamo por su ID
    public LoanEntity getLoanById(Long id) {
        return loanRepository.findById(id).get();
    }

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
        changeLoan.calculateMonthlyPayment();//---------------PENDIENTE CORREGIRLOOOOOOOOOO--------------
        return loanRepository.save(changeLoan);
    }

    public double Calculo(LoanEntity loan) {
        // Calcular la parte superior de la fórmula de amortización
        double upPart = 1 + loan.getMonthlyInteresRate(); // Tasa de interés mensual
        upPart = Math.pow(upPart, loan.getTotalPayments()); // (1 + tasa)^n
        upPart = upPart * loan.getMonthlyInteresRate(); // (1 + tasa)^n * tasa

        double downPart = 1 + loan.getMonthlyInteresRate(); // Tasa de interes mensual
        downPart = Math.pow(downPart, loan.getTotalPayments()); // (1 + tasa)^n
        downPart = downPart - 1; // (1 + tasa)^n - 1

        double result = upPart / downPart; // Division
        result = result * loan.getLoanAmount(); // Calculo * el monto del prestamo

        return result; // Devuelve el resultado
    }


    //Buscar todos los prestamos por el estado de la solicitud
    public ArrayList<LoanEntity> getLoanByStatus(int status) {
        return (ArrayList<LoanEntity>) loanRepository.findByStatus(status);
    }

    //Busca todos los prestamos
    public ArrayList<LoanEntity> getLoanByType(int type) {
        return (ArrayList<LoanEntity>) loanRepository.findByType(type);
    }

    //------Metodos de evaluacion en automatico----------

    //Calcular edad
    public static int calcularEdad(int diaNacimiento, int mesNacimiento, int anioNacimiento) {
        // Crear un objeto LocalDate con la fecha de nacimiento
        LocalDate fechaNacimiento = LocalDate.of(anioNacimiento, mesNacimiento, diaNacimiento);

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Calcular la edad usando Period
        Period periodo = Period.between(fechaNacimiento, fechaActual);

        // Devolver la cantidad de años
        return periodo.getYears();
    }

    //R1 relacion cuotas ingresos, si es mayor 35% se rechaza
    public int paymentToIncome(LoanEntity loan) {
        int income = loan.getIncome();
        double monthlyPayment = loan.getMonthlyPayment();
        double ratio = (monthlyPayment / income) * 100;
        int number = 2;//Pendiente revisar
        if (ratio > 35) {
            number = 0;//Se rechaza
        } else {
            number = 1;//Se aprueba
        }

        return number;
    }

    //R2 historial, no es posible evaluar se pone 2 de pendiente

    //R3 se ve la antiguedad laboral
    public int seniority(LoanEntity loan) {
        int veteran = loan.getVeteran();
        if (veteran < 2) {
            return 0;
        } else {
            return 1;
        }
    }

    //R4 ver la relacion deuda ingreso
    public int debtToIncome(LoanEntity loan) {
        int income = loan.getIncome();
        int debt = loan.getTotaldebt();
        int ratio = (income / debt) * 100;
        if (ratio > 50) {
            return 0;
        } else {
            return 1;
        }
    }

    //R5 es una condicional de tabla y es ase limita en otro lado el monto a pedir maximo

    //R6 verificar la edad del solicitante
    public int yearOld(LoanEntity loan) {
        Long idUser = loan.getIdUser();

        UserEntity user = userRepository.findById(idUser).get();
        int edad = calcularEdad(user.getBirthDay(), user.getBirthMonth(), user.getBirthYear());
        if (edad > 69) {
            return 0;
        } else {
            return 1;
        }
    }

    //R7 Verificar la capacidad de ahorro, se utiliza una tabla de puntaje

    //(Credito, Frecuencia de depositos, Años de antiguedad de la cuenta, list(Saldo ultimos 12 meses), list(Retiro de los ultimos 12 meses)
    public ArrayList<Integer> savingSkills(LoanEntity loan, int depositFrequency, int acountYears, ArrayList<Integer> balanceLast12, ArrayList<Integer> last12Withdrawals) {
        //PENDIENTE IMPLEMENTAR TODOS LOS R7.NUMBER CREADOS
    }

    //R71 saldo minimo del 10% del prestamo solicitado
    public int minBalance(int balance, int loan) {
        double tenPercentage = loan * 0.1;
        if (balance >= tenPercentage) {
            return 1;
        } else {
            return 0;
        }
    }

    //R72 Saldo positivo los ultimos 12 meses y no retiros del mas del 50%
    public int recordSaving(ArrayList<Integer> balanceLast12, ArrayList<Integer> last12Withdrawals) {

        //Ciclo que analiza los saldos de los 12 meses
        for (int i = 0; i < balanceLast12.size(); i++) {
            int value = balanceLast12.get(i);
            if (value <= 0) {
                return 0;
            }
        }

        for (int i = 0; i < last12Withdrawals.size(); i++) {
            int value = last12Withdrawals.get(i);
            int balance = balanceLast12.get(i);
            if (value > balance * 0.5) {
                return 0;
            }
        }

        return 1;//Si ya paso ambos ciclos significa que esta todo bien
    }

    //R73 verificar si los depositos son periodicos y la suma total suman almenos un 5% del ingreso mensual
    public int periodicDeposits(ArrayList<Integer> balanceLast12, int income, int frecuencyDeposits) {

        if (frecuencyDeposits > 3) {
            return 0;
        }

        int acum = 0;
        //Acumulador que calcula el saldo total de los ultimos 12 meses
        for (int i = 0; i < balanceLast12.size(); i++) {
            acum = balanceLast12.get(i) + acum;
        }
        //verifica si el acumulado es mayor al 5% del ingreso mensual

        if (acum < income * 0.05) {
            return 0;
        } else {
            return 1;
        }
    }

    //PENDIENTE ARREGLARLO
    //R74 Si la cuenta de ahorros tiene menos de 2 años el acum debe ser 20% del prestamo solicitado
    public int ratioBalanceVeteran(int acountYears, ArrayList<Integer> balanceLast12, int loan) {

        //NOTA: ACA LOAN ES EL VALOR DEL PRESTAMO, NO ES LA ENTIDAD LOAN
        int acum = 0;
        //Acumulador que calcula el saldo total de los ultimos 12 meses
        for (int i = 0; i < balanceLast12.size(); i++) {
            acum = balanceLast12.get(i) + acum;
        }

        //Consulta cuantos años tiene la cuenta
        if (acountYears < 2) {
            if (acum < loan * 0.2) {
                return 0;
            } else {
                return 1;
            }

            //Si es mayor a 2 años, que el acum solo sea mayor al 10% del prestamo
        } else if (acum < loan * 0.1) {
            return 0;
        } else {
            return 1;
        }
    }

    //R75 verifica si los retiros de los ultimos 6 meses son mayores a 30% del saldo actual
    public int recentWithdrawals(ArrayList<Integer> last12Withdrawals, int income){
        for (int i = 0; i <= 6; i++) {
            int value = last12Withdrawals.get(i);
            if (value > income * 0.3) {return 0;}
        }
        //si registro todos los meses y no retorno cero, entonces esta aprobado este punto
        return 1;
    }

}
