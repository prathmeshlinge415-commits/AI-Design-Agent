package backend;

public class PromptGenerator {

    public String buildPrompt(String designType, String width, String length, String items) {

        StringBuilder prompt = new StringBuilder();

        prompt.append(
            "Generate ONLY an actual visual architectural floor plan image. "
        );

        prompt.append(
            "Do NOT display, write, print, or reproduce this prompt as text. "
        );

        prompt.append(
            "Do NOT create a text document, poster, description, paragraph, or typography. "
        );

        prompt.append(
            "Create a clean professional TOP-VIEW 2D FLOOR PLAN of a "
        );

        prompt.append(width)
              .append(" x ")
              .append(length)
              .append(" feet ")
              .append(designType.toLowerCase())
              .append(". ");

        prompt.append(
            "Show the complete room boundary as a rectangular architectural plan. "
        );

        prompt.append(
            "Place these furniture items inside the room with realistic proportions: "
        );

        prompt.append(items)
              .append(". ");

        prompt.append(
            "Clearly show furniture as simple recognizable graphical shapes. "
        );

        prompt.append(
            "Include realistic walking space between furniture and walls. "
        );

        prompt.append(
            "Include a visible door and practical furniture arrangement. "
        );

        prompt.append(
            "Use a clean white or light background with thin black architectural outlines. "
        );

        prompt.append(
            "The final result must look like a real 2D architectural floor plan drawing, "
            + "not a written prompt or text-based image. "
        );

        prompt.append(
            "No paragraphs. No sentences. No large text. No decorative graphics. "
            + "No people. No 3D perspective. No photorealistic room. "
        );

        prompt.append(
            "OUTPUT MUST BE ONLY THE FLOOR PLAN IMAGE."
        );

        return prompt.toString();
    }
}
// package backend;

// public class PromptGenerator {

//     public String buildPrompt(String designType, String width, String length, String items) {

//         StringBuilder prompt = new StringBuilder();

//         prompt.append(
//             "Generate ONLY an actual visual architectural floor plan image. "
//         );

//         prompt.append(
//             "Do NOT display, write, print, or reproduce this prompt as text. "
//         );

//         prompt.append(
//             "Do NOT create a text document, poster, description, paragraph, or typography. "
//         );

//         prompt.append(
//             "Create a clean professional TOP-VIEW 2D FLOOR PLAN of a "
//         );

//         prompt.append(width)
//               .append(" x ")
//               .append(length)
//               .append(" feet ")
//               .append(designType.toLowerCase())
//               .append(". ");

//         prompt.append(
//             "Show the complete room boundary as a rectangular architectural plan. "
//         );

//         prompt.append(
//             "Place these furniture items inside the room with realistic proportions: "
//         );

//         prompt.append(items)
//               .append(". ");

//         prompt.append(
//             "Clearly show furniture as simple recognizable graphical shapes. "
//         );

//         prompt.append(
//             "Include realistic walking space between furniture and walls. "
//         );

//         prompt.append(
//             "Include a visible door and practical furniture arrangement. "
//         );

//         prompt.append(
//             "Use a clean white or light background with thin black architectural outlines. "
//         );
//          prompt.append("The final result must look like a real 2D architectural floor plan drawing, ");
//         // prompt.append ("The final result must look like a real 2D architectural floor plan drawing,"not a written prompt or text-based image.");

//         prompt.append(
//             "No paragraphs. No sentences. No large text. No decorative graphics. "
//             "No people. No 3D perspective. No photorealistic room. "
//         );

//         prompt.append(
//             "OUTPUT MUST BE ONLY THE FLOOR PLAN IMAGE."
//         );

//         return prompt.toString();
//     }
// }