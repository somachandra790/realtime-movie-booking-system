import axios from "axios"
import {
    GET_CINEMAS_FAILURE,
    GET_CINEMAS_REQUEST,
    GET_CINEMAS_SUCCESS,
    GET_CINEMAS_SUCCESS_DATE
} from "./actionTypes"


// GET CINEMAS --------------------------------------------

const getCinemasRequest = () => {
    return {
        type: GET_CINEMAS_REQUEST
    }
}
export const getCinemasSuccess = (payload) => {
    return {
        type: GET_CINEMAS_SUCCESS,
        payload
    }
}
const getCinemasFailure = () => {
    return {
        type: GET_CINEMAS_FAILURE
    }
}

export const getCinemasForSelectedDate = (payload) => {
    return {
        type : GET_CINEMAS_SUCCESS_DATE,
        payload
    }
}

export const getShows = (movieId) => dispatch => {
    dispatch(getCinemasRequest());
    return axios.get(`http://localhost:8080/projection/movie/${movieId}`)
        .then(res => dispatch(getCinemasSuccess(res.data)))
        .catch(error => dispatch(getCinemasFailure(error)))
}