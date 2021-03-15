package de.exxcellent.challenge;

import commonHelpers.*;
import java.util.Arrays;
import java.util.List;

/**
 * The entry class for your solution. This class is only aimed as starting point
 * and not intended as baseline for your software design. Read: create your own
 * classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * 
     * @param args The CLI arguments passed
     */

    public static void main(String... args) {

        // Set delimiter of the '.csv' file
        final String DELIMITER = ",";

        // Define path where to find the files
        String fileWeather = "src\\main\\resources\\de\\exxcellent\\challenge\\weather.csv";
        String fileFootball = "src\\main\\resources\\de\\exxcellent\\challenge\\football.csv";

        // Read the content of the weather file
        List<String[]> weather = CommonHelpers.readCSV(fileWeather, DELIMITER);

        // Find the index of the smallest temperature spread between the max and min
        // temperature
        int indexSmallestTempSpread = CommonHelpers.calcTwoCols(weather, "MxT", "MnT", "sub", "min", true);

        // Output the day with the smallest temperature spread
        int indexColumnDay = Arrays.asList(weather.get(0)).indexOf("Day");
        if (indexColumnDay != -1 && indexSmallestTempSpread != -1) {
            String dayWithSmallestTempSpread = weather.get(indexSmallestTempSpread)[indexColumnDay];
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);
        } else {
            System.out.println("Error occurs while determining the day with the smallest temperature spread");
        }

        // Read the content of the football file
        List<String[]> football = CommonHelpers.readCSV(fileFootball, DELIMITER);

        // Find the index of the smallest goal spread between the goals and goals
        // allowed
        int indexTeamSmallestDistance = CommonHelpers
                .calcTwoCols(football, "Goals", "Goals Allowed", "sub", "min", true);

        // Output the team with the smallest goal spread
        int indexTeamName = Arrays.asList(football.get(0)).indexOf("Team");
        if (indexTeamSmallestDistance != -1 && indexTeamName != -1) {
            String teamWithSmallestGoalSpread = football.get(indexTeamSmallestDistance)[indexTeamName];
            System.out.printf("Team with smallest goal spread: %s%n", teamWithSmallestGoalSpread);
        } else {
            System.out.println("Error occurs while determining the team with the smallest goal spread");
        }
    }
}
