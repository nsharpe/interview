import React from "react";
import {Link} from 'react-router-dom';

const PageHeader: React.FC = () => {

    return (
        <table>
            <thead>
            <tr>
                <th><Link to="/">Home</Link></th>
                <th><Link to="/qa-dashboard">Qa</Link></th>
                <th><Link to="/series">Series</Link></th>
                <th><Link to="/admin/user">Users</Link></th>
            </tr>
            </thead>
        </table>
    );
};

export default PageHeader;