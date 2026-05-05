# Fineprint AI - Terms of Service Analyzer

A Spring Boot application that analyzes Terms of Service text for potential red flags using AI.

## Prerequisites

- Java 17
- Maven 3.6+
- OpenAI API Key

## Running the Application

1. Clone the repository
2. Set the OpenAI API key as an environment variable:
   ```bash
   export OPENAI_API_KEY=your-api-key-here
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on http://localhost:8080

## API Endpoints

### POST /fineprint-ai/api/analyze

Analyzes the provided ToS text and returns structured red flags.

**Request:**
- Method: POST
- Content-Type: application/x-www-form-urlencoded
- Parameter: `text` (string, max 10,000 characters)

**Response:**
```json
{
  "overallRiskScore": 7,
  "flaggedClauses": [
    {
      "clauseText": "We may change these terms at any time without notice.",
      "severity": "high",
      "rationale": "Allows unilateral changes without user consent.",
      "comparisonToBaseline": "worse than baseline"
    }
  ],
  "disclaimer": "This is not legal advice."
}
```

**Sample Curl Command:**
```bash
curl -X POST http://localhost:8080/fineprint-ai/api/analyze \
  -d "text=Sample Terms of Service text here."
```

**Error Responses:**
- 400 Bad Request: Invalid input (empty, too long, etc.)
- 500 Internal Server Error: Processing error

## Testing

Run tests with coverage:
```bash
mvn test jacoco:report
```

Coverage report will be in `target/site/jacoco/index.html`

## Project Structure

- `src/main/java/com/fineprintai/tosanalyzer/`: Main application code
  - `controller/`: REST controllers
  - `service/`: Business logic
  - `dto/`: Data transfer objects
- `src/test/java/`: Unit tests
- `src/main/resources/`: Configuration files

## Technologies Used

- Spring Boot 3.2.0
- Spring AI with OpenAI GPT-4o mini
- Thymeleaf (for future UI)
- JUnit 5, Mockito for testing
- JaCoCo for code coverage
- SLF4J for logging
