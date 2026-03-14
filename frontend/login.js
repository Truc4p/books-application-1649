document.addEventListener("DOMContentLoaded", () => {
  const tabs = document.querySelectorAll(".tab");
  const loginForm = document.getElementById("login-form");
  const registerForm = document.getElementById("register-form");
  const regRole = document.getElementById("reg-role");
  const regAdminKeyContainer = document.getElementById("reg-adminkey-container");

  if (regRole && regAdminKeyContainer) {
    regRole.addEventListener("change", (e) => {
      regAdminKeyContainer.style.display = e.target.value === "admin" ? "block" : "none";
    });
  }

  tabs.forEach((tab) => {
    tab.addEventListener("click", () => {
      tabs.forEach((t) => t.classList.remove("is-active"));
      tab.classList.add("is-active");
      loginForm.classList.toggle("is-hidden", tab.dataset.tab !== "login");
      registerForm.classList.toggle("is-hidden", tab.dataset.tab !== "register");
    });
  });

  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("login-email").value;
    const role = document.getElementById("login-role").value;
    const password = document.getElementById("login-password") ? document.getElementById("login-password").value : "password"; // Fallback if no password field in UI

    try {
      const response = await fetch("https://books-application-1649.onrender.com", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, role, password })
      });

      const result = await response.json();
      if (result.success) {
        localStorage.setItem("currentUser", JSON.stringify({
          email: email,
          role: role,
          name: result.name || email.split('@')[0]
        }));
        
        if (role === "admin") {
          window.location.href = "admin.html";
        } else {
          window.location.href = "index.html";
        }
      } else {
        alert(result.message || "Login failed. Check your credentials.");
      }
    } catch (error) {
      console.error("Login error:", error);
      alert("Error connecting to server.");
    }
  });

  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("reg-name").value;
    const email = document.getElementById("reg-email").value;
    const role = document.getElementById("reg-role").value;
    const password = document.getElementById("reg-password") ? document.getElementById("reg-password").value : "password"; // Fallback if no password field
    const adminKey = document.getElementById("reg-adminkey") ? document.getElementById("reg-adminkey").value : "";
    
    try {
      const response = await fetch("https://books-application-1649.onrender.com/api/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ name, email, role, password, adminKey })
      });

      const result = await response.json();
      if (result.success) {
        localStorage.setItem("currentUser", JSON.stringify({
          email: email,
          role: role,
          name: name
        }));

        alert("Account created successfully. Logging you in...");
        
        if (role === "admin") {
          window.location.href = "admin.html";
        } else {
          window.location.href = "index.html";
        }
      } else {
        alert(result.message || "Registration failed.");
      }
    } catch (error) {
      console.error("Registration error:", error);
      alert("Error connecting to server.");
    }
  });
});