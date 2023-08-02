import axios from "axios";
import { getToken } from "./AuthService";
const BASE_REST_API_URL = 'http://localhost:8080/api/tasks';
axios.interceptors.request.use(function (config) {
    
    config.headers['Authorization'] = getToken();

    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });
export const getAllTasks = () => axios.get(BASE_REST_API_URL)

export const saveTask = (task) => axios.post(BASE_REST_API_URL, task)

export const getTask = (id) => axios.get(BASE_REST_API_URL + '/' + id)

export const updateTask = (id, task) => axios.put(BASE_REST_API_URL + '/' + id, task)

export const deleteTask = (id) => axios.delete(BASE_REST_API_URL + '/' + id)

export const completeTask = (id) => axios.patch(BASE_REST_API_URL + '/' + id + '/complete')

export const inCompleteTask = (id) => axios.patch(BASE_REST_API_URL + '/' + id + '/in-complete')
export const getAllUsers = () => axios.get('http://localhost:8080/api/auth/users');
export const getTaskById = (id) => axios.get(BASE_REST_API_URL + '/' + id);