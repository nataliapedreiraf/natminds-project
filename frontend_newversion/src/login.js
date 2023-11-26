import React, { useState, useEffect } from 'react';
import './login.css';
import { useAuth } from './AuthContext'; // Asegúrate de importar useAuth

const Login = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { loggedInUserId, login } = useAuth(); // Obtén el userId del contexto de autenticación

  useEffect(() => {
    // Este código se ejecutará después de cada renderizado cuando loggedInUserId cambie
    console.log('loggedInUserId after render: ', loggedInUserId);
  }, [loggedInUserId]); // Establece loggedInUserId como una dependencia del efecto

  const handleLogin = async (e) => {
    e.preventDefault();

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
        console.log('Usuario autenticado:', user);

        // Obtener el userId del usuario autenticado
        const userId = user.userId;
        console.log('userId:', userId);

        // Esperar a que login(userId) se complete antes de llamar a onLogin(userId)
        await login(userId);

        // Llamar a la función onLogin y pasar el userId
        onLogin(userId);
      } else {
        // Muestra un aviso en el navegador cuando las credenciales son incorrectas
        window.alert('Email o contraseña incorrectos');
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
      window.alert('Ocurrió un error al intentar iniciar sesión. Por favor, inténtalo nuevamente.');
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