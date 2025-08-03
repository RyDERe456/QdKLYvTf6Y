// 代码生成时间: 2025-08-03 21:41:43
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Component
public class WebContentFetcher {

    private final RestTemplate restTemplate;

    // Constructor
    public WebContentFetcher() {
        // Create a client request factory with a connection timeout and read timeout
        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        // Create the RestTemplate with the client request factory
        this.restTemplate = new RestTemplate(factory);
    }

    /**
     * Fetches web content from a given URL.
     *
     * @param url The URL of the web content to fetch.
     * @param responseProcessor A consumer that processes the ResponseEntity.
     * @param errorProcessor A consumer that processes exceptions.
     */
    public void fetchWebContent(String url,
                              Consumer<ResponseEntity<String>> responseProcessor,
                              Consumer<Exception> errorProcessor) {
        try {
            // Execute the GET request using the RestTemplate
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Process the response if the request was successful
            if (response.getStatusCode().is2xxSuccessful()) {
                responseProcessor.accept(response);
            } else {
                // Handle non-2xx responses
                errorProcessor.accept(new IOException("Failed to fetch web content. HTTP status code: " + response.getStatusCode()));
            }
        } catch (ResourceAccessException e) {
            // Handle resource access issues (e.g., connection timeout)
            errorProcessor.accept(new IOException("Resource access error: " + e.getMessage(), e));
        } catch (Exception e) {
            // Handle any other exceptions
            errorProcessor.accept(e);
        }
    }
}
