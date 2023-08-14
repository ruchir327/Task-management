import React, { useState } from 'react';
import { registerAPICall } from '../services/AuthService';
import { Link, useNavigate } from 'react-router-dom';
import { Button,Card } from 'antd';
import { toast } from 'react-toastify';
const RegisterComponent = () => {
  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('user');
  const navigator = useNavigate();

  function handleRegistrationForm(e) {
    e.preventDefault();
      if (name.length < 3) {
        toast.error('Enter a valid name!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
        return;
      }
      if (username.length < 5) {
        toast.error('Enter a valid username!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
        return;
      }
      function isValidEmail(email) {
        // Email validation regex pattern
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
      }
      if (!isValidEmail(email)) {
        toast.error('Enter a valid emailId!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
        return;
      }
      if (password.length < 6) {
        toast.error('Password must be at least 6 characters long!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
        return;
      }
      const register = { name, username, email, password, role };
    console.log(register);

    registerAPICall(register)
      .then((response) => {
        console.log(response.data);

        // Show success toast notification with a 2-second delay
        toast.success('Registration successful!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000, // 2000ms = 2 seconds
        });

        // Navigate to /login after a 2-second delay
        setTimeout(() => {
          navigator('/login');
        }, 2000); // 2000ms = 2 seconds
      })
      .catch((error) => {
        console.error(error);

        // Show error toast notification with a 3-second delay
        toast.error('Registration failed!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 3000, // 3000ms = 3 seconds
        });
      });
  }

  return (
    <div className='container'>
      <br />
      <div className='row justify-content-center'>
        <Card className='bg-light'>
          <div className='card-header'>
          <h2 className='text-center'>
              Create an Account
            </h2>      
          </div>
          <div className='card-body'>
            <form>
              <div className='form-group'>
                <label className='control-label'> Name </label>
                <input
                  type='text'
                  name='name'
                  className='form-control'
                  placeholder='Enter your name'
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
              </div>

              <div className='form-group'>
                <label className='control-label'> Username </label>
                <input
                  type='text'
                  name='username'
                  className='form-control'
                  placeholder='Choose a username'
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>

              <div className='form-group'>
                <label className='control-label'> Email </label>
                <input
                  type='text'
                  name='email'
                  className='form-control'
                  placeholder='Enter your email address'
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>

              <div className='form-group'>
                <label className='control-label'> Password </label>
                <input
                  type='password'
                  name='password'
                  className='form-control'
                  placeholder='Choose a password'
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
              <div className='form-group'>
                <label className='control-label'> Role </label>
                <select
                    className='form-control'
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                >
                    <option value='user'>User</option>
                    <option value='admin'>Admin</option>
                </select>
            </div>
              <div className='form-group mb-3 text-center'>
                <Button type='primary' size='large' onClick={(e) => handleRegistrationForm(e)}>
                  Create Account
                </Button>
              </div>

              <div className='text-center'>
                <p className="mb-0">Already have an account?</p>
                <Link to='/login' className='btn btn-link' style={{ marginTop: '-10px' }}>
                  Sign-In
                </Link>
              </div>
              <div className='text-center'>
              <p>
                  By creating an account, you agree to Task Management application's Conditions of Use and Privacy Notice. <br />
                </p>
                </div>
            </form>
          </div>
        </Card>
      </div>
    </div>
  );
}

export default RegisterComponent