"use client"

import { useEffect, useState } from "react";
import s from "./menu.module.css";

type Menu = {
    menuId:number;
    menuName: string;
    price: number;
    point: number;
    menuImg: string;
    mimeType:string;
}

export default function Menu() {
    const [ drink, setDrink] = useState<Menu[]>([]);
    const [ food, setFood] = useState<Menu[]>([]);

    useEffect(() => {
        fetch("/api/backend/menu")
            .then(r => r.json())
            .then((m: {drink: Menu[]; food:Menu[] }) => {
                setDrink(m.drink);
                setFood(m.food);
            });
    },[]);

    return (
        <main className={s.menu}>
            <h2 className={s.title}>Menu List</h2>
            <section className={s.menuList}>
                <h3>Drink Menu</h3>
                <div className={s.grid}>
                {drink.map(d => (
                    <nav key={d.menuId} className={s.product}>
                        <div className={s.productImg}>
                            {d.menuImg ? (
                                <img src={`data:${d.mimeType || 'image/jpeg'};base64,${d.menuImg}`} alt={d.menuName} />
                            ) : (
                                <p>No Image Foto...</p>
                            )}
                        </div>
                        <div className={s.productTxt}>
                            <p><span>Drink</span> : {d.menuName}</p>
                            <p><span>Price</span> : {d.price} Yen</p>
                            <p><span>GetPoint</span> : {d.point} Point</p>
                        </div>
                    </nav>
                ))}
                </div>
            </section>
            <section className={s.menuList}>
                <h3>Food Menu</h3>
                <div className={s.grid}>
                {food.map(f => (
                    <nav key={f.menuId} className={s.product}>
                        <div className={s.productImg}>
                            {f.menuImg ? (
                                <img src={`data:${f.mimeType || 'image/jpeg'};base64,${f.menuImg}`} alt={f.menuName} />
                            ) : (
                                <p>No Image Foto...</p>
                            )}
                        </div>
                        <div className={s.productTxt}>
                            <p><span>Food</span> : {f.menuName}</p>
                            <p><span>Price</span> : {f.price} Yen</p>
                            <p><span>GetPoint</span> : {f.point} Point</p>
                        </div>
                    </nav>
                ))}
                </div>
            </section>
            <a href="/order" className={s.link}>Orderはこちらから</a>
        </main>
    );
}