package edu.mtisw.prestabank_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
    private int statusLoan; //Indica si la solicitud fue aprobada o rechazada, 0=rechazado, 1=aprobado y 2=pendiente
    private int statusAppli; //indica si el credito esta: revision inicial(1), pendiente de documentacion(2), en evaluacion(3), pre-aprobado(4), arpobacion final(5), aprobada(6), rechazada(7), cancelada por el cliente(8), en desembolso(9)

    private int type; //Tipo de credito,
    private int yearInterest; //Interes anual
    private int maxDuration; // Tiempo maximo para pagar
    private int maxLoan; //Maximo monto del credito (financiamiento)
    private int income; //Ingresos mensuales (Revisar con combrobante)
    private int veteran; //Anos de antiguedad en el empleo actual (Revisar con comprobante)
    private int totaldebt; //Total de deudas

    private double monthlyPayment; //Cuota mensual que se va a pagar M (Se calcula)
    private double loanAmount; //Monto del prestamo (capital) P
    private double monthlyInteresRate; //Tasa de interes mensual (Tasa anual / 12 / 100) r
    private int totalPayments; //Numero total de pagos (plazo en años x 12) n

    private int saving; //Puntos de capacidad de ahorro

    private ArrayList<String> papers; //Documentacion a evaluar manualmente

    private ArrayList<Integer> evalue; //Listado que indica automaticamente las cosas 0=rechazado, 1=aprobado y 2=pendiente [R1, R2, R3, R4, R5, R6, R7]


    public LoanEntity(Long idUser, int statusLoan, int statusAppli, int type, int yearInterest, int maxDuration, int maxLoan, int income, int veteran, int totaldebt, double loanAmount, double monthlyInteresRate, int totalPayments, int saving, ArrayList<String> papers, ArrayList<Integer> evalue) {
        this.idUser = idUser;
        this.statusLoan = statusLoan;
        this.statusAppli = statusAppli;
        this.type = type;
        this.yearInterest = yearInterest;
        this.maxDuration = maxDuration;
        this.maxLoan = maxLoan;
        this.income = income;
        this.veteran = veteran;
        this.totaldebt = totaldebt;
        this.loanAmount = loanAmount;
        this.monthlyInteresRate = monthlyInteresRate;
        this.totalPayments = totalPayments;
        this.saving = saving;
        this.papers = papers;
        this.evalue = evalue;

        //Carlcular la cuota mensual
        calculateMonthlyPayment();
    }

    public LoanEntity() {this.statusAppli = 1; this.statusLoan=2;}// inicializamos con estatus Revicion inicial

    //Calculo de las cuotas mensuales
    public void calculateMonthlyPayment() {
            this.monthlyPayment = loanAmount * ((monthlyInteresRate * Math.pow(1 + monthlyInteresRate, totalPayments)) / (Math.pow(1 + monthlyInteresRate, totalPayments) - 1));
    }


}
