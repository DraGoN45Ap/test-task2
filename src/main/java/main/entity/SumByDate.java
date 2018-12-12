package main.entity;

import java.time.LocalDate;

/**
 * DTO for serialize summary by date.
 */
public class SumByDate extends AbstractSumDTO{

    private LocalDate date;
    private double price;

    public SumByDate(LocalDate date, double price) {
        this.date = date;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Total price on: " + date + " " + price + '\n';
    }
}
