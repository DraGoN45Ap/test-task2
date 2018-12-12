package main.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for serialize operation data.
 */
public class OperationDTO implements Serializable {

    private LocalDateTime time;
    private Integer pointOfSale;
    private Integer operationNumber;
    private double operationPrice;

    public OperationDTO(LocalDateTime time, Integer pointOfSale, Integer operationNumber, double operationPrice) {
        this.time = time;
        this.pointOfSale = pointOfSale;
        this.operationNumber = operationNumber;
        this.operationPrice = operationPrice;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(Integer pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Integer getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(Integer operationNumber) {
        this.operationNumber = operationNumber;
    }

    public double getOperationPrice() {
        return operationPrice;
    }

    public void setOperationPrice(double operationPrice) {
        this.operationPrice = operationPrice;
    }

    @Override
    public String toString() {
        return time + " Point of sale: " + pointOfSale
                + " Number: " + operationNumber + " Price: " + operationPrice + "\n";
    }
}
