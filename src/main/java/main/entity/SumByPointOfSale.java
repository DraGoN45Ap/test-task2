package main.entity;

/**
 * DTO for serialize summary by points of sale.
 */
public class SumByPointOfSale extends AbstractSumDTO{

    private Integer pointOfSale;
    private double price;

    public SumByPointOfSale(Integer pointOfSale, double price) {
        this.pointOfSale = pointOfSale;
        this.price = price;
    }

    public Integer getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(Integer pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Total price on point of sale: " + pointOfSale + " " + price + '\n';
    }
}
