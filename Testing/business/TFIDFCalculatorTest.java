package business;

import dal.TFIDFCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TFIDFCalculatorTest {

    private TFIDFCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new TFIDFCalculator();

        // Add sample Arabic documents to corpus
        calculator.addDocumentToCorpus("بسم الله الرحمن الرحيم");
        calculator.addDocumentToCorpus("الحمد لله رب العالمين");
        calculator.addDocumentToCorpus("الرحمن الرحيم");
    }

    @Test
    void testTfIdfWithValidArabicDocument() {
        String document = "بسم الله الرحمن الرحيم";

        double tfIdfScore = calculator.calculateDocumentTfIdf(document);

        // TF-IDF score should be a finite number
        assertFalse(Double.isNaN(tfIdfScore), "TF-IDF score should not be NaN");
        assertFalse(Double.isInfinite(tfIdfScore), "TF-IDF score should not be infinite");

        // Score should be >= 0 (log-based IDF may produce small negatives, but total should be reasonable)
        assertTrue(tfIdfScore >= -1.0, "TF-IDF score should be within a reasonable range");
    }

    @Test
    void testTfIdfWithEmptyDocument() {
        String document = "";

        double tfIdfScore = calculator.calculateDocumentTfIdf(document);

        assertFalse(Double.isNaN(tfIdfScore), "TF-IDF score for empty document should not be NaN");
        assertFalse(Double.isInfinite(tfIdfScore), "TF-IDF score for empty document should not be infinite");
    }

    @Test
    void testTfIdfWithSpecialCharactersOnly() {
        String document = "@@@ ### !!!";

        double tfIdfScore = calculator.calculateDocumentTfIdf(document);

        assertFalse(Double.isNaN(tfIdfScore), "TF-IDF score for special characters should not be NaN");
        assertFalse(Double.isInfinite(tfIdfScore), "TF-IDF score for special characters should not be infinite");
    }

    @Test
    void testTfIdfWithSingleWordDocument() {
        String document = "الرحمن";

        double tfIdfScore = calculator.calculateDocumentTfIdf(document);

        assertFalse(Double.isNaN(tfIdfScore));
        assertFalse(Double.isInfinite(tfIdfScore));
    }

    @Test
    void testTfIdfChangesWhenCorpusChanges() {
        String document = "الرحمن الرحيم";

        double scoreBefore = calculator.calculateDocumentTfIdf(document);

        // Add a new document containing same words to change IDF
        calculator.addDocumentToCorpus("الرحمن الرحيم");

        double scoreAfter = calculator.calculateDocumentTfIdf(document);

        // TF-IDF score should change when corpus changes
        assertNotEquals(scoreBefore, scoreAfter, "TF-IDF score should change when corpus is updated");
    }
}
