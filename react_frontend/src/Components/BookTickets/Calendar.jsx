import React, { useEffect, useState } from "react";
import Carousel from "react-multi-carousel";
import { useDispatch, useSelector } from "react-redux";
import { handleSelectDate } from "../../Redux/booking_details/actions";
import styles from "../Styling/Cinemas.module.css";
import { dayMap } from "../../Utils/constants";
import { getCinemasSuccess, getCinemasForSelectedDate } from "../../Redux/cinemas/action";

export const Calendar = () => {
    let currentDate = new Date().getDate();
    let currentDay = new Date().getDay();
    let [selectedDate, setSelectedDate] = useState(dayMap?.findIndex((day)=>day===currentDay));
    const dispatch = useDispatch();
    const cinemas_data = useSelector(state => state.cinemas.cinemas_data);
    
    let dates = [];
    let weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    for (let i = 0; i < 7; i++) {
        if (currentDate > 30) currentDate = 1;
        if (currentDay === 7) currentDay = 0;

        dates.push({ date: currentDate, day: weekdays[currentDay] });
        currentDate++;
        currentDay++;
    }

    useEffect(()=>{
        const selectedDateShows = cinemas_data?.filter((cinema)=>cinema?.projectionDate?.day === dayMap?.[selectedDate]);
        dispatch(getCinemasForSelectedDate(selectedDateShows));
    },[selectedDate])

    const responsive = {
        superLargeDesktop: {
            breakpoint: { max: 4000, min: 3000 },
            items: 4
        },
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 4
        },
        tablet: {
            breakpoint: { max: 1024, min: 464 },
            items: 2
        },
        mobile: {
            breakpoint: { max: 464, min: 0 },
            items: 1
        }
    };

    const handleDateChange = (index) => {

        setSelectedDate(index);
    }

    return (
        <div style={{ width: 250 }}>
            <Carousel className={styles.arrow} responsive={responsive} removeArrowOnDeviceType={["mobile"]}>
                {
                    dates?.map((item, index) => (
                        <div className={styles.dateItem} onClick={() => { handleDateChange(index); dispatch(handleSelectDate(dates[index].date, dates[index].day)) }} style={index === selectedDate ? { backgroundColor: "#F84464", color: "white" } : { backgroundColor: "transparent" }} key={item.date}>
                            <h2 style={index === selectedDate ? { color: "white" } : { color: "black" }}>{item.date}</h2>
                            <p>{item.day.slice(0, 3)}</p>
                        </div>
                    ))
                }
            </Carousel>
        </div>
    )
}