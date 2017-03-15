package memorization;

import java.io.Serializable;
import java.util.Random;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;
import speednumbers.mastersofmemory.challenges.presentation.enums.DigitSource;

public class GridData implements Serializable {

    private char[] data;
    public int numRows;
    public int numCols;
    private int numDigitsPerColumn;
    private int numDigits;
    private int maxValidHighlightPosition;
    private DigitSource digitSource;
    public static char empty = '\u0000';

    public GridData(Challenge challenge) {
        this(
            NumberChallenge.getNumDigitsSetting(challenge).getValue(),
            NumberChallenge.getDigitsPerGroupSetting(challenge).getValue(),
            DigitSource.values()[NumberChallenge.getDigitSourceSetting(challenge).getValue()]
        );
    }

    private GridData(int numDigits, int numDigitsPerColumn, DigitSource digitSource)  {
        this.numDigits = numDigits;
        this.numDigitsPerColumn = numDigitsPerColumn;
        this.digitSource = digitSource;
        this.numCols = calculateNumColumns(numDigits, numDigitsPerColumn);
        int numDigitsPerRow = (numCols - 1) * numDigitsPerColumn;
        this.numRows = numDigits / numDigitsPerRow + (numDigits % numDigitsPerRow == 0 ? 0 : 1);
        maxValidHighlightPosition = (numDigits / numDigitsPerColumn) + numRows - 1 + (numDigits % numDigitsPerColumn == 0 ? 0 : 1);
        data = new char[numDigits];
    }

    public void loadData() {
        Random rand = new Random();

        if (digitSource == DigitSource.RANDOM) {
            for (int i=0; i<numDigits; i++) {
                data[i] = Character.forDigit(rand.nextInt(10), 10);
            }
        }
        else if (digitSource == DigitSource.PI) {
            String pi = "1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865132823066470938446095505822317253594081284811174502841027019385211055596446229489549303819644288109756659334461284756482337867831652712019091456485669234603486104543266482133936072602491412737245870066063155881748815209209628292540917153643678925903600113305305488204665213841469519415116094330572703657595919530921861173819326117931051185480744623799627495673518857527248912279381830119491298336733624406566430860213949463952247371907021798609437027705392171762931767523846748184676694051320005681271452635608277857713427577896091736371787214684409012249534301465495853710507922796892589235420199561121290219608640344181598136297747713099605187072113499999983729780499510597317328160963185950244594553469083026425223082533446850352619311881710100031378387528865875332083814206171776691473035982534904287554687311595628638823537875937519577818577805321712268066130019278766111959092164201989";
            data = pi.substring(0, numDigits).toCharArray();
        }
    }

    public String getText(int position) {
        return getValue(position) == null ? "" : getValue(position);
    }

    public int getNumCells() {
        return numRows * numCols;
    }

    public int getMaxValidHighlightPosition() {
        return this.maxValidHighlightPosition;
    }

    public int getNumDigitsAttempted() {
        return this.numDigits;
    }

    public int getRowNumber(int position) {
        return this.getRow(position) + 1;
    }

    public boolean isRowMarker(int position) {
        return getCol(position) == 0;
    }

    protected int getStartIndexFromPosition(int position) {
        int row = getRow(position);
        int col = getCol(position);
        if (col <= 0)
            return -1;

        return ((col - 1) * numDigitsPerColumn) + ((numCols - 1) * numDigitsPerColumn * row);
    }

    public int numDigitsAtCell(int position) {
        int startIndex = getStartIndexFromPosition(position);
        if (startIndex < 0)
            return 0;

        if (startIndex >= data.length) {
            return 0;
        }

        if (startIndex + (numDigitsPerColumn - 1) >= data.length) {
            return data.length - startIndex;
        }
        else {
            return numDigitsPerColumn;
        }
    }

    protected String getValue(int position) {
        int length = numDigitsAtCell(position);
        int startIndex = getStartIndexFromPosition(position);

        if (length > 0 && data[startIndex] == GridData.empty)
            return "";

        if (length == 1 || (length > 1 && data[startIndex+1] == GridData.empty))
            return new String(new char[] { data[startIndex] });

        if (length == 2 || (length > 2 && data[startIndex+2] == GridData.empty))
            return new String(new char[] { data[startIndex], data[startIndex+1] });

        if (length == 3)
            return new String(new char[] { data[startIndex], data[startIndex+1], data[startIndex+2] });

        return "";
    }

    private int leastOf(int a, int b) {
        return a < b ? a : b;
    }

    private int calculateNumColumns(int numDigits, int numDigitsPerColumn) {
        int numChunks = numDigits / numDigitsPerColumn + (numDigits % numDigitsPerColumn == 0 ? 0 : 1);
        if (numDigitsPerColumn == 1) {
            return 1 + leastOf(numChunks, 5);
        }
        if (numDigitsPerColumn == 2) {
            return 1 + leastOf(numChunks, 5);
        }
        if (numDigitsPerColumn == 3) {
            return 1 + leastOf(numChunks, 5);
        }

        return -1;
    }

    public int getCol(int pos) {
        return pos % (numCols);
    }

    public int getRow(int pos) {
        return pos / (numCols);
    }

    public char[] getData() {
        return data;
    }
}
