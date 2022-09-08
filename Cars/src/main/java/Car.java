public class Car {
    private final static String OUTPUT_PATTERN = "ID - %d\nBrand - %s\nModel - %s\nAge of produce - %d\n";
    private int id;
    private String brand;
    private String model;
    private int ageOfProduce;

    public void setId(final int id) {
        this.id = id;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public void setAgeOfProduce(final int ageOfProduce) {
        this.ageOfProduce = ageOfProduce;
    }

    @Override
    public String toString() {
        return String.format(OUTPUT_PATTERN, id, brand, model, ageOfProduce);
    }
}
