package ua.foxminded.carrest.custom.response;

import java.util.List;

import ua.foxminded.carrest.dao.model.CarType;

public class CarTypeResponse {
    private List<CarType> carTypeList;

    public CarTypeResponse(List<CarType> carTypeList){
        this.carTypeList = carTypeList;
    }
}
