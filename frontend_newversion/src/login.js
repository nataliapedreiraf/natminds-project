import React, { useState } from 'react';
import './login.css';

// Componente funcional Login que maneja el formulario de inicio de sesión
const Login = ({ onLogin }) => {
  // Estados para almacenar el correo electrónico y la contraseña ingresados por el usuario
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // Función para manejar el envío del formulario de inicio de sesión
  const handleLogin = async (e) => {
    e.preventDefault();

    // Realizar la solicitud al backend para verificar el usuario y contraseña
    try {
      const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          password,
        }),
        mode: 'cors',
        credentials: 'include'
      });

      // Verificar si la respuesta del servidor es exitosa
      if (response.ok) {
        // Obtener la información del usuario desde la respuesta
        const user = await response.json();
        // Llamar a la función proporcionada en las propiedades para realizar acciones después del inicio de sesión
        onLogin(user);
      } else {
        // Muestra un aviso en el navegador cuando las credenciales son incorrectas
        window.alert('Email o contraseña incorrectos');
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  // Renderiza el formulario de inicio de sesión
  return (
    <div className="login">
      <h2 className="app-name__text">NatMinds</h2>
      <form onSubmit={handleLogin}>
        <label>
          Email:
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </label>
        <label>
          Contraseña:
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </label>
        <button type="submit">Iniciar sesión</button>
      </form>
    </div>
  );
};

// Exporta el componente Login para su uso en otros lugares
export default Login;