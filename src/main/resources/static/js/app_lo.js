function login() {
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value.trim();

  if (email === '' || password === '') {
    alert('Debe completar todos los campos.');
    return;
  }

  const datosLogin = {
    email: email,
    password: password
  };

  // URL del endpoint de login (ajusta si es diferente)
  const urlLogin = 'http://10.155.64.162:8080/api/v1/usuario/login';

  fetch(urlLogin, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datosLogin)
  })
  .then(response => {
    if (!response.ok) {
      return response.json().then(err => { 
        throw new Error(err.message || 'Error en el login'); 
      });
    }
    return response.json();
  })
  .then(data => {
    // Suponiendo que la respuesta contiene datos del usuario autenticado
    console.log('Login exitoso:', data);

    // Extraer el nombre de usuario desde el email (antes del @)
    const nombreUsuario = email.split('@')[0];

    // Guardar el nombre de usuario (o token, según tu API) en localStorage
    localStorage.setItem('usuarioActivo', nombreUsuario);

    // Aquí puedes usar datos devueltos por el servidor si quieres

    // Si tienes un formulario para redirigir, puedes hacerlo así:
    document.getElementById('nombre').value = nombreUsuario;
    document.getElementById('redirectForm').submit();

    // O simplemente redirigir con window.location.href si no usas formulario
    // window.location.href = 'index.html';
  })
  .catch(error => {
    console.error('Error durante el login:', error);
    alert('Error al iniciar sesión: ' + error.message);
  });
}
