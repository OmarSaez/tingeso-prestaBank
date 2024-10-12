package edu.mtisw.prestabank_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "loan")//Prestamo
//@NoArgsConstructor
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Long idUser;//ID del usuario que solicito credito
    private double monthlyPayment; //Cuota mensual que se va a pagar M
    private double loanAmount; //Monto del prestamo (capital) P
    private double monthlyInteresRate; //Tasa de interes mensual (Tasa anual / 12 / 100) r
    private int totalPayments; //Numero total de pagos (plazo en años x 12) n
    private int status; //indica si el credito esta revision inicial(1), pendiente de documentacion(2), en evaluacion(3), pre-aprobado(4), arpobacion final(5), aprobada(6), rechazada(7), cancelada por el cliente(8), en desembolso(9)


    public LoanEntity(Long idUser, double loanAmount, double monthlyInteresRate, int totalPayments) {
        this.idUser = idUser;
        this.loanAmount = loanAmount;
        this.monthlyInteresRate = monthlyInteresRate;
        this.totalPayments = totalPayments;
        this.status = 1;

        // Calcular la cuota mensual (monthlyPayment)
        calculateMonthlyPayment();
    }

    public LoanEntity() {this.status = 1;}// inicializamos con estatus Revicion inicial

    //Calculo de las cuotas mensuales
    public void calculateMonthlyPayment() {
            this.monthlyPayment = loanAmount * ((monthlyInteresRate * Math.pow(1 + monthlyInteresRate, totalPayments)) / (Math.pow(1 + monthlyInteresRate, totalPayments) - 1));
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setMonthlyInteresRate(double monthlyInteresRate) {
        this.monthlyInteresRate = monthlyInteresRate;
    }

    public void setTotalPayments(int totalPayments) {
        this.totalPayments = totalPayments;
    }
    /*
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
        calculateMonthlyPayment(); // Invocar el cálculo cuando se cambia el monto
    }

    public void setMonthlyInteresRate(double monthlyInteresRate) {
        this.monthlyInteresRate = monthlyInteresRate;
        calculateMonthlyPayment(); // Invocar el cálculo cuando se cambia la tasa
    }

    public void setTotalPayments(int totalPayments) {
        this.totalPayments = totalPayments;
        calculateMonthlyPayment(); // Invocar el cálculo cuando se cambia el número de pagos
    }
    */

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getMonthlyInteresRate() {
        return monthlyInteresRate;
    }

    public int getTotalPayments() {
        return totalPayments;
    }

    public int getStatus() {
        return status;
    }
}
