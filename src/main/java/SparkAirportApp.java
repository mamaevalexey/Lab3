import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkAirportApp {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> fligtsFile = sc.textFile(args[0]);
        JavaRDD<String> airportsFile = sc.textFile(args[1]);

        

    }
}

// Tasks:
// a +
// б
// в
// г
// д
// е
// ё
// ж