package data;

import dal.HashCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashCalculatorTest {

    @Test
    void testSameInputProducesSameHash() throws Exception {
        String text = "بسم الله الرحمن الرحيم";

        String hash1 = HashCalculator.calculateHash(text);
        String hash2 = HashCalculator.calculateHash(text);

        assertEquals(hash1, hash2, "Same input should produce identical MD5 hash");
    }

    @Test
    void testDifferentInputProducesDifferentHash() throws Exception {
        String original = "الحمد لله رب العالمين";
        String edited = "الحمد لله رب العالمين!"; // small edit

        String hashOriginal = HashCalculator.calculateHash(original);
        String hashEdited = HashCalculator.calculateHash(edited);

        assertNotEquals(hashOriginal, hashEdited, "Edited content should change the hash value");
    }

    @Test
    void testEmptyStringHash() throws Exception {
        String empty = "";

        String hash = HashCalculator.calculateHash(empty);

        assertNotNull(hash, "Hash for empty string should not be null");
        assertEquals(32, hash.length(), "MD5 hash should be 32 hex characters long");
    }

    @Test
    void testUnicodeAndArabicContentHash() throws Exception {
        String arabicText = "السَّلَامُ عَلَيْكُمْ";

        String hash = HashCalculator.calculateHash(arabicText);

        assertNotNull(hash, "Hash should not be null for Arabic Unicode content");
        assertEquals(32, hash.length(), "MD5 hash should be 32 hex characters long");
    }

    @Test
    void testHashFormatIsHexadecimal() throws Exception {
        String text = "Test Hash Format 123";

        String hash = HashCalculator.calculateHash(text);

        assertTrue(hash.matches("[0-9A-F]{32}"),
                "MD5 hash should be a 32-character uppercase hexadecimal string");
    }
}