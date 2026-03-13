const state = {
  view: "customer",
  books: [
    {
      id: 101,
      title: "Velvet Autumn",
      author: "Mira Solis",
      price: 18.5,
      stock: 10,
      category: "Fiction",
      cover: "images/velvet-autumn.svg"
    },
    {
      id: 102,
      title: "Quiet Geometry",
      author: "R. Han",
      price: 24.0,
      stock: 6,
      category: "Non-fiction",
      cover: "images/quiet-geometry.svg"
    },
    {
      id: 103,
      title: "Paper Lullaby",
      author: "Neve Holt",
      price: 16.75,
      stock: 12,
      category: "Poetry",
      cover: "images/paper-lullaby.svg"
    },
    {
      id: 104,
      title: "The Amber Hour",
      author: "L. Ikari",
      price: 20.0,
      stock: 8,
      category: "Classics",
      cover: "images/amber-hour.svg"
    },
    {
      id: 105,
      title: "Cedar & Salt",
      author: "Jules Arno",
      price: 19.25,
      stock: 9,
      category: "Fiction",
      cover: "images/cedar-salt.svg"
    },
    {
      id: 106,
      title: "Under Soft Skies",
      author: "A. Mire",
      price: 22.0,
      stock: 4,
      category: "Memoir",
      cover: "images/under-soft-skies.svg"
    }
  ],
  cart: [],
  orders: []
};

const elements = {
  bookGrid: document.getElementById("book-grid"),
  adminTable: document.getElementById("admin-table"),
  cartDrawer: document.getElementById("cart-drawer"),
  cartItems: document.getElementById("cart-items"),
  cartTotal: document.getElementById("cart-total"),
  cartCount: document.getElementById("cart-count"),
  searchInput: document.getElementById("search-input"),
  categoryTags: document.getElementById("category-tags"),
  sortSelect: document.getElementById("sort"),
  adminView: document.getElementById("admin-view")
};

const categories = ["All", ...new Set(state.books.map((book) => book.category))];
let activeCategory = "All";

const formatCurrency = (value) => `$${value.toFixed(2)}`;

const renderCategories = () => {
  const nextCategories = ["All", ...new Set(state.books.map((book) => book.category))];
  categories.length = 0;
  categories.push(...nextCategories);

  elements.categoryTags.innerHTML = categories
    .map(
      (category) =>
        `<button class="tag ${category === activeCategory ? "is-active" : ""}" data-category="${category}">${category}</button>`
    )
    .join("");
};

const getFilteredBooks = () => {
  const query = elements.searchInput.value.trim().toLowerCase();
  let filtered = state.books.filter((book) => {
    const matchesCategory = activeCategory === "All" || book.category === activeCategory;
    const matchesQuery =
      book.title.toLowerCase().includes(query) ||
      book.author.toLowerCase().includes(query) ||
      String(book.id).includes(query);
    return matchesCategory && matchesQuery;
  });

  const sortValue = elements.sortSelect.value;
  if (sortValue === "title") {
    filtered = filtered.sort((a, b) => a.title.localeCompare(b.title));
  } else if (sortValue === "author") {
    filtered = filtered.sort((a, b) => a.author.localeCompare(b.author));
  } else if (sortValue === "price") {
    filtered = filtered.sort((a, b) => a.price - b.price);
  }

  return filtered;
};

const renderBooks = () => {
  const books = getFilteredBooks();
  elements.bookGrid.innerHTML = books
    .map(
      (book) => `
      <article class="book-card">
        <div class="book-cover" style="background-image: url('${book.cover}')"></div>
        <div>
          <div class="book-title">${book.title}</div>
          <div class="book-meta">${book.author} · ${book.category}</div>
        </div>
        <div class="book-actions">
          <span class="book-price">${formatCurrency(book.price)}</span>
          <button class="primary" data-add="${book.id}">Add</button>
        </div>
      </article>
    `
    )
    .join("");
};

const renderAdmin = () => {
  elements.adminTable.innerHTML = state.books
    .map(
      (book) => `
      <div class="admin-row">
        <div>${book.title} <span class="book-meta">(${book.id})</span></div>
        <div>${book.author}</div>
        <div>${book.stock} in stock</div>
        <div>${formatCurrency(book.price)}</div>
      </div>
    `
    )
    .join("");
};

const updateCart = () => {
  const total = state.cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  elements.cartTotal.textContent = formatCurrency(total);
  elements.cartCount.textContent = state.cart.reduce((sum, item) => sum + item.quantity, 0);

  elements.cartItems.innerHTML = state.cart
    .map(
      (item) => `
      <div class="cart-item">
        <div class="cart-item-details">
          <div class="cart-thumb" style="background-image: url('${item.cover}')"></div>
          <div>
            <div class="book-title">${item.title}</div>
            <div class="book-meta">${item.quantity} × ${formatCurrency(item.price)}</div>
          </div>
        </div>
        <button class="ghost" data-remove="${item.id}">Remove</button>
      </div>
    `
    )
    .join("");
};

const renderOrders = () => {
  const ordersList = document.getElementById("orders-list");
  if (!ordersList) {
    return;
  }

  if (state.orders.length === 0) {
    ordersList.innerHTML = `<div class="order-empty">No orders yet. Place your first order to see it here.</div>`;
    return;
  }

  ordersList.innerHTML = state.orders
    .map((order) => {
      const itemsHtml = order.items
        .map(
          (item) => `
          <div class="order-item">
            <span>${item.title} × ${item.quantity}</span>
            <span>${formatCurrency(item.price * item.quantity)}</span>
          </div>
        `
        )
        .join("");

      return `
        <div class="order-card">
          <div class="order-meta">
            <span>Order #${order.id}</span>
            <span>${order.date}</span>
          </div>
          <div class="order-items">
            ${itemsHtml}
          </div>
          <div class="order-meta">
            <span>${order.items.length} items</span>
            <strong>${formatCurrency(order.total)}</strong>
          </div>
        </div>
      `;
    })
    .join("");
};

const addToCart = (bookId) => {
  const book = state.books.find((item) => item.id === bookId);
  if (!book) {
    return;
  }
  const existing = state.cart.find((item) => item.id === bookId);
  if (existing) {
    existing.quantity += 1;
  } else {
    state.cart.push({ ...book, quantity: 1 });
  }
  updateCart();
};

const removeFromCart = (bookId) => {
  state.cart = state.cart.filter((item) => item.id !== bookId);
  updateCart();
};

const setView = (view) => {
  state.view = view;
  document.querySelectorAll(".chip").forEach((chip) => {
    chip.classList.toggle("is-active", chip.dataset.view === view);
  });
  elements.adminView.classList.toggle("is-active", view === "admin");
  document.querySelector(".content").style.display = view === "admin" ? "none" : "grid";
  document.querySelector(".hero").style.display = view === "admin" ? "none" : "grid";
};

const hookEvents = () => {
  document.getElementById("open-cart").addEventListener("click", () => {
    elements.cartDrawer.classList.add("is-open");
  });
  document.getElementById("close-cart").addEventListener("click", () => {
    elements.cartDrawer.classList.remove("is-open");
  });
  document.getElementById("open-auth").addEventListener("click", () => {
    document.getElementById("auth-modal").classList.add("is-open");
  });
  document.getElementById("close-auth").addEventListener("click", () => {
    document.getElementById("auth-modal").classList.remove("is-open");
  });

  document.getElementById("view-orders").addEventListener("click", () => {
    renderOrders();
    document.getElementById("orders-modal").classList.add("is-open");
  });

  document.getElementById("close-orders").addEventListener("click", () => {
    document.getElementById("orders-modal").classList.remove("is-open");
  });

  document.getElementById("shop-now").addEventListener("click", () => {
    document.querySelector(".catalog").scrollIntoView({ behavior: "smooth" });
  });

  const addBookModal = document.getElementById("add-book-modal");
  const addBookForm = document.getElementById("add-book-form");

  document.getElementById("add-book").addEventListener("click", () => {
    addBookModal.classList.add("is-open");
  });

  document.getElementById("close-add-book").addEventListener("click", () => {
    addBookModal.classList.remove("is-open");
  });

  document.getElementById("cancel-add-book").addEventListener("click", () => {
    addBookModal.classList.remove("is-open");
  });

  addBookForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const formData = new FormData(addBookForm);
    const coverUrl = String(formData.get("coverUrl") || "").trim();
    const coverFileInput = addBookForm.querySelector("input[name='coverFile']");
    const coverFile = coverFileInput && coverFileInput.files && coverFileInput.files[0];
    const coverSource = coverFile ? URL.createObjectURL(coverFile) : coverUrl;
    if (!coverSource) {
      alert("Please provide a cover image URL or upload a cover file.");
      return;
    }
    const newBook = {
      id: state.books.length ? Math.max(...state.books.map((book) => book.id)) + 1 : 100,
      title: String(formData.get("title")).trim(),
      author: String(formData.get("author")).trim(),
      category: String(formData.get("category")).trim(),
      price: Number(formData.get("price")),
      stock: Number(formData.get("stock")),
      cover: coverSource
    };

    state.books.unshift(newBook);
    addBookForm.reset();
    addBookModal.classList.remove("is-open");
    activeCategory = "All";
    renderCategories();
    renderBooks();
    renderAdmin();
  });

  document.querySelectorAll(".chip[data-view]").forEach((chip) => {
    chip.addEventListener("click", () => setView(chip.dataset.view));
  });

  document.getElementById("book-grid").addEventListener("click", (event) => {
    const button = event.target.closest("button[data-add]");
    if (!button) {
      return;
    }
    addToCart(Number(button.dataset.add));
  });

  document.getElementById("cart-items").addEventListener("click", (event) => {
    const button = event.target.closest("button[data-remove]");
    if (!button) {
      return;
    }
    removeFromCart(Number(button.dataset.remove));
  });

  elements.searchInput.addEventListener("input", renderBooks);
  elements.sortSelect.addEventListener("change", renderBooks);
  elements.categoryTags.addEventListener("click", (event) => {
    const tag = event.target.closest("button[data-category]");
    if (!tag) {
      return;
    }
    activeCategory = tag.dataset.category;
    renderCategories();
    renderBooks();
  });

  document.getElementById("checkout").addEventListener("click", () => {
    if (state.cart.length === 0) {
      alert("Your cart is empty.");
      return;
    }
    const orderTotal = state.cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
    const orderId = state.orders.length + 1;
    const orderDate = new Date().toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric"
    });
    state.orders.unshift({
      id: orderId,
      date: orderDate,
      items: state.cart.map((item) => ({
        title: item.title,
        quantity: item.quantity,
        price: item.price
      })),
      total: orderTotal
    });

    alert("Order placed! Check Orders history to view details.");
    state.cart = [];
    updateCart();
    elements.cartDrawer.classList.remove("is-open");
  });

  document.querySelectorAll(".tab").forEach((tab) => {
    tab.addEventListener("click", () => {
      document.querySelectorAll(".tab").forEach((t) => t.classList.remove("is-active"));
      tab.classList.add("is-active");
      document.getElementById("login-form").classList.toggle("is-hidden", tab.dataset.tab !== "login");
      document.getElementById("register-form").classList.toggle("is-hidden", tab.dataset.tab !== "register");
    });
  });
};

const init = () => {
  const heroCover = document.getElementById("hero-cover");
  if (heroCover && state.books.length > 0) {
    heroCover.style.backgroundImage = `url('${state.books[0].cover}')`;
  }
  renderCategories();
  renderBooks();
  renderAdmin();
  updateCart();
  renderOrders();
  hookEvents();
  setView("customer");
};

init();
