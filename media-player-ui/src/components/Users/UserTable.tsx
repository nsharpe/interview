import React, { useState, useEffect } from 'react';
import {userAdminClient} from "../../api/user-admin-client";
import '../Table.css';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

export interface UserItem {
    id: string;
    firstName: string;
    email: string;
    createdAt?: string;
}

const UserTable: React.FC = () => {
    const usersPerPage = 40;

    const [users, setUsers] = useState<UserItem[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const logInAs = async (id: string) =>{
        const request = await userAdminClient().loginAsUser(id)
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

    useEffect(() => {
        const fetchPosts = async () => {
            setLoading(true);

            const data = await userAdminClient()
                .getUsers(currentPage,usersPerPage)

            if(data.data==null){
                setError("No Data Returned")
                return;
            }
            if(data.data.content==null){
                setError("No Data Returned")
                return;
            }

            const userItems = data.data.content.map((x: any): UserItem => ({
                id: x.id,
                firstName: x.firstName,
                email: x.email
            }));

            setUsers(userItems)
            setLoading(false)
        };

        fetchPosts();
    }, []);

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(users.length / usersPerPage); i++) {
        pageNumbers.push(i);
    }

    const navigate = useNavigate();

    const resetPage = () => {
        navigate('/series');
    };

    if (loading) return <h2>Loading...</h2>;

    return (
        <div>
            <table>
                <thead>
                <tr>
                    <th className="table-header">ID</th>
                    <th className="table-header">Name</th>
                    <th className="table-header">Email</th>
                    <th className="table-header">LogIn As</th>
                </tr>
                </thead>

                <tbody>
                {users.map((item) => (
                    <tr key={item.id}>
                        <td>{item.id}</td>
                        <td>{item.firstName}</td>
                        <td>{item.email}</td>
                        <td>
                            <button
                                onClick={() => {
                                    logInAs(item.id)
                                    resetPage()
                                }}
                            >
                                Log In As
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserTable;