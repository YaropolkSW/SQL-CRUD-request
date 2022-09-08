import java.util.List;

public class Car {
    private final List<Integer> idList;
    private final List<String> brandList;
    private final List<String> modelList;
    private final List<Integer> ageOfProduceList;

    public Car(final List<Integer> idList, final List<String> brandList, final List<String> modelList,
               final List<Integer> ageOfProduceList) {

        this.idList = idList;
        this.brandList = brandList;
        this.modelList = modelList;
        this.ageOfProduceList = ageOfProduceList;
    }

    public List<Integer> getListOfID() {
        return idList;
    }

    public List<String> getBrandList() {
        return brandList;
    }

    public List<String> getModelList() {
        return modelList;
    }

    public List<Integer> getAgeOfProduceList() {
        return ageOfProduceList;
    }
}
