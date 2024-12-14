import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getShows } from "../Redux/cinemas/action";
import { CinemasBody } from "../Components/BookTickets/CinemasBody";

import { useParams } from "react-router";
import { Header } from "../Components/BookTickets/Header";
import { getMovies } from "../Redux/data/actions";
import { Filter } from "../Components/BookTickets/Filter";

export const BookTicketsPage = () => {
    const dispatch = useDispatch();
    const { id } = useParams();
    const [filters, setFilters] = useState([])
    const [count, setCount] = useState(0)

    useEffect(() => {
        dispatch(getShows(id));
        dispatch(getMovies(id));
    }, [])

    const handleFilters = (item) => {
        const newData = filters;
        if (filters.indexOf(item) >= 0) {
            newData.splice(filters.indexOf(item), 1);
            setFilters(newData);
        } else {
            newData.push(item);
            setFilters(newData);
        }
        setCount(prev => prev + 1);
    }
   
    return (
        <div style={{ backgroundColor: "#F2F2F2", paddingBottom: 20 }}>
            <Header />
            <Filter handleFilters={handleFilters} filters={filters} />
            <CinemasBody filters={filters} />
        </div>
    )
}