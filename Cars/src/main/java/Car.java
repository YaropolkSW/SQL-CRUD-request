import java.util.List;

public class Car {
    private final List<Integer> idList;
    private final List<String> brandList;
    private final List<Integer> ageOfProduceList;

    public Car(final List<Integer> idList, final List<String> brandList,
               final List<Integer> ageOfProduceList) {

        this.idList = idList;
        this.brandList = brandList;
        this.ageOfProduceList = ageOfProduceList;
    }

    public List<Integer> getListOfID() {
        return idList;
    }

    public List<String> getBrandList() {
        return brandList;
    }

    public List<Integer> getAgeOfProduceList() {
        return ageOfProduceList;
    }
}
