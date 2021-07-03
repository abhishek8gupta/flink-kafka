package com.aaa.samples.flinkkafkasample;/*
 * @created 01/07/2021 - 6:15 PM
 * @author abhigup4
 */

import java.util.Properties;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkKafkaStream {
    private static final Logger LOG = LoggerFactory.getLogger(FlinkKafkaStream.class);
    public static void main(String[] args) throws Exception{
        try {
            String inputTopic = "flink_input";
            String outputTopic = "flink_output";
            String consumerGroup = "baeldung";
            String address = "localhost:9092";
            StreamExecutionEnvironment environment = StreamExecutionEnvironment
                .getExecutionEnvironment();
            FlinkKafkaConsumer<String> flinkKafkaConsumer = createStringConsumerForTopic(
                inputTopic, address, consumerGroup);
            DataStream<String> stringInputStream = environment
                .addSource(flinkKafkaConsumer);

            FlinkKafkaProducer flinkKafkaProducer = createStringProducer(
                outputTopic, address);

            stringInputStream
                .map(new WordsCapitalizer())
                .addSink(flinkKafkaProducer);

            environment.execute("CustomerRegistrationApp");
        }catch (Exception ex){
            LOG.error(ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }


    public static FlinkKafkaConsumer<String> createStringConsumerForTopic(
        String topic, String kafkaAddress, String kafkaGroup ) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
            topic, new SimpleStringSchema(), props);

        return consumer;
    }

    public static FlinkKafkaProducer createStringProducer(
        String topic, String kafkaAddress){

        return new FlinkKafkaProducer(kafkaAddress,
            topic, new SimpleStringSchema());
    }
}
