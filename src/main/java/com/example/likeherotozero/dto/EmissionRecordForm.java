package com.example.likeherotozero.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class EmissionRecordForm {

    @NotNull(message = "Please select a country.")
    private Long countryId;

    @NotNull(message = "Please enter a reporting year.")
    @Min(value = 1750, message = "The reporting year must be 1750 or later.")
    @Max(value = 2100, message = "The reporting year must not be later than 2100.")
    private Integer year;

    @NotNull(message = "Please enter an emission value.")
    @DecimalMin(value = "0.0", message = "Emissions must not be negative.")
    @Digits(integer = 15, fraction = 3, message = "Use up to 15 digits and 3 decimal places.")
    private BigDecimal emissionsKt;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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