# 🤖 AI Design Agent

An AI-powered interior design assistant that converts room requirements into professional 2D architectural floor plans.

The system accepts the room type, dimensions, and required furniture items, generates an optimized AI prompt, sends it to an image-generation model, displays the generated floor plan in the web interface, and stores the design information in a MySQL database.

---

## 📌 Project Overview

**AI Design Agent** is a Java-based AI application designed to simplify the initial interior-space planning process.

Instead of manually creating a basic room layout, the user can enter:

* Room type
* Room width
* Room length
* Furniture/items required

The application then:

1. Analyzes the user's requirements.
2. Generates a structured architectural prompt.
3. Sends the prompt to an AI image-generation model.
4. Downloads and stores the generated floor-plan image.
5. Displays the result in the frontend.
6. Saves the design details and generated image path in MySQL.

---

## ✨ Key Features

* 🤖 AI-powered floor-plan generation
* 📐 Custom room dimensions
* 🛏️ Furniture-aware layout generation
* 🏠 Multiple room/design types
* 🧠 Automatic prompt generation
* 🎨 AI-generated 2D architectural floor plans
* 🌐 Web-based frontend
* 🗄️ MySQL database integration
* 💾 Automatic design history storage
* 🔐 Environment-variable based API and database credentials
* ⚡ Java HTTP backend without a heavy framework

---

## 🏗️ System Architecture

```text
                ┌──────────────────────┐
                │      User Input      │
                │ Room + Size + Items  │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │    Web Frontend      │
                │ HTML / CSS / JS      │
                └──────────┬───────────┘
                           │ HTTP POST
                           ▼
                ┌──────────────────────┐
                │    Java Backend      │
                │   HTTP Server        │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │ Requirement Analyzer │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │   Prompt Generator   │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │ AI Image Generator   │
                │ Hugging Face API     │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │ Generated Floor Plan │
                └──────────┬───────────┘
                           │
                 ┌─────────┴─────────┐
                 ▼                   ▼
        ┌─────────────────┐  ┌─────────────────┐
        │    Frontend     │  │     MySQL       │
        │ Display Image   │  │ Save Design     │
        └─────────────────┘  └─────────────────┘
```

---

## 🔄 Application Workflow

```text
User enters requirements
          ↓
Frontend sends JSON request
          ↓
Java backend receives request
          ↓
Requirement analysis
          ↓
AI prompt generation
          ↓
Hugging Face image generation
          ↓
Generated image URL received
          ↓
Image downloaded locally
          ↓
Image displayed in frontend
          ↓
Design details saved in MySQL
```

---

## 🛠️ Technologies Used

### Frontend

* HTML5
* CSS3
* JavaScript
* Fetch API

### Backend

* Java
* Java HTTP Server
* JDBC
* JSON processing

### AI

* Hugging Face Inference Providers
* FLUX image-generation model
* AI prompt engineering

### Database

* MySQL
* MySQL Connector/J

### Development Tools

* Visual Studio Code / any Java-compatible IDE
* PowerShell
* Git
* GitHub

---

## 📂 Project Structure

```text
AI-Design-Agent/
│
├── backend/
│   ├── DatabaseConnection.java
│   ├── DesignAgent.java
│   ├── ImageGenerator.java
│   ├── JsonUtil.java
│   ├── Main.java
│   └── PromptGenerator.java
│
├── database/
│   └── database.sql
│
├── frontend/
│   ├── index.html
│   ├── script.js
│   └── style.css
│
├── generated-images/
│   └── Generated images are stored locally
│
├── .gitignore
├── DOCUMENTATION.md
└── README.md
```

> `generated-images/` and local dependency JAR files are excluded from Git using `.gitignore`.

---

## 🧩 Main Components

### `Main.java`

Starts the Java HTTP server and exposes the API endpoints.

```text
http://localhost:8080
```

Main API:

```text
POST /generate-design
```

Generated images are served through:

```text
/generated-images/<filename>
```

---

### `DesignAgent.java`

Acts as the main application controller.

It:

* Receives the design request.
* Extracts room information.
* Analyzes requirements.
* Calls the prompt generator.
* Calls the image generator.
* Saves the design to MySQL.
* Returns the generated image path and prompt.

---

### `PromptGenerator.java`

Converts user requirements into a structured AI prompt.

The generated prompt instructs the AI model to create:

* Top-view floor plans
* Room boundaries
* Furniture placement
* Walking space
* Door placement
* Architectural outlines
* Practical furniture arrangements

It also explicitly prevents the model from returning a text document or a 3D/photorealistic room instead of a floor plan.

---

### `ImageGenerator.java`

Handles communication with the Hugging Face image-generation API.

The component:

1. Reads the Hugging Face token from an environment variable.
2. Sends the generated prompt to the AI model.
3. Receives the generated image URL.
4. Downloads the image.
5. Saves it locally.
6. Returns the image path to the backend.

---

### `DatabaseConnection.java`

Handles MySQL database operations using JDBC.

The application stores:

* User ID
* Design type
* Requirements
* Room dimensions
* Generated AI prompt
* Generated image path
* Creation timestamp

Database credentials are read from environment variables instead of being stored directly in source code.

---

## 🗄️ Database

Database name:

```text
ai_design_agent
```

Main table:

```text
designs
```

Important fields:

| Field                  | Description                       |
| ---------------------- | --------------------------------- |
| `id`                   | Unique design ID                  |
| `user_id`              | User identifier                   |
| `design_type`          | Type of room/design               |
| `requirements`         | User requirements                 |
| `dimensions`           | Room dimensions                   |
| `design_prompt`        | AI prompt generated by the system |
| `generated_image_path` | Saved image location              |
| `created_at`           | Design creation timestamp         |

The database schema is available in:

```text
database/database.sql
```

---

# 🚀 Getting Started

## 1. Clone the Repository

```bash
git clone https://github.com/prathmeshlinge415-commits/AI-Design-Agent.git
```

Then:

```bash
cd AI-Design-Agent
```

---

## 2. Install Requirements

Make sure the following are installed:

* Java JDK
* MySQL Server
* Git
* Hugging Face account/token

Check Java:

```bash
java --version
```

Check Git:

```bash
git --version
```

---

## 3. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE ai_design_agent;
```

Then select the database:

```sql
USE ai_design_agent;
```

Run the SQL statements from:

```text
database/database.sql
```

---

## 4. Configure Environment Variables

The application does **not** store sensitive credentials directly in Java source code.

### MySQL Password

Windows PowerShell:

```powershell
$env:DB_PASSWORD = "YOUR_MYSQL_PASSWORD"
```

### Hugging Face Token

```powershell
$env:HF_TOKEN = "YOUR_HUGGINGFACE_TOKEN"
```

Never commit real passwords or API tokens to GitHub.

---

## 5. Add MySQL Connector/J

Download the MySQL Connector/J JAR and place it inside:

```text
lib/
```

For example:

```text
lib/mysql-connector-j-<version>.jar
```

The `lib/*.jar` rule in `.gitignore` prevents the dependency JAR from being uploaded to GitHub.

---

## 6. Compile the Backend

From the project root:

```powershell
javac -cp "lib\*" -d . backend\*.java
```

---

## 7. Start the Backend

```powershell
java -cp ".;lib\*" backend.Main
```

When successful, the server starts at:

```text
http://localhost:8080
```

API endpoint:

```text
POST http://localhost:8080/generate-design
```

---

## 8. Open the Frontend

Open:

```text
frontend/index.html
```

in a browser.

Enter:

* Room type
* Room width
* Room length
* Furniture requirements

Then click **Generate Design**.

The generated AI floor plan should appear in the result section.

---

# 📡 API Example

### Endpoint

```text
POST /generate-design
```

### Example Request

```json
{
  "userId": "user_001",
  "designType": "Bedroom",
  "roomWidth": "8",
  "roomLength": "24",
  "items": "Wardrobe, bed, table, chair"
}
```

### Example Response

```json
{
  "status": "success",
  "prompt": "Generated architectural floor-plan prompt...",
  "imageUrl": "/generated-images/design_XXXXXXXX.jpg"
}
```

---

# 🖼️ Example Use Case

### Input

```text
Room Type: Bedroom
Room Size: 8 × 24 feet

Furniture:
- Wardrobe
- Bed
- Table
- Chair
```

### Processing

The system converts the requirements into an architectural prompt and sends it to the AI image-generation model.

### Output

The AI generates a top-view 2D floor plan containing:

* Room boundary
* Furniture arrangement
* Walking space
* Door
* Architectural-style outlines

---

# 🔐 Security

This project follows basic credential-security practices.

Sensitive values should be provided through environment variables:

```text
DB_PASSWORD
HF_TOKEN
```

Do not commit:

* MySQL passwords
* Hugging Face API tokens
* `.env` files
* API keys
* Private credentials

The repository `.gitignore` is configured to exclude sensitive/local files.

---

# 🎯 Project Objectives

The main objectives of this project are:

1. To develop an AI-assisted interior design application.
2. To convert natural user requirements into structured design prompts.
3. To integrate an AI image-generation service with a Java application.
4. To generate visual 2D architectural floor plans.
5. To store generated design information in a relational database.
6. To provide a simple web-based interface for users.

---

# 🚀 Future Enhancements

Possible future improvements include:

* 📐 More accurate dimension-aware furniture placement
* 🚪 Multiple door and window configurations
* 🪟 Window placement optimization
* 🏠 Living room, kitchen, office and commercial layouts
* 📊 Design history dashboard
* 👤 User authentication
* 💾 Download generated floor plans
* 🖨️ Print-ready architectural layouts
* 📱 Responsive mobile interface
* 🎨 Multiple design styles
* 🔄 Regenerate / refine an existing design
* 🧠 Conversational AI design assistant
* 📏 Automatic scale and measurement annotations
* ☁️ Cloud-based image and database storage

---

# 📚 Academic Relevance

This project demonstrates practical implementation of:

* Artificial Intelligence
* Generative AI
* Prompt Engineering
* Image Generation
* Java Programming
* REST-style HTTP communication
* JDBC
* MySQL Database Management
* Frontend Web Development
* API Integration
* Software Architecture
* Environment-based credential management

It can be used as an academic project, portfolio project, or demonstration of AI integration skills.

---

# 👨‍💻 Author

**Prathmesh**

AI Design Agent — Academic / Portfolio Project

---

# 📄 License

This project is intended for educational and portfolio purposes.

If you plan to publish or distribute the project commercially, add an appropriate open-source or proprietary license.
