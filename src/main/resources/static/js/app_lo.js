function login() {
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value.trim();

  if (email === '' || password === '') {
    alert('Debe completar todos los campos.');
    return;
  }

  // Extraer solo el nombre de usuario desde el correo (antes del @)
  const nombreUsuario = email.split('@')[0];

  // Guardar en localStorage
  localStorage.setItem('usuarioActivo', nombreUsuario);

  // Redireccionar con el nombre al index.html
  document.getElementById('nombre').value = nombreUsuario;
  document.getElementById('redirectForm').submit();
}
