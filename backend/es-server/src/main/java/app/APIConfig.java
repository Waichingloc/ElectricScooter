package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Set;

//@EnableWebMvc  -- has a side effect of changing JSON LocalDate format, also impacts cors on unmapped paths
@Configuration
public class APIConfig implements WebMvcConfigurer {

    @Value("${jwt.issuer:private company}")
    private String issuer;

    @Value("${jwt.passphrase:This is very secret information for my private encryption key.}")
    private String passphrase;

    @Value("${jwt.duration-of-validity:1200}") // default 20 minutes;
    private int tokenDurationOfValidity;

    public Set<String> SECURED_PATHS =
            Set.of("/scooters");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*", getHostIPAddressPattern(), "http://*.hva.nl:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //.allowedOrigins("*")
                .allowedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .allowCredentials(true)
        ;
    }

    private String getHostIPAddressPattern() {
        try {
            return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":*";
        } catch (UnknownHostException ignored) {
        }
        return "http://192.168.*.*:*";
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

    public void setTokenDurationOfValidity(int tokenDurationOfValidity) {
        this.tokenDurationOfValidity = tokenDurationOfValidity;
    }
}
