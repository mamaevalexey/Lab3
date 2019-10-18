start-dfs.sh
start-dfs.sh



spark-submit --class ru.highload.airports.SparkAirportApp --master yarn-client --num-executors 3 ./target/Lab3-1.0-SNAPSHOT.jar 664600583_T_ONTIME_sample.csv L_AIRPORT_ID.csv output3.0 
