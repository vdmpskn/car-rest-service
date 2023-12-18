package ua.foxminded.carrest;

import java.util.List;

import ua.foxminded.carrest.dao.model.Car;

public class CarResponse {
    private List<Car> carList;

    public CarResponse(List<Car> carList){
        this.carList = carList;
    }
}
