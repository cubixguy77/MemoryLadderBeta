package speednumbers.mastersofmemory.com.presentation;

import org.junit.Test;

import playChallenge.writtenNumbersChallenge.memorization.GridData;
import selectChallenge.viewChallengeCard.challengeSettings.digitsource.DigitSource;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GridDataTest {

    @Test
    public void GridData_tenDigits_oneDigitPerCell_decimal() {
        GridData data = new GridData(10, 1, DigitSource.DECIMAL);
        data.loadData();

        assertEquals(2, data.numRows);
        assertEquals(6, data.numCols);
        assertEquals(12, data.getNumCells());
        assertEquals(11, data.getMaxValidHighlightPosition());
        assertEquals(10, data.getNumDigitsAttempted());
        assertEquals(1, data.getRowNumber(0));
        assertEquals(2, data.getRowNumber(6));
        assertEquals(true, data.isRowMarker(0));
        assertEquals(true, data.isRowMarker(6));
        assertEquals(false, data.isRowMarker(5));
        assertEquals("", data.getText(0));
        assertEquals("0", data.getText(1));
        assertEquals("1", data.getText(2));
        assertEquals("2", data.getText(3));
        assertEquals("3", data.getText(4));
    }

    @Test
    public void GridData_tenDigits_twoDigitsPerCell_decimal() {
        GridData data = new GridData(10, 2, DigitSource.DECIMAL);
        data.loadData();

        assertEquals(1, data.numRows);
        assertEquals(6, data.numCols);
        assertEquals(6, data.getNumCells());
        assertEquals(5, data.getMaxValidHighlightPosition());
        assertEquals(10, data.getNumDigitsAttempted());
        assertEquals(1, data.getRowNumber(0));
        assertEquals(1, data.getRowNumber(5));
        assertEquals(true, data.isRowMarker(0));
        assertEquals(false, data.isRowMarker(1));
        assertEquals("", data.getText(0));
        assertEquals("01", data.getText(1));
        assertEquals("23", data.getText(2));
        assertEquals("45", data.getText(3));
        assertEquals("67", data.getText(4));
    }

    @Test
    public void GridData_tenDigits_threeDigitsPerCell_decimal() {
        GridData data = new GridData(10, 3, DigitSource.DECIMAL);
        data.loadData();

        assertEquals(1, data.numRows);
        assertEquals(5, data.numCols);
        assertEquals(5, data.getNumCells());
        assertEquals(4, data.getMaxValidHighlightPosition());
        assertEquals(10, data.getNumDigitsAttempted());
        assertEquals(1, data.getRowNumber(0));
        assertEquals(1, data.getRowNumber(4));
        assertEquals(true, data.isRowMarker(0));
        assertEquals(false, data.isRowMarker(2));
        assertEquals("", data.getText(0));
        assertEquals("012", data.getText(1));
        assertEquals("345", data.getText(2));
        assertEquals("678", data.getText(3));
        assertEquals("9",   data.getText(4));
    }

    @Test
    public void GridData_thirtyDigits_threeDigitsPerCell_decimal() {
        GridData data = new GridData(30, 3, DigitSource.DECIMAL);
        data.loadData();

        assertEquals(2, data.numRows);
        assertEquals(6, data.numCols);
        assertEquals(12, data.getNumCells());
        assertEquals(11, data.getMaxValidHighlightPosition());
        assertEquals(30, data.getNumDigitsAttempted());
        assertEquals(1, data.getRowNumber(0));
        assertEquals(2, data.getRowNumber(6));
        assertEquals(true, data.isRowMarker(0));
        assertEquals(true, data.isRowMarker(6));
        assertEquals(false, data.isRowMarker(8));
        assertEquals("", data.getText(0));
        assertEquals("012", data.getText(1));
        assertEquals("345", data.getText(2));
        assertEquals("678", data.getText(3));
        assertEquals("901", data.getText(4));
    }

    @Test
    public void GridData_tenDigits_threeDigitsPerCell_binary() {
        GridData data = new GridData(10, 3, DigitSource.BINARY);
        data.loadData();

        assertEquals("", data.getText(0));
        assertEquals("010", data.getText(1));
        assertEquals("101", data.getText(2));
        assertEquals("010", data.getText(3));
        assertEquals("1",   data.getText(4));
    }
}