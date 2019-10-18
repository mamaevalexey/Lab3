package ru.highload.airports;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class SparkAirportApp {
    private static final int AIRPORT_CODE_INDEX = 0;
    private static final int AIRPORT_DESCRIPTION_INDEX = 1;

    private static final int FLIGHT_ORIGIN_AIRPORT_INDEX = 11;
    private static final int FLIGHT_DEST_AIRPORT_INDEX = 14;
    private static final int FLIGHT_DELAY_INDEX = 18;
    private static final int FLIGHT_CANCELLED_INDEX = 19;

    private static final String AIRPORT_CODE_COLUMN_NAME = "Code";
    private static final String FLIGHT_DEST_AIRPORT_COLUMN_NAME = "DEST_AIRPORT_ID";

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flightsLines = sc.textFile(args[0]);
        JavaRDD<String[]> flightsLinesParsed = flightsLines
                .map(CSVParser::makeCols)
                .filter(col -> !col[FLIGHT_DEST_AIRPORT_INDEX].equals(FLIGHT_DEST_AIRPORT_COLUMN_NAME));

        JavaPairRDD<Tuple2<String, String>, FlightStatsKey> flightStatPairs = flightsLinesParsed.mapToPair(
                line -> new Tuple2<>(
                        new Tuple2<>(line[FLIGHT_ORIGIN_AIRPORT_INDEX], line[FLIGHT_DEST_AIRPORT_INDEX]),
                        new FlightStatsKey(line[FLIGHT_DELAY_INDEX], line[FLIGHT_CANCELLED_INDEX])
                )
        );

        JavaPairRDD<Tuple2<String, String>, FlightStatsKey> flightsStatPairsSummarized = flightStatPairs
                .reduceByKey(FlightStatsKey::add);


        JavaRDD<String> airportsLines = sc.textFile(args[1]);
        JavaRDD<String[]> airportsLinesParsed = airportsLines
                .map(CSVParser::makeCols)
                .filter(col -> !col[AIRPORT_CODE_INDEX].equals(AIRPORT_CODE_COLUMN_NAME));



        final Broadcast<Map<String, AirportData>> airportsBroadcasted =
                sc.broadcast(stringAirportDataMap);

    }
}

// Tasks:
// a +
// б +
// в +
// г +
// д +
// е
// ё
// ж