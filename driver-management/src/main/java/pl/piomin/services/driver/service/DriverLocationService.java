package pl.piomin.services.driver.service;

import org.springframework.stereotype.Service;
import pl.piomin.services.driver.model.Driver;

import java.util.List;

@Service
public class DriverLocationService {

    public Driver searchNearestDriver(int locationX, int locationY, List<Driver> drivers) {
        double diff = -1;
        Driver driverRes = null;
        for (Driver driver : drivers) {
            double diffTmp = Math.pow(locationX - driver.getCurrentLocationX(), 2) + Math.pow(locationY - driver.getCurrentLocationY(), 2);
            if (diffTmp < diff || diff < 0) {
                diff = diffTmp;
                driverRes = driver;
            }
        }
        return driverRes;
    }

}
