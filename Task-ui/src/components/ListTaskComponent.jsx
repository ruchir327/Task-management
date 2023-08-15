
import React, { useEffect, useState } from 'react';
import { completeTask, deleteTask, getAllTasks, inCompleteTask } from '../services/TaskService';
import { useNavigate } from 'react-router-dom';
import { isAdminUser,getLoggedInUsername,getUserRoles } from '../services/AuthService';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ListTaskComponent = () => {
  const [tasks, setTasks] = useState([]);
  const navigate = useNavigate();
  const isAdmin = isAdminUser();
  const loggedInUsername = getLoggedInUsername(); // Retrieve the currently logged-in username
  const isUserRole = getUserRoles();
  useEffect(() => {
    listTasks();
  }, []);
  
  function listTasks() {
    getAllTasks()
      .then((response) => {
        // Filter tasks based on the currently logged-in user (assignedUser) or the tasks assigned by the logged-in user (assignedBy)
        const filteredTasks = response.data.filter(
          (task) => task.assignedUser === loggedInUsername || task.assignedBy === loggedInUsername
        );
        setTasks(filteredTasks);
      })
      .catch((error) => {
        console.error(error);
      });
  }
  function addNewTask() {
        navigate('/add-task');
        listTasks();
      }
    
  function updateTask(id) {
    navigate(`/update-task/${id}`);
  }

  function removeTask(id) {
    deleteTask(id)
      .then(() => {
        listTasks();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function markCompleteTask(id) {
    console.log(tasks)
    completeTask(id)
      .then(() => {
        listTasks();
        toast.success('Task marked as complete!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
      })
      .catch((error) => {
        console.error(error);
        toast.error('Failed to mark task as complete!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
      });
  }

  function markInCompleteTask(id) {
    inCompleteTask(id)
      .then(() => {
        listTasks();
        toast.info('Task marked as incomplete!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
      })
      .catch((error) => {
        console.error(error);
        toast.error('Failed to mark task as incomplete!', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 2000,
        });
      });
  }

  return (
    <div className='container'>
     <h2 className='text-center'>
        {isUserRole
          ? `List of Tasks for ${loggedInUsername}`
          : 'List of Tasks'}
      </h2>
      {isAdmin && (
        <div className='text-center' style={{ marginBottom: '20px' }}>
          <button className='btn btn-primary' onClick={addNewTask}>
            Add Task
          </button>
        </div>
      )}

      <div>
        <table className='table table-bordered table-striped'>
          <thead>
            <tr>
              <th>Task Title</th>
              <th>Task Description</th>
              <th>Task Completed</th>
              <th>Assigned By</th>
              <th>Assigned User</th>
          
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {tasks.map((task) => (
              <tr key={task.id}>
                <td>{task.title}</td>
                <td>{task.description}</td>
                <td>{task.completed ? 'YES' : 'NO'}</td>
                <td>{task.assignedBy}</td>
               <td>{task.assignedUser}</td>
            
                <td>
                  {isAdmin && (
                    <>
                      <button className='btn btn-info' onClick={() => updateTask(task.id)}>
                        Update
                      </button>
                      <button
                        className='btn btn-danger'
                        onClick={() => removeTask(task.id)}
                        style={{ marginLeft: '10px' }}
                      >
                        Delete
                      </button>
                    </>
                  )}
                  <button
                    className='btn btn-success'
                    onClick={() => markCompleteTask(task.id)}
                    style={{ marginLeft: '10px' }}
                  >
                    Complete
                  </button>
                  <button
                    className='btn btn-info'
                    onClick={() => markInCompleteTask(task.id)}
                    style={{ marginLeft: '10px' }}
                  >
                    In Complete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListTaskComponent;
