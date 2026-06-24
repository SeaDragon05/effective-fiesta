import React, {useState, useEffect} from 'react'
import { useNavigate } from "react-router-dom"
import "bootstrap/dist/css/bootstrap.min.css";
import { MDBContainer, MDBRow, MDBCol } from "mdb-react-ui-kit";


const UserProfile = () => {
    const navigate = useNavigate();
    const [userColumns, setUserColumns] = useState([]);
    const [record, setRecord] = useState([]);
    const [addressColumns, setAddressColumns] = useState([]);
    const [addresses, setAddresses] = useState([]);
    const [phoneColumns, setPhoneColumns] = useState([]);
    const [phones, setPhones] = useState([]);
    const [infoColumns, setInfoColumns] = useState([]);
    const [info, setInfo] = useState([]);
    const [picLink, setPicLink] = useState();
    const [infoBio, setInfoBio] = useState();
    const user_id = window.sessionStorage.getItem("user_id")


    useEffect(() => {
        console.log("[UserProfile] useEffect triggered. Checking auth sessionStorage...");
        const isAuth = window.sessionStorage.getItem("auth");
        console.log("[UserProfile] auth in sessionStorage:", isAuth);
        console.log("[UserProfile] user_id in sessionStorage:", user_id);

        if (!isAuth) {
            console.warn("[UserProfile] No auth found in sessionStorage, navigating to /unauthorized...");
            navigate('/unauthorized');
            return;
        }

        const url = process.env.REACT_APP_API_URL_BASE + '/users/user/' + user_id;
        console.log("[UserProfile] Fetching profile data from URL:", url);

        fetch(url)
        .then(res => {
            console.log("[UserProfile] Fetch returned. Status:", res.status, "Status Text:", res.statusText);
            if (!res.ok) {
                console.error("[UserProfile] Response was not OK (non-2xx). Throwing Error.");
                throw new Error('Failed to fetch user data');
            }
            return res.json();
        })
        .then(data => {
            console.log("[UserProfile] Parsed user profile data successfully:", data);
            if (data && data.user) {
                console.log("[UserProfile] Setting user record:", data.user);
                setUserColumns(Object.keys(data.user))
                setRecord(data.user)
            } else {
                console.warn("[UserProfile] data.user is missing from response!");
            }
            if (data && data.info && data.info.length > 0) {
                console.log("[UserProfile] Setting bio and profile picture from info:", data.info);
                setInfoColumns(Object.keys(data.info[0]))
                setInfo(data.info)
                setInfoBio(data.info[0].profile_bio)
                setPicLink(data.info[0].profile_picture)
            } else {
                console.warn("[UserProfile] data.info is empty or missing from response!");
            }
            if (data && data.addresses && data.addresses.length > 0) {
                console.log("[UserProfile] Setting addresses:", data.addresses);
                setAddressColumns(Object.keys(data.addresses[0]))
                setAddresses(data.addresses)
            } else {
                console.warn("[UserProfile] data.addresses is empty or missing from response!");
            }
            if (data && data.phones && data.phones.length > 0) {
                console.log("[UserProfile] Setting phones:", data.phones);
                setPhoneColumns(Object.keys(data.phones[0]))
                setPhones(data.phones)
            } else {
                console.warn("[UserProfile] data.phones is empty or missing from response!");
            }
        })
        .catch(error => {
            console.error('[UserProfile] Caught an exception in useEffect profile fetch:', error);
            console.error('[UserProfile] Exception message:', error.message, 'Stack:', error.stack);
            console.log("[UserProfile] Navigating to /unauthorized due to fetch error...");
            navigate('/unauthorized');
        });
    }, [navigate, user_id]);

    const handleLogout = (e) => {
        e.preventDefault();
        window.sessionStorage.removeItem("auth")
        window.sessionStorage.removeItem("user_id")
        window.sessionStorage.removeItem("token")
        navigate('/login')
    }

  return (
    <div>
        <br />
        <div className="page shadow">
            <div className="main-container shadow">
                <MDBContainer>
                <br />
                    <MDBRow>
                        <MDBCol>
                            <div className="container">
                            <img
                                src={picLink}
                                alt={record.first_name}
                                style={{ width: "20%", borderRadius: "48%" }}
                            />
                            </div>
                        </MDBCol>
                    </MDBRow>
                    <MDBRow>
                        <MDBCol>
                            <div class="container">
                                <h3>{record.first_name} {record.last_name}</h3>

                                <p>{infoBio}</p>
                            </div>

                        </MDBCol>
                    </MDBRow>
                    <MDBRow>
                        <table className='user-table'>
                            <thead>
                                <tr>
                                    <th key="1">USER ID</th>
                                    <th key="2">EMAIL</th>
                                    <th key="3">USER SINCE</th>
                                    <th key="4">LAST LOGIN</th>

                                </tr>
                            </thead>
                            <tbody>
                                {
                                    <tr key={record.web_user_id}>
                                        <td>{record.web_user_id}</td>
                                        <td>{record.email}</td>
                                        <td>{new Date(record.created_date).toLocaleDateString()}</td>
                                        <td>{new Date(record.last_login).toLocaleString()}</td>
                                    </tr>
                                }
                            </tbody>
                        </table>
                    </MDBRow>
                    <br />
                </MDBContainer>
            </div>
        </div>

         <br />
        <h2>Address</h2>
        <table className='user-table'>
            <thead>
                <tr>
                    {
                        addressColumns.map((c, i) => (<th key={i}>{c.replaceAll("_", " ").toUpperCase()}</th>))
                    }
                </tr>
            </thead>
            <tbody>
                {
                    addresses.map((address,i) => (
                    <tr key={address.user_address_id}>
                        <td>{address.user_address_id}</td>
                        <td>{address.street_1}</td>
                        <td>{address.street_2}</td>
                        <td>{address.city}</td>
                        <td>{address.st}</td>
                        <td>{address.zip}</td>
                        <td>{address.country}</td>
                        <td>{new Date(address.created_date).toLocaleDateString()}</td>
                        <td>{address.web_user.web_user_id}</td>
                        <td>{address.address_type.address_type}</td>
                    </tr>
                    ))
                }
            </tbody>
        </table>

        <h2>Phone</h2>
        <table className='user-table'>
            <thead>
                <tr>
                    {
                        phoneColumns.map((c, i) => (<th key={i}>{c.replaceAll("_", " ").toUpperCase()}</th>))
                    }
                </tr>
            </thead>
            <tbody>
                {
                    phones.map((phone,i) => (
                    <tr key={phone.user_phone_id}>
                        <td>{phone.phone_number}</td>
                        <td>{phone.phone_type.phone_type}</td>
                        <td>{String(phone.is_active)}</td>
                        <td>{new Date(phone.created_date).toLocaleDateString()}</td>
                    </tr>
                    ))
                }
            </tbody>
        </table>
        <button className="login-button" onClick={handleLogout}>Logout</button>
    </div>
  )
}

export default UserProfile