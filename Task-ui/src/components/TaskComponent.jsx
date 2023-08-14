import React, { useEffect, useState } from 'react';
import { saveTask, updateTask, getTaskById } from '../services/TaskService';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getAllUsers } from '../services/TaskService';
import { TaskDto } from './TaskDto'; // Import the TodoDto class
import { Card,Button } from 'antd';
const TaskComponenent = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [completed, setCompleted] = useState(false);
  const [users, setUsers] = useState([]);
  const [formCompleted, setFormCompleted] = useState(false);
  const navigate = useNavigate();
  const { id } = useParams();
  const [assignedUserName, setAssignedUserName] = useState('');

  useEffect(() => {
    getAllUsers()
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        console.error(error);
      });

    // If an 'id' is present in the URL, it means we are in update mode
    if (id) {
      getTaskById(id)
        .then((response) => {
          // Populate the form fields with the values from the fetched Todo
          setTitle(response.data.title);
          setDescription(response.data.description);
          setCompleted(response.data.completed);
          setAssignedUserName(response.data.assignedUser.username);
        })
        .catch((error) => {
          console.error(error);
          toast.error('Failed to fetch task details!', {
            position: toast.POSITION.TOP_CENTER,
            autoClose: 2000,
          });
        });
    }
  }, [id]);

  function saveOrUpdateTask(e) {
    e.preventDefault();
    if (title.length === 0 || title.length < 5) {
      toast.error('Todo title cannot be empty or have a length less than 5 characters!', {
        position: toast.POSITION.TOP_CENTER,
        autoClose: 2000,
      });
      return;
    }

    if (description.length === 0 || description.length < 5) {
      toast.error('Task description cannot be empty or have a length less than 5 characters!', {
        position: toast.POSITION.TOP_CENTER,
        autoClose: 2000,
      });
      return;
    }

    const taskDto = new TaskDto(id, title, description, completed, assignedUserName);

 
    if (id) {
      updateTask(id, taskDto)
        .then(() => {
          navigate('/tasks');
          toast.success('Task updated successfully!', {
            position: toast.POSITION.TOP_CENTER,
            autoClose: 2000,
          });
        })
        .catch((error) => {
          console.error(error);
          toast.error('Failed to update task!', {
            position: toast.POSITION.TOP_CENTER,
            autoClose: 2000,
          });
        });
    } else {
      saveTask(taskDto)
        .then((response) => {
          console.log(response.data);
          navigate('/tasks');
          toast.success('Task added successfully!', {
            position: toast.POSITION.TOP_CENTER,
            autoClose: 2000,
          });
        })
        .catch((error) => {
          console.error(error);
          toast.error('Failed to add task! Only Admin user can do this action', {
            position: toast.POSITION.TOP_CENTER,
            autoClose: 2000,
          });
        });
    }
  }

  function pageTitle() {
    return id ? <h2 className='text-center'>Update Task</h2> : <h2 className='text-center'>Add Task</h2>;
  }

  useEffect(() => {
    setFormCompleted(!!title && !!description && typeof completed !== 'undefined');
  }, [title, description, completed]);

  return (
    <div className='container'>
     
      <div className='row mt-5'>
      <Card className='bg-light'>
      <div className='card-header'>
          {pageTitle()}
          <div  className='card-body'>
            <form>
              <div className='form-group mb-2'>
                <label className='form-label'>Task Title:</label>
                <input
                  type='text'
                  className='form-control'
                  placeholder='Enter Task Title'
                  name='title'
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                />
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'>Task Description:</label>
                <input
                  type='text'
                  className='form-control'
                  placeholder='Enter Task Description'
                  name='description'
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'>Task Completed:</label>
                <select
                  className='form-control'
                  value={completed}
                  onChange={(e) => setCompleted(e.target.value)}
                >
                  <option value='false'>No</option>
                  <option value='true'>Yes</option>
                </select>
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'>Assigned User:</label>
                <select
                  className='form-control'
                  value={assignedUserName}
                  onChange={(e) => setAssignedUserName(e.target.value)}
                >
                  <option value=''>Select User</option>
                  {users.map((user) => (
                    <option key={user.id} value={user.username}>
                      {user.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className='shape text-center pt-3'>
                <Button size='large' className='btn btn-success' onClick={saveOrUpdateTask} disabled={!formCompleted}>
                  Submit
                </Button>
              </div>
            </form>
          </div>
        </div>
        </Card>
      </div>
    </div>
  );
};

export default TaskComponenent;
