package ru.javawebinar.topjava;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;

public class Profiles implements
        ApplicationContextInitializer {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = JDBC;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String profile = System.getProperty("profile");

        if (profile==null || profile.equalsIgnoreCase(DATAJPA)){
            applicationContext.getEnvironment().setActiveProfiles(DATAJPA);
        }else if(profile.equalsIgnoreCase(JPA)){
            applicationContext.getEnvironment().setActiveProfiles(JPA);
        }else if(profile.equalsIgnoreCase(JDBC)){
            applicationContext.getEnvironment().setActiveProfiles(JDBC);
        }
    }
}
