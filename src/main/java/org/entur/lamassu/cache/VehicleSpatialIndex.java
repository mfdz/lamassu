package org.entur.lamassu.cache;

import org.redisson.api.GeoOrder;
import org.redisson.api.GeoUnit;

import java.util.List;

public interface VehicleSpatialIndex {
    long add(Double longitude, Double latitude, String id);
    boolean remove(String id);
    List<String> radius(Double longitude, Double latitude, Double radius, GeoUnit geoUnit, GeoOrder geoOrder, Integer count);
}
