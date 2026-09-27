# AI Image Design Generator Agent
### Complete Project Documentation (Marathi + English)

Degree Project — 7th Semester, Computer Science Engineering

---

## 1. PROJECT IDEA

### AI Design Generator Agent म्हणजे काय? (What is it?)

**English:** An AI Design Generator Agent is a software "agent" that takes a
human's description of what they want (in plain text, e.g. "10x12 bedroom
with a bed, wardrobe, study table and chair") and *automatically* produces a
finished **design image** — not just text or numbers, an actual picture the
user can look at.

**Marathi:** AI Design Generator Agent ha ek software agent aahe jo
user cha simple text requirement (उदा. "10x12 bedroom madhe bed, wardrobe,
study table ani chair havi") ghetो, ani त्यावरून automatically ek **design
image** तयार करतो — फक्त text नाही, तर user ला direct dolyani baघण्यासाठी
image.

### हे कोणती समस्या सोडवते? (What problem does it solve?)

- सामान्य माणसाला (non-designer) interior design software (AutoCAD, SketchUp
  इ.) वापरता येत नाही — ते complicated असतात.
- Professional designer hire करणे महाग आणि वेळखाऊ असते.
- Text मध्ये requirement सांगणे सोपे आहे, पण design imagine करणे कठीण असते.

This AI Agent removes that gap: **user just types what they want, AI shows
them a picture.**

### हे मानवांना कसे मदत करते? (How does it help humans?)

1. Design साठी लागणारा वेळ आणि पैसा वाचतो.
2. Non-technical लोकही सहज वापरू शकतात (no design skills needed).
3. Multiple options पटकन try करता येतात (different item combinations).
4. Final decision घेण्याआधी visual idea मिळतो.

### Real-World Applications

- Interior design / room-layout planning (घर, ऑफिस)
- Furniture placement planning for small apartments
- Garden / landscape layout design
- Classroom / office seating layout design
- Event / stage layout design (weddings, exhibitions)

### 5 Examples of Things It Can Design

1. **Bedroom layout** — 10×12 ft room with bed, wardrobe, study table, chair.
2. **Living room layout** — sofa set, TV unit, coffee table arrangement.
3. **Kitchen layout** — modular kitchen with platform, sink, fridge placement.
4. **Office cabin layout** — desk, chair, cupboard, meeting table.
5. **Small garden layout** — plants, pathway, seating area design.

---

## 2. PROJECT WORKING (Workflow Explanation)

```
User Input
   ↓
Requirement Analyzer
   ↓
AI Design Agent
   ↓
Design Generation (Prompt Building)
   ↓
Image Generation (API Call)
   ↓
Final Design Image
   ↓
User
```

| Step | English Explanation | Marathi Explanation |
|---|---|---|
| **User Input** | User enters room size and required items in a simple web form. | User एका simple form मध्ये room size आणि लागणाऱ्या वस्तू टाकतो. |
| **Requirement Analyzer** | Backend reads and organizes this raw input into a clean, structured requirement (room dimensions + item list + design type). | Backend हा raw input वाचून त्याला व्यवस्थित requirement मध्ये (size + items + type) रूपांतरित करतो. |
| **AI Design Agent** | The "brain" class (`DesignAgent.java`) coordinates every step — it does not just do one job, it manages the whole pipeline. | ही class (`DesignAgent.java`) पूर्ण process control करते — एकच काम न करता संपूर्ण pipeline सांभाळते. |
| **Design Generation** | The structured requirement is converted into a detailed **text prompt** that an image-AI can understand (`PromptGenerator.java`). | Structured requirement ला एका detailed **text prompt** मध्ये बदलले जाते, जे image-AI ला समजेल (`PromptGenerator.java`). |
| **Image Generation** | That prompt is sent to an external AI Image API (e.g., OpenAI Images API). The API returns actual image data. | तो prompt बाहेरील AI Image API कडे पाठवला जातो, आणि API कडून खरी image data मिळते. |
| **Final Design Image** | The image is saved on the server and its link/path is sent back, along with saving a record in the database. | Image server वर save होते आणि तिचा path परत पाठवला जातो, तसेच database मध्ये record save होतो. |
| **User** | The final image is displayed on the webpage for the user to view/download. | शेवटी ती image webpage वर user ला बघण्यासाठी दाखवली जाते. |

---

## 3. PROJECT EXAMPLE

**User enters:**
- Design Type: Bedroom
- Room Size: 10 × 12 feet
- Items: Bed, Wardrobe, Study Table, Chair

**What the AI does internally:**

1. Requirement Analyzer builds this summary:
   `Design type: Bedroom | Room size: 10x12 feet | Items: Bed, Wardrobe, Study Table, Chair`

2. PromptGenerator converts it into an AI image prompt:
   > "Create a clean, professional top-view 2D floor plan design for a 10 x
   > 12 feet bedroom. Include the following furniture items, clearly placed
   > with realistic proportions and proper walking space between them: Bed,
   > Wardrobe, Study Table, Chair. Use simple labelled shapes... minimal
   > architectural floor plan..."

3. ImageGenerator sends this prompt to the Image API and receives an actual
   PNG image, saves it as `generated-images/design_<timestamp>.png`.

4. DatabaseConnection saves a row in MySQL with all these details.

5. **Final Output → an actual IMAGE of the bedroom layout**, shown on the
   webpage — not text, an image.

---

## 4. TECHNOLOGY (आणि का वापरले?)

| Technology | Why it is used |
|---|---|
| **Java** | Beginner-friendly, strongly typed, widely taught in CSE syllabus; used to build the backend logic (the "AI Agent" classes) and connect everything together. |
| **Java's built-in `HttpServer`** | Lets us create a working REST API without installing heavy frameworks like Spring — good for a degree project, easy to explain in viva. |
| **MySQL** | A simple, free, widely-used relational database to permanently store every design request and its result. |
| **JDBC (Java Database Connectivity)** | The standard Java API to connect and run SQL queries against MySQL from our Java code. |
| **HTML/CSS/JavaScript** | Builds the simple browser-based interface where the user enters requirements and views the final image — no framework needed, keeps it beginner-friendly. |
| **AI Image-Generation API** (e.g., OpenAI Images API) | The actual "AI" that converts our text prompt into a real design image — this is the core AI component of the "AI Agent". |

---

## 5. COMPLETE ARCHITECTURE

```
        User
         │
         ▼
     Frontend (HTML/CSS/JS)
         │   (sends room size + items as JSON, via fetch())
         ▼
   Java Backend (Main.java + HttpServer)
         │
         ▼
   AI Design Agent (DesignAgent.java)
     │        │
     │        ▼
     │   PromptGenerator.java  (builds the AI prompt)
     │        │
     ▼        ▼
DatabaseConnection.java   ImageGenerator.java
   (saves to MySQL)        (calls Image Generation API)
                                 │
                                 ▼
                        Generated Design Image
                                 │
                                 ▼
                            Frontend
                                 │
                                 ▼
                               User
```

**Component explanation:**

- **Frontend** — collects input, displays the final image (no logic, just UI).
- **Java Backend (Main.java)** — receives HTTP requests, routes them to the agent, serves the saved image files back to the browser.
- **AI Design Agent (DesignAgent.java)** — the coordinator/"brain" that runs every step in order.
- **PromptGenerator.java** — turns structured data into natural-language AI prompt text.
- **ImageGenerator.java** — talks to the external AI Image API and saves the returned image.
- **DatabaseConnection.java** — saves a permanent record using JDBC + MySQL.
- **Generated Design Image** — the final deliverable shown to the user.

---

## 6. DATABASE

See `database/database.sql` for the full SQL script, and `backend/DatabaseConnection.java`
for the JDBC code that inserts a row every time a design is generated.

**Table: `designs`**

| Column | Type | Purpose |
|---|---|---|
| id | INT (auto increment) | Primary key |
| user_id | VARCHAR(50) | Identifies which user made the request |
| design_type | VARCHAR(100) | Bedroom / Kitchen / Office etc. |
| requirements | TEXT | Full requirement summary |
| dimensions | VARCHAR(50) | e.g. "10x12" |
| design_prompt | TEXT | The exact AI prompt used |
| generated_image_path | VARCHAR(255) | Where the generated image is stored |
| created_at | DATETIME | When it was generated |

---

## 7. PROJECT STRUCTURE

```
AI-Design-Agent/
│
├── backend/
│   ├── Main.java              (starts server, handles routes)
│   ├── DesignAgent.java       (the AI Agent — coordinates everything)
│   ├── PromptGenerator.java   (builds the AI image prompt)
│   ├── ImageGenerator.java    (calls the Image API, saves the image)
│   ├── DatabaseConnection.java(JDBC — saves records to MySQL)
│   └── JsonUtil.java          (tiny helper to read/write simple JSON)
│
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── generated-images/          (created automatically — stores output images)
│
└── database/
    └── database.sql
```

---

## 8. COMPLETE CODE

All code files are provided as **separate, complete, ready-to-run files** in
this project folder (not shown here again to avoid duplication):

- `backend/Main.java`
- `backend/DesignAgent.java`
- `backend/PromptGenerator.java`
- `backend/ImageGenerator.java`
- `backend/DatabaseConnection.java`
- `backend/JsonUtil.java`
- `frontend/index.html`
- `frontend/style.css`
- `frontend/script.js`
- `database/database.sql`

**How the files communicate:**

`script.js` → (HTTP POST JSON) → `Main.java` → `DesignAgent.java` →
`PromptGenerator.java` (builds prompt) → `ImageGenerator.java` (calls API,
saves image) → `DatabaseConnection.java` (saves record) → `Main.java`
(sends JSON response) → `script.js` (displays image in browser).

---

## 9. IMAGE GENERATION EXPLAINED

**Step 1 — User requirement (plain text):**
> "10 × 12 bedroom with bed, wardrobe and study table."

**Step 2 — AI builds a structured, descriptive prompt:**
> "Create a clean top-view 2D bedroom layout for a 10 × 12 feet room,
> including a bed, wardrobe, and study table with proper walking space,
> minimal architectural floor-plan style..."

**Step 3 — This prompt is sent to the Image Generation API** (`ImageGenerator.java`)
via an HTTPS POST request, exactly like sending a message to ChatGPT but
asking it to return an image instead of text.

**Step 4 — Receiving the image:** The API responds with the image encoded
as **base64 text**. Java decodes this into real image bytes and saves it
as a `.png` file inside `generated-images/`.

**Step 5 — Displaying it:** The backend sends the image's URL
(`/generated-images/design_xxx.png`) back to the browser. The frontend sets
this as the `src` of an `<img>` tag, so the picture appears directly on the
webpage.

---

## 10. OUTPUT (What the User Sees)

```
User Requirements
──────────────────
Room Size: 10 × 12
[✓] Bed
[✓] Wardrobe
[✓] Study Table
[✓] Chair

        ↓
   [ GENERATE DESIGN ]
        ↓

┌───────────────────────────┐
│                           │
│   GENERATED DESIGN IMAGE  │
│   (actual picture of the  │
│    room layout)           │
│                           │
└───────────────────────────┘
```

The final output shown on screen is an **actual generated image**, displayed
inside `resultImage` (`<img>` tag) in `index.html` — not just text.

---

## 11. RUNNING PROCESS (Step by Step)

1. **Install JDK 17+** — download from Oracle/OpenJDK, verify with `java -version`.
2. **Install VS Code** — download from code.visualstudio.com.
3. **Install extensions** — "Extension Pack for Java" and "Live Server" (for frontend).
4. **Install MySQL** — MySQL Community Server + MySQL Workbench.
5. **Set up the Java project:**
   - Open the `AI-Design-Agent` folder in VS Code.
   - Download the **MySQL Connector/J** jar (`mysql-connector-j-x.x.x.jar`) and add it to the classpath (VS Code Java extension → "Add Folder to Java Build Path" or place it in a `lib/` folder and reference it).
6. **Set up frontend** — no installation needed; just the files in `frontend/`.
7. **Configure the AI/Image API:**
   - Sign up for an API key (e.g., OpenAI).
   - Open `backend/ImageGenerator.java` and replace `YOUR_OPENAI_API_KEY_HERE` with your real key.
8. **Configure database:**
   - Open MySQL Workbench, run `database/database.sql`.
   - Update `USER` / `PASSWORD` in `backend/DatabaseConnection.java` to match your MySQL login.
9. **Run backend:**
   - In VS Code, open `backend/Main.java` and click **Run**.
   - You should see: `AI Design Agent Backend Server Started`.
10. **Run frontend:**
    - Right-click `frontend/index.html` → "Open with Live Server" (or just open the file in a browser).
11. **Generate the first design image:**
    - Fill the form (e.g., 10×12, Bedroom, "Bed, Wardrobe, Study Table, Chair").
    - Click **Generate Design** and wait a few seconds — the image appears on screen.

---

## 12. DEMO (Complete Walkthrough)

**Input:**
> "Design a 10 × 12 bedroom with one bed, one wardrobe, one study table and one chair."

**What happens step by step after clicking "Generate Design":**

1. `script.js` collects the form values and sends them as JSON to
   `http://localhost:8080/generate-design` using `fetch()`.
2. `Main.java`'s `DesignHandler` receives the request and reads the JSON body.
3. It calls `DesignAgent.processRequest(...)`.
4. `DesignAgent` extracts the room size and items (**Requirement Analyzer**).
5. `PromptGenerator.buildPrompt(...)` converts these into a detailed AI prompt.
6. `ImageGenerator.generateImage(prompt)` sends this prompt to the Image API.
7. The API returns image data (base64); Java decodes and saves it as a `.png` file.
8. `DatabaseConnection.saveDesign(...)` stores a record of this request in MySQL.
9. `DesignAgent` builds a JSON response containing the image path and prompt.
10. `Main.java` sends this JSON back to the browser.
11. `script.js` receives the response and sets the `<img>` tag's `src` to the
    returned image URL.
12. **The generated bedroom-layout image appears on the screen.**

---

## 13. FACULTY PRESENTATION (3-Minute Script)

**Introduction:**
"Good morning/afternoon. My project is an *AI Image Design Generator Agent* —
a system that turns a plain text description of a room into an actual
generated design image."

**Problem Statement:**
"Designing a room layout normally needs design software or a professional
designer, which is costly and time-consuming for an ordinary person."

**Objective:**
"To build an AI Agent where a user simply describes their room and required
furniture, and the system automatically generates a visual design image."

**Proposed System:**
"The user enters room dimensions and required items through a simple web
form. The Java backend analyzes this requirement, builds an AI prompt, and
sends it to an image-generation API."

**AI Agent:**
"The `DesignAgent` class acts as the coordinator — it manages the full
pipeline: analyzing requirements, generating the prompt, calling the image
API, and saving results — this is what makes it an 'agent' rather than a
single function."

**Image Generation:**
"We use an AI image-generation API which converts our descriptive text
prompt into an actual PNG image of the room layout."

**Database:**
"Every request — user details, requirements, the AI prompt used, and the
resulting image path — is stored in a MySQL database using JDBC, so history
can be tracked."

**Technologies:**
"Java for the backend logic, MySQL + JDBC for storage, HTML/CSS/JS for the
interface, and an AI Image API for the actual design generation."

**Working:**
"User Input → Requirement Analyzer → AI Design Agent → Prompt Generation →
Image Generation → Final Image → shown to User."

**Output:**
"The final output is not just text — it is an actual generated image showing
the room layout with all requested furniture placed inside it."

**Future Scope:**
"This can be extended to 3D room visualization, multiple design style
options, cost estimation for furniture, and mobile app support."

**Conclusion:**
"This project demonstrates how AI agents can bridge the gap between a
simple human description and a professional-looking visual design, making
design accessible to everyone."

---

## 14. VIVA QUESTIONS (25 Q&A)

**AI Agent**

1. **Q: What is an AI Agent?**
   A: A program that perceives input (user requirement), reasons/plans over
   it, and takes action (generates a design) to achieve a goal automatically.

2. **Q: Why is `DesignAgent.java` called an "agent" and not just a function?**
   A: Because it coordinates multiple steps/decisions (analyze → prompt →
   generate → store) instead of doing one single, fixed task.

3. **Q: What is the difference between AI Agent and normal software?**
   A: Normal software just follows fixed instructions; an agent adapts its
   output based on varying user input and combines multiple sub-tasks to
   reach a goal.

**Image Generation**

4. **Q: How does the AI generate an image from text?**
   A: The text prompt is sent to a trained image-generation model via an
   API; the model returns image data based on patterns learned from huge
   image-text datasets.

5. **Q: What format is the image received in?**
   A: As base64-encoded text in the JSON response, which Java decodes into
   real image bytes.

6. **Q: Why do we build a detailed prompt instead of sending raw user input?**
   A: A detailed, structured prompt gives the AI clearer instructions,
   producing a more accurate and useful image.

7. **Q: Can this project use a different image API?**
   A: Yes — only `ImageGenerator.java` needs to change; the rest of the
   architecture stays the same.

**Java**

8. **Q: Why was Java chosen for the backend?**
   A: It's strongly typed, widely taught, has built-in networking (`HttpServer`)
   and JDBC support, making it easy to build a complete backend without
   extra frameworks.

9. **Q: What is `HttpServer` in Java?**
   A: A built-in class (`com.sun.net.httpserver.HttpServer`) that lets Java
   act as a lightweight web server without installing Tomcat/Spring.

10. **Q: What is the role of `Main.java`?**
    A: It starts the server and routes incoming HTTP requests to the right
    handler classes.

**API**

11. **Q: What is an API?**
    A: Application Programming Interface — a defined way for two programs
    (here, our backend and the AI image service) to talk to each other.

12. **Q: How does Java call an external API?**
    A: Using `HttpURLConnection` to send an HTTP POST request with a JSON
    body, then reading the response.

13. **Q: What is an API key and why is it needed?**
    A: A secret code that identifies and authorizes our application to use
    the paid/rate-limited image-generation service.

**Database**

14. **Q: Why is MySQL used here?**
    A: It's free, reliable, and widely used to permanently store structured
    data like each design request and its result.

15. **Q: What data is stored in the database?**
    A: User ID, design type, requirements, room dimensions, the AI prompt
    used, the generated image's path, and the timestamp.

16. **Q: Why store the image path and not the image itself in the database?**
    A: Storing large binary image data directly in the database is
    inefficient; storing just the file path/URL is faster and simpler.

**JDBC**

17. **Q: What is JDBC?**
    A: Java Database Connectivity — a standard Java API used to connect to
    and run SQL statements against a database like MySQL.

18. **Q: What is `PreparedStatement` and why use it instead of plain `Statement`?**
    A: It safely inserts parameter values into SQL, preventing SQL-injection
    attacks and handling special characters correctly.

19. **Q: What does `DriverManager.getConnection()` do?**
    A: It opens a live connection to the MySQL database using the given
    URL, username, and password.

**Frontend/Backend**

20. **Q: How does the frontend communicate with the backend?**
    A: Via `fetch()` in JavaScript, sending a JSON POST request to the
    backend's `/generate-design` endpoint, and receiving a JSON response.

21. **Q: What is CORS, and why is it configured here?**
    A: Cross-Origin Resource Sharing — browsers block requests between
    different origins/ports by default; we add CORS headers so the frontend
    (opened as a file/Live Server) can call the backend running on port 8080.

22. **Q: How is the generated image shown in the browser?**
    A: The backend serves it as a static file; the frontend sets that URL
    as the `src` attribute of an `<img>` tag.

**Project Architecture**

23. **Q: What are the main components of this architecture?**
    A: Frontend (UI), Java Backend (API + agent logic), AI Design Agent
    (coordinator), Image Generation API (external AI), and MySQL Database
    (storage).

24. **Q: What happens if the database save fails — does the whole request fail?**
    A: No — `DatabaseConnection.saveDesign()` catches the error and just
    logs it, so the user still receives their generated image.

25. **Q: How could this project be extended in future?**
    A: Add 3D visualization, multiple style options (modern/traditional),
    cost estimation, user login system, and history page to view all past
    designs from the database.

---

## Notes for Beginners

- **First get the simple version working**: run backend → run frontend →
  generate one image successfully. Only then explore extra features.
- If you don't have an image-API budget, you can temporarily replace
  `ImageGenerator.java`'s API call with a placeholder image, just to test
  the rest of the flow (form → backend → database → display).
- Keep your real API key and MySQL password private; do not upload them to
  GitHub — use placeholder values in any code you submit/share publicly.
