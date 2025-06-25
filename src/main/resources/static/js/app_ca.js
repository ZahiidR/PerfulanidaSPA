// app_car.js
const cartApiUrl = "http://localhost:8080/api/v1/carrito"; // Cambia por tu URL real si es diferente

const cartFrontend = {
  items: [],

  // Cargar carrito desde el backend
  loadFromServer() {
    fetch(cartApiUrl)
      .then(response => response.json())
      .then(data => {
        this.items = data;
        this.updateUI();
      })
      .catch(error => console.error("Error cargando carrito:", error));
  },

  // Agregar producto al carrito vía backend
  addToCart(id) {
    fetch(${cartApiUrl}/agregar/${id}, { method: "POST" })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Error al agregar producto");
      })
      .then(() => {
        this.loadFromServer(); // Actualizar UI
        alert("Producto agregado al carrito");
      })
      .catch(error => alert(error.message));
  },

  // Eliminar producto del carrito
  removeFromCart(productId) {
    fetch(${cartApiUrl}/eliminar/${productId}, { method: "DELETE" })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Error al eliminar producto");
      })
      .then(() => {
        this.loadFromServer();
      })
      .catch(error => alert(error.message));
  },

  // Vaciar carrito
  clearCart() {
    fetch(${cartApiUrl}/vaciar, { method: "DELETE" })
      .then(response => {
        if (response.ok) {
          this.items = [];
          this.updateUI();
        } else {
          throw new Error("No se pudo vaciar el carrito");
        }
      })
      .catch(error => alert(error.message));
  },

  // Obtener cantidad total de productos
  getTotalItems() {
    fetch(${cartApiUrl}/total)
      .then(response => response.json())
      .then(data => {
        document.querySelector(".cart-count").textContent = data;
      });
  },

  // Obtener precio total del carrito
  getTotalPrice() {
    fetch(${cartApiUrl}/precioTotal)
      .then(response => response.json())
      .then(data => {
        document.querySelector(".cart-total").textContent = $${data.totalPrecio};
      });
  },

  // Actualizar UI del carrito
  updateUI() {
    const cartItemsContainer = document.getElementById("cartItems");
    const cartCount = document.querySelector(".cart-count");

    if (!cartItemsContainer) return;

    cartItemsContainer.innerHTML = "";

    if (this.items.length === 0) {
      cartItemsContainer.innerHTML = '<p class="text-center empty-cart-message">Tu carrito está vacío</p>';
      cartCount.textContent = "0";
      return;
    }

    this.items.forEach(item => {
      const el = document.createElement("div");
      el.className = "d-flex justify-content-between align-items-center mb-2";
      el.innerHTML = `
        <div>
          <strong>${item.nombrePerfume}</strong><br>
          $${item.precio.toFixed(2)} x ${item.cantidad || 1}
        </div>
        <button class="btn btn-sm btn-danger" onclick="cartFrontend.removeFromCart(${item.id})">
          <i class="fas fa-trash"></i>
        </button>
      `;
      cartItemsContainer.appendChild(el);
    });

    this.getTotalItems();
    this.getTotalPrice();
  }
};

// Exportarlo globalmente
window.cartFrontend = cartFrontend;