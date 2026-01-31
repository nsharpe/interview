import React, {createContext, useContext, useState, ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

interface AuthContextType {
    token: string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const TokenManager: React.FC = () => {
    const [activePage, setActivePage] = useState<'setup' | 'generate'>('setup');
    const [token, setToken] = useState<string>('');
    const navigate = useNavigate();

    const handleApplyToken = (): void => {
        if (token.trim()) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            localStorage.setItem('token', token);
        } else {
            delete axios.defaults.headers.common['Authorization'];
            localStorage.removeItem('token');
        }

        navigate('/qa-dashboard')
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement>): void => {
        setToken(e.target.value);
    };

    return (
        <div style={{ padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <h3>Manual Broker Token Setup</h3>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', maxWidth: '400px' }}>
                <input
                    type="text"
                    placeholder="Bearer Toeken"
                    value={token}
                    onChange={handleChange}
                    style={{ padding: '10px' }}
                />
                <button
                    onClick={
                    handleApplyToken
                }
                    style={{ padding: '10px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                >
                    Set Authorization Bearer Token
                </button>
            </div>
        </div>
    );
};

export const getToken = () => {
    return localStorage.getItem('token')
};

export default TokenManager;