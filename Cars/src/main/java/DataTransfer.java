import java.util.List;

public class DataTransfer {
    private int id;
    private String brand;
    private int ageOfProduce;
    private List<Integer> listOfID;
    private List<String> brandList;
    private List<Integer> ageOfProduceList;

    public DataTransfer(final int id, final String brand, final int ageOfProduce) {
        this.id = id;
        this.brand = brand;
        this.ageOfProduce = ageOfProduce;
    }

    public DataTransfer(final List<Integer> listOfID, final List<String> brandList,
                        final List<Integer> ageOfProduceList) {

        this.listOfID = listOfID;
        this.brandList = brandList;
        this.ageOfProduceList = ageOfProduceList;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public int getAgeOfProduce() {
        return ageOfProduce;
    }

    public List<Integer> getListOfID() {
        return listOfID;
    }

    public List<String> getBrandList() {
        return brandList;
    }

    public List<Integer> getAgeOfProduceList() {
        return ageOfProduceList;
    }
}
