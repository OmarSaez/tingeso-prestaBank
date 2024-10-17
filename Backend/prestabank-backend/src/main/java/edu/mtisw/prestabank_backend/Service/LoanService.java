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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);


    //rescatar todos los prestamos todo bien
    public ArrayList<LoanEntity> getLoans() {
        return (ArrayList<LoanEntity>) loanRepository.findAll();
    }

    //Rescata todos los loan por el ID de un user todo bien
    public ArrayList<LoanEntity> getLoansByIdUser(Long idUser) {
        return (ArrayList<LoanEntity>) loanRepository.findByIdUser(idUser);
    }

    //Guardar un prestamo todo bien
    public LoanEntity saveLoan(LoanEntity saveLoan) {

        logger.info("--Se ingreso al save");

        saveLoan.setMonthlyInteresRate(saveLoan.getYearInterest()/12/100);
        saveLoan.setTotalPayments(saveLoan.getMaxDuration()*12);



        Double CalculeMonthlyPayment = Calculo(saveLoan);
        saveLoan.setMonthlyPayment(CalculeMonthlyPayment);
        logger.info("--Resultado de la cuota {}", CalculeMonthlyPayment);
        logger.info("--Calculo cuota mensual guardado: {}", saveLoan.getMonthlyPayment());

        int EvalueWithR1 = paymentToIncome(saveLoan);
        logger.info("--Salido R1 {}", EvalueWithR1);
        //R2 no se puede evaluar automaticamente

        int EvalueWithR3 = seniority(saveLoan);
        logger.info("--Salido R3 {}", EvalueWithR3);

        int EvalueWithR4 = debtToIncome(saveLoan);
        logger.info("--Salido R4 {}", EvalueWithR4);

        //R5 es tabla, no se verifica aca

        int EvalueWithR6 = yearOld(saveLoan);
        logger.info("--Salido R6 {}", EvalueWithR6);

        //La primera vez que se guarda, no se puede ingresar los datos necesarios para usar la funcion R7, son datos proporcionador por el evaluador (ejecutivo), se pone R7=2 de pendiente

        ArrayList <Integer> newEvalue = new ArrayList<>(Arrays.asList(EvalueWithR1, 2, EvalueWithR3, EvalueWithR4, EvalueWithR6, 2));
        saveLoan.setEvalue(newEvalue);
        logger.info("--Se guardo la lista Evalue con todos los Rs {}", newEvalue);
        logger.info("--Todo lo guardado: {}", saveLoan);

        return loanRepository.save(saveLoan);
    }

    //Buscar un prestamo por su ID todo bien
    public LoanEntity getLoanById(Long id) {
        return loanRepository.findById(id).get();
    }

    //Borrar un prestamo todo bien
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
        //changeLoan.calculateMonthlyPayment();  //---------------PENDIENTE CORREGIRLOOOOOOOOOO, agregar toda le evaluacion de Rs e incluir ahora R7--------------
        return loanRepository.save(changeLoan);
    }

    // Calculo de la cuota mensual todo bien
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


    //Buscar todos los prestamos por el estado de la solicitud todo bien
    public ArrayList<LoanEntity> getLoanByStatus(int status) {
        return (ArrayList<LoanEntity>) loanRepository.findByStatus(status);
    }

    //Busca todos los prestamos todo bien
    public ArrayList<LoanEntity> getLoanByType(int type) {
        return (ArrayList<LoanEntity>) loanRepository.findByType(type);
    }

    //------Metodos de evaluacion en automatico----------

    //Calcular edad todo bien
    public static int calcularYearOld(int diaNacimiento, int mesNacimiento, int anioNacimiento) {
        // Crear un objeto LocalDate con la fecha de nacimiento
        LocalDate fechaNacimiento = LocalDate.of(anioNacimiento, mesNacimiento, diaNacimiento);

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Calcular la edad usando Period
        Period periodo = Period.between(fechaNacimiento, fechaActual);

        // Devolver la cantidad de años
        return periodo.getYears();
    }

    //R1 relacion cuotas ingresos, si es mayor 35% se rechaza todo bien
    public int paymentToIncome(LoanEntity loan) {
        int income = loan.getIncome();
        double monthlyPayment = loan.getMonthlyPayment();
        double ratio = (monthlyPayment / income) * 100;
        logger.info("--R1 ratio = {}/{} * 100 = {}", monthlyPayment, income, ratio);
        if (ratio > 35) {
            return 0;
        } else {
            return 1;
        }
    }

    //R2 historial, no es posible evaluar se pone 2 de pendiente

    //R3 se ve la antiguedad laboral todo bien
    public int seniority(LoanEntity loan) {
        int veteran = loan.getVeteran();
        if (veteran < 2) {
            return 0;
        } else {
            return 1;
        }
    }

    //R4 ver la relacion deuda ingreso todo bien
    public int debtToIncome(LoanEntity loan) {
        int income = loan.getIncome();
        double debt = loan.getTotaldebt()+loan.getMonthlyPayment();
        double ratio = (income / debt) * 100;

        if (ratio > income*0.5) {
            return 0;
        } else {
            return 1;
        }
    }

    //R5 es una condicional de tabla y es ase limita en otro lado el monto a pedir maximo

    //R6 verificar la edad del solicitante todo bien
    public int yearOld(LoanEntity loan) {
        Long idUser = loan.getIdUser();

        logger.info("--R6 Procesando préstamo para el usuario con ID: {}", idUser);

        UserEntity user = userRepository.findById(idUser).get();
        int edad = calcularYearOld(user.getBirthDay(), user.getBirthMonth(), user.getBirthYear());
        logger.info("--R6 edad: {}", edad);
        if (edad > 69) {
            return 0;
        } else {
            return 1;
        }
    }

    //R7 Verificar la capacidad de ahorro, se utiliza una tabla de puntaje

    //(Credito, Frecuencia de depositos, Años de antiguedad de la cuenta, list(Saldo ultimos 12 meses), list(Retiro de los ultimos 12 meses)
    public ArrayList<Integer> savingSkills(LoanEntity loan, int depositFrequency, int acountYears, ArrayList<Integer> balanceLast12, ArrayList<Integer> last12Withdrawals) {
        ArrayList<Integer> listEvalue = new ArrayList<>();
        //agregamos R71
        listEvalue.add(minBalance(balanceLast12.get(0), loan.getLoanAmount()));
        //Agregamos R72
        listEvalue.add(recordSaving(balanceLast12, last12Withdrawals));
        //Agregamos R73
        listEvalue.add(periodicDeposits(balanceLast12, loan.getIncome(), depositFrequency));
        //Agremos R74
        listEvalue.add(ratioBalanceVeteran(acountYears, balanceLast12, loan.getLoanAmount()));
        //Agregamos R75
        listEvalue.add(recentWithdrawals(last12Withdrawals, loan.getIncome()));

        return listEvalue;
    }

    //R71 saldo minimo del 10% del prestamo solicitado, aca loan es el prestamo en numero, no la entidad
    public int minBalance(int balance, double loan) {
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
    public int ratioBalanceVeteran(int acountYears, ArrayList<Integer> balanceLast12, double loan) {

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
    public int recentWithdrawals(ArrayList<Integer> last12Withdrawals, double income){
        for (int i = 0; i <= 6; i++) {
            int value = last12Withdrawals.get(i);
            if (value > income * 0.3) {return 0;}
        }
        //si registro todos los meses y no retorno cero, entonces esta aprobado este punto
        return 1;
    }

}
