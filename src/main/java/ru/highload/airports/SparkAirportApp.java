package ru.highload.airports;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

public class SparkAirportApp {
    private static final int AIRPORT_CODE_INDEX = 0;
    private static final int AIRPORT_DESCRIPTION_INDEX = 1;
    private static final int AIRPORT_EXTRA_DESCRIPTION_INDEX = 2;

    private static final int FLIGHT_ORIGIN_AIRPORT_INDEX = 11;
    private static final int FLIGHT_DEST_AIRPORT_INDEX = 14;
    private static final int FLIGHT_DELAY_INDEX = 18;
    private static final int FLIGHT_CANCELLED_INDEX = 19;

    private static final String AIRPORT_CODE_COLUMN_NAME = "Code";
    private static final String FLIGHT_DEST_AIRPORT_COLUMN_NAME = "DEST_AIRPORT_ID";

    private static final String FLIGHT_INPUT_FILE = "664600583_T_ONTIME_sample.csv";
    private static final String AIRPORT_INPUT_FILE = "L_AIRPORT_ID.csv";

    private static boolean notColumnName(int index, String columnName) {
        
    }

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flightsLines = sc.textFile(FLIGHT_INPUT_FILE);
        JavaRDD<String[]> flightsLinesParsed = flightsLines
                .map(CSVParser::makeCols)
                .filter(col -> !col[FLIGHT_DEST_AIRPORT_INDEX].equals(FLIGHT_DEST_AIRPORT_COLUMN_NAME));

        JavaPairRDD<Tuple2<String, String>, FlightStatsValueSerializable> flightStatPairs = flightsLinesParsed.mapToPair(
                line -> new Tuple2<>(
                        new Tuple2<>(line[FLIGHT_ORIGIN_AIRPORT_INDEX], line[FLIGHT_DEST_AIRPORT_INDEX]),
                        new FlightStatsValueSerializable(line[FLIGHT_DELAY_INDEX], line[FLIGHT_CANCELLED_INDEX])
                )
        );

        JavaPairRDD<Tuple2<String, String>, FlightStatsValueSerializable> flightsStatPairsSummarized = flightStatPairs
                .reduceByKey(FlightStatsValueSerializable::add);


        JavaRDD<String> airportsLines = sc.textFile(AIRPORT_INPUT_FILE);
        JavaRDD<String[]> airportsLinesParsed = airportsLines
                .map(CSVParser::makeCols)
                .filter(col -> !col[AIRPORT_CODE_INDEX].equals(AIRPORT_CODE_COLUMN_NAME));

        JavaPairRDD<String, String> airportsPairs = airportsLinesParsed.mapToPair(
                cols -> new Tuple2<>(cols[AIRPORT_CODE_INDEX],
                        cols[AIRPORT_DESCRIPTION_INDEX] + (cols.length == 3 ? cols[AIRPORT_EXTRA_DESCRIPTION_INDEX] : ""))
        );
        Map<String, String> airportsMap = airportsPairs.collectAsMap();

        final Broadcast<Map<String, String>> airportsBroadcast =
                sc.broadcast(airportsMap);

        JavaRDD<String> statsLines = flightsStatPairsSummarized.map(
                pair -> airportsBroadcast.value().get(pair._1._1) + ", " +
                        airportsBroadcast.value().get(pair._1._2) + ", " +
                        pair._2.toString()
        );

        statsLines.saveAsTextFile(args[0]);
    }
}