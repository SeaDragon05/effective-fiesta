import React, {useState, useEffect} from 'react'
import { useNavigate } from "react-router-dom"


const UserView = () => {
    const navigate = useNavigate();
    const [columns, setColumns] = useState([]);
    const [records, setRecords] = useState([]);


    useEffect(() => {
        if (!window.sessionStorage.getItem("auth")) navigate('/unauthorized')
        fetch(process.env.REACT_APP_API_URL_BASE + '/users/')
        .then(res => {
            if (!res.ok) throw new Error('Failed to fetch users');
            return res.json();
        })
        .then(data => {
            if (data && Array.isArray(data) && data.length > 0) {
                setColumns(Object.keys(data[0]))
                setRecords(data)
            }
        })
        .catch(error => {
            console.error('Error fetching users:', error);
            navigate('/unauthorized');
        });
    }, [navigate]);

  return (
    <div>
        <h2>Users</h2>
        <table className='user-table'>
            <thead>
                <tr>
                    {columns.map((c, i) => (
                        <th key={i}>{c.replaceAll("_", " ").toUpperCase()}</th>
                    ))}
                </tr>
            </thead>
            <tbody>
                {
                    records.map((record,i) => (
                        <tr key={i}>
                            <td>{record.web_user_id}</td>
                            <td>{record.first_name}</td>
                            <td>{record.last_name}</td>
                            <td>{record.email}</td>
                            <td>{new Date(record.created_date).toLocaleString()}</td>
                            <td>{String(record.is_active)}</td>
                            <td>{new Date(record.last_login).toLocaleString()}</td>
                        </tr>
                    ))
                }
            </tbody>
        </table>

    </div>
  )
}

export default UserView
