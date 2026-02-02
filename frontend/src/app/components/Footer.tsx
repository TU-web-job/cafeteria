"use client"

import style from "./Footer.module.css";

export default function Footer(){
    return(
        <footer className={style.footer}>
            <h2>Cafe</h2>
            <nav className={style.text}><p>&copy;NextSpring Cafeteria</p></nav>
        </footer>
    );
}