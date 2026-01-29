import React from 'react';

// Define the shape of your data based on your generated SDK types
export interface UserItem {
    id: string;
    firstName: string;
    email: string;
    createdAt?: string;
}

export interface UserTableProps {
    items: UserItem[];
    isLoading: boolean;
    onDelete?: (id: string) => void;
}

const UserTable: React.FC<UserTableProps> = ({ items, isLoading, onDelete }) => {
    if (isLoading) {
        return <div className="p-8 text-center text-gray-500">Loading Users...</div>;
    }

    if (items.length === 0) {
        return <div className="p-8 text-center text-gray-400">No users found.</div>;
    }

    return (
        <div className="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
            <table className="min-w-full divide-y divide-gray-200 bg-white text-sm">
                <thead className="bg-gray-50">
                <tr>
                    <th className="px-4 py-3 text-left font-semibold text-gray-900">ID</th>
                    <th className="px-4 py-3 text-left font-semibold text-gray-900">Title</th>
                    <th className="px-4 py-3 text-left font-semibold text-gray-900">Status</th>
                    <th className="px-4 py-3 text-right font-semibold text-gray-900">Actions</th>
                </tr>
                </thead>

                <tbody className="divide-y divide-gray-200">
                {items.map((item) => (
                    <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                        <td className="whitespace-nowrap px-4 py-3 font-mono text-xs text-gray-500">
                            {item.id}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3 text-gray-700 font-medium">
                            {item.firstName}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3 text-right">
                            <button
                                onClick={() => onDelete?.(item.id)}
                                className="text-red-600 hover:text-red-900 font-medium text-xs"
                            >
                                Delete
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