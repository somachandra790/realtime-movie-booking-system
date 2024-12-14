import React from 'react'
import { useHistory } from 'react-router';
import styles from './Styling/Card.module.css';

const Card = (props) => {
    
    const history = useHistory();
    const handleChange = () => {
        history.push(`/movies/${props?.id}`)
    }
    return (
        <div onClick={handleChange} className={styles.card}> 
            <img src={props?.banner_image_url} alt={props?.title} />
            <div className={styles.title}>{ props?.title }</div>
            <div className={styles.genre}>{props?.genre}</div>
        </div>
    )
}

export default Card
