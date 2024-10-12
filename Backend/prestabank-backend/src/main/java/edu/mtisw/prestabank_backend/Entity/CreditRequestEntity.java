package edu.mtisw.prestabank_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.List;


@Entity
@Table(name = "CreditRequest")
@AllArgsConstructor
public class CreditRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;


    private int type; //Tipo de credito
    private int status; //Indica si la solicitud fue aprobada o rechazada, 0=rechazado, y 1=aprobado
    private int saving; //Puntos de capacidad de ahorro
    private int maxDuration; // Tiempo maximo para pagar
    private int yearInterest; //Interes por ano
    private int maxLoan; //Maximo monto del credito (financiamiento)

    @ElementCollection
    @CollectionTable(name = "credit_request_papers", joinColumns = @JoinColumn(name = "credit_request_id"))
    @Column(name = "paper")
    private List<String> papers; //Lista de documentos, puede ser una foto o datos.

    public CreditRequestEntity() {
        this.saving = 0;
    }
}

