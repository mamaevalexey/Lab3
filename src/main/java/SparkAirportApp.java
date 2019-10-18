import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class SparkAirportApp {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> fligtsLines = sc.textFile(args[0]);
        JavaRDD<String> airportsLines = sc.textFile(args[1]);

        JavaRDD<String[]> parsedFLightsLines = fligtsLines.map(line ->
                line.replaceAll("\"", "").
                        split(","));
        parsedFLightsLines = parsedFLightsLines.filter(col -> !col[14].equals("DEST_AIRPORT_ID"));

        JavaRDD<String[]> parsedAirportsLines = airportsLines.map(line ->
                line.replaceAll("\"", "").
                        split(","));
        parsedAirportsLines = parsedAirportsLines.filter(col -> !col[0].equals("DEST_AIRPORT_ID"));

        JavaPairRDD<Tuple2<String, String>, Integer> flightStatPairs = fligtsLines.mapToPair(
                line -> new Tuple2<>(
                        new Tuple2<>()
                )
        )

    }
}

// Tasks:
// a +
// б +
// в +-
// г +
// д +-
// е
// ё
// ж