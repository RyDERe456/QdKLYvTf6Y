// 代码生成时间: 2025-09-22 15:25:47
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@Component
public class WebContentFetcher {

    private static final Logger logger = LoggerFactory.getLogger(WebContentFetcher.class);
    private final RestTemplate restTemplate;

    public WebContentFetcher() {
        // Create a RestTemplate with a custom request factory for increased connection timeout
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * Fetches the content of a webpage
     * 
     * @param url The URL of the webpage to fetch
     * @return The fetched content or null if an error occurred
     */
    public String fetchWebContent(String url) {
        try {
            return restTemplate.execute(url, HttpMethod.GET,
                request -> {
                    // No need to set headers in this case
                },
                response -> {
                    // Extract the response body as a String
                    return response.getBody();
                }
            );
        } catch (Exception e) {
            logger.error("Error fetching web content from: " + url, e);
            return null;
        }
    }
}
