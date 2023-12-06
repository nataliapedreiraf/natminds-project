import React, { useState, useEffect } from 'react';
import Sidebar from './Sidebar';
import RightSidebar from './RightSidebar';
import Feed from './Feed';
import Login from './login';
import './App.css';
import PostForm from './PostForm';

function App() {
  // Estados para controlar el estado de inicio de sesión y los datos del usuario.
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [userData, setUserData] = useState(null); 

  // Función para manejar el inicio de sesión exitoso.
  const handleLogin = (userData) => {
  
    setLoggedIn(true);
    setUserData(userData);
  };

  // Función para manejar el cierre de sesión.
  const handleLogout = () => {
    setLoggedIn(false);
    setUserData(null);
  };

  return (
    <div className="app">
      {!isLoggedIn ? (
        <Login onLogin={handleLogin} />
      ) : (
        <>
          <Sidebar userData={userData} onLogout={handleLogout} />
          <Feed />
          <RightSidebar />
        </>
      )}
    </div>
  );
}

export default App;