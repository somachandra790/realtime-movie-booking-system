import React from "react";
import {
    GET_CINEMAS_FAILURE,
    GET_CINEMAS_REQUEST,
    GET_CINEMAS_SUCCESS,
    GET_CINEMAS_SUCCESS_DATE
} from "./actionTypes";

const initState = {
    isLoading: false,
    isError: false,
    cinemas_data: [],
    date_formated_cinemas_data : []
}

export const cinemasReducer = (state = initState, {
    type,
    payload
}) => {
    switch (type) {
        case GET_CINEMAS_REQUEST: {
            return {
                ...state,
                isLoading: true
            }
        }
        case GET_CINEMAS_SUCCESS: {
            return {
                ...state,
                isLoading: false,
                cinemas_data: payload
            }
        }
        case GET_CINEMAS_FAILURE: {
            return {
                ...state,
                isLoading: false,
                isError: true
            }
        }
        case GET_CINEMAS_SUCCESS_DATE:
            return {
                ...state,
                isLoading: false,
                date_formated_cinemas_data: payload
            }
        default:
            return state
    }
}