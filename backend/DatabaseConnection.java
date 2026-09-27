package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * DatabaseConnection.java
 * -----------------------
 * Handles MySQL database operations using JDBC.
 *
 * Database password is read from the DB_PASSWORD
 * environment variable so that credentials are not
 * exposed in the source code.
 */
public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ai_design_agent";

    private static final String USER = "root";

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public void saveDesign(String userId,
                           String designType,
                           String requirements,
                           String dimensions,
                           String designPrompt,
                           String imagePath) {

        if (PASSWORD == null || PASSWORD.isEmpty()) {
            System.out.println(
                "[Database] DB_PASSWORD environment variable not configured."
            );
            return;
        }

        String sql = "INSERT INTO designs "
                + "(user_id, design_type, requirements, dimensions, "
                + "design_prompt, generated_image_path, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.setString(2, designType);
            stmt.setString(3, requirements);
            stmt.setString(4, dimensions);
            stmt.setString(5, designPrompt);
            stmt.setString(6, imagePath);

            stmt.executeUpdate();

            System.out.println(
                "[Database] Design record saved successfully."
            );

        } catch (Exception e) {

            System.out.println(
                "[Database] Could not save record: "
                + e.getMessage()
            );
        }
    }
}

// package backend;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;

// /**
//  * DatabaseConnection.java
//  * -----------------------
//  * Handles all MySQL database work using plain JDBC (no ORM / framework,
//  * so it stays simple to understand for a degree project).
//  *
//  * Marathi: Hi class JDBC वापरून MySQL database la connect karte ani
//  * generated design chi details (user, room size, prompt, image path)
//  * ek row mhanun save karte.
//  *
//  * Before running: create the database using database/database.sql,
//  * and update USER / PASSWORD below to match your MySQL installation.
//  */
// public class DatabaseConnection {

//     private static final String URL      = "jdbc:mysql://localhost:3306/ai_design_agent";
//     private static final String USER     = "root";

//     public void saveDesign(String userId, String designType, String requirements,
//                             String dimensions, String designPrompt, String imagePath) {

//         String sql = "INSERT INTO designs "
//                 + "(user_id, design_type, requirements, dimensions, design_prompt, generated_image_path, created_at) "
//                 + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

//         try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//              PreparedStatement stmt = conn.prepareStatement(sql)) {

//             stmt.setString(1, userId);
//             stmt.setString(2, designType);
//             stmt.setString(3, requirements);
//             stmt.setString(4, dimensions);
//             stmt.setString(5, designPrompt);
//             stmt.setString(6, imagePath);

//             stmt.executeUpdate();
//             System.out.println("[Database] Design record saved successfully.");

//         } catch (Exception e) {
//             // We don't stop the whole request if the DB save fails — the user
//             // still gets to see the generated image. We just log the error.
//             System.out.println("[Database] Could not save record: " + e.getMessage());
//         }
//     }
// }
