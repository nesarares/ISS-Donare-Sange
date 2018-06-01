package donation.server.utils.distance.calculator;

import donation.model.interfaces.HasAddress;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestPlanner<T extends HasAddress<T>> {

    private DistanceCalculator distanceCalculator = DistanceCalculator.getInstance();
    private List<T> centers = new ArrayList<>();
    private Map<Pair<String, String>, Double> distanceMemoize = new HashMap<>();


    /**
     * @param locationOne->the first location
     * @param locationTwo->the second location
     * @return the distance between those two locations
     */
    private double getDistanceFromTwoLocations(T locationOne, T locationTwo) {

        Pair<String, String> pointXY = new Pair<>(locationOne.getAddress(), locationTwo.getAddress());
        Pair<String, String> pointYX = new Pair<>(locationTwo.getAddress(), locationOne.getAddress());

        if (distanceMemoize.get(pointXY) == null && distanceMemoize.get(pointYX) == null) {
            try {


                double distance = distanceCalculator.getDistance(locationOne.getAddress(), locationTwo.getAddress());
                distanceMemoize.put(pointXY, distance);
                distanceMemoize.put(pointYX, distance);
            } catch (Exception e) {
                System.out.println("GetDistanceFromTwoLocations->" + e.getMessage());
            }
        }


        return distanceMemoize.get(pointXY);
    }

    private List<Pair<T, Double>> getAllDistances(T object) {

        List<Pair<T, Double>> list = new ArrayList<>();

        if (centers.size() == 1) {

            double distances = getDistanceFromTwoLocations(centers.get(0), object);

            list.add(new Pair<>(
                    centers.get(0), distances
            ));

            return list;
        }

        centers.forEach(
                x -> list.add(
                        new Pair<>(
                                x,
                                distanceMemoize.get(new Pair<>(x.getAddress(), object.getAddress()))
                        )
                )
        );

        return list;
    }

    /**
     * @param object->the     object we refer
     * @param alsoDistances-> if is true,function will
     *                        return a list of objects and their distances to the @param object,else will return the location in decreasing order sorted by their distance to the reference point
     * @return a list
     */
    public List<?> getNearestObjectsTo(T object, boolean alsoDistances) {


        centers = centers.stream().sorted((x, y) -> {
            Double first = getDistanceFromTwoLocations(x, object);
            Double second = getDistanceFromTwoLocations(y, object);
            return first.compareTo(second);
        }).collect(Collectors.toList());

        if (!alsoDistances) return centers;

        return getAllDistances(object);
    }

    public List<T> getNearestObjectsTo(String location) {

        centers = centers.stream().sorted((x, y) -> {
            try {
                Double first = distanceCalculator.getDistance(x.getAddress(), location);
                Double second = distanceCalculator.getDistance(y.getAddress(), location);
                return first.compareTo(second);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }).collect(Collectors.toList());

        return centers;
    }

    /**
     * Adds a new location into the graph,also calculates the distances between it and all other location
     *
     * @param object a new location
     */
    public void addNewLocation(T object) {
        centers.add(object);
    }
}
