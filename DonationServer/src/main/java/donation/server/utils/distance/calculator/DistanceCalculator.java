package donation.server.utils.distance.calculator;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

class DistanceCalculator {

    private GeoApiContext context;
    private static DistanceCalculator instance  = new DistanceCalculator();

    private DistanceCalculator(){
        context = new GeoApiContext
                .Builder()
                .apiKey("AIzaSyAWbi3iXbtkAcNwVEPuZ2HOdzYCPevHxn0")
                .build();
    }

    /**
     * @return an instance of object
     */

     static DistanceCalculator getInstance(){
        return  instance;
    }

    /**
     *
     * @param from -> the start location(street,country,region,anything :))
     * @param to -> destination location(street,country,region)
     * @return -> double which represents the distances between those two places
     * @throws Exception if something went wrong.
     */

     double getDistance(String from,String to) throws  Exception{

        DistanceMatrix result = DistanceMatrixApi.newRequest(context)
                .origins(from)
                .destinations(to)
                .mode(TravelMode.DRIVING)
                .avoid(DirectionsApi.RouteRestriction.TOLLS)
                .language("en-US")
                .await();

        if(result == null)throw new Exception("Unreachable by car!");
        return (result.rows[0].elements[0].distance.inMeters +.0) / 1000;
    }

}
