import React, { useState, useEffect } from 'react';
import {userAdminClient} from "../../api/user-admin-client";
import axios from "axios";

export interface UserItem {
    id: string;
    firstName: string;
    email: string;
    createdAt?: string;
}


const UserTable: React.FC = () => {
    const usersPerPage = 40;

    const [token, setToken] = useState<string>('');
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

    if (loading) return <h2>Loading...</h2>;

    return (
        <div className="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
            <table className="min-w-full divide-y divide-gray-200 bg-white text-sm">
                <thead className="bg-gray-50">
                <tr>
                    <th className="px-4 py-3 text-left font-semibold text-gray-900">ID</th>
                    <th className="px-4 py-3 text-left font-semibold text-gray-900">Name</th>
                    <th className="px-4 py-3 text-left font-semibold text-gray-900">Email</th>
                    <th className="px-4 py-3 text-right font-semibold text-gray-900">LogIn As</th>
                </tr>
                </thead>

                <tbody className="divide-y divide-gray-200">
                {users.map((item) => (
                    <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                        <td className="whitespace-nowrap px-4 py-3 font-mono text-xs text-gray-500">
                            {item.id}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3 text-gray-700 font-medium">
                            {item.firstName}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3 text-gray-700 font-medium">
                            {item.email}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3 text-right">
                            <button
                                onClick={() => {
                                    logInAs(item.id)
                                }}
                                className="text-red-600 hover:text-red-900 font-medium text-xs"
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