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
      cover: "images/velvet-autumn.svg",
      description: "A grieving florist uncovers letters hidden in secondhand books and follows them through one fading city block before autumn ends.",
      isbn: "978-1-60123-410-1",
      publisher: "Willow & Pine Press",
      year: 2021,
      pages: 336,
      language: "English",
      format: "Paperback"
    },
    {
      id: 102,
      title: "Quiet Geometry",
      author: "R. Han",
      price: 24.0,
      stock: 6,
      category: "Non-fiction",
      cover: "images/quiet-geometry.svg",
      description: "A visual guide to how symmetry, proportion, and pattern shape architecture, transit maps, and everyday decision-making.",
      isbn: "978-1-60123-411-8",
      publisher: "North Axis Books",
      year: 2020,
      pages: 288,
      language: "English",
      format: "Hardcover"
    },
    {
      id: 103,
      title: "Paper Lullaby",
      author: "Neve Holt",
      price: 16.75,
      stock: 12,
      category: "Poetry",
      cover: "images/paper-lullaby.svg",
      description: "A soft-spoken collection of poems about night trains, voicemail ghosts, and the small rituals that keep people tender.",
      isbn: "978-1-60123-412-5",
      publisher: "Lantern Verse",
      year: 2019,
      pages: 176,
      language: "English",
      format: "Paperback"
    },
    {
      id: 104,
      title: "The Amber Hour",
      author: "L. Ikari",
      price: 20.0,
      stock: 8,
      category: "Classics",
      cover: "images/amber-hour.svg",
      description: "A restored 1958 novella about two radio hosts broadcasting hope through a coastal blackout during a historic storm.",
      isbn: "978-1-60123-413-2",
      publisher: "Mariner Classics",
      year: 1958,
      pages: 248,
      language: "English",
      format: "Collector's Edition"
    },
    {
      id: 105,
      title: "Cedar & Salt",
      author: "Jules Arno",
      price: 19.25,
      stock: 9,
      category: "Fiction",
      cover: "images/cedar-salt.svg",
      description: "On a remote island, a chef and a cartographer map both shoreline and memory after a decade-long family feud.",
      isbn: "978-1-60123-414-9",
      publisher: "Tidehouse",
      year: 2022,
      pages: 312,
      language: "English",
      format: "Paperback"
    },
    {
      id: 106,
      title: "Under Soft Skies",
      author: "A. Mire",
      price: 22.0,
      stock: 4,
      category: "Memoir",
      cover: "images/under-soft-skies.svg",
      description: "A painter documents one year of recovery through cloud studies, bus rides, and late-night notes to her younger self.",
      isbn: "978-1-60123-415-6",
      publisher: "Blue Room Memoir",
      year: 2023,
      pages: 264,
      language: "English",
      format: "Hardcover"
    },
    {
      id: 107,
      title: "Midnight Flora",
      author: "Elara Vance",
      price: 21.50,
      stock: 15,
      category: "Fiction",
      cover: "images/velvet-autumn.svg",
      description: "A botanist cataloging nocturnal flowers discovers coded messages in a greenhouse logbook tied to a vanished musician.",
      isbn: "978-1-60123-416-3",
      publisher: "Fernlight House",
      year: 2024,
      pages: 340,
      language: "English",
      format: "Paperback"
    },
    {
      id: 108,
      title: "Silent Architects",
      author: "K. T. Lin",
      price: 28.0,
      stock: 5,
      category: "Non-fiction",
      cover: "images/quiet-geometry.svg",
      description: "Profiles of overlooked engineers whose bridge, dam, and housing designs transformed life across three continents.",
      isbn: "978-1-60123-417-0",
      publisher: "Civic Frame",
      year: 2018,
      pages: 402,
      language: "English",
      format: "Hardcover"
    },
    {
      id: 109,
      title: "Echoes in Ink",
      author: "J. P. Morran",
      price: 15.0,
      stock: 20,
      category: "Poetry",
      cover: "images/paper-lullaby.svg",
      description: "Minimalist poems built from courtroom transcripts, diary fragments, and one stubborn rainstorm over seven days.",
      isbn: "978-1-60123-418-7",
      publisher: "Quiet Bell Poetry",
      year: 2022,
      pages: 160,
      language: "English",
      format: "Paperback"
    },
    {
      id: 110,
      title: "The Gilded Cage",
      author: "S. V. Stone",
      price: 23.50,
      stock: 7,
      category: "Classics",
      cover: "images/amber-hour.svg",
      description: "A satirical classic following a social climber who inherits a palace where every room enforces a different rule.",
      isbn: "978-1-60123-419-4",
      publisher: "Mariner Classics",
      year: 1931,
      pages: 296,
      language: "English",
      format: "Annotated Paperback"
    },
    {
      id: 111,
      title: "Driftwood Tales",
      author: "Mara Reed",
      price: 17.99,
      stock: 11,
      category: "Fiction",
      cover: "images/cedar-salt.svg",
      description: "Interlinked stories from a harbor town where a retired diver mentors a generation that has never seen open sea.",
      isbn: "978-1-60123-420-0",
      publisher: "Harborline",
      year: 2021,
      pages: 284,
      language: "English",
      format: "Paperback"
    },
    {
      id: 112,
      title: "Memories of Mist",
      author: "O. Renn",
      price: 25.0,
      stock: 3,
      category: "Memoir",
      cover: "images/under-soft-skies.svg",
      description: "A mountain guide retraces lost trails with his daughter and records the stories his village forgot to pass down.",
      isbn: "978-1-60123-421-7",
      publisher: "Blue Room Memoir",
      year: 2020,
      pages: 318,
      language: "English",
      format: "Hardcover"
    },
    {
      id: 113,
      title: "The Glass Horizon",
      author: "Lila Bourne",
      price: 19.50,
      stock: 14,
      category: "Fiction",
      cover: "images/velvet-autumn.svg",
      description: "Two sisters rebuild their late mother's observatory and uncover a decades-old correspondence with an unknown astronomer.",
      isbn: "978-1-60123-422-4",
      publisher: "Skylight Row",
      year: 2023,
      pages: 352,
      language: "English",
      format: "Paperback"
    },
    {
      id: 114,
      title: "Urban Patterns",
      author: "T. Z. Chen",
      price: 26.0,
      stock: 8,
      category: "Non-fiction",
      cover: "images/quiet-geometry.svg",
      description: "An evidence-rich look at why some neighborhoods become walkable, resilient, and socially connected while others fragment.",
      isbn: "978-1-60123-423-1",
      publisher: "North Axis Books",
      year: 2024,
      pages: 376,
      language: "English",
      format: "Hardcover"
    },
    {
      id: 115,
      title: "Whispers of Green",
      author: "E. F. Wells",
      price: 14.50,
      stock: 18,
      category: "Poetry",
      cover: "images/paper-lullaby.svg",
      description: "Nature poems written across four seasons, balancing field-note precision with intimate reflections on family and place.",
      isbn: "978-1-60123-424-8",
      publisher: "Lantern Verse",
      year: 2017,
      pages: 148,
      language: "English",
      format: "Paperback"
    },
    {
      id: 116,
      title: "Vintage Dreams",
      author: "H. G. Wells",
      price: 21.0,
      stock: 6,
      category: "Classics",
      cover: "images/amber-hour.svg",
      description: "A rediscovered early-20th-century novel where a clockmaker predicts market crashes by listening to church bells.",
      isbn: "978-1-60123-425-5",
      publisher: "Old Lantern Editions",
      year: 1912,
      pages: 272,
      language: "English",
      format: "Restored Hardcover"
    },
    {
      id: 117,
      title: "Pine & Paper",
      author: "C. D. Lewis",
      price: 18.25,
      stock: 13,
      category: "Fiction",
      cover: "images/cedar-salt.svg",
      description: "Set in a traveling print shop, this novel follows apprentices preserving banned folk stories one town at a time.",
      isbn: "978-1-60123-426-2",
      publisher: "Willow & Pine Press",
      year: 2022,
      pages: 304,
      language: "English",
      format: "Paperback"
    },
    {
      id: 118,
      title: "Shadows of Yesterday",
      author: "M. N. Knight",
      price: 24.50,
      stock: 5,
      category: "Memoir",
      cover: "images/under-soft-skies.svg",
      description: "A former war photojournalist revisits archived negatives and finally writes the stories behind each image.",
      isbn: "978-1-60123-427-9",
      publisher: "Blue Room Memoir",
      year: 2019,
      pages: 332,
      language: "English",
      format: "Hardcover"
    },
    {
      id: 119,
      title: "Crimson Leaves",
      author: "P. Q. Ross",
      price: 20.50,
      stock: 9,
      category: "Fiction",
      cover: "images/velvet-autumn.svg",
      description: "A campus mystery where botany students decode pigments in autumn leaves to solve a ten-year-old disappearance.",
      isbn: "978-1-60123-428-6",
      publisher: "Skylight Row",
      year: 2025,
      pages: 326,
      language: "English",
      format: "Paperback"
    },
    {
      id: 120,
      title: "Logic of Light",
      author: "A. B. Clarke",
      price: 27.0,
      stock: 4,
      category: "Non-fiction",
      cover: "images/quiet-geometry.svg",
      description: "Explains optics from camera lenses to fiber networks with experiments that can be run using household materials.",
      isbn: "978-1-60123-429-3",
      publisher: "Civic Frame",
      year: 2021,
      pages: 390,
      language: "English",
      format: "Hardcover"
    }
  ],
  cart: [],
  orders: []
};

const elements = new Proxy({}, {
  get: function(target, prop) {
    const idMap = {
      bookGrid: "book-grid",
      adminTable: "admin-table",
      cartDrawer: "cart-drawer",
      cartItems: "cart-items",
      cartTotal: "cart-total",
      cartCount: "cart-count",
      searchInput: "search-input",
      categoryTags: "category-tags",
      sortSelect: "sort",
      adminView: "admin-view",
      bookDetailsModal: "book-details-modal",
      bookDetailsContent: "book-details-content"
    };
    if (idMap[prop]) {
       return document.getElementById(idMap[prop]) || {
         classList: { add:()=>{}, remove:()=>{}, toggle:()=>{} },
         style: {},
         innerHTML: '',
         value: '',
         appendChild: ()=>{},
         addEventListener: ()=>{},
         querySelector: () => null,
         querySelectorAll: () => []
       };
    }
    return target[prop];
  }
});

const categories = ["All", ...new Set(state.books.map((book) => book.category))];
let activeCategory = "All";

const getNextBookId = () => (state.books.length ? Math.max(...state.books.map((book) => book.id)) + 1 : 100);

const getBookCoverMarkup = (book, variant = "card") => `
  <div class="book-cover book-cover--${variant}">
    <div class="book-cover-inner">
      <div class="book-cover-title-text">${book.title}</div>
      <div class="book-cover-author-text">${book.author}</div>
    </div>
  </div>
`;

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

const showBookDetails = (bookId) => {
  const book = state.books.find((item) => item.id === bookId);
  if (!book) return;

  elements.bookDetailsContent.innerHTML = `
    <div style="display: flex; gap: 32px; align-items: flex-start;">
      <div class="book-cover-wrap">
        ${getBookCoverMarkup(book, "detail")}
      </div>
      <div style="flex: 1;">
        <h2 style="font-family: 'Fraunces', serif; font-size: 2rem; margin-bottom: 8px; line-height: 1.2;">${book.title}</h2>
        <div style="color: #6b6257; font-size: 1.1rem; margin-bottom: 16px;">By ${book.author}</div>
        
        <div style="display: flex; gap: 12px; margin-bottom: 24px;">
           <span class="chip is-active">${book.category}</span>
           ${book.stock < 5 ? '<span class="chip" style="color: #ef4444; border-color: #ef4444;">Low stock</span>' : ''}
        </div>

        <p style="font-size: 1.5rem; font-weight: 600; color: #2a2a2a; margin-bottom: 12px;">${formatCurrency(book.price)}</p>

        <div style="display: grid; gap: 6px; margin-bottom: 18px; color: #5f5448; font-size: 0.95rem;">
          <div><strong>ISBN:</strong> ${book.isbn || "N/A"}</div>
          <div><strong>Publisher:</strong> ${book.publisher || "N/A"} (${book.year || "N/A"})</div>
          <div><strong>Format:</strong> ${book.format || "N/A"} · <strong>Pages:</strong> ${book.pages || "N/A"} · <strong>Language:</strong> ${book.language || "N/A"}</div>
        </div>

        <p style="color: #4b5563; line-height: 1.6; margin-bottom: 32px;">
          ${book.description || "No description available for this title yet."}
        </p>

        <div style="display: flex; gap: 16px; align-items: center;">
          <button id="details-add-btn" class="primary" style="padding: 12px 32px; font-size: 1.1rem;">
            Add to Bag
          </button>
          <div style="color: #6b6257;">${book.stock} copies available</div>
        </div>
      </div>
    </div>
  `;
  
  safeAddEventListener("details-add-btn", "click", () => {
    addToCart(book.id);
    elements.bookDetailsModal.classList.remove('is-open');
  });

  elements.bookDetailsModal.classList.add("is-open");
};

const renderBooks = () => {
  const books = getFilteredBooks();
  elements.bookGrid.innerHTML = books
    .map(
      (book) => `
      <article class="book-card" data-id="${book.id}">
        ${getBookCoverMarkup(book, "card")}
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
  const sortedBooks = [...state.books].sort((a, b) => a.title.localeCompare(b.title));

  elements.adminTable.innerHTML = `
    <div class="admin-head-row">
      <div>Book</div>
      <div>Author</div>
      <div>Stock</div>
      <div>Price</div>
      <div>Actions</div>
    </div>
    ${sortedBooks
    .map(
      (book) => `
      <div class="admin-row">
        <div>
          <div class="admin-book-title">${book.title}</div>
          <span class="book-meta">ID ${book.id} · ${book.category}</span>
        </div>
        <div>${book.author}</div>
        <div>
          ${book.stock} in stock
          ${book.stock < 5 ? '<span class="low-stock-badge">Low</span>' : ""}
        </div>
        <div>${formatCurrency(book.price)}</div>
        <div class="admin-actions">
          <button class="ghost admin-action admin-icon-btn" data-admin-edit="${book.id}" aria-label="Edit ${book.title}" title="Edit">
            <svg viewBox="0 0 24 24" class="admin-icon" aria-hidden="true" focusable="false">
              <path d="M4 20h4l10-10-4-4L4 16v4zm13.7-11.3c.4-.4.4-1 0-1.4l-1-1c-.4-.4-1-.4-1.4 0l-1 1 2.4 2.4 1-1z"></path>
            </svg>
          </button>
          <button class="danger admin-action admin-icon-btn" data-admin-delete="${book.id}" aria-label="Delete ${book.title}" title="Delete">
            <svg viewBox="0 0 24 24" class="admin-icon" aria-hidden="true" focusable="false">
              <path d="M7 21c-.6 0-1-.4-1-1V7h12v13c0 .6-.4 1-1 1H7zm3-3h2V10h-2v8zm4 0h2V10h-2v8zM15.5 4l-1-1h-5l-1 1H5v2h14V4h-3.5z"></path>
            </svg>
          </button>
        </div>
      </div>
    `
    )
    .join("")}
  `;
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
          ${getBookCoverMarkup(item, "thumb")}
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

async function fetchOrders() {
    try {
        const response = await fetch(`https://books-application-1649.onrender.com/api/orders`);
        if (response.ok) {
            const data = await response.json(); data.forEach(o => o.total = o.items.reduce((s, i) => s + i.price * i.quantity, 0)); state.orders = data;
            renderOrders();
        }
    } catch (error) {
        console.error('Error fetching orders:', error);
    }
}

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
  localStorage.setItem("currentView", view);
  document.querySelectorAll(".chip").forEach((chip) => {
    chip.classList.toggle("is-active", chip.dataset.view === view);
  });
  elements.adminView.classList.toggle("is-active", view === "admin");
  const c = document.querySelector(".content"); if(c) c.style.display = view === "admin" ? "none" : "grid";
  const h = document.querySelector(".hero"); if(h) h.style.display = view === "admin" ? "none" : "grid";
};


const safeAddEventListener = (id, event, handler) => {
  const el = typeof id === 'string' ? document.getElementById(id) : id;
  if (el) el.addEventListener(event, handler);
}

const hookEvents = () => {
  safeAddEventListener("open-cart", "click", () => {
    elements.cartDrawer.classList.add("is-open");
  });
  safeAddEventListener("close-cart", "click", () => {
    elements.cartDrawer.classList.remove("is-open");
  });
  safeAddEventListener("open-auth", "click", () => {
    document.getElementById("auth-modal").classList.add("is-open");
  });
  safeAddEventListener("close-auth", "click", () => {
    document.getElementById("auth-modal").classList.remove("is-open");
  });

  safeAddEventListener("view-orders", "click", () => {
    renderOrders();
    document.getElementById("orders-modal").classList.add("is-open");
  });

  safeAddEventListener("close-orders", "click", () => {
    document.getElementById("orders-modal").classList.remove("is-open");
  });

  safeAddEventListener("shop-now", "click", () => {
    const cat = document.querySelector(".catalog"); if(cat) cat.scrollIntoView({ behavior: "smooth" });
  });

  const addBookModal = document.getElementById("add-book-modal");
  const addBookForm = document.getElementById("add-book-form");
  const editBookModal = document.getElementById("edit-book-modal");
  const editBookForm = document.getElementById("edit-book-form");

  safeAddEventListener("add-book", "click", () => {
    addBookModal.classList.add("is-open");
  });

  safeAddEventListener("close-add-book", "click", () => {
    addBookModal.classList.remove("is-open");
  });

  safeAddEventListener("cancel-add-book", "click", () => {
    addBookModal.classList.remove("is-open");
  });

  if(addBookForm) addBookForm.addEventListener("submit", (event) => {
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
      id: getNextBookId(),
      title: String(formData.get("title")).trim(),
      author: String(formData.get("author")).trim(),
      category: String(formData.get("category")).trim(),
      price: Number(formData.get("price")),
      stock: Number(formData.get("stock")),
      cover: coverSource
    };

    // Send the new book to the backend
    try {
      fetch('https://books-application-1649.onrender.com/api/books', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newBook)
      }).catch(err => {
        console.warn("Could not save to backend. Change is only local.", err);
      });
    } catch (err) {
      console.warn("Could not save to backend. Change is only local.", err);
    }

    state.books.unshift(newBook);
    addBookForm.reset();
    addBookModal.classList.remove("is-open");
    activeCategory = "All";
    renderCategories();
    renderBooks();
    renderAdmin();
  });

  const closeEditBookModal = () => {
    editBookForm.reset();
    editBookModal.classList.remove("is-open");
  };

  safeAddEventListener("close-edit-book", "click", closeEditBookModal);
  safeAddEventListener("cancel-edit-book", "click", closeEditBookModal);

  if(editBookForm) editBookForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(editBookForm);
    const bookId = Number(formData.get("id"));
    const targetBook = state.books.find((book) => book.id === bookId);
    if (!targetBook) {
      closeEditBookModal();
      return;
    }

    const coverUrl = String(formData.get("coverUrl") || "").trim();
    const coverFileInput = editBookForm.querySelector("input[name='coverFile']");
    const coverFile = coverFileInput && coverFileInput.files && coverFileInput.files[0];
    const nextCover = coverFile ? URL.createObjectURL(coverFile) : coverUrl || targetBook.cover;

    targetBook.title = String(formData.get("title")).trim();
    targetBook.author = String(formData.get("author")).trim();
    targetBook.category = String(formData.get("category")).trim();
    targetBook.price = Number(formData.get("price"));
    targetBook.stock = Number(formData.get("stock"));
    targetBook.cover = nextCover;

    // Send update to Java server
    try {
      await fetch('https://books-application-1649.onrender.com/api/books', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(targetBook)
      });
    } catch (err) {
      console.warn("Could not save to backend. Change is only local.", err);
    }

    closeEditBookModal();
    renderCategories();
    renderBooks();
    renderAdmin();
  });

  document.querySelectorAll(".chip[data-view]").forEach((chip) => {
    chip.addEventListener("click", () => setView(chip.dataset.view));
  });

  safeAddEventListener("book-grid", "click", (event) => {
    const button = event.target.closest("button[data-add]");
    if (button) {
      addToCart(Number(button.dataset.add));
      return;
    }

    const card = event.target.closest(".book-card");
    if (card) {
      showBookDetails(Number(card.dataset.id));
    }
  });

  elements.adminTable.addEventListener("click", (event) => {
    const editButton = event.target.closest("button[data-admin-edit]");
    if (editButton) {
      const bookId = Number(editButton.dataset.adminEdit);
      const book = state.books.find((item) => item.id === bookId);
      if (!book) {
        return;
      }

      editBookForm.elements.id.value = String(book.id);
      editBookForm.elements.title.value = book.title;
      editBookForm.elements.author.value = book.author;
      editBookForm.elements.category.value = book.category;
      editBookForm.elements.price.value = String(book.price);
      editBookForm.elements.stock.value = String(book.stock);
      editBookForm.elements.coverUrl.value = book.cover && String(book.cover).startsWith("http") ? book.cover : "";
      const coverFileInput = editBookForm.querySelector("input[name='coverFile']");
      if (coverFileInput) {
        coverFileInput.value = "";
      }
      editBookModal.classList.add("is-open");
      return;
    }

    const deleteButton = event.target.closest("button[data-admin-delete]");
    if (!deleteButton) {
      return;
    }

    const bookId = Number(deleteButton.dataset.adminDelete);
    const book = state.books.find((item) => item.id === bookId);
    if (!book) {
      return;
    }

    const shouldDelete = window.confirm(`Delete "${book.title}" from inventory?`);
    if (!shouldDelete) {
      return;
    }

    // Send delete to Java server
    try {
      fetch('https://books-application-1649.onrender.com/api/books?id=' + bookId, {
        method: 'DELETE'
      }).catch(err => {
        console.warn("Could not save to backend. Change is only local.", err);
      });
    } catch (err) {
      console.warn("Could not save to backend. Change is only local.", err);
    }

    state.books = state.books.filter((item) => item.id !== bookId);
    state.cart = state.cart.filter((item) => item.id !== bookId);
    renderCategories();
    renderBooks();
    renderAdmin();
    updateCart();
  });

  safeAddEventListener("close-book-details", "click", () => {
    elements.bookDetailsModal.classList.remove("is-open");
  });

  safeAddEventListener("cart-items", "click", (event) => {
    const button = event.target.closest("button[data-remove]");
    if (!button) {
      return;
    }
    removeFromCart(Number(button.dataset.remove));
  });

  if(elements.searchInput.addEventListener) elements.searchInput.addEventListener("input", renderBooks);
  if(elements.sortSelect.addEventListener) elements.sortSelect.addEventListener("change", renderBooks);
  elements.categoryTags.addEventListener("click", (event) => {
    const tag = event.target.closest("button[data-category]");
    if (!tag) {
      return;
    }
    activeCategory = tag.dataset.category;
    renderCategories();
    renderBooks();
  });

  safeAddEventListener("checkout", "click", () => {
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

const init = async () => {
  state.view = "admin";
  setView("admin");

  try {
    // Fetch from Java WebServer
    const response = await fetch('https://books-application-1649.onrender.com/api/books');
    if (response.ok) {
      const dbBooks = await response.json();
      if (dbBooks && dbBooks.length > 0) {
        state.books = dbBooks;
      }
    }
  } catch (err) {
    console.warn("Could not connect to Java backend. Falling back to local hardcoded books.", err);
  }

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
};

init();
