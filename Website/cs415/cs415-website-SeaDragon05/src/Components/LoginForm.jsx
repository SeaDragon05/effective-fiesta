import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { FidgetSpinner } from 'react-loader-spinner';


const LoginForm = (props) => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [pass, setPass] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
      e.preventDefault();
      console.log("[LoginForm] Form submitted. Starting login process...");
      console.log("[LoginForm] Input Email:", email);
      setError(null)
      setLoading(true)
      const url = process.env.REACT_APP_API_URL_BASE + '/login/';
      console.log("[LoginForm] Target API URL:", url);

      const payload = JSON.stringify({
          email: email,
          password: pass
      })
      console.log("[LoginForm] Request Payload:", payload);

      const controller = new AbortController();
      const timeoutId = setTimeout(() => {
          console.warn("[LoginForm] 30 seconds reached! Aborting request...");
          controller.abort();
      }, 30000);

      try {
          console.log("[LoginForm] Sending fetch request...");
          const res = await fetch(url, {
              method: 'POST',
              body: payload,
              headers: {
                  'Content-Type': 'application/json'
              },
              signal: controller.signal
          });

          console.log("[LoginForm] Fetch returned. Status:", res.status, "Status Text:", res.statusText);
          clearTimeout(timeoutId);
          setLoading(false);

          console.log("[LoginForm] Parsing response JSON...");
          const data = await res.json();
          console.log("[LoginForm] Parsed Response Data:", data);

          if (!res.ok) {
              console.warn("[LoginForm] Response was not OK (non-2xx status code)");
              let errorText = "Error: " + res.status + ' - ';
              if (data && data.error) {
                  errorText += data.error;
              } else if (data && data.errors) {
                  console.log("[LoginForm] Serializer errors found:", data.errors);
                  for (const err in data.errors) {
                      for (const msg in data.errors[err]) {
                          errorText += data.errors[err][msg];
                      }
                  }
              }
              console.error("[LoginForm] Setting UI error:", errorText);
              setError(errorText);
          } else {
              console.log("[LoginForm] Response is OK (2xx status code)");
              if (data.success) {
                  console.log("[LoginForm] Login successful! Writing sessionStorage...");
                  window.sessionStorage.setItem("auth", true)
                  window.sessionStorage.setItem("user_id", data.user_id)
                  window.sessionStorage.setItem("token", data.token)
                  console.log("[LoginForm] sessionStorage values set - auth:", window.sessionStorage.getItem("auth"), "user_id:", window.sessionStorage.getItem("user_id"));

                  setEmail('')
                  setPass('')
                  setError('Logged In Successfully!')
                  console.log("[LoginForm] Navigating to /userprofile...");
                  navigate('/userprofile')
              } else {
                  console.warn("[LoginForm] data.success is false or falsy:", data);
                  setError(data.error || 'Invalid Login Credentials')
              }
          }
      } catch (err) {
          clearTimeout(timeoutId);
          setLoading(false)
          console.error("[LoginForm] Caught an exception in handleSubmit:", err);
          console.error("[LoginForm] Exception details - Name:", err.name, "Message:", err.message, "Stack:", err.stack);
          if (err.name === 'AbortError') {
              setError('Login request timed out after 30 seconds');
          } else {
              setError(err.message || 'Error Logging In - Check your connection and try again')
          }
      }
    }
    return (
    <div >
      <h2>Login</h2>
            <form className="login-form" onSubmit={handleSubmit}>
                <label className="login-label" htmlFor="email">Email</label>
                <input className="login-input" required value={email} onChange={(e) => setEmail(e.target.value)} type="email" placeholder="email@email.com" id="email" name="email"/>
                <label className="login-label" htmlFor="password">Password</label>
                <input className="login-input" required value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="*********" id="password" name="password"/>
                <button className="login-button" type="submit">Login</button>
                <p color="white">{error}</p>
            </form>
            <button className="link-btn" onClick={() => navigate('/register')}>Don't have an account? Register here.</button>
            <p>{loading ? <FidgetSpinner /> : ''}</p>
    </div>
  );
};

export default LoginForm;