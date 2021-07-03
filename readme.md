## install kafka

https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-20-04 

## install flink 

https://data-flair.training/blogs/apache-flink-installation-on-ubuntu/

## build and deploy

```bash
rm -rf target  ; mvn package
```

## open flink ui

http://<ip>:8081/#/job-manager/metrics

upload and submit the jar

## create topics

```bash
/home/kafka/kafka_2.13-2.6.2/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic flink_input
/home/kafka/kafka_2.13-2.6.2/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic flink_output
```

## run producer and consumer
```bash
/home/kafka/kafka_2.13-2.6.2/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic flink_input
/home/kafka/kafka_2.13-2.6.2/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic flink_output
```

### references
[java kafka-flink-data-pipeline](https://www.baeldung.com/kafka-flink-data-pipeline)

[apache-flink-installation-on-ubuntu](https://data-flair.training/blogs/apache-flink-installation-on-ubuntu)

[install-apache-kafka-on-ubuntu-20-04](https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-20-04) 



