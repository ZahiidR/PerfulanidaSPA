function registrar() {
  const nombre = document.getElementById('nombre').value.trim();
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value.trim();

  if (nombre === '' || email === '' || password === '') {
    alert('Debe completar todos los campos.');
    return;
  }

  const nuevoUsuario = {
    nombre: nombre,
    email: email,
    password: password
  };

  // URL del endpoint de registro
  const urlRegistro = 'http://192.168.119.154:8080/api/v1/usuario';

  // Realizar la petición POST
  fetch(urlRegistro, {
    method: 'POST', // Método HTTP para crear un recurso
    headers: {
      'Content-Type': 'application/json' // Indica que el cuerpo de la petición es JSON
    },
    body: JSON.stringify(nuevoUsuario) // Convierte el objeto JavaScript a una cadena JSON
  })
  .then(response => {
    // Verifica si la respuesta es exitosa (código 2xx)
    if (!response.ok) {
      // Si la respuesta no es exitosa, lanza un error para ser capturado por .catch()
      // Puedes leer el cuerpo del error si el servidor envía detalles
      return response.json().then(err => { throw new Error(err.message || 'Error en el registro'); });
    }
    // Si la respuesta es exitosa, parsea el JSON de la respuesta
    return response.json();
  })
  .then(data => {
    // Aquí puedes manejar la respuesta exitosa del servidor
    // Por ejemplo, si el servidor devuelve el usuario registrado o un mensaje de éxito
    console.log('Registro exitoso:', data);
    alert('Registro exitoso. Ahora puede iniciar sesión.');
    window.location.href = 'login.html'; // Redirige al usuario
  })
  .catch(error => {
    // Captura cualquier error que ocurra durante la petición o el procesamiento
    console.error('Error durante el registro:', error);
    alert('Error al intentar registrarse: ' + error.message);
  });
}
