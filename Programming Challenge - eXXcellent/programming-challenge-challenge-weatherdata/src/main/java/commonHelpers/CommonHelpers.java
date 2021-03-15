package commonHelpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommonHelpers {
    /**
     * The purpose of the function is to read a file from a specified path and store
     * the content in an List<String[]> object.
     * 
     * @param filePath  Path of the file which should be read in.
     * @param delimiter Delimiter of the file. E. g.: {'.', ',', ';'}
     * @return Returns an List<String[]> object where the content of the
     *         file is stored.
     * @author Hochweiss Alexander <a.hochweiss@gmx.de>
     */
    public static List<String[]> readCSV(String filePath, String delimiter) {
        // Initialize
        BufferedReader reader = null;
        String line = "";
        List<String[]> content = new ArrayList<>();

        // read file line by line
        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                // Store the file content into a list of String arrays
                content.add(line.split(delimiter));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // close previous opened buffered reader
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * The purpose of the function is to perform a basic arithmetic operation of two
     * columns from a List<String[]> object.
     * <p>
     * Note:
     * <ul>
     * <li>In case of non integer values in the columns an exceptions will be
     * thrown.</li>
     * <li>In case of more than one min or max values, always the first one will be
     * given.</li>
     * </ul>
     * 
     * @param content         List from type <String> where the values can be found
     *                        on which the arithmetic operation should be performed
     * @param colName1        Name of the first column to perform the arithmetic
     *                        operation
     * @param colName2        Name of the second column to perform the arithmetic
     *                        operation
     * @param operationType   Type of the basic arithmetic operation. Use 'add',
     *                        'sub', 'mul' or 'div' for 'addition', 'subtraction',
     *                        'multiplication' or 'division'.
     * @param aggregationType Type of the calculation. Use 'max' or 'min' give the
     *                        'maximum' or the 'minimum' value.
     * @param absolute        Flag if the <aggregationType> has to be performed on
     *                        absolute values or not.
     * @return Index of the <aggregationType> value based on the performed
     *         arithmetic operation. In case of errors -1 will be returned.
     * @author Hochweiss Alexander <a.hochweiss@gmx.de>
     */
    public static int calcTwoCols(List<String[]> content, String colName1, String colName2, String operationType,
            String aggregationType, Boolean absolute) {
        // Initialize
        List<Integer> calculation = new ArrayList<>();
        int indexColName1 = Arrays.asList(content.get(0)).indexOf(colName1);
        int indexColName2 = Arrays.asList(content.get(0)).indexOf(colName2);

        // Error handling
        if (indexColName1 == -1) {
            System.out.println(
                    "Error occurs while trying to calculate the calculation. There is no column with the name: "
                            + colName1);
            return -1;
        }

        if (indexColName2 == -1) {
            System.out.println(
                    "Error occurs while trying to calculate the calculation. There is no column with the name: "
                            + colName2);
            return -1;
        }

        // Perform basic arithmetic operation for each row on column1 and column2
        switch (operationType) {
        // Perform operation: 'Addition'
        case "add":
            for (int i = 1; i < content.size(); i++) {
                try {
                    calculation.add(Integer.parseInt(content.get(i)[indexColName1])
                            + Integer.parseInt(content.get(i)[indexColName2]));
                } catch (Exception e) {
                    System.out.println("The value at row " + i + " in one of the provided columns is not an integer!");
                    System.out.println("Please check the content of your file!");
                    return -1;
                }
            }
            break;
        // Perform operation: 'Subtraction'
        case "sub":
            for (int i = 1; i < content.size(); i++) {
                try {
                    calculation.add(Integer.parseInt(content.get(i)[indexColName1])
                            - Integer.parseInt(content.get(i)[indexColName2]));
                } catch (Exception e) {
                    System.out.println("The value at row " + i + " in one of the provided columns is not an integer!");
                    System.out.println("Please check the content of your file!");
                    return -1;
                }
            }
            break;
        // Perform operation: 'Multiplication'
        case "mul":
            for (int i = 1; i < content.size(); i++) {
                try {
                    calculation.add(Integer.parseInt(content.get(i)[indexColName1])
                            * Integer.parseInt(content.get(i)[indexColName2]));
                } catch (Exception e) {
                    System.out.println("The value at row " + i + " in one of the provided columns is not an integer!");
                    System.out.println("Please check the content of your file!");
                    return -1;
                }
            }
            break;
        // Perform operation: 'Division'
        case "div":
            for (int i = 1; i < content.size(); i++) {
                if (Integer.parseInt(content.get(i)[indexColName2]) != 0) {
                    try {
                        calculation.add(Integer.parseInt(content.get(i)[indexColName1])
                                / Integer.parseInt(content.get(i)[indexColName2]));
                    } catch (Exception e) {
                        System.out.println(
                                "The value at row " + i + " in one of the provided columns is not an integer!");
                        System.out.println("Please check the content of your file!");
                        return -1;
                    }
                } else {
                    System.out.println("The value at row " + i + " in " + colName2 + " is zero!");
                    System.out.println("Please check the content of your file!");
                    return -1;
                }
            }
            break;
        // Error handling: Invalid operationType
        default:
            System.out.println(
                    "Invalid parameter: 'operationType'. Please use one of the following ['add', 'sub', 'mul', 'div']");
            return -1;
        }

        if (absolute == true) {
            // transform each value to the absolute value
            calculation.replaceAll(value -> Math.abs(value));
            if (aggregationType == "min") {
                // return the index of the smallest absolute value
                return calculation.indexOf(Collections.min(calculation)) + 1;
            } else if (aggregationType == "max") {
                // return the index of the biggest absolute value
                return calculation.indexOf(Collections.max(calculation)) + 1;
            } else {
                System.out.println(
                        "Invalid parameter: 'aggregationType'. Please use one of the following ['min', 'max']");
                return -1;
            }
        } else {
            if (aggregationType == "min") {
                // return the index of the smallest value
                return calculation.indexOf(Collections.min(calculation)) + 1;
            } else if (aggregationType == "max") {
                // return the index of the biggest value
                return calculation.indexOf(Collections.max(calculation)) + 1;
            } else {
                System.out.println(
                        "Invalid parameter: 'aggregationType'. Please use one of the following ['min', 'max']");
                return -1;
            }
        }
    }
}
