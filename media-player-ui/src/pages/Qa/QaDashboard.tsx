import React, { useState, ChangeEvent } from 'react';
import GeneratorUsers from "./GeneratorUsers";
import GeneratorMovies from "./GeneratorMovies";

const QaDashboard = ()=> {

    const [qaPage, setQaPage] = useState('users');

    const renderPage = () => {
        switch (qaPage) {
            case 'users':
                return <GeneratorUsers />;
            case 'movies':
                return <GeneratorMovies />;
        }
    }

    return (
        <div>
            <nav>
                <button onClick={() => setQaPage('users')}>Users</button>
                <button onClick={() => setQaPage('movies')}>Movies</button>
            </nav>
            {renderPage()}
        </div>
    );
}

export default QaDashboard;