
const API_URL = "http://localhost:8080/api/v1/perfumes"; // URL de la API para acceder a los perfumes
const CARRITO_API = "http://localhost:8080/api/v1/carrito";

// Función para listar los perfumes en la tabla
// Se utiliza la API Fetch para obtener los datos de los perfumes  desde el servidor
function listarPerfumes() {
    fetch(PERFUMES_API)
        .then(response => response.json())
        .then(perfumes => {
            const tbody = document.querySelector("#tablaPerfumes tbody");
            tbody.innerHTML = "";
            perfumes.forEach(perfumes => {
                const fila = `
                    <tr>
                        <td>${perfumes.id}</td>
                        <td>${perfumes.nombrePerfume}</td>
                        <td>${perfumes.marca}</td>
                        <td>${perfumes.precio}}</td>
                        <td>${perfumes.cantidadMl}</td>
                        <td>${perfumes.descripcion}</td>
                        <td>${perfumes.stock}</td>
                        <td> 
                            <button class="btn btn-success btn-sm" onclick="carrito.agregarPerfumes(${perfumes.id})">🛒 Añadir</button>
                        </td>
                    </tr>
                `; 
                tbody.innerHTML += fila;
            });
        });
}
let perfumes = []; // Variable para almacenar la lista de perfumes
// Función para agregar un perfume
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
        stock: parseInt(stock) || 0 // Asegurarse de que el precio sea un número
    };
    // Enviar el nuevo libro al servidor
    // Se utiliza la API Fetch para enviar los datos al servidor
    fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(nuevoPerfume)
    })// Enviar el nuevo perfume al servidor
    .then(response => response.json())
    .then(data => {
        alert("Perfume agregado exitosamente");
        listarPerfumes();// Actualizar la tabla de perfumes
        limpiarFormulario();// Limpiar el formulario
    });
}
// Función para eliminar un perfume
function eliminarPerfume(id) {
    fetch(`${API_URL}/${id}`, { method: "DELETE" })
        .then(response => {
            if (response.ok) {
                alert("Perfume eliminado exitosamente");
                listarPerfumes();
            }
        });
}
// Función para buscar un perfume por su ID y cargarlo en el formulario
// Se utiliza la API Fetch para obtener los datos del perfume desde el servidor
let perfumeEnEdicionId = null; // Variable para almacenar el ID del perfume en edición
function buscarPerfume(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(perfumes => {
            document.getElementById("nombrePerfume").value = perfume.nombrePerfume;
            document.getElementById("marca").value = perfumes.marca;
            document.getElementById("precio").value = perfumes.precio;
            document.getElementById("cantidadMl").value = perfume.cantidadMl;
            document.getElementById("descripcion").value = perfume.descripcion;
            document.getElementById("stock").value = perfume.stock;

             // Guardar el ID del perfume en edición
             perfumeEnEdicionId = perfumes.id;
             
            // Cambiar el botón de agregar por actualizar
            const boton = document.getElementById("botonActualizar");
            if (boton) {
                boton.textContent = "Actualizar Perfumes";
                boton.onclick = function() {
                    actualizarPerfumes(perfumes.id);
                };
            }
        });
}
// Función para actualizar un perfume
// Se utiliza la API Fetch para enviar los datos actualizados al servidor
function actualizarPerfumes(id) {
    const nombrePerfume =  document.getElementById("nombrePerfume").value = perfume.nombrePerfume;
    const marca = document.getElementById("marca").value = perfumes.marca;
    const precio = document.getElementById("precio").value = perfumes.precio;
    const cantidadMl = document.getElementById("cantidadMl").value = perfume.cantidadMl;
    const descripcion = document.getElementById("descripcion").value = perfume.descripcion;
    const stock = document.getElementById("stock").value = perfume.stock;

    const perfumeActualizado = {
        id: id,
        nombrePerfume: nombrePerfume,
        marca: marca,
        precio: parseInt(precio) || 0,
        cantidadMl: cantidadMl,
        descripcion: descripcion,
        stock: stock
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
// Función para limpiar el formulario después de agregar o actualizar un perfume
// Se utiliza para restaurar el formulario a su estado inicial
function limpiarFormulario() {
    document.getElementById("nombrePerfume").value = "";
    document.getElementById("marca").value = "";
    document.getElementById("precio").value = "";
    document.getElementById("cantidadMl").value = "";
    document.getElementById("descripcion").value = "";
    document.getElementById("stock").value = "";

    // Restaurar botón
    const boton = document.getElementById("botonFormulario");
    boton.innerText = "Agregar Perfume";
    boton.setAttribute("onclick", "agregarPerfume()");

    // Resetear la variable global
    perfumeEnEdicionId = null; // Resetear el ID después de limpiar
}

// Cargar perfumes al abrir la página

listarPerfumes();
