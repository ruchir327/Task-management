import React, { useState } from 'react';
import { loginAPICall, saveLoggedInUser, storeToken } from '../services/AuthService';
import { Link, useNavigate } from 'react-router-dom';
import { Button, Row, Col } from 'antd';
import { toast } from 'react-toastify';

const LoginComponent = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigator = useNavigate();

  async function handleLoginForm(e) {
    e.preventDefault();
    if (!username) {
      toast.error('Enter a valid username!', {
        position: toast.POSITION.TOP_CENTER,
        autoClose: 2000,
      });
      return;
    }
    if (!password) {
      toast.error('Enter a valid password!', {
        position: toast.POSITION.TOP_CENTER,
        autoClose: 2000,
      });
      return;
    }
    await loginAPICall(username, password)
      .then((response) => {
        console.log(response.data);
        const token = 'Bearer ' + response.data.accessToken;
        let role = response.data.role;
        storeToken(token);
        saveLoggedInUser(username,role);

        // Show success toast notification with a 2-second delay
        toast.success('Login successful!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 1000,
        });

        setTimeout(() => {
          navigator('/tasks');
        }, 1000);
      })
      .catch((error) => {
        console.error(error);

        // Show error toast notification with a 3-second delay
    toast.error('Login failed!Check your credentials', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
      });
  }

  return (
    <div className='container'>
      <br />
      <div className='row justify-content-center'>
        {/* Apply CSS class for styling the login card */}
        <div className='login-card'>
          <div className='card-header'>
            <h2 className='text-center'> Sign-In </h2>
          </div>
          <div className='card-body'>
            <form>
              <div className='form-group'>
                <label className='control-label'> Username or Email</label>
                <input
                  type='text'
                  name='username'
                  className='form-control'
                  placeholder='Enter email'
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>

              <div className='form-group'>
                <label className='control-label'> Password </label>
                <input
                  type='password'
                  name='password'
                  className='form-control'
                  placeholder='Enter password'
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>

              <Row justify='center'>
                <Col>
                  <Button type='primary' onClick={(e) => handleLoginForm(e)}>
                    Sign-In
                  </Button>
                </Col>
              </Row>
            </form> 
          </div>
          <div className='text-center'>
            <p>New to Task management application?</p>
            <Link to='/register' className='btn btn-link'  style={{ marginTop: '-10px' }}>
              Create a new account
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;

