package adsl;

import domain.Hotel;
import domain.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.sql.*;

import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.lit;

/**
 * The ADLSReader class reads datasets of hotel and weather data from Azure Data Lake Storage.
 * It uses SparkSession to read the datasets and returns a Dataset of Hotel or Weather objects.
 */
public class ADLSReaderWriter {

    private static final Logger logger = LogManager.getLogger(ADLSReaderWriter.class);
    private static Map<String, String> optionsMap = new HashMap<String, String>();

    private static String columnName = "geohash4Character";
    private static Column columnToAdd = lit(null);

    static {
        optionsMap.put("header", "true");
        optionsMap.put("inferSchema", "true");
    }


    /**
     * Reads a dataset of hotel data from Azure Data Lake Storage.
     *
     * @param spark    the SparkSession object used to read the dataset
     * @param filePath the path to the dataset in Azure Data Lake Storage
     * @return a Dataset of Hotel objects
     */
    public static Dataset<Hotel> readHotelsDF(SparkSession spark, String filePath) {
        Dataset<Hotel> dataset = spark.read()
                .options(optionsMap)
                .csv(filePath)
                .withColumn(columnName, columnToAdd)
                .as(Encoders.bean(Hotel.class));
        return dataset;
    }

    /**
     * Reads a dataset of weather data from Azure Data Lake Storage.
     *
     * @param spark    the SparkSession object used to read the dataset
     * @param filePath the path to the dataset in Azure Data Lake Storage
     * @return a Dataset of Weather objects
     */
    public static Dataset<Weather> readWeatherDF(SparkSession spark, String filePath) {
        Dataset<Weather> dataset = spark.read()
                .options(optionsMap)
                .parquet(filePath)
                .withColumn(columnName, columnToAdd)
                .as(Encoders.bean(Weather.class));
        return dataset;
    }

    /**
     * Writes a given dataset to a specified location in Azure Data Lake Storage Gen2 in Parquet format.
     *
     * @param dataset The dataset to write
     */
    public static void writeDatasetToBlob(Dataset<Row> dataset) {
        // Specify the location of the ADLS Gen2 storage account
        String storageAccountName = "stdevelopmentwesteurope";
        String container = "data";
        String path = "weather_hotel";

        // Set the URL and path to write the output data.
        String outputPathAndUrl = "abfss://" + container + "@" + storageAccountName + ".dfs.core.windows.net" + "/" + path;

        String accessKey = "2J4hxKH7p9sQMqYSrF3OCBT+3EZPibV2hQ9yEbC5/G3GhuKJLrM8drLez7s4QBcL1nmmm4l9/jEg+ASt0a8a1A==";
        String accessPropertyName = "fs.azure.account.key.stdevelopmentwesteurope.dfs.core.windows.net";

        // Write the enriched data to ADLS Gen2 as partitioned Parquet files
        try {
            logger.info("Writing data to blob: {}", outputPathAndUrl);
            dataset.write()
                    .partitionBy("year", "month", "day")
                    .option("path", outputPathAndUrl)
                    .format("parquet")
                    .mode("overwrite")
                    .option(accessPropertyName, accessKey)
                    .save();
            logger.info("Data written successfully to blob: {}", outputPathAndUrl);
        } catch (Exception e) {
            logger.error("Failed to write data to blob: {}", outputPathAndUrl, e);
        }
    }

}
