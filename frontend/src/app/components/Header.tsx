"use client"

import style from "./Header.module.css";

type headerType = {
    index: number;
    title: string;
    link: string;
}

const headerList: headerType[] = [
    {index: 1, title: "トップ", link: "/"},
    {index: 2, title: "メニュー", link: "/menu"},
    {index: 3, title: "注文", link: "/order"},
    {index: 4, title: "マイページ", link: "/user/mypage"},
]

export default function Header(){
    return(
        <header className={style.header}>
            <section className={style.headerLogo}>
                <h1><a href="/">Cafeteria</a></h1>
            </section>
            <section className={style.listWrapper}>
                    <ul className={style.listContents}>
                        {headerList.map((list) => (
                            <li key={list.index} className={style.headerList}><a href={list.link}>{list.title}</a></li>
                        ))}
                    </ul>
            </section>
        </header>
    );
}