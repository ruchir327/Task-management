import { useState } from 'react'
import './App.css'
import ListTaskComponent from './components/ListTaskComponent'
import HeaderComponent from './components/HeaderComponent'
import FooterComponent from './components/FooterComponent'
import { BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import TaskComponent from './components/TaskComponent'
import RegisterComponent from './components/RegisterComponent'
import LoginComponent from './components/LoginComponent'
import { isUserLoggedIn } from './services/AuthService'

function App() {

  function AuthenticatedRoute({children}){

    const isAuth = isUserLoggedIn();

    if(isAuth) {
      return children;
    }

    return <Navigate to="/" />

  }

  return (
    <>
    <BrowserRouter>
        <HeaderComponent />
          <Routes>
              {/* http://localhost:8080 */}
              <Route path='/' element = { <LoginComponent /> }></Route>
               {/* http://localhost:8080/tasks */}
              <Route path='/tasks' element = { 
              <AuthenticatedRoute>
                <ListTaskComponent />
              </AuthenticatedRoute> 
              }></Route>
              {/* http://localhost:8080/add-taskdo */}
              <Route path='/add-task' element = { 
                <AuthenticatedRoute>
                <TaskComponent /> 
                </AuthenticatedRoute>
              }></Route>
              {/* http://localhost:8080/update-task/1 */}
              <Route path='/update-task/:id' element = { 
              <AuthenticatedRoute>
              <TaskComponent/> 
              </AuthenticatedRoute>
              }></Route>
               {/* http://localhost:8080/register */}
              <Route path='/register' element = { <RegisterComponent />}></Route>

               {/* http://localhost:8080/login */}
               <Route path='/login' element = { <LoginComponent /> }></Route>

          </Routes>
        <FooterComponent />
        </BrowserRouter>
    </>
  )
}

export default App