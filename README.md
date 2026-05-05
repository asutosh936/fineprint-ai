# Fineprint AI - Terms of Service Analyzer

A Spring Boot application that analyzes Terms of Service text for potential red flags using AI.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- OpenAI API Key (get one from [OpenAI Platform](https://platform.openai.com/api-keys))

## Setup and Running the Application

### 1. Clone the Repository
```bash
git clone <repository-url>
cd fineprint-ai
```

### 2. Configure OpenAI API Key

**Option A: Environment Variable (Recommended)**
```bash
export OPENAI_API_KEY=your-actual-openai-api-key-here
```

**Option B: Update application.properties**
Edit `src/main/resources/application.properties` and replace:
```properties
spring.ai.openai.api-key=<REPLACE_WITH_YOUR_API_KEY>
```
with your actual API key:
```properties
spring.ai.openai.api-key=sk-your-actual-api-key-here
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

You should see output like:
```
Tomcat started on port 8080 (http) with context path '/fineprint-ai'
Started TosAnalyzerApplication in X.XXX seconds
```

## Accessing the Web Interface

1. **Open your browser** and navigate to: **http://localhost:8080/fineprint-ai**

2. **You'll see the main page** with:
   - A hero section explaining the service
   - A text area for pasting ToS content
   - An "Analyze ToS" button

## Testing the Application

### Sample Test Case 1: Basic ToS Analysis
1. Copy and paste this sample ToS text into the text area:
```
Terms of Service

1. We reserve the right to change these terms at any time without notice.
2. Your data may be shared with third parties for marketing purposes.
3. You agree to mandatory arbitration and waive your right to sue.
4. We are not responsible for any damages or losses you may incur.
```

2. Click **"Analyze ToS"**

3. **Expected Results:**
   - **Overall Risk Score**: 7-9 (high risk)
   - **Flagged Clauses**: 3-4 concerning clauses identified
   - **Severity Levels**: Mix of HIGH and MEDIUM severity badges
   - **Analysis Details**: Each flagged clause shows rationale and comparison

### Sample Test Case 2: Low-Risk ToS
1. Try this more consumer-friendly ToS:
```
Terms of Service

1. We will notify users 30 days before making significant changes to these terms.
2. Your personal data is encrypted and never sold to third parties.
3. You can opt-out of marketing communications at any time.
4. We provide a 30-day money-back guarantee.
```

2. **Expected Results:**
   - **Overall Risk Score**: 1-3 (low risk)
   - **Flagged Clauses**: Few or none
   - **Severity Levels**: LOW or no flags

### Sample Test Case 3: Error Handling
1. Try submitting an empty form or very long text (>10,000 characters)
2. **Expected Results:** Error message displayed on the same page

## Features

- **AI-Powered Analysis**: Uses GPT-4o mini for intelligent ToS analysis
- **Risk Scoring**: 0-10 scale indicating overall risk level
- **Structured Results**: Detailed breakdown of concerning clauses
- **Severity Classification**: HIGH, MEDIUM, LOW risk levels with color coding
- **Web Interface**: Clean, responsive Bootstrap UI
- **Input Validation**: Prevents empty/long submissions with user feedback
- **Comprehensive Logging**: Detailed logs for debugging and monitoring

## Troubleshooting

### Application Won't Start
- **Check Java Version**: Ensure Java 17+ is installed (`java -version`)
- **Verify API Key**: Confirm your OpenAI API key is correctly set
- **Port Conflict**: If port 8080 is busy, change it in `application.properties`

### Analysis Fails
- **API Key Issues**: Check your OpenAI API key is valid and has credits
- **Network Issues**: Ensure internet connection for OpenAI API calls
- **Rate Limits**: OpenAI has rate limits; wait and retry if exceeded

### UI Issues
- **Clear Browser Cache**: Try refreshing or clearing browser cache
- **Check URL**: Ensure you're accessing `http://localhost:8080/fineprint-ai`

## Architecture

- **Backend**: Spring Boot 3.2.0 with Spring AI
- **Frontend**: Thymeleaf templates with Bootstrap 5
- **AI Model**: GPT-4o mini via OpenAI API
- **Testing**: JUnit 5, Mockito, JaCoCo coverage
- **Build**: Maven with Spring Boot plugin
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
