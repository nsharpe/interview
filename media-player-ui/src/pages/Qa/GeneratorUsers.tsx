import React, {ChangeEvent, useState} from "react";
import {userGeneratorControllerApi} from "../../api/qa-rest-client-factory";
import {userAdminClient} from "../../api/user-admin-client";
import { useNavigate } from 'react-router-dom';
import axios from "axios";


const GeneratorUsers = ()=> {

    const [count, setCount] = useState<number>(1);
    const [result, setResult] = useState<string[]>([]);
    const [error, setError] = useState<string | null>(null);

    const handleNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(e.target.value, 10);
        setCount(isNaN(value) ? 0 : value);
    };

    const handleGenerateUser = async () => {
        try {
            const response = await userGeneratorControllerApi().generate(count);
            setResult(response.data);
        } catch (error) {
            setResult([]);
            alert("Error: Check console or token");
        }
    };

    const handleLoginAsUser = async (userId: string) => {
        const request = await userAdminClient().loginAsUser(userId)
        if(request.data == null) {
            setError("no data on login as")
        }
        const newToken = request.data.token;
        if (!newToken) {
            setError("no token on login as");
            return;
        }
        axios.defaults.headers.common['Authorization'] = `Bearer ${request.data.token}`
        localStorage.setItem('token', newToken);
    };

    const navigate = useNavigate();

    const resetPage = () => {
        navigate('/series');
    };

    return (
        <div>
            <h3>Generate Users</h3>

            <div>
                <label>
                    Number of users to generate:
                    <input
                        type="number"
                        value={count}
                        onChange={handleNumberChange}
                        min="1"
                        max="100"
                        maxLength={6}
                    />
                </label>

                <button
                    onClick={handleGenerateUser}
                >
                    Generate User
                </button>
            </div>

            {result && (
                <div>
                    <h4>Generated Users:</h4>
                    <table style={{borderCollapse: 'collapse', width: '100%'}}>
                        <thead>
                            <tr style={{backgroundColor: '#f2f2f2'}}>
                                <th style={{border: '1px solid #ddd', padding: '8px'}}>User ID (UUID)</th>
                                <th style={{border: '1px solid #ddd', padding: '8px'}}>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {result.map((userId, index) => (
                                <tr key={index} style={{backgroundColor: index % 2 === 0 ? '#f9f9f9' : 'white'}}>
                                    <td style={{border: '1px solid #ddd', padding: '8px'}}>{userId}</td>
                                    <td style={{border: '1px solid #ddd', padding: '8px'}}>
                                        <button
                                            onClick={() => {
                                                handleLoginAsUser(userId)
                                                resetPage()
                                        }}
                                            style={{
                                                padding: '4px 8px',
                                                backgroundColor: '#007bff',
                                                color: 'white',
                                                border: 'none',
                                                borderRadius: '4px',
                                                cursor: 'pointer'
                                            }}
                                        >
                                            Log in as user
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default GeneratorUsers;