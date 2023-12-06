import React, { useState } from 'react';
import './PostForm.css';
import perfilImage1 from './perfil1.png';

// Componente funcional PostForm que representa el formulario para crear nuevos posts
const PostForm = ({ onPostSubmit }) => {
  // Estado para almacenar el texto del nuevo post
  const [text, setText] = useState('');

  // Función para manejar cambios en el texto del post
  const handleTextChange = (e) => {
    setText(e.target.value);
  };

  // Función para manejar el envío del formulario de nuevo post
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Botón clicado');

    // Realizar la solicitud POST al backend
    try {
      const response = await fetch('http://localhost:8080/posts', {
        method: 'POST',
        credentials: "include",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          text,
          userId: 1,
        }),
      });

      // Verificar si la respuesta del servidor es exitosa
      if (response.ok) {
        // Limpiar el contenido del cuadro de texto después de enviar el post
        setText('');
      } else {
        console.error('Error al realizar la solicitud:', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  // Renderiza el formulario de nuevo post
  return (
    <div className="post-form">
      <img
        src={perfilImage1}
        alt="Profile"
        className="post-section__profileImage"
      />
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="¿En qué estás pensando?"
          className="post-input"
          value={text}
          onChange={handleTextChange}
        />
        <button type="submit" className="post-button">
          Post
        </button>
      </form>
    </div>
  );
};

// Exporta el componente PostForm para su uso en otros lugares
export default PostForm;