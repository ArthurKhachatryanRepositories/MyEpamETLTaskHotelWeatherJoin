package utils;

import domain.Hotel;
import domain.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;

/**
 * The DatasetUtils class provides utility methods to fill geohash values of Spark Datasets domain objects.
 */
public class DatasetUtils {

    private static final Logger logger = LogManager.getLogger(DatasetUtils.class);

    /**
     * Maps the given Dataset of Hotel objects to a new Dataset where each hotel's geohash value is filled in.
     *
     * @param hotelsDataset the original Dataset of Hotel objects
     * @return a new Dataset of Hotel objects where each hotel's geohash value is filled in
     */
    public static Dataset<Hotel> mapHotelToFillGeohash(Dataset<Hotel> hotelsDataset) {
        return hotelsDataset.map((MapFunction<Hotel, Hotel>) hotel -> {
            final String doubleValueCheckRegex = "[-+]?[0-9]*\\.?[0-9]+";
            if (hotel.getLatitude() == null
                    || hotel.getLongitude() == null
                    || !hotel.getLatitude().matches(doubleValueCheckRegex)
                    || !hotel.getLongitude().matches(doubleValueCheckRegex)) {
                hotel.setLatitude(utils.GeohashUtils.getLatitude(hotel.getAddress(), hotel.getCity(), hotel.getCountry()));
                hotel.setLongitude(utils.GeohashUtils.getLongitude(hotel.getAddress(), hotel.getCity(), hotel.getCountry()));
            }
            String geohash = utils.GeohashUtils.get4CharacterGeohash(Double.valueOf(hotel.getLatitude()), Double.valueOf(hotel.getLongitude()));
            hotel.setGeohash4Character(geohash);
            return hotel;
        }, Encoders.bean(Hotel.class));
    }

    /**
     * Maps the given Dataset of Weather objects to a new Dataset where each weather record's geohash value is filled in.
     *
     * @param weatherDataSet the original Dataset of Weather objects
     * @return a new Dataset of Weather objects where each weather record's geohash value is filled in
     */
    public static Dataset<Weather> mapWeatherToFillGeohash(Dataset<Weather> weatherDataSet) {
        return weatherDataSet.map((MapFunction<Weather, Weather>) weather -> {

            if (weather.getLat() == null || weather.getLng() == null) {
                return weather;
            }
            String geohash = null;
            try {
                geohash = utils.GeohashUtils.get4CharacterGeohash(weather.getLat(), weather.getLng());
            } catch (NumberFormatException e) {
                logger.info("catch in weather map method", e);
            }
            weather.setGeohash4Character(geohash);
            return weather;
        }, Encoders.bean(Weather.class));
    }
}
