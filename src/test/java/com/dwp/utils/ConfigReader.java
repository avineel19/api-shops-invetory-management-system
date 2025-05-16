package com.dwp.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    public static class Config {
        public String baseUrl;
        public Credentials credentials;

        public static class Credentials {
            public String username;
            public String password;
        }
    }

    private static Config config;
    static {
        try(InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.yaml")) {
            LoaderOptions options = new LoaderOptions();
            Yaml yaml = new Yaml(new Constructor(Config.class, options));
            config = yaml.load(in);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    public static String getBaseUrl() {
        return config.baseUrl;
    }
    public static String getUserName() {
        return config.credentials.username;
    }
    public static String getPassword() {
        return config.credentials.password;
    }
}
