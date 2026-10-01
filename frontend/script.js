// script.js
// ---------
// Connects the frontend form to the Java backend's /generate-design API,
// then displays the returned image on the page.

const form = document.getElementById("designForm");
const statusEl = document.getElementById("status");
const resultBox = document.getElementById("resultBox");
const resultImage = document.getElementById("resultImage");
const promptText = document.getElementById("promptText");

const BACKEND_URL = "https://ai-design-agent.onrender.com/generate-design";
// const BACKEND_URL = "http://localhost:8080/generate-design";

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const payload = {
    userId: "user_001",
    designType: document.getElementById("designType").value,
    roomWidth: document.getElementById("roomWidth").value,
    roomLength: document.getElementById("roomLength").value,
    items: document.getElementById("items").value
  };

  statusEl.textContent = "⏳ Generating your design... please wait (this can take 10-20 seconds).";
  resultBox.classList.add("hidden");

  try {
    const response = await fetch(BACKEND_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    const data = await response.json();

    if (data.status === "success") {
      statusEl.textContent = "✅ Design generated successfully!";
      resultImage.src = "https://ai-design-agent.onrender.com" + data.imageUrl;
      // resultImage.src = "http://localhost:8080" + data.imageUrl;
      promptText.textContent = "AI Prompt used: " + data.prompt;
      resultBox.classList.remove("hidden");
    } else {
      statusEl.textContent = "❌ Error: " + (data.error || "Unknown error occurred.");
    }
  } catch (err) {
    statusEl.textContent = "❌ Could not reach the backend server. Is it running on port 8080?";
    console.error(err);
  }
});
