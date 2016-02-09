package DRSpark.streaming


import org.apache.spark.streaming.dstream.ForEachDStream
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel._
import org.apache.spark.rdd.RDD



object ScalaFirstStreamingExample {
  
  def main(args: Array[String]){
    println("Creating Spark Configuration")
    
    //Create an Object of Spark Configuration
    val sconf = new SparkConf()
    
    //Set user defined Name of this Application
    sconf.setAppName("Example Spark Streaming application")

    println("Retreiving Streaming Context from Spark Conf")
    
    /* Second parameter is the time interval at which 
     * streaming data will be divided into batches*/  
    val streamCtx = new StreamingContext(sconf, Seconds(2))

    /* Next we define the type of Stream using TCP 
     * Socket as text stream, It will keep watching for 
     * the incoming data from a specific machine (first arg)
     * and port 9087 (second arg) Once the data is 
     * retrieved it will be saved in the memory 
     * and in case memory is not sufficient, then it 
     * will store it on the Disk. It will further read the 
     * Data and convert it into DStream*/
    val lines = streamCtx.socketTextStream("localhost", 9087, MEMORY_AND_DISK_SER_2)

    
    /* Apply the Split() function to elements of DStream 
     * which will generate multiple net new records from 
     * each record in Source Stream and then use flatmap 
     * to consolidate all records and create a new DStream.*/
    val words = lines.flatMap(x => x.split(" "))
    
    /* Now, we will count these words by using map()
     * map() helps in applying a given function to each
     * element in an RDD. */
    val pairs = words.map(word => (word, 1))
    
    /* Further we will aggregate the value of each key 
     * by using/applying the given function.*/
    val wordCounts = pairs.reduceByKey(_ + _)
    
    //Lastly we will print all Values
    printValues(wordCounts,streamCtx)
    
    //initiate the Streaming Context
    streamCtx.start();
    //Wait till the execution is completed.
    streamCtx.awaitTermination();  

  }
  
  def printValues(stream:DStream[(String,Int)],streamCtx: StreamingContext){
    stream.foreachRDD(foreachFunc)
    def foreachFunc = (rdd: RDD[(String,Int)]) => {
      val array = rdd.collect()
      println("++++++++++++++++++Start Printing Results++++++++++++++++++")
      for(res<-array){
        println(res)
      }
      println("++++++++++++++++++Finished Printing Results++++++++++++++++++")
    }
  }
}