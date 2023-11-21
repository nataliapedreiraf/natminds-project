// App.js
import React, { useState } from 'react';
import Sidebar from './Sidebar';
import RightSidebar from './RightSidebar';
import Feed from './Feed';
import Login from './login';
import './App.css';
import PostForm from './PostForm';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  const handleLogin = () => {
    // Usuario autenticado, actualiza el estado para mostrar el Feed
    setLoggedIn(true);
  };

  return (
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
  );
}

export default App;