"use client"

import { useRouter } from "next/navigation";
import s from "./orderList.module.css";
import { useEffect, useState } from "react";

type orderHistoryList = {
    orderId: number;
    orderDate: string;
    orderPrice: number;
}

export default function orderList() {
    const router = useRouter();

    const [ orderHistory, setOrderHistory ] = useState<orderHistoryList[]>([]);
    useEffect(() => {
        fetch("/api/backend/order/orderList")
            .then(h => h.json())
            .then((list : {orderHistory : orderHistoryList[]}) => {
                setOrderHistory(list.orderHistory);
            });
    },[]);


    return(
        <main>
            <section className={s.orderList}>
                <h2>注文履歴リスト</h2>
                <table className={s.table}>
                    <thead>
                        <tr>
                        <th>No.</th>
                        <th>注文日</th>
                        <th>合計金額</th>
                        <th>履歴詳細</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orderHistory.length === 0 ? (
                            <tr><td>注文履歴がありません。</td></tr>
                        ) : (
                        orderHistory.map((detail,idx) => (
                            <tr key={detail.orderId}>
                                <td>{idx +1}</td>
                                <td>{detail.orderDate}</td>
                                <td>{detail.orderPrice} Yen</td>
                                <td><button type="button" className={s.detailBtn} onClick={() => router.push(`/order/orderList/${detail.orderId}`)}>詳細</button></td>
                            </tr>
                        )))}
                    </tbody>
                </table>
                <a href="/order" className={s.link}>注文する</a>
            </section>
        </main>
    );
}