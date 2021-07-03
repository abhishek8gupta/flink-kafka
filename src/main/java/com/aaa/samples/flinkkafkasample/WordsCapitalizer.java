package com.aaa.samples.flinkkafkasample;/*
 * @created 01/07/2021 - 5:12 PM
 * @author abhigup4
 */

import org.apache.flink.api.common.functions.MapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordsCapitalizer implements MapFunction<String, String> {
    private static final Logger LOG = LoggerFactory.getLogger(WordsCapitalizer.class);
    @Override
    public String map(String s) {
        LOG.info("reading : {} ", s);
        return
            s.toUpperCase();
    }
}