// App.js
import React, { useState } from 'react';
import Sidebar from './Sidebar';
import RightSidebar from './RightSidebar';
import Feed from './Feed';
import Login from './login';
import { AuthProvider } from './AuthContext';  // Importa AuthProvider
import './App.css';
import PostForm from './PostForm';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  const handleLogin = () => {
    // Usuario autenticado, actualiza el estado para mostrar el Feed
    setLoggedIn(true);
  };

  return (
    <AuthProvider>  {/* Agrega el AuthProvider alrededor de tu aplicación */}
      <div className="app">
        {!isLoggedIn ? (
          <Login onLogin={handleLogin} />
        ) : (
          <>
            <Sidebar />
            <Feed />
            <RightSidebar />
          </>
        )}
      </div>
    </AuthProvider>
  );
}

export default App;