import React from 'react'
import styles from '../Components/Styling/Footer.module.css'
const Footer = () => {
    return (
        <div>
            {/* <span>Home</span> */}

            <div className={styles.part6}>
                <div>Copyright 2024
                    © Bigtree Entertainment Pvt. Ltd. All Rights Reserved.</div>
                <div>The content and images used on
                    this site are copyright protected and copyrights vests with the respective owners. The usage of the content and
                    images on this website is intended to promote the works and no endorsement of the artist shall be implied.</div>
                <div>Unauthorized use is prohibited and punishable by law.
                </div>
            </div>
        </div>
    )
}

export default Footer
