// 代码生成时间: 2025-10-11 02:39:22
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
@RestController
@RequestMapping("/api/medical-data")
public class MedicalDataMiningService {

    private static final Logger logger = LoggerFactory.getLogger(MedicalDataMiningService.class);

    // Dependency injection for data repository
    private final MedicalDataRepository medicalDataRepository;

    @Autowired
    public MedicalDataMiningService(MedicalDataRepository medicalDataRepository) {
        this.medicalDataRepository = medicalDataRepository;
    }

    /**
     * Handles GET requests to mine medical data.
     *
     * @param criteria Search criteria as request parameter.
     * @return A list of mined medical data.
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MedicalData>> mineData(@RequestParam String criteria) {
        try {
            return ResponseEntity.ok(
                medicalDataRepository.findByCriteria(criteria)
            );
        } catch (Exception e) {
            logger.error("Error mining medical data: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred while mining medical data.");
        }
    }

    /**
     * Global exception handler for this service.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception occurred: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error: " + ex.getMessage());
    }
}
