
;import React,  { useState, useEffect } from 'react';
import './App.css';
import { useAuth } from './AuthContext';
import Login from './Login';
import Register from './Register';

function App() {
    const [movies, setMovies] = useState([]);
    const [newMovie, setNewMovie] = useState({
        title: '',
        director: '',
        releaseYear: '',
        rating: '',
        genre: ''
    });
    const [showLogin, setShowLogin] = useState(true);
    const { user, token, logout, isAuthenticated } = useAuth();

    useEffect(() => {
        fetchMovies();
    }, []);

    const fetchMovies = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/movies');
            const data = await response.json();
            setMovies(data);
        } catch (error) {
            console.error('Error fetching movies:', error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewMovie({
            ...newMovie,
            [name]: value
        });
    };

    const addMovie = async (e) => {
        e.preventDefault();

        if (!isAuthenticated) {
            alert('Please login to add movies');
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/movies', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(newMovie)
            });

            if (response.ok) {
                fetchMovies();
                setNewMovie({
                    title: '',
                    director: '',
                    releaseYear: '',
                    rating: '',
                    genre: ''
                });
            } else {
                alert('Failed to add movie. Please try again.');
            }
        } catch (error) {
            console.error('Error adding movie:', error);
            alert('Error adding movie');
        }
    };

    const deleteMovie = async (id) => {
        if (!isAuthenticated) {
            alert('Please login to delete movies');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/movies/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                fetchMovies();
            } else {
                alert('Failed to delete movie');
            }
        } catch (error) {
            console.error('Error deleting movie:', error);
        }
    };

    if (!isAuthenticated) {
        return showLogin ? (
            <Login onSwitchToRegister={() => setShowLogin(false)} />
        ) : (
            <Register onSwitchToLogin={() => setShowLogin(true)} />
        );
    }

    return (
        <div className="App">
            <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '20px', backgroundColor: '#282c34', color: 'white' }}>
                <h1>Movie Management System</h1>
                <div>
                    <span style={{ marginRight: '15px' }}>Welcome, {user?.username}!</span>
                    <button onClick={logout} style={{ padding: '8px 15px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                        Logout
                    </button>
                </div>
            </header>

            <div style={{ padding: '20px' }}>
                <h2>Add New Movie</h2>
                <form onSubmit={addMovie} style={{ marginBottom: '30px', maxWidth: '600px' }}>
                    <input
                        type="text"
                        name="title"
                        placeholder="Title"
                        value={newMovie.title}
                        onChange={handleInputChange}
                        required
                        style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
                    />
                    <input
                        type="text"
                        name="director"
                        placeholder="Director"
                        value={newMovie.director}
                        onChange={handleInputChange}
                        required
                        style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
                    />
                    <input
                        type="number"
                        name="releaseYear"
                        placeholder="Release Year"
                        value={newMovie.releaseYear}
                        onChange={handleInputChange}
                        required
                        style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
                    />
                    <input
                        type="number"
                        step="0.1"
                        name="rating"
                        placeholder="Rating"
                        value={newMovie.rating}
                        onChange={handleInputChange}
                        required
                        style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
                    />
                    <input
                        type="text"
                        name="genre"
                        placeholder="Genre"
                        value={newMovie.genre}
                        onChange={handleInputChange}
                        required
                        style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
                    />
                    <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                        Add Movie
                    </button>
                </form>

                <h2>Movie List</h2>
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
                    {movies.map((movie) => (
                        <div key={movie.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px' }}>
                            <h3>{movie.title}</h3>
                            <p><strong>Director:</strong> {movie.director}</p>
                            <p><strong>Year:</strong> {movie.releaseYear}</p>
                            <p><strong>Rating:</strong> {movie.rating}</p>
                            <p><strong>Genre:</strong> {movie.genre}</p>
                            <button
                                onClick={() => deleteMovie(movie.id)}
                                style={{ padding: '8px 15px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                            >
                                Delete
                            </button>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default App;