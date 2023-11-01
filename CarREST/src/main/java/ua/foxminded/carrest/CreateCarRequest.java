package ua.foxminded.carrest;

import java.util.Set;

import ua.foxminded.carrest.dao.model.CarBodyType;

public class CreateCarRequest {

    private String producerName;
    private String modelName;
    private Set<CarBodyType> carBodyType;
    private int year;

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(final String producerName) {
        this.producerName = producerName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public Set<CarBodyType> getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(final Set<CarBodyType> carBodyType) {
        this.carBodyType = carBodyType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }
}
