package haughton.dvdstore.config;

import haughton.dvdstore.model.Cart;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@PropertySource("app.properties")
@EnableTransactionManagement
public class AppConfig {
    @Autowired
    private Environment env;

    @Bean
    @Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cart checkOutCounter(){
        return new Cart();
    }

    @Bean
    public Hashids hashids() {
        return new Hashids(env.getProperty("hash.salt"),8);
    }
}
