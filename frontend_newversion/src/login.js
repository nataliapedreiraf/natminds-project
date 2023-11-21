import React, { useState } from 'react';
import './login.css';

const Login = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

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
      });

      if (response.ok) {
        const user = await response.json();
        onLogin(user);
      } else {
        // Muestra un aviso en el navegador cuando las credenciales son incorrectas
        window.alert('Email o contraseña incorrectos');
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

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

export default Login;