package domain;

import java.io.Serializable;

/**
 * A class representing a hotel with its properties, including id, name, country, city,
 * address, latitude, longitude and geohash4Character.
 * Implements Serializable to allow its instances to be serialized and deserialized.
 */
public final class Hotel implements Serializable {

    /**
     * Default constructor.
     */
    public Hotel() {
    }

    /**
     * Constructor to initialize a hotel with the given properties.
     *
     * @param id                the id of the hotel
     * @param name              the name of the hotel
     * @param country           the country where the hotel is located
     * @param city              the city where the hotel is located
     * @param address           the address of the hotel
     * @param latitude          the latitude of the hotel's location
     * @param longitude         the longitude of the hotel's location
     * @param geohash4Character the 4-character geohash code of the hotel's location
     */
    public Hotel(long id, String name, String country, String city, String address, String latitude, String longitude, String geohash4Character) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.geohash4Character = geohash4Character;
    }

    @Override
    public String toString() {
        return "domain.Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", geohash4Character='" + geohash4Character + '\'' +
                '}';
    }

    private long id;
    private String name;
    private String country;
    private String city;
    private String address;
    private String latitude;
    private String longitude;
    private String geohash4Character;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGeohash4Character() {
        return geohash4Character;
    }

    public void setGeohash4Character(String geohash4Character) {
        this.geohash4Character = geohash4Character;
    }
}
