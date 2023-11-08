package ua.foxminded.carrest;

import java.util.List;

import ua.foxminded.carrest.dao.model.Car;

public class ApiResponse {
    private List<Car> carList;

    public ApiResponse(List<Car> carList){
        this.carList = carList;
    }
}
