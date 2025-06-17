const carrito = (() => {
    const API = "/api/v1/carrito";
    
    async function listarCarrito() {
        try {
            const response = await fetch(API); 
            const perfumes = await response.json();

            const tbody = document.querySelector("#tablaCarrito tbody"); // Obtener el tbody de la tabla
            const totalSpan = document.getElementById("totalCarrito"); // Obtener el span del total
            const totalPrecio = document.getElementById("totalPrecio"); // Obtener el span del total precio
            tbody.innerHTML = ""; // Limpiar el tbody antes de agregar nuevos elementos
            totalSpan.textContent = perfumes.length; // Mostrar la cantidad de perfumes en el carrito

            let sumaTotal = 0;// Inicializar sumaTotal
            
            perfumes.forEach(perfumes => { // Iterar sobre cada perfume en el carrito
                sumaTotal += perfume.precio ?? 0;// Asumiendo que cada perfume tiene un precio
                const fila = `
                    <tr>
                        <td>${perfumes.id}</td>
                        <td>${perfumes.nombrePerfume}</td>
                        <td>${perfumes.marca}</td>
                        <td>${perfumes.precio}</td>
                        <td>${perfumes.cantidadMl}</td>
                        <td>${perfumes.descripccion}</td>
                        <td>${perfumes.stock}</td>
                        <td> 
                            <button class="btn btn-sm btn-danger" onclick="carrito.eliminarPerfumes(${perfumes.id})">🗑️</button> 
                        </td> 
                    </tr>
                `;
                tbody.innerHTML += fila; // Agregar la fila al tbody
            });
            totalPrecio.textContent = sumaTotal; // Mostrar el total en el span

        } catch (err) {
            console.error("Error al cargar carrito", err);
        }
    }
    // Funciones para agregar, eliminar y vaciar el carrito
    async function agregarPerfume(id) {
        try {
            await fetch(`${API}/agregar/${id}`, { method: "POST" });
            alert("Libro agregado al carrito");
            listarCarrito();
        } catch (err) {
            console.error("Error al agregar al carrito", err);
        }
    }

    async function eliminarLibro(id) {
        try {
            await fetch(`${API}/eliminar/${id}`, { method: "DELETE" });
            alert("Libro eliminado del carrito");
            listarCarrito();
        } catch (err) {
            console.error("Error al eliminar del carrito", err);
        }
    }

    async function vaciarCarrito() {
        if (confirm("¿Estás seguro de vaciar el carrito?")) {
            await fetch(`${API}/vaciar`, { method: "DELETE" });
            alert("Carrito vaciado");
            listarCarrito();
        }
        //////SEGUIR MODIFICANDO
    }
    // Función para confirmar la compra
    // Se asume que el precio total se obtiene de la API o se calcula en el frontend
    async function confirmarCompra() {
        const total = document.getElementById("totalPrecio").textContent;
        if (parseInt(total) === 0) {
            alert("El carrito está vacío.");
            return;
        }

        if (confirm(`¿Deseas confirmar tu compra por $${total}?`)) {
            await fetch(`${API}/vaciar`, { method: "DELETE" });
            alert("¡Gracias por tu compra/reserva!");
            listarCarrito();
        }
    }

    return { listarCarrito, agregarLibro, eliminarLibro, vaciarCarrito, confirmarCompra };
})();

// Cargar carrito al iniciar
document.addEventListener("DOMContentLoaded", () => {
    app.listarLibros();        // del módulo anterior
    carrito.listarCarrito();   // nuevo módulo
});
