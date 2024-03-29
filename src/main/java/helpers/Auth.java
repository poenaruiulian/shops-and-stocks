package main.java.helpers;

import main.java.constants.StringConstants;
import main.java.data.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is used to make the authentication process.
 */
public class Auth {
    /**
     * This function verifies if a user is logged in or not.
     * @return true if a user is logged in  and false if a user is logged out
     */
    static public boolean isUserLogged() {
        boolean response = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(StringConstants.currentUserFile));
            String fileRow = reader.readLine();
            if (fileRow != null) {
                response = !fileRow.isEmpty();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * This function returns the current logged user.
     * @return a Person object representing the current logged user
     */
    static public Person getCurrentUser() {
        Person currentUser = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(StringConstants.currentUserFile));
            String currentUserId = reader.readLine();
            reader.close();

            currentUser = FileProcessing.getUserFromFile(Integer.parseInt(currentUserId));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return currentUser;

    }

    /**
     * This user logs a user using his first name and last name.
     * @param firstName The first name inputed by the user
     * @param lastName The last name inputed by the user
     * @return The current user, or null if it is not found in the file of users.
     */
    static public Person loggInUser(String firstName, String lastName) {
        Person currentUser = null;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(StringConstants.userFile));
            String fileRow = csvReader.readLine();
            while ((fileRow = csvReader.readLine()) != null) {
                String[] data = fileRow.split(",");
                if (Objects.equals(data[2], firstName) && Objects.equals(data[3], lastName)) {
                    currentUser = FileProcessing.getUserFromFile(Integer.parseInt(data[0]));
                    break;
                }
            }
            csvReader.close();
            if (currentUser != null) {
                FileWriter writer = new FileWriter(StringConstants.currentUserFile);
                writer.append(String.valueOf(currentUser.getUserID()));
                writer.flush();
                writer.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentUser;
    }

    /**
     * Clears the current user from the file holding the id of the current user.
     */
    static public void logOut() {
        try {
            FileWriter writer = new FileWriter(StringConstants.currentUserFile);
            writer.append("");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
