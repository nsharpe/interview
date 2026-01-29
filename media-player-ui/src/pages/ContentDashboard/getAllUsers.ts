import { useState, useEffect } from 'react';
import { adminClient} from "../../api/admin-client";
import {userControllerApi} from "../../api/public-rest-client";
import {UserItem} from "../../components/Users/UserTable";

export function getAllUsers() {
    const [users, setUsers] = useState<UserItem[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadAllUserData() {
            try {
                const idResponse = await adminClient.getUsers();
                const { data: ids } = await adminClient.getUsers() as { data: string[] };

                const detailPromises = ids.map(id =>
                    userControllerApi.getUser(id).then(res => res.data)
                );

                const fullUserDetails = await Promise.all(detailPromises);

                const formattedUsers: UserItem[] = fullUserDetails.map(u => ({
                    id: u.id ?? '',
                    firstName: u.firstName ?? '',
                    email: u.email ?? ''
                }));

                setUsers(formattedUsers);
            } catch (err) {
                console.error("Failed to load users", err);
            } finally {
                setLoading(false);
            }
        }
    }, []);

    return { users, loading };
}