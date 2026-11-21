public class DiscountManager {
    private static DiscountManager instance;
    private double discountRate;

    private DiscountManager() {
        this.discountRate = 0.1; // Default 10% discount
    }

    public static DiscountManager getInstance() {
        if (instance == null) {
            instance = new DiscountManager();
        }
        return instance;
    }

    public double getDiscountRate() {
        return this.discountRate;
    }

    public void setDiscountRate(double rate) {
        this.discountRate = rate;
    }

    public double applyDiscount(double fee) {
        return fee * (1 - discountRate);
    }
}
