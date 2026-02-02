"use client"

import { useEffect, useState } from "react";
import style from "./staffpage.module.css";

type LoginInfo = {
    userName: string;
    address: string;
    email: string;
    totalPoint: number;
    phone: string;
    loginAt: string;
};


export default function staffpage(){
    const [info, setInfo ] = useState<LoginInfo | null>(null);

    useEffect(() => {
        const raw = sessionStorage.getItem("loginInfo");
        if(raw){
            try {
                const parsed = JSON.parse(raw) as Partial<LoginInfo>;
                setInfo((parsed) as LoginInfo);
            } catch {
                sessionStorage.removeItem("loginInfo");
            }
        }
        (async () => {
            try {
                const r = await fetch("/api/backend/staff/staffpage", {
                    credentials: "include",
                });
                if(!r.ok) return;
                const data = await r.json();
                const u = (data.user ?? data) as any;
                const next: LoginInfo = {
                    userName: u.userName,
                    address: u.address,
                    email: u.email,
                    totalPoint: u.totalPoint,
                    phone: u.phone,
                    loginAt: u.loginAt,
                };
                setInfo(next);
                sessionStorage.setItem("loginInfo", JSON.stringify(next));
            } catch {
                /* NOOP */
            }
        })();
    },[]);

    const handleLogout = () => {
        sessionStorage.removeItem("loginInfo");
        setInfo(null);
    };

    const loginAtJst = info?.loginAt ? new Date(info.loginAt).toLocaleString("ja-JP", { timeZone: "Asia/Tokyo" }) : "";

    if(!info) return (
        <main>
            <section>
                <h2>会員登録・ログインはこちら</h2>
                <p><a href="/staff/staffSignin">会員登録はこちらから</a></p>
                <p><a href="/staff/login">ログインはこちらから</a></p>
            </section>
            <p className={style.errMsg}>アカウントが見つかりません。</p>
        </main>
    )

    return(
        <main>
            <section>
                <p>ログインユーザー : {info.userName}</p>
                <p>最終ログイン : {loginAtJst}</p>
            </section>
            <section>
                <h2>会員登録・ログインはこちら</h2>
                <p><a href="/staff/staffSignin">会員登録はこちらから</a></p>
                <p><a href="/staff/login">ログインはこちらから</a></p>
            </section>
            <section>
                <h2>顧客一覧</h2>
                
            </section>
        </main>
    );
}