import React, { useState } from 'react';
import './PostForm.css'; // Asegúrate de importar el archivo CSS correspondiente

const PostForm = ({ onPostSubmit }) => {
  const [text, setText] = useState('');

  const handleTextChange = (e) => {
    setText(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Botón clicado');
  
    // Realizar la solicitud POST al backend
    try {
      const response = await fetch('http://localhost:8080/posts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          text,
          userId: 1, // Reemplaza esto con el ID del usuario actual
        }),
      });
  
      // Resto del código...
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };
  return (
    <div className="post-section">
      <img
        src="https://placekitten.com/40/40"
        alt="Profile"
        className="post-section__profileImage"
      />
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="¿En qué estás pensando?"
          className="post-section__input"
          value={text}
          onChange={handleTextChange}
        />
        <button type="submit" className="post-section__button">
          Post
        </button>
      </form>
    </div>
  );
};

export default PostForm;