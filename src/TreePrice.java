

public class TreePrice implements Comparable<TreePrice> {
    private double realPrice;
    private double nominalPrice;
    private PriceDate priceDate;

    public TreePrice(double realPrice, double nominalPrice, PriceDate priceDate) {
        this.realPrice = realPrice;
        this.nominalPrice = nominalPrice;
        this.priceDate = priceDate;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public double getNominalPrice() {
        return nominalPrice;
    }

    public PriceDate getPriceDate() {
        return priceDate;
    }

    @Override
    public int compareTo(TreePrice other) {
        return this.priceDate.compareTo(other.priceDate);
    }

    @Override
    public String toString() {
        String dateString = priceDate.toString();
        return String.format("%s Nominal: %.1f Real: %.1f", dateString, nominalPrice, realPrice);
    }


}
