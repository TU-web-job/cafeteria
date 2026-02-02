"use client"

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import s from "../orderList.module.css";

type Item = {
    orderName: string;
    orderPrice: number;
    orderCount: number;
}

type orderDetail = {
    orderId: number;
    orderDate: string;
    subtotal: number;
    tax: number;
    totalPrice: number;
    getPrice: number;
    totalPoint: number;
    lists: Item[];
}

export default function OrderDetail(){
    const params = useParams<{ orderId: string}>();
    const orderId = Array.isArray(params.orderId) ? params.orderId[0] : params.orderId;
    const [data, setData] = useState<orderDetail | null>(null);
    const [err, setErr] = useState<string | null>(null);

    useEffect(() => {
        fetch(`/api/backend/order/orderList/${orderId}`,{ credentials: "include"})
            .then(async (r) => {
                const text = await r.text();
                const json = (() => { try { return JSON.parse(text); } catch { return text;}})();
                if(!r.ok) throw new Error(typeof json === "string" ? json : JSON.stringify(json));
                setData(json.orderDetail as orderDetail);
            })
            .catch((e) => setErr(String(e)));
    }, [orderId]);

    if(!data) return ( <main><p>No Data....</p></main>)

    return(
        <main className={s.orderDetail}>
            <h2>注文履歴詳細</h2>
            <section className={s.orderDate}>
                <label><span>注文日時</span> : {data.orderDate}</label>
            </section>
            <table className={s.detailTable}>
            {data.lists.map((list, idx) => (
                <tbody key={idx}>
                    <tr>
                    <td>{list.orderCount}</td>
                    <td>{list.orderName}</td>
                    <td>{list.orderPrice} Yen</td>
                    </tr>
                </tbody>
            ))}
            </table>
            <section className={s.total}>
                <label className={s.totalItem}><span>獲得ポイント</span> : {data.totalPoint} Pt</label>
                <label className={s.totalItem}><span>小計</span> : {data.subtotal} Yen</label>
                <label className={s.totalItem}><span>合計金額 </span>: {data.totalPrice}</label>
            </section>
            <p><a href="/order/orderList" className={s.link}>注文履歴一覧はこちら</a></p>
        </main>
    )
}