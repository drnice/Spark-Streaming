# Spark-Streaming
Example of spark streaming listening on socket
Client listens on standard in and writes data captured to socket for spark streaming to absorb

This project includes both a the client application (where client awaits input of data from standard in) and pushes data to socket on which Spark Streaming is listening on.

NOTE: All variables are hard coded in this for simplicity sakes.

# Spark Stand alone setup
Download spark http://spark.apache.org/downloads.html
Tested version 1.3 - 1.6 of Spark with this project.

Extract tar ball to directory of your choice

Download scala version 2.10.5
http://downloads.typesafe.com/scala/2.10.5/scala-2.10.5.tgz?_ga=1.7758962.1104547853.1428884173.

Ensure java version 1.7 or greater is installed and JAVA_HOME set.

Set environment variables

export SPARK_HOME=/Users/drice/examples/spark-1.3.0-bin-hadoop2.4/

export SCALA_HOME=/Users/drice/examples/scala-2.10.5

export PATH=$PATH:$SCALA_HOME/bin

Start Spark Master
root# cd spark-1.3.0-bin-hadoop2.4/sbin

root# ./start-master.sh

starting org.apache.spark.deploy.master.Master, logging to /Users/drice/examples/spark-1.3.0-bin-hadoop2.4/sbin/../logs/spark-root-org.apache.spark.deploy.master.Master-1-HW10593.local.out

Once Spark Master is running you should be able to launch this URL from browser (note hostname will be different)
http://hw10593.local:8080/

Start Spark Worker - where HOSTNAME is the name of the master host

root# ./bin/spark-class org.apache.spark.deploy.worker.Worker spark://HOSTNAME:7077 &

#Setting up IDE - in my case Eclipse
Installed Scala perspective - Scala IDE for Eclipse	4.3.0.v-2_11-201512011536-499c89a	org.scala-ide.sdt.feature.feature.group	scala-ide.org

Changed project to include Scala library container to 2.10.6

added library files from spark lib and scala lib



#Run Project
In IDE of choice export JAR file

Then run the below ClientApplication
java -classpath Spark-Streaming.jar DRSpark.streaming.ClientApp

This will launch client application which is waiting for Spark Streaming program to connect

Now in another terminal launch the Spark-Streaming application (note have to change host name)
$SPARK_HOME/bin/spark-submit --class DRSpark.streaming.ScalaFirstStreamingExample --master spark://<HOSTNAME>:7077 Spark-Streaming.jar

At this point the client and spark streaming application will connect over port 9087 (hardcoded in code) and from client any data you write via standard in will get absorb in spark stream.

#Run Project on YARN on HDP Sandox
<b>STEP 1</b>: Launch Client Application

[root@sandbox ~]# java -classpath Spark-Example.jar DRSpark.streaming.ClientApp

This clientapp will await spark stream to connect to port on client application.

<b>STEP 2</b>: From a new terminal Launch Spark streaming application on YARN

[root@sandbox ~]# java -classpath Spark-Example.jar DRSpark.streaming.ClientApp

This clientapp will await spark stream to connect to port on client application.

<b>STEP 3</b>: From the Client Application Terminal wait for spark stream to connect 

should see information like below when spark stream connects to client application

[root@sandbox ~]# java -classpath Spark-Example.jar DRSpark.streaming.ClientApp

Defining new Socket

Waiting for Incoming Connection

Connection Received

Waiting for user to input some data


<b>STEP 4</b>: type sentences with repeating words in client application terminal and hit enter. 

example input: this is a test this is a test this is a test again and again and again


<b>STEP 5</b>: look at Spark Streaming terminal and see similar line as follows

16/06/17 15:50:23 INFO BlockManagerInfo: Added input-0-1466178623400 in memory on sandbox.hortonworks.com:35223 (size: 77.0 B, free: 143.6 MB)

<b>STEP 6</b>: control+c the spark stream to finish the application - understanding the output

16/06/17 15:51:00 INFO DAGScheduler: Job 11 finished: collect at ScalaFirstStreamingExample.scala:70, took 0.132667 s

++++++++++++++++++Start Printing Results++++++++++++++++++

(this,3)

(is,3)

(again,3)

(test,3)

(a,3)

(and,2)

++++++++++++++++++Finished Printing Results++++++++++++++++++


