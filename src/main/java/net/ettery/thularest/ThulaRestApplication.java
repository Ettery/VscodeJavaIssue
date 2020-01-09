package net.ettery.thularest;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.TimeZone;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import net.ettery.configuration.ThulaRestConfiguration;
import net.ettery.thularest.resources.*;

import org.eclipse.jetty.servlets.CrossOriginFilter;

public class ThulaRestApplication extends Application<ThulaRestConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ThulaRestApplication().run(args);
    }

    @Override
    public String getName() {
        return "thularest";
    }

    @Override
    public void initialize(final Bootstrap<ThulaRestConfiguration> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/wwwroot", "/", "index.html"));

        bootstrap.getObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        bootstrap.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void run(final ThulaRestConfiguration configuration, final Environment environment) {

        configureCors(configuration, environment);
        configureDateSetup(configuration, environment);
        registerResources(configuration, environment);
    }

    private void configureDateSetup(final ThulaRestConfiguration configuration, final Environment environment) {
        environment.getObjectMapper().registerModule(new JodaModule());
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        environment.getObjectMapper().setDateFormat(df);
    }

    private void registerResources(final ThulaRestConfiguration configuration, final Environment environment) {
        // Test resource
        environment.jersey().register(new HelloResource());
    }

    private void configureCors(final ThulaRestConfiguration configuration, final Environment environment) {
        
        // Configure CORS
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, configuration.getCorsOriginsAllowed());
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        System.out.println("Allowed CORS Origins: " + configuration.getCorsOriginsAllowed());

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
