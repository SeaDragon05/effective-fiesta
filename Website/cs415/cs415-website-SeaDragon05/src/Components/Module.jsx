import React, {useState, useEffect} from 'react'
import { useNavigate, useParams } from "react-router-dom"

const Module = ({id}) => {
  const navigate = useNavigate();
  const routeParams = useParams();
  const [pageName, setPageName] = useState('')
  const [pageTitle, setPageTitle] = useState('')
  const [pageDescription, setPageDescription] = useState('')
  const [picLink, setPicLink] = useState('');

  useEffect(() => {
    if (!window.sessionStorage.getItem("auth")) navigate('/unauthorized')
    fetch(process.env.REACT_APP_API_URL_BASE + '/pages/page/' + routeParams.id)
    .then(res => {
      if (!res.ok) throw new Error('Failed to fetch module data');
      return res.json();
    })
    .then(data => {
        if (data) {
            setPageName(data.page_name || '')
            setPageTitle(data.page_title || '')
            setPageDescription(data.page_description || '')
            setPicLink(data.page_picture || '')
        }
    })
    .catch(error => {
      console.error('Error fetching module data:', error);
      navigate('/unauthorized');
    });
  }, [routeParams.id, navigate]);

  return (
    <div>

      <h1>CS415 Private Page - Module {routeParams.id}</h1>
      <p><font color="white">This is only accessible when logged in</font></p>

        <h2>{pageName}</h2>
        <p><b>{pageTitle}</b></p>
        <p><font color='white'>{pageDescription}</font></p>
        <p><img src={picLink} alt="Module Pic"></img></p>
    </div>

  )
}

export default Module