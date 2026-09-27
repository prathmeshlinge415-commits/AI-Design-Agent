package backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageGenerator {

    private static final String API_KEY = System.getenv("HF_TOKEN");

    private static final String API_URL =
            "https://router.huggingface.co/fal-ai/fal-ai/flux/schnell";

    public String generateImage(String prompt) {

        String outputDir = "generated-images";

        try {
            File dir = new File(outputDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            if (API_KEY == null || API_KEY.isBlank()) {
                System.out.println("[Image Generator] HF_TOKEN not configured.");
                return createPlaceholder(outputDir);
            }

            System.out.println("[DEBUG] Hugging Face token loaded: true");

            URL url = URI.create(API_URL).toURL();

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty(
                    "Authorization",
                    "Bearer " + API_KEY
            );

            connection.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            String jsonBody =
                    "{"
                    + "\"prompt\":\"" + escapeJson(prompt) + "\","
                    + "\"image_size\":\"square_hd\","
                    + "\"num_images\":1"
                    + "}";

            connection.getOutputStream().write(
                    jsonBody.getBytes(StandardCharsets.UTF_8)
            );

            int status = connection.getResponseCode();

            System.out.println("[DEBUG] HTTP Status: " + status);

            InputStream responseStream;

            if (status >= 400) {
                responseStream = connection.getErrorStream();
            } else {
                responseStream = connection.getInputStream();
            }

            String responseBody = new String(
                    responseStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            System.out.println(
                    "[DEBUG] Response Size: "
                    + responseBody.length()
                    + " characters"
            );

            if (status >= 400) {

                System.out.println(
                        "[DEBUG] Hugging Face Error: "
                        + responseBody
                );

                throw new Exception(
                        "Hugging Face API returned HTTP "
                        + status
                );
            }

            System.out.println(
                    "[DEBUG] API JSON received successfully."
            );

            /*
             * Hugging Face/FAL response contains:
             *
             * "images":[
             *   {
             *      "url":"https://....jpg"
             *   }
             * ]
             *
             * Extract the image URL.
             */

            Pattern pattern = Pattern.compile(
                    "\"url\"\\s*:\\s*\"([^\"]+)\""
            );

            Matcher matcher = pattern.matcher(responseBody);

            if (!matcher.find()) {

                System.out.println(
                        "[DEBUG] Could not find image URL."
                );

                System.out.println(
                        "[DEBUG] Response body: "
                        + responseBody
                );

                throw new Exception(
                        "Image URL not found in API response"
                );
            }

            String imageUrl = matcher.group(1);

            System.out.println(
                    "[DEBUG] Image URL received: "
                    + imageUrl
            );

            /*
             * Download actual generated image.
             */

            URL imageDownloadUrl =
                    URI.create(imageUrl).toURL();

            HttpURLConnection imageConnection =
                    (HttpURLConnection)
                    imageDownloadUrl.openConnection();

            imageConnection.setRequestMethod("GET");

            imageConnection.setRequestProperty(
                    "User-Agent",
                    "AI-Design-Agent"
            );

            int imageStatus =
                    imageConnection.getResponseCode();

            System.out.println(
                    "[DEBUG] Image Download HTTP Status: "
                    + imageStatus
            );

            if (imageStatus >= 400) {

                throw new Exception(
                        "Could not download generated image"
                );
            }

            String contentType =
                    imageConnection.getContentType();

            System.out.println(
                    "[DEBUG] Image Content-Type: "
                    + contentType
            );

            /*
             * Save as JPG because the HF response
             * currently returns image/jpeg.
             */

            String fileName =
                    "design_" + System.currentTimeMillis() + ".jpg";

            File outputFile =
                    new File(dir, fileName);

            try (
                InputStream input =
                        imageConnection.getInputStream();

                FileOutputStream output =
                        new FileOutputStream(outputFile)
            ) {

                byte[] buffer = new byte[8192];

                int bytesRead;

                long totalBytes = 0;

                while ((bytesRead = input.read(buffer)) != -1) {

                    output.write(buffer, 0, bytesRead);

                    totalBytes += bytesRead;
                }

                System.out.println(
                        "[DEBUG] Image saved bytes: "
                        + totalBytes
                );
            }

            if (!outputFile.exists()
                    || outputFile.length() == 0) {

                throw new Exception(
                        "Downloaded image file is empty"
                );
            }

            System.out.println(
                    "[Image Generator] Real AI image saved at: "
                    + outputFile.getAbsolutePath()
            );

            String imagePath =
                    "/generated-images/" + fileName;

            System.out.println(
                    "[Image Generator] Saved image at: "
                    + imagePath
            );

            return imagePath;

        } catch (Exception e) {

            System.out.println(
                    "[Image Generator] HF image generation failed: "
                    + e.getMessage()
            );

            e.printStackTrace();

            return createPlaceholder(outputDir);
        }
    }

    private String escapeJson(String text) {

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private String createPlaceholder(String outputDir) {

        try {

            String fileName =
                    "design_" + System.currentTimeMillis() + ".png";

            File outputFile =
                    new File(outputDir, fileName);

            /*
             * Simple 1x1 transparent PNG.
             */

            byte[] pngBytes = new byte[] {
                    (byte) 137, 80, 78, 71, 13, 10, 26, 10,
                    0, 0, 0, 13, 73, 72, 68, 82,
                    0, 0, 0, 1, 0, 0, 0, 1,
                    8, 6, 0, 0, 0, 31, 21, -60,
                    -119, 0, 0, 0, 13, 73, 68, 65,
                    84, 120, -100, 99, 0, 1, 0, 0,
                    5, 0, 1, 13, 10, 45, -76,
                    0, 0, 0, 0, 73, 69, 78, 68,
                    -82, 66, 96, -126
            };

            Files.write(
                    outputFile.toPath(),
                    pngBytes
            );

            System.out.println(
                    "[Image Generator] Placeholder image saved at: "
                    + outputFile.getAbsolutePath()
            );

            return "/generated-images/" + fileName;

        } catch (Exception e) {

            System.out.println(
                    "[Image Generator] Placeholder creation failed: "
                    + e.getMessage()
            );

            return "";
        }
    }
}
// package backend;

// import java.awt.Color;
// import java.awt.Font;
// import java.awt.Graphics2D;
// import java.awt.RenderingHints;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.OutputStream;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import java.nio.charset.StandardCharsets;
// import java.util.ArrayList;
// import java.util.Base64;
// import java.util.List;
// import java.util.Scanner;
// import javax.imageio.ImageIO;

// /**
//  * ImageGenerator.java
//  * -------------------
//  * Sends the AI prompt to an external Image Generation API and saves the
//  * image it returns onto the local disk (inside the "generated-images" folder).
//  *
//  * IMPORTANT (beginner-friendly safety net): if the API key has not been
//  * configured yet, or the API call fails for any reason (no internet, wrong
//  * key, no credits, service down), this class will NOT crash your project.
//  * Instead it automatically draws a simple local placeholder image, so the
//  * rest of your app (frontend, database, display) can always be tested and
//  * demoed even before your real API key is working.
//  *
//  * Marathi: Hi class prompt AI Image API la pathavte. Pan jar API key set
//  * naslel ya, ya API call fail zali, tar app crash hot nahi — tya aivaji
//  * ek simple placeholder image automatically banवली jaते, jyamule tumhi
//  * pura flow (frontend + database + display) test karू shakता, real API
//  * key milण्याआधीच.
//  */
// public class ImageGenerator {

//     // TODO (IMPORTANT): put your own API key here to use REAL AI image generation.
//     // Never commit your real key to GitHub — use an environment variable in a real project.
//     private static final String API_KEY = System.getenv("OPENAI_API_KEY");
//     private static final String API_URL = "https://api.openai.com/v1/images/generations";

//     public String generateImage(String prompt) {

//         // If no real key has been set, don't even try the network call —
//         // go straight to the guaranteed-to-work placeholder image.
//         // if (API_KEY == null || API_KEY.isEmpty() || API_KEY.equals("YOUR_OPENAI_API_KEY_HERE")) {
//         //     System.out.println("[Image Generator] No API key configured — drawing local placeholder image instead.");
//         //     return savePlaceholderImage(prompt);
//         // }

//         System.out.println("[DEBUG] API key loaded: " + (API_KEY != null && !API_KEY.isEmpty()));

//         if (API_KEY == null || API_KEY.isEmpty() || API_KEY.equals("YOUR_OPENAI_API_KEY_HERE")) {
//         System.out.println("[Image Generator] No API key configured — drawing local placeholder image instead.");
//         return savePlaceholderImage(prompt);
//        }
//         try {
//             return callImageApi(prompt);
//         } catch (Exception e) {
//             System.out.println("[Image Generator] API call failed (" + e.getMessage()
//                     + ") — falling back to local placeholder image so the app still works.");
//             return savePlaceholderImage(prompt);
//         }
//     }

//     // ------------------------------------------------------------------
//     // REAL AI IMAGE GENERATION (used once you add a valid API key)
//     // ------------------------------------------------------------------
//     private String callImageApi(String prompt) throws Exception {

//         String requestBody = "{"
//                 + "\"model\":\"gpt-image-1\","
//                 + "\"prompt\":\"" + JsonUtil.escape(prompt) + "\","
//                 + "\"size\":\"1024x1024\""
//                 + "}";

//         URL url = new URL(API_URL);
//         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//         connection.setRequestMethod("POST");
//         connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
//         connection.setRequestProperty("Content-Type", "application/json");
//         connection.setDoOutput(true);
//         connection.setConnectTimeout(30000);
//         connection.setReadTimeout(60000);

//         try (OutputStream os = connection.getOutputStream()) {
//             os.write(requestBody.getBytes(StandardCharsets.UTF_8));
//         }

//         int status = connection.getResponseCode();
//         java.io.InputStream responseStream =
//                 (status < 400) ? connection.getInputStream() : connection.getErrorStream();

//         Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8).useDelimiter("\\A");
//         String response = scanner.hasNext() ? scanner.next() : "";
//         scanner.close();

//         if (status >= 400) {
//             throw new RuntimeException("Image API returned an error: " + response);
//         }

//         String base64Image = JsonUtil.getValue(response, "b64_json");
//         if (base64Image.isEmpty()) {
//             throw new RuntimeException("Could not find image data in API response: " + response);
//         }

//         byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//         return saveBytesAsImage(imageBytes);
//     }

//     // ------------------------------------------------------------------
//     // FALLBACK: draws a simple labelled box diagram locally, no internet
//     // or API key needed. Guarantees the app always has an image to show.
//     // ------------------------------------------------------------------
//     private String savePlaceholderImage(String prompt) {
//         try {
//             int width = 900, height = 700;
//             BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//             Graphics2D g = img.createGraphics();
//             g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//             // Background
//             g.setColor(new Color(248, 250, 252));
//             g.fillRect(0, 0, width, height);

//             // Room outline
//             g.setColor(new Color(30, 41, 59));
//             g.setStroke(new java.awt.BasicStroke(4));
//             g.drawRect(60, 120, width - 120, height - 220);

//             // Title
//             g.setFont(new Font("SansSerif", Font.BOLD, 26));
//             g.drawString("AI Design Agent - Placeholder Layout", 60, 60);
//             g.setFont(new Font("SansSerif", Font.PLAIN, 14));
//             g.drawString("(No image API configured yet - showing a local placeholder instead)", 60, 88);

//             // Wrap and draw the prompt text as the "design description" inside the room
//             g.setFont(new Font("SansSerif", Font.PLAIN, 16));
//             List<String> lines = wrapText(prompt, 70);
//             int y = 160;
//             for (String line : lines) {
//                 g.drawString(line, 90, y);
//                 y += 24;
//                 if (y > height - 120) break; // don't overflow the box
//             }

//             g.dispose();

//             String folderPath = "generated-images";
//             new File(folderPath).mkdirs();
//             String fileName = "design_" + System.currentTimeMillis() + ".png";
//             File outFile = new File(folderPath, fileName);
//             ImageIO.write(img, "png", outFile);

//             System.out.println("[Image Generator] Placeholder image saved at: " + outFile.getAbsolutePath());
//             return "/generated-images/" + fileName;

//         } catch (Exception e) {
//             // Even the placeholder drawing should basically never fail, but just in case:
//             throw new RuntimeException("Could not create placeholder image: " + e.getMessage(), e);
//         }
//     }

//     private String saveBytesAsImage(byte[] imageBytes) throws Exception {
//         String folderPath = "generated-images";
//         new File(folderPath).mkdirs();
//         String fileName = "design_" + System.currentTimeMillis() + ".png";
//         String filePath = folderPath + "/" + fileName;

//         try (FileOutputStream fos = new FileOutputStream(filePath)) {
//             fos.write(imageBytes);
//         }
//         System.out.println("[Image Generator] Real AI image saved at: " + new File(filePath).getAbsolutePath());
//         return "/generated-images/" + fileName;
//     }

//     private List<String> wrapText(String text, int maxCharsPerLine) {
//         List<String> lines = new ArrayList<>();
//         String[] words = text.split(" ");
//         StringBuilder current = new StringBuilder();
//         for (String word : words) {
//             if (current.length() + word.length() + 1 > maxCharsPerLine) {
//                 lines.add(current.toString());
//                 current = new StringBuilder();
//             }
//             if (current.length() > 0) current.append(" ");
//             current.append(word);
//         }
//         if (current.length() > 0) lines.add(current.toString());
//         return lines;
//     }
// }
