function login() {
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value.trim();

  if (email === '' || password === '') {
    alert('Debe completar todos los campos.');
    return;
  }

  const apiUrl = 'http://192.168.119.154:8080/api/v1/usuario';

  // Datos para enviar al backend
  const datosLogin = {
    email: email,
    password: password
  };

  fetch(apiUrl + '/login', { 
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datosLogin)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Credenciales incorrectas o error en el servidor');
    }
    return response.json();
  })
  .then(data => {
    // Asumiendo que la respuesta contiene el email o nombre de usuario
    const nombreUsuario = email.split('@')[0]; // O data.nombreUsuario si viene en la respuesta

    localStorage.setItem('usuarioActivo', nombreUsuario);

    // Redireccionar con el nombre al index.html
    document.getElementById('nombre').value = nombreUsuario;
    document.getElementById('redirectForm').submit();
  })
  .catch(error => {
    console.error('Error:', error);
    alert('Error al iniciar sesión: ' + error.message);
  });
}
