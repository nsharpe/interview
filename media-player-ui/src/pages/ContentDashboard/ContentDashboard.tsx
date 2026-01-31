import React from 'react';
// import { getAllUsers } from './getAllUsers';
// import UserTable, {UserItem} from "../../components/Users/UserTable";
//
// export const ContentDashboard = () => {
//     const { users, loading } = getAllUsers();
//
//     const formattedUsers: UserItem[] = users.map((user: UserItem) => ({
//         id: user.id,
//         firstName: user.firstName,
//         email: user.email
//     }));
//
//     if (loading) return <div>Loading users...</div>;
//
//     return (
//         <div className="p-6">
//             <h1 className="text-2xl font-bold mb-4">Admin Users Content List</h1>
//             <UserTable
//                 items={formattedUsers}
//                 isLoading={loading}
//                 onDelete={(id) => console.log('Delete user:', id)}
//             />
//         </div>
//     );
// };