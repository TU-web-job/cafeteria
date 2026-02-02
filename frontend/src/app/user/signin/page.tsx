"use client"

import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import style from "./signin.module.css";

type Form = {
    userName: string;
    address: string;
    email: string;
    phone: string;
    password: string;
}
export default function signin(){
    const router = useRouter();
    const { register, handleSubmit, setError, formState:{errors} } = useForm<Form>();
    const onSubmit = async (v: Form) => {
    try{
        const res = await fetch("/api/backend/user/signin", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(v),
            credentials: "include",
        });

        const text  =await res.text();
        const json = (() => { try{ return JSON.parse(text); } catch { return text; }})();
        
        if(!res.ok) {
            Object.entries(json as Record<string, string>).forEach(([field, msg]) => {
                if(field in v) {
                    setError(field as keyof Form, {type:"server", message: String(msg)});
                }
            })
            console.error("API Error", res.status, json);
            alert(`登録に失敗しました。(${res.status}) \n${typeof json === "string" ? json : JSON.stringify(json)}`);
            return;
        }

        router.push("/user/login");
    } catch(e: any){
        console.error(e);
        alert("システムエラーが発生しました。");
    }
    };

    return(
        <main className={style.signin}>
            <section className={style.signinForm}>
            <h2>新規会員登録</h2>
            <form onSubmit={handleSubmit(onSubmit)} className={style.form}>
                <nav className={style.inputLabel}>
                    <label htmlFor="userName">名前  </label>
                    <input id="userName" type="text" {...register("userName",{
                        required: "※名前は必須項目です。"})} placeholder=" 名前 太郎"/>
                    {errors.userName && <p className={style.error}>{errors.userName.message}</p>}
                </nav>
                <nav className={style.inputLabel}>
                    <label htmlFor="address">住所  </label>
                    <input id="address" type="text" {...register("address")} placeholder=" 住所 1-1-1"/>
                </nav>
                <nav className={style.inputLabel}>
                    <label htmlFor="email">メールアドレス  </label>
                    <input id="email" type="email" {...register("email", {
                        required: "※メールアドレスは必須項目です。"})} placeholder=" XXX@XX.com"/>
                    {errors.email && <p className={style.error}>{errors.email.message}</p>}
                </nav>
                <nav className={style.inputLabel}>
                    <label htmlFor="phone">電話番号  </label>
                    <input id="phone" type="tel" {...register("phone")} placeholder=" 000-0000-0000" />
                </nav>
                <nav className={style.inputLabel}>
                    <label htmlFor="password">パスワード  </label>
                    <input id="password" type="password" {...register("password", {
                        required: "※パスワードは必須項目です。"})} placeholder=" XXXX" />
                    {errors.password && <p className={style.error}>{errors.password.message}</p>}
                </nav>
                <button type="submit" className={style.btn}>登録</button>
            </form>
            
            <p className={style.toLogin}><a href="/user/login">登録済みの方はこちら</a></p>
            </section>
        </main>
    );
}