import React, { useState, useEffect } from 'react';
import {seriesControllerApi} from "../../api/media-management-client";
import '../../components/Table.css';
import PageHeader from "../../components/util/PageHeader";
import { useNavigate } from 'react-router-dom';

export interface SeriesItem {
    id: string;
    title: string;
}


const SeriesDashBoard: React.FC = () => {
    const usersPerPage = 40;

    const [selectedSeries, setSelectedSeries] = useState<string|undefined>(undefined)
    const [series, setSeries] = useState<SeriesItem[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const naviagate = useNavigate()

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

    const goToMedia = (item: SeriesItem) => {
        var itemId = item.id;
        if(itemId == null){
            setError("No item id")
            return;
        }
        naviagate('/player/'+itemId);
    };

    return (
        <div className="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
            <table>
                <thead className="bg-gray-50">
                <tr>
                    <th className="table-header">ID</th>
                    <th className="table-header">Title</th>
                    <th className="table-header">Play</th>
                </tr>
                </thead>

                <tbody >
                {series.map((item) => (
                    <tr key={item.id} >
                        <td>
                            {item.id}
                        </td>
                        <td>
                            {item.title}
                        </td>
                        <td>
                            <button
                                onClick={(x) => {
                                    goToMedia(item)
                                }}
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