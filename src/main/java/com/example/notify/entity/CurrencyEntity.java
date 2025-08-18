package com.example.notify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CURRENCY")
public class CurrencyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    @SequenceGenerator(name = "currency_seq", sequenceName = "CURRENCY_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CCY_CODE")
    private String ccyCode;
    @Column(name = "CCY_NAME")
    private String ccyName;
    @Column(name = "NUM_OF_DECIMAL")
    private Integer numOfDecimal;

}
