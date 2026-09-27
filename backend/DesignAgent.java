package backend;

/**
 * DesignAgent.java
 * ----------------
 * This is the "AI Agent" of the project. It does NOT talk to the network or
 * database directly by itself — instead it coordinates the other classes:
 *
 *   1. Reads user requirements   (Requirement Analyzer step)
 *   2. Calls PromptGenerator     -> builds an AI image prompt
 *   3. Calls ImageGenerator      -> calls the Image API, gets the image
 *   4. Calls DatabaseConnection  -> saves everything in MySQL
 *   5. Returns the final result back to Main.java
 *
 * Marathi: Hi class "AI Agent" mhanun kaam karte. User cha requirement
 * ghete, tyacha analysis karte, prompt banavte, image generate karte,
 * ani database madhe save karte.
 */
public class DesignAgent {

    public String processRequest(String requestJson) throws Exception {

        // ---------- STEP 1: Requirement Analyzer ----------
        // Extract the fields the frontend sent us
        String userId     = JsonUtil.getValue(requestJson, "userId");
        String designType = JsonUtil.getValue(requestJson, "designType"); // e.g. "Bedroom"
        String roomWidth  = JsonUtil.getValue(requestJson, "roomWidth");  // e.g. "10"
        String roomLength = JsonUtil.getValue(requestJson, "roomLength"); // e.g. "12"
        String items      = JsonUtil.getValue(requestJson, "items");     // e.g. "Bed, Wardrobe, Study Table, Chair"

        if (designType.isEmpty()) designType = "Room";
        if (userId.isEmpty()) userId = "guest_user";

        String requirementSummary = "Design type: " + designType
                + " | Room size: " + roomWidth + "x" + roomLength + " feet"
                + " | Items: " + items;

        System.out.println("[Requirement Analyzer] " + requirementSummary);

        // ---------- STEP 2: Build the AI image-generation prompt ----------
        PromptGenerator promptGenerator = new PromptGenerator();
        String finalPrompt = promptGenerator.buildPrompt(designType, roomWidth, roomLength, items);
        System.out.println("[Prompt Generator] " + finalPrompt);

        // ---------- STEP 3: Generate the image using the AI Image API ----------
        ImageGenerator imageGenerator = new ImageGenerator();
        String imagePath = imageGenerator.generateImage(finalPrompt);
        System.out.println("[Image Generator] Saved image at: " + imagePath);

        // ---------- STEP 4: Save the whole record to MySQL ----------
        DatabaseConnection db = new DatabaseConnection();
        db.saveDesign(userId, designType, requirementSummary,
                roomWidth + "x" + roomLength, finalPrompt, imagePath);

        // ---------- STEP 5: Build the JSON response for the frontend ----------
        return "{"
                + "\"status\":\"success\","
                + "\"prompt\":\"" + JsonUtil.escape(finalPrompt) + "\","
                + "\"imageUrl\":\"" + JsonUtil.escape(imagePath) + "\""
                + "}";
    }
}
