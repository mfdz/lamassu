package org.entur.lamassu.integration;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphQLIntegrationTest extends AbstractIntegrationTestBase {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void testVehiclesQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("vehicles_query_with_disabled.graphql");
        assertEquals(HttpStatus.OK,response.getStatusCode());

        // TODO: investigate why this started failing
        //assertEquals(2, response.get("$.data.vehicles", List.class).size());
        assertEquals(2, JsonPath.parse(response.getRawResponse().getBody()).read("$.data.vehicles", List.class).size());

        assertEquals("TST:Vehicle:1234", response.get("$.data.vehicles[0].id"));
        assertEquals("Test", response.get("$.data.vehicles[0].system.name.translation[0].value"));
    }

    @Test
    public void testVehicleByIdQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("vehicle_by_id_query.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TST:Vehicle:1235", response.get("$.data.vehicle.id"));
    }

    @Test
    public void testVehiclesByIdQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("vehicles_by_id_query.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TST:Vehicle:1235", response.get("$.data.vehicles[0].id"));
    }

    @Test
    public void testVehicleQueryWithoutDisabled() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("vehicles_query_without_disabled.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // TODO: investigate why this started failing
        //assertEquals(1, response.get("$.data.vehicles", List.class).size());
        assertEquals(1, JsonPath.parse(response.getRawResponse().getBody()).read("$.data.vehicles", List.class).size());
    }

    @Test
    public void testStationsQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("stations_query.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TST:Station:2", response.get("$.data.stations[0].id"));
        assertEquals("Cooler bikes", response.get("$.data.stations[0].name.translation[0].value"));
        assertEquals("https://rentmybikes.com", response.get("$.data.stations[0].rentalUris.web"));
    }

    @Test
    public void testStationByIdQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("station_by_id_query.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TST:Station:1", response.get("$.data.station.id"));
        assertEquals("2", response.get("$.data.station.vehicleDocksAvailable[0].count"));
    }

    @Test
    public void testStationsByIdQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("stations_by_id_query.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TST:Station:1", response.get("$.data.stations[0].id"));
        assertEquals("2", response.get("$.data.stations[0].vehicleDocksAvailable[0].count"));
    }

    @Test
    public void testGeofencingZones() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("geofencing_zones_query.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testatlantis", response.get("$.data.geofencingZones[0].systemId"));
    }

    @Test
    public void testUnknownOperatorDoesNotThrow() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("stations_query_unknown_operator.graphql");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // TODO: investigate why this started failing
        //assertTrue(response.get("$.data.stations", List.class).isEmpty());
        assertTrue(JsonPath.parse(response.getRawResponse().getBody()).read("$.data.stations", List.class).isEmpty());
    }
 }
