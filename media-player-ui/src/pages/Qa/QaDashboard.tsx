
import React, { useState, ChangeEvent } from 'react';

import {userGeneratorControllerApi} from '../../api/qa-rest-client-factory'
import axios from "axios";

const getCurrentToken = () => {
    // Access the common headers object
    const authHeader = axios.defaults.headers.common['Authorization'];

    if (authHeader && typeof authHeader === 'string') {
        // Removes 'Bearer ' and returns just the JWT string
        return authHeader.replace('Bearer ', '');
    }
    return null;
};

const QaDashboard = ()=> {

    const [count, setCount] = useState<number>(1);
    const [result, setResult] = useState<any>(null);

    const handleNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(e.target.value, 10);
        setCount(isNaN(value) ? 0 : value);
    };

    const handleGenerate = async () => {
        try {
            const response = await userGeneratorControllerApi(getCurrentToken()).generate(count);
            setResult(response.data);
        } catch (error) {
            setResult("Error: Check console or token");
        }
    };

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <h3>Generate Users</h3>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '15px', maxWidth: '300px' }}>
                <label>
                    Number of users to generate:
                    <input
                        type="number"
                        value={count}
                        onChange={handleNumberChange}
                        min="1"
                        max="100"
                        style={{
                            display: 'block',
                            marginTop: '5px',
                            padding: '8px',
                            width: '100%'
                        }}
                    />
                </label>

                <button
                    onClick={handleGenerate}
                    style={{
                        padding: '10px',
                        backgroundColor: '#28a745',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer'
                    }}
                >
                    Generate {count} User{count !== 1 ? 's' : ''}
                </button>
            </div>

            {result && (
                <div style={{ marginTop: '20px' }}>
                    <strong>Response:</strong>
                    <pre style={{ background: '#f4f4f4', padding: '10px' }}>
                        {JSON.stringify(result, null, 2)}
                    </pre>
                </div>
            )}
        </div>
    );
}

export default QaDashboard;