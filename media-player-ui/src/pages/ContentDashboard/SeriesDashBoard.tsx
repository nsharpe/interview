import React, { useState, useEffect } from 'react';
import {seriesControllerApi} from "../../api/media-management-client";
import './SeriesDashBoard.css';

export interface SeriesItem {
    id: string;
    title: string;
}


const SeriesDashBoard: React.FC = () => {
    const usersPerPage = 40;

    const [token, setToken] = useState<string>('');
    const [series, setSeries] = useState<SeriesItem[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchPosts = async () => {
            setLoading(true);

            const data = await seriesControllerApi()
                .getAll(currentPage,usersPerPage)

            if(data.data==null){
                setError("No Data Returned")
                return;
            }
            if(data.data.content==null){
                setError("No Data Returned")
                return;
            }

            const userItems = data.data.content.map((x: any): SeriesItem => ({
                id: x.id,
                title: x.title
            }));

            setSeries(userItems)
            setLoading(false)
        };

        fetchPosts();
    }, []);

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(series.length / usersPerPage); i++) {
        pageNumbers.push(i);
    }

    if (loading) return <h2>Loading...</h2>;

    return (
        <div className="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
            <table>
                <thead className="bg-gray-50">
                <tr>
                    <th className="cell-header">ID</th>
                    <th className="cell-header">Title</th>
                    <th className="cell-header">Play</th>
                </tr>
                </thead>

                <tbody className="divide-y divide-gray-200">
                {series.map((item) => (
                    <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                        <td>
                            {item.id}
                        </td>
                        <td>
                            {item.title}
                        </td>
                        <td>
                            <button
                                onClick={() => {
                                    //logInAs(item.id)
                                }}
                                className="text-red-600 hover:text-red-900 font-medium text-xs"
                            >
                                Play
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default SeriesDashBoard;