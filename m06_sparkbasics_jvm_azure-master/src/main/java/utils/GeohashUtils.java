package utils;

import ch.hsr.geohash.GeoHash;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 This class provides utility methods related to geohashing.
 */
public class GeohashUtils {

    private static final Logger logger = LogManager.getLogger(GeohashUtils.class);

    /**
     * Computes the 4-character geohash for the given latitude and longitude.
     *
     * @param latitude the latitude value of the location
     * @param longitude the longitude value of the location
     * @return the 4-character geohash for the given location
     * @throws NumberFormatException if an exception is thrown during computation
     */
    public static String get4CharacterGeohash(Double latitude, Double longitude) throws NumberFormatException {
        boolean exceptionIsThrown = true;
        String geohash = null;
        try {
            GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, 4);
            geohash = geoHash.toBase32();
            exceptionIsThrown = false;
        } catch (NumberFormatException e) {
            logger.error("Exception caught in get4CharacterGeohash() method: ", e);
            logger.debug("Latitude: " + latitude + " Longitude: " + longitude);
            throw e;
        } finally {
            if (exceptionIsThrown)
                logger.debug("Exception was thrown in method get4CharacterGeohash(): Latitude: " + latitude + " Longitude: " + longitude);
        }
        return geohash;
    }

    /**
     * Retrieves the latitude for a location given its address, city, and country.
     *
     * @param address the street address of the location
     * @param city the city where the location is situated
     * @param country the country where the location is situated
     * @return the latitude for the given location
     */
    public static String getLatitude(String address, String city, String country) {
        String query = address + ", " + city + ", " + country;
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("c90f95b84b424606bc2bf7d34cc9ecdf");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(query);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();
        String latitude = String.valueOf(firstResultLatLng.getLat());
        return latitude;
    }

    /**
     * Retrieves the longitude for a location given its address, city, and country.
     *
     * @param address the street address of the location
     * @param city the city where the location is situated
     * @param country the country where the location is situated
     * @return the longitude for the given location
     */
    public static String getLongitude(String address, String city, String country) {
        String query = address + ", " + city + ", " + country;
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("c90f95b84b424606bc2bf7d34cc9ecdf");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(query);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
        String longitude = String.valueOf(firstResultLatLng.getLng());
        return longitude;
    }

}
