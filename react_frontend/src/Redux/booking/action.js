import axios from "axios"
import {
    ADD_BOOKING_DETAILS_FAILURE,
    ADD_BOOKING_DETAILS_REQUEST,
    ADD_BOOKING_DETAILS_SUCCESS,
    GET_BOOKING_DETAILS_FAILURE,
    GET_BOOKING_DETAILS_REQUEST,
    GET_BOOKING_DETAILS_SUCCESS
} from "./actionTypes"


// POST BOKING DETAILS --------------------------------------------

const postBookingDetailsRequest = () => {
    return {
        type: ADD_BOOKING_DETAILS_REQUEST
    }
}
const postBookingDetailsSuccess = (payload) => {
    return {
        type: ADD_BOOKING_DETAILS_SUCCESS
    }
}
const postBookingDetailsFailure = () => {
    return {
        type: ADD_BOOKING_DETAILS_FAILURE
    }
}
export const postBookingDetails = (payload) => dispatch => {
    dispatch(postBookingDetailsRequest());
    return axios.post("http://localhost:8080//booking", payload)
        .then(res => {
            dispatch(postBookingDetailsSuccess(res.data));
            return {
                success: true
            }
        })
        .catch(error => dispatch(postBookingDetailsFailure(error)));
}


// GET BOOKING DETAILS------------------------------------


const getBookingDetailsRequest = () => {
    return {
        type: GET_BOOKING_DETAILS_REQUEST
    }
}
const getBookingDetailsSuccess = (payload) => {
    return {
        type: GET_BOOKING_DETAILS_SUCCESS,
        payload
    }
}
const getBookingDetailsFailure = () => {
    return {
        type: GET_BOOKING_DETAILS_FAILURE
    }
}
export const getBookingDetails = () => dispatch => {
    dispatch(getBookingDetailsRequest());
    return axios.get("http://localhost:8080//booking")
        .then(res => {
            dispatch(getBookingDetailsSuccess(res.data.data));
        })
        .catch(error => dispatch(getBookingDetailsFailure(error)));
}