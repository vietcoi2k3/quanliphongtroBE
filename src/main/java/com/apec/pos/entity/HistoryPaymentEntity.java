package com.apec.pos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date payDate;
    private long totalAmount;
    private String descriptions;

    @Column(name = "accountEntityId")
    private long accountEntityId;

    @ManyToOne
    @JsonBackReference(value = "account-his")
    @JoinColumn(name = "accountEntityId",updatable = false,insertable = false)
    private AccountEntity accountEntity;
}
