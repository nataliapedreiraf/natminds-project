import React, { useState, useEffect } from 'react';
import Sidebar from './Sidebar';
import RightSidebar from './RightSidebar';
import Feed from './Feed';
import Login from './login';
import './App.css';
import PostForm from './PostForm';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [userData, setUserData] = useState(null); // Nuevo estado para almacenar la información del usuario

  const handleLogin = (userData) => {
    // Usuario autenticado, actualiza el estado para mostrar el Feed y la información del usuario
    setLoggedIn(true);
    setUserData(userData);
  };

  return (
    <div className="app">
      {!isLoggedIn ? (
        <Login onLogin={handleLogin} />
      ) : (
        <>
          <Sidebar userData={userData} />
          <Feed />
          <RightSidebar />
        </>
      )}
    </div>
  );
}

export default App;