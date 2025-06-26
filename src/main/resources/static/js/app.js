const API_URL = "http://10.155.64.162:8080/api/v1/perfumes";
const CARRITO_API = "http://10.155.64.162:8080/api/v1/carrito";

// ✅ Actualizar el contador del carrito
async function actualizarContadorCarrito() {
  try {
    const response = await fetch(`${CARRITO_API}/total`);
    if (!response.ok) throw new Error("Error al obtener el total del carrito");
    const total = await response.json();

    const contadorCarrito = document.getElementById("contadorCarrito");
    if (contadorCarrito) {
      contadorCarrito.textContent = total;
    }
  } catch (error) {
    console.error("Error actualizando el contador del carrito:", error);
  }
}

// ✅ Listar perfumes en tabla
function listarPerfumes() {
  fetch(API_URL)
    .then(response => response.json())
    .then(perfumes => {
      const tbody = document.querySelector("#tablaPerfumes tbody");
      tbody.innerHTML = "";
      
      perfumes.forEach(perfume => {
        const fila = `
          <tr>
            <td>${perfume.id}</td>
            <td>
              <img src="${perfume.imagen || 'https://via.placeholder.com/60'}" width="60" height="60" alt="${perfume.nombrePerfume}">
            </td>
            <td>${perfume.nombrePerfume}</td>
            <td>${perfume.marca}</td>
            <td>${perfume.precio}</td>
            <td>${perfume.cantidadMl}</td>
            <td>${perfume.descripcion}</td>
            <td>${perfume.stock}</td>
            <td>
              <button class="btn btn-success btn-sm" onclick="agregarAlCarrito(${perfume.id})">🛒 Añadir</button>
            </td>
          </tr>
        `;
        tbody.innerHTML += fila;
      }); // ✅ Aquí cierras el forEach

    }) // ✅ Aquí cierras el .then
    .catch(error => {
      console.error("Error al cargar perfumes:", error);
    });
}


// ✅ Agregar un perfume al carrito
function agregarAlCarrito(id) {
  fetch(`${CARRITO_API}/agregar/${id}`, { method: "POST" })
    .then(response => {
      if (response.ok) {
        alert("✅ Perfume agregado al carrito (backend)");
        actualizarContadorCarrito();
      } else {
        alert("❌ Error al agregar perfume al carrito");
      }
    })
    .catch(error => {
      console.error("❌ Error de conexión con el backend:", error);
      alert("❌ No se pudo conectar al servidor");
    });
}

// ✅ Agregar un perfume nuevo
function agregarPerfume() {
  const nombrePerfume = document.getElementById("nombrePerfume").value;
  const marca = document.getElementById("marca").value;
  const precio = document.getElementById("precio").value;
  const cantidadMl = document.getElementById("cantidadMl").value;
  const descripcion = document.getElementById("descripcion").value;
  const stock = document.getElementById("stock").value;

  const nuevoPerfume = {
    nombrePerfume,
    marca,
    precio: parseInt(precio) || 0,
    cantidadMl: parseInt(cantidadMl) || 0,
    descripcion,
    stock: parseInt(stock) || 0
  };

  fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(nuevoPerfume)
  })
    .then(response => response.json())
    .then(data => {
      alert("Perfume agregado exitosamente");
      listarPerfumes();
      limpiarFormulario();
    });
}

// ✅ Eliminar perfume
function eliminarPerfume(id) {
  fetch(`${API_URL}/${id}`, { method: "DELETE" })
    .then(response => {
      if (response.ok) {
        alert("Perfume eliminado exitosamente");
        listarPerfumes();
      }
    });
}

// ✅ Buscar perfume para editar
let perfumeEnEdicionId = null;
function buscarPerfume(id) {
  fetch(`${API_URL}/${id}`)
    .then(response => response.json())
    .then(perfume => {
      document.getElementById("nombrePerfume").value = perfume.nombrePerfume;
      document.getElementById("marca").value = perfume.marca;
      document.getElementById("precio").value = perfume.precio;
      document.getElementById("cantidadMl").value = perfume.cantidadMl;
      document.getElementById("descripcion").value = perfume.descripcion;
      document.getElementById("stock").value = perfume.stock;

      perfumeEnEdicionId = perfume.id;

      const boton = document.getElementById("botonActualizar");
      if (boton) {
        boton.textContent = "Actualizar Perfume";
        boton.onclick = function () {
          actualizarPerfumes(perfume.id);
        };
      }
    });
}

// ✅ Actualizar perfume
function actualizarPerfumes(id) {
  const nombrePerfume = document.getElementById("nombrePerfume").value;
  const marca = document.getElementById("marca").value;
  const precio = document.getElementById("precio").value;
  const cantidadMl = document.getElementById("cantidadMl").value;
  const descripcion = document.getElementById("descripcion").value;
  const stock = document.getElementById("stock").value;

  const perfumeActualizado = {
    id,
    nombrePerfume,
    marca,
    precio: parseInt(precio) || 0,
    cantidadMl: parseInt(cantidadMl) || 0,
    descripcion,
    stock: parseInt(stock) || 0
  };

  fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(perfumeActualizado)
  })
    .then(response => response.json())
    .then(data => {
      alert("Perfume actualizado exitosamente");
      listarPerfumes();
      limpiarFormulario();
    });
}

// ✅ Limpiar formulario
function limpiarFormulario() {
  document.getElementById("nombrePerfume").value = "";
  document.getElementById("marca").value = "";
  document.getElementById("precio").value = "";
  document.getElementById("cantidadMl").value = "";
  document.getElementById("descripcion").value = "";
  document.getElementById("stock").value = "";

  const boton = document.getElementById("botonFormulario");
  if (boton) {
    boton.innerText = "Agregar Perfume";
    boton.setAttribute("onclick", "agregarPerfume()");
  }

  perfumeEnEdicionId = null;
}

// ✅ Al cargar la página
document.addEventListener("DOMContentLoaded", () => {
  listarPerfumes();
  actualizarContadorCarrito();
});
