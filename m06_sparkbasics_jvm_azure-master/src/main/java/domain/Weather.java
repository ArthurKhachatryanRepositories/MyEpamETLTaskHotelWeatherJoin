package domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * The Weather class represents weather data with various attributes such as latitude, longitude, temperature, date, etc.
 * It implements Serializable interface to allow the object to be serialized and deserialized.
 */
public final class Weather implements Serializable {

    private static final Logger logger = LogManager.getLogger(Weather.class);

    /**
     * Default constructor for the Weather class.
     */
    public Weather() {
    }

    /**
     * Constructor for the Weather class.
     *
     * @param lat               the latitude of the location
     * @param lng               the longitude of the location
     * @param avg_tmpr_f        the average temperature in Fahrenheit
     * @param avg_tmpr_c        the average temperature in Celsius
     * @param wthr_date         the date of the weather data
     * @param year              the year of the weather data
     * @param month             the month of the weather data
     * @param day               the day of the weather data
     * @param geohash4Character the geohash value of the location
     */
    public Weather(Double lat, Double lng, Double avg_tmpr_f, Double avg_tmpr_c, String wthr_date, Integer year, Integer month, Integer day, String geohash4Character) {
        this.lat = lat;
        this.lng = lng;
        this.avg_tmpr_f = avg_tmpr_f;
        this.avg_tmpr_c = avg_tmpr_c;
        this.wthr_date = wthr_date;
        this.year = year;
        this.month = month;
        this.day = day;
        this.geohash4Character = geohash4Character;
    }

    private Double lat;
    private Double lng;
    private Double avg_tmpr_f;
    private Double avg_tmpr_c;
    private String wthr_date;
    private Integer year;
    private Integer month;
    private Integer day;
    private String geohash4Character;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getAvg_tmpr_f() {
        return avg_tmpr_f;
    }

    public void setAvg_tmpr_f(Double avg_tmpr_f) {
        this.avg_tmpr_f = avg_tmpr_f;
    }

    public Double getAvg_tmpr_c() {
        return avg_tmpr_c;
    }

    public void setAvg_tmpr_c(Double avg_tmpr_c) {
        this.avg_tmpr_c = avg_tmpr_c;
    }

    public String getWthr_date() {
        return wthr_date;
    }

    public void setWthr_date(String wthr_date) {
        this.wthr_date = wthr_date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getGeohash4Character() {
        return geohash4Character;
    }

    public void setGeohash4Character(String geohash4Character) {
        this.geohash4Character = geohash4Character;
    }

    @Override
    public String toString() {
        return "domain.Weather{" +
                "latitude=" + lat +
                ", longitude=" + lng +
                ", avgTmprF=" + avg_tmpr_f +
                ", avgTmprC=" + avg_tmpr_c +
                ", wthr_Date=" + wthr_date +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", geohash4Character='" + geohash4Character + '\'' +
                '}';
    }


}
