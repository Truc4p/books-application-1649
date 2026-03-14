document.addEventListener("DOMContentLoaded", () => {
    const userJson = localStorage.getItem("currentUser");
    
    if (!userJson) {
      window.location.href = "login.html";
      return;
    }
  
    const user = JSON.parse(userJson);
    
    document.getElementById("profile-name").textContent = user.name || "Customer";
    document.getElementById("profile-email").textContent = user.email || "-";
    document.getElementById("profile-role").textContent = user.role === "admin" ? "Adminsitrator" : "Customer";
  
    const nav = document.getElementById("profile-nav");
    if (user.role === "admin") {
      nav.innerHTML = `
        <a href="admin.html" class="chip" style="text-decoration:none;">Admin</a>
        <a href="profile.html" class="chip is-active" style="text-decoration:none;">Account</a>
      `;
    } else {
      nav.innerHTML = `
        <a href="index.html" class="chip" style="text-decoration:none;">Shop</a>
        <a href="profile.html" class="chip is-active" style="text-decoration:none;">Account</a>
      `;
    }
  
    document.getElementById("logout-btn").addEventListener("click", () => {
      localStorage.removeItem("currentUser");
      window.location.href = "login.html";
    });
  });