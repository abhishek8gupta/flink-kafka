package com.aaa.samples.flinkkafkasample;

import java.util.Objects;
import java.util.Properties;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	private static final ObjectMapper OM = new ObjectMapper();

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("group.id", "customerAnalytics");
		LOG.info("Properties set {}", properties);

		FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>("customer.create", new SimpleStringSchema(), properties);
		DataStream<String> stream = see.addSource(kafkaSource);

		LOG.info("stream created, {}", stream);

		SingleOutputStreamOperator<String> customerPerCountryStream = stream.map(data -> {
			try {
				return data;
//				return OM.readValue(data, String.class);
			} catch (Exception e) {
				LOG.info("exception reading data: " + data);
				return null;
			}
		}).filter(Objects::nonNull);

//		DataStream<Tuple2<String, Long>> result = customerPerCountryStream.timeWindowAll(Time.seconds(5))
//				.aggregate(new CustomerAggregatorByCountry());

		customerPerCountryStream.print();

		see.execute("CustomerRegistrationApp");
	}
}
