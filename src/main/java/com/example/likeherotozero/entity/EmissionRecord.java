package com.example.likeherotozero.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;

@Entity
@Table(
        name = "emission_records",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_emission_country_year",
                columnNames = {"country_id", "reporting_year"}
        )
)
public class EmissionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "reporting_year", nullable = false)
    private Integer year;

    @Column(name = "emissions_kt", nullable = false, precision = 18, scale = 3)
    private BigDecimal emissionsKt;

    protected EmissionRecord() {
    }

    public EmissionRecord(Country country, Integer year, BigDecimal emissionsKt) {
        this.country = country;
        this.year = year;
        this.emissionsKt = emissionsKt;
    }

    public Long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getEmissionsKt() {
        return emissionsKt;
    }

    public void setEmissionsKt(BigDecimal emissionsKt) {
        this.emissionsKt = emissionsKt;
    }
}