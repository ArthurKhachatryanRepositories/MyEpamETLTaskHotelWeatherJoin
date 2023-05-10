import adsl.ADLSReaderWriter;
import domain.Hotel;
import domain.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import utils.DatasetUtils;

/**
 * The main class for an ETL (Extract, Transform, Load) job that analyzes hotel and weather data.
 * This job reads data from Azure Data Lake Storage and performs a join operation between the two datasets
 * based on their geohash values. The resulting dataset includes information about hotels and the weather
 * conditions in their locations for a given day.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String HOTELS_PATH = "abfss://m06sparkbasics@bd201stacc.dfs.core.windows.net/hotels";
    private static final String WEATHER_PATH = "abfss://m06sparkbasics@bd201stacc.dfs.core.windows.net/weather";
    private static final String GEOCODING_API_KEY = "c90f95b84b424606bc2bf7d34cc9ecdf";
    private static final String appName = "Analyzing hotel data";
    private static final String storageAccountName = "bd201stacc";
    private static final String fileSystemName = "your_file_system_name";
    private static final String clientId = "f3905ff9-16d4-43ac-9011-842b661d556d";
    private static final String clientSecret = "AkI8Q~AocBjw2~R3rMi-b-2VIlsFzNTiD6kTGcv7";
    private static final String tenantId = "b41b72d0-4e9f-4c26-8a69-f949f367c91d";

    /**
     * The main method for the ETL job.
     * It reads hotel and weather data from Azure Data Lake Storage (ADLS),
     * applies geohashing on the data,
     * joins the two datasets using the geohash4Character column,
     * and selects the relevant columns for the weather and hotel data.
     *
     * @param args An array of strings containing command line arguments
     */
    public static void main(String[] args) {
        logger.info("ETL job starts");

        SparkConf conf = new SparkConf()
                .setAppName(appName)
                .setMaster("local[*]")
                .set("fs.azure.account.auth.type." + storageAccountName + "." + "dfs.core.windows.net", "OAuth")
                .set("fs.azure.account.oauth.provider.type." + storageAccountName + "." + "dfs.core.windows.net", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider")
                .set("fs.azure.account.oauth2.client.id." + storageAccountName + "." + "dfs.core.windows.net", clientId)
                .set("fs.azure.account.oauth2.client.secret." + storageAccountName + "." + "dfs.core.windows.net", clientSecret)
                .set("fs.azure.account.oauth2.client.endpoint." + storageAccountName + "." + "dfs.core.windows.net", "https://login.microsoftonline.com/" + tenantId + "/oauth2/token")
                .set("fs.azure.account.key." + storageAccountName + ".dfs.core.windows.net", "2J4hxKH7p9sQMqYSrF3OCBT+3EZPibV2hQ9yEbC5/G3GhuKJLrM8drLez7s4QBcL1nmmm4l9/jEg+ASt0a8a1A==");

        SparkSession spark = SparkSession.builder()
                .config(conf)
                .getOrCreate();
        logger.info("Spark session is created");

        Dataset<Hotel> hotelsDataset = ADLSReaderWriter.readHotelsDF(spark, HOTELS_PATH);
        logger.info("Dataset<Hotel> hotelsDataSet is created");

        Dataset<Hotel> hotelsWithGeohash = DatasetUtils.mapHotelToFillGeohash(hotelsDataset);
        logger.info("show hotelsWithGeohash");

        Dataset<Weather> weatherDataSet = ADLSReaderWriter.readWeatherDF(spark, WEATHER_PATH);

        Dataset<Weather> weatherWithGeohash = DatasetUtils.mapWeatherToFillGeohash(weatherDataSet);
        logger.info("show  weatherWithGeohash");

        Dataset<Row> weatherHotel = weatherWithGeohash.join(
                hotelsWithGeohash,
                weatherWithGeohash.col("geohash4Character").equalTo(hotelsWithGeohash.col("geohash4Character")),
                "left"
        ).select(
                weatherWithGeohash.col("avg_tmpr_c"),
                weatherWithGeohash.col("avg_tmpr_f"),
                weatherWithGeohash.col("day"),
                weatherWithGeohash.col("month"),
                weatherWithGeohash.col("wthr_date"),
                weatherWithGeohash.col("year"),
                hotelsWithGeohash.col("address"),
                hotelsWithGeohash.col("city"),
                hotelsWithGeohash.col("country"),
                hotelsWithGeohash.col("geohash4Character"),
                hotelsWithGeohash.col("latitude"),
                hotelsWithGeohash.col("id"),
                hotelsWithGeohash.col("longitude"),
                hotelsWithGeohash.col("name")
        );
        logger.info("show  weatherHotel");

        ADLSReaderWriter.writeDatasetToBlob(weatherHotel);
        spark.close();
    }

}
