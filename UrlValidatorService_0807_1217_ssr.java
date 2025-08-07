// 代码生成时间: 2025-08-07 12:17:59
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import java.net.URI;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.config.RequestConfig;
import org.apache.commons.validator.routines.UrlValidator;

@Service
public class UrlValidatorService {

    private final UrlValidator urlValidator;
    private final RestTemplate restTemplate;

    public UrlValidatorService() {
        this.urlValidator = new UrlValidator();
        // Configure the RestTemplate to set the connection timeout and read timeout
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionTimeToLive(10000, TimeUnit.MILLISECONDS)
                .setMaxConnTotal(50)
                .setMaxConnPerRoute(20)
                .build();
        requestFactory.setHttpClient(httpClient);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.setRequestFactory(requestFactory);
    }

    /**
     * Validate a URL.
     *
     * @param url The URL to validate.
     * @return true if the URL is valid, false otherwise.
     */
    public boolean validateUrl(String url) {
        return urlValidator.isValid(url);
    }

    /**
     * Check if a URL is reachable.
     *
     * @param url The URL to check.
     * @return true if the URL is reachable, false otherwise.
     */
    public boolean isUrlReachable(String url) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            // Log the exception (omitted for brevity)
            return false;
        }
    }
}
