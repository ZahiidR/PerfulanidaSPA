function registrar() {
  const nombre = document.getElementById('nombre').value.trim();
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value.trim();

  if (nombre === '' || email === '' || password === '') {
    alert('Debe completar todos los campos.');
    return;
  }

  // Guardar usuario (simulado)
  const nuevoUsuario = {
    nombre: nombre,
    email: email,
    password: password
  };

  // Guardar en localStorage como ejemplo (solo uno)
  localStorage.setItem('usuarioRegistrado', JSON.stringify(nuevoUsuario));

  alert('Registro exitoso. Ahora puede iniciar sesión.');
  window.location.href = 'login.html';
}
