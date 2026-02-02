"use client"

import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import s from "./order.module.css";
import { useEffect, useState, useMemo } from "react";

type FormType = {
    orderId: number;
    orderName: string;
    orderPrice: number;
    orderCount: number;
    taxChoice:number;
}

type menuList = {
    menuId: number;
    menuName: string;
    price: number;
    point: number;
}

type cartItem = {
    orderId: number;
    orderName: string;
    orderPrice: number;
    point: number;
    orderCount: number;
    type: "drink"| "food";
}

type orderItem = {
    taxChoice: number;
    items: { menuId: number; count: number }[];
};

export default function Order() {
    const [ drink, setDrink ] = useState<menuList[]>([]);
    const [ food, setFood ] = useState<menuList[]>([]);
    useEffect(() => {
        fetch("/api/backend/order")
            .then(r => r.json())
            .then((m: {drink: menuList[]; food:menuList[] }) => {
                setDrink(m.drink);
                setFood(m.food);
            });
    },[]);

    const [selectedDrinkId, setSelectedDrinkId] = useState<number | "">("");
    const [selectedFoodId, setSelectedFoodId] = useState<number | "">("");

    const [cart, setCart] = useState<cartItem[]>([]);

    const addDrinkCart = (id: number) => {
        const d = drink.find(x => x.menuId === id);
        if(!d) return;
        setCart(prev => {
            const idx = prev.findIndex(it => it.type === "drink" && it.orderId === id);
            if(idx >= 0) {
                const copy = [...prev];
                copy[idx] = {...copy[idx], orderCount: Math.min(99, copy[idx].orderCount + 1) };
                return copy;
            }
            return [...prev, {
                orderId: d.menuId,
                orderName: d.menuName,
                orderPrice: d.price,
                orderCount: 1,
                point: d.point,
                type: "drink"
            }];
        });
    };

    const addFoodCart = (id: number) => {
        const f = food.find(x => x.menuId === id);
        if(!f) return;
        setCart(prev => {
            const idx = prev.findIndex(it => it.type === "food" && it.orderId === id);
            if(idx >= 0) {
                const copy = [...prev];
                copy[idx] = {...copy[idx], orderCount:Math.min(99, copy[idx].orderCount + 1) };
                return copy;
            }
            return [...prev, {
                orderId: f.menuId,
                orderName: f.menuName,
                orderPrice: f.price,
                orderCount: 1,
                point: f.point,
                type: "food"
            }];
        });
    };

    const pls = (i:number)=> setCart(prev => {
        const copy = [...prev];
        copy[i] = {...copy[i], orderCount: Math.min(99, copy[i].orderCount + 1) };
        return copy;
    });

    const mns = (i:number) => setCart(prev => {
        const copy = [...prev];
        copy[i] = {...copy[i], orderCount: Math.max(1, copy[i].orderCount - 1) };
        return copy;
    });

    const remove = (i: number) => setCart(prev => prev.filter((_, idx) => idx !== i));

    const drinkRows = useMemo(
        () => cart.map((it, idx) => ({it, idx})).filter((x) => x.it.type === "drink"),[cart]
    );

    const foodRows = useMemo(
        () => cart.map((it, idx) => ({it, idx})).filter((x) => x.it.type === "food"),[cart]
    );

    const drinkPrice = useMemo(
        () => drinkRows.reduce((s, x) => s + x.it.orderPrice * x.it.orderCount, 0),[drinkRows]
    );

    const foodPrice = useMemo(
        () => foodRows.reduce((s, x) => s + x.it.orderPrice * x.it.orderCount, 0),[drinkRows]
    );

    const totalPrice = useMemo(() =>
        cart.reduce((sum, it) => sum + it.orderPrice * it.orderCount, 0), [cart]
    );

    const drinkPoint = useMemo(() =>
        drinkRows.reduce((s, x) => s + x.it.point * x.it.orderCount, 0),[drinkRows]
    );

    const foodPoint = useMemo(() =>
        foodRows.reduce((s, x) => s + x.it.point * x.it.orderCount,0),[foodRows]
    );

    const totalPoint = useMemo(() =>
        cart.reduce((sum, it) => sum + it.point * it.orderCount,0),[cart]
    );

    const router = useRouter();
    const { register, handleSubmit } = useForm<FormType>();
    const onSubmit = async (v: FormType) => {

        if(cart.length === 0) {
            alert("カートが空です。");
        }

        const payload: orderItem = {
            taxChoice: v.taxChoice,
            items: cart.map(item => ({
                menuId: item.orderId,
                count: item.orderCount,
            })),
        };

        
        const res = await fetch("/api/backend/order", {
            method: "POST",
            headers: { "Content-Type": "application/json",
                        "Accept": "application/json",
             },
            body: JSON.stringify(payload),
            credentials: "include",
        });

        const text = await res.text();
        const json = (() => { try { return JSON.parse(text); } catch { return text;}})();

        if(!res.ok){
            alert(`注文に失敗しました。(${res.status})\n${typeof json == "string" ? json : JSON.stringify(json)}`);
            return;
        }

        const orderId = typeof json === "object" && json?.orderId;

        router.push(orderId ? `/order/orderList/${orderId}` : "/order/orderList");
    }

    return(
        <main className={s.order}>
            <h2>Order</h2>
            <form className={s.orderForm} onSubmit={handleSubmit(onSubmit)}>
            <div className={s.radioBtn}>
                <label className={s.radio}><input type="radio" value={10} {...register("taxChoice", { required: true, valueAsNumber: true })} />店内飲食</label>
                <label className={s.radio}><input type="radio" value={8} {...register("taxChoice", { required: true, valueAsNumber: true })} />持ち帰り</label>
            </div>

            <section className={s.orderList}>
                <nav className={s.orderWrapper}>
                    <div className={s.drinkWrapper}>
                        <h3>Drink Order</h3>
                        <select className={s.orderSelect} value={selectedDrinkId} onChange={(e) => {
                            const val = e.target.value === "" ? "" : Number(e.target.value);
                            setSelectedDrinkId(val);
                            if(val !== ""){
                                addDrinkCart(Number(val));
                                setSelectedDrinkId("");
                            }
                        }}>
                            <option>-- Drink Menu --</option>
                            {drink.map(d => (
                                <option value={d.menuId} key={d.menuId}>{d.menuName}</option>
                            ))}
                        </select>

                        <section className={s.orderContent}>
                            {drinkRows.map(({ it, idx }) => (
                                <div key={`drink-${it.orderId}`} className={s.orderItem}>
                                    <p>商品名 : {it.orderName}</p>
                                    <p>金額 : {it.orderPrice} Yen</p>
                                    <p>獲得ポイント(1個) : {it.point} Pt</p>
                                    <div className={s.count}>
                                        <button type="button" className={s.mnsBtn} onClick={() => mns(idx)}> - </button>
                                        <span>{it.orderCount} 個</span>
                                        <button type="button" className={s.plsBtn} onClick={() => pls(idx)}> + </button>
                                    </div>
                                        <button type="button" className={s.deleteBtn} onClick={() => remove(idx)}>削除</button>
                                </div>
                            ))}
                            <p className={s.itemTotal}><span>Drink合計ポイント</span> : {drinkPoint} Pt</p>
                            <p className={s.itemTotal}><span>Drink合計金額</span> : {drinkPrice} Yen</p>
                        </section>
                    </div>

                    <div className={s.foodWrapper}>
                        <h3>Food Order</h3>
                        <select className={s.orderSelect} value={selectedFoodId} onChange={(e) => {
                            const val = e.target.value === "" ? "" : Number(e.target.value);
                            setSelectedFoodId(val);
                            if(val !== ""){
                                addFoodCart(Number(val));
                                setSelectedFoodId("");
                            }
                        }}>
                            <option>-- Food Menu --</option>
                            {food.map(f => (
                                <option key={f.menuId} value={f.menuId}>{f.menuName}</option>
                            ))}
                        </select>

                        <section className={s.orderContent}>
                            {foodRows.map(({it, idx}) => (
                                <div key={`food-${it.orderId}`} className={s.orderItem}>
                                    <p>商品名 : {it.orderName}</p>
                                    <p>金額 : {it.orderPrice} Yen</p>
                                    <p>獲得ポイント(1個) : {it.point} Pt</p>
                                    <div className={s.count}>
                                        <button type="button" className={s.mnsBtn} onClick={() =>mns(idx)}> - </button>
                                        <span>{it.orderCount} 個</span>
                                        <button type="button" className={s.plsBtn} onClick={() =>pls(idx)}> + </button>
                                    </div>
                                    <button type="button" className={s.deleteBtn} onClick={() => remove(idx)}> 削除</button>
                                </div>
                            ))}
                            <p className={s.itemTotal}><span>Food合計ポイント</span> : {foodPoint} Pt</p>
                            <p className={s.itemTotal}><span>Food合計金額</span> : {foodPrice} Yen</p>
                        </section>
                    </div>
                </nav>
                <div className={s.totalWrapper}>
                    <p><span>合計獲得ポイント</span> : {totalPoint} Pt</p>
                    <p><span>合計金額</span> : {totalPrice} Yen</p>
                </div>
            </section>
            <div className={s.orderBtnWrapper}>
                <button type="submit" className={s.orderBtn}>注文する</button>
            </div>
            </form>
            <p><a href="/order/orderList" className={s.link}>注文履歴一覧はこちら</a></p>
        </main>
    );
}