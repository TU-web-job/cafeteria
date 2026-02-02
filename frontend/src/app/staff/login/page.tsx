"use client"

import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";

import style from "./login.module.css";

type Form = {
    email: string;
    password: string;
}
export default function login(){
    const router = useRouter();
    const { register, handleSubmit, setError, formState: {errors} } = useForm<Form>();
    const onSubmit = async (v: Form) => {
        const res = await fetch("/api/backend/staff/login", {
            method: "POST",
            headers: { "Content-Type": "application/json",
                        "Accept": "application/json",
             },
            body: JSON.stringify(v),
            credentials: "include",
        });

        const text = await res.text();
        const json = (() => { try{ return JSON.parse(text); } catch{ return text; }})();

        if(!res.ok){
            alert(`ログインに失敗しました。(${res.status})\n${typeof json == "string" ? json : JSON.stringify(json)}`);
            return;
        }

        router.push("/staff/staffpage");
    };
    return(
        <main className={style.login}>
            <section className={style.formWrapper}>
                <h2>ログイン</h2>
                <form className={style.form} onSubmit={handleSubmit(onSubmit)}>
                    <nav className={style.inputLabel}>
                        <label>ログインID</label>
                        <input type="email" {...register("email", { required: "メールアドレスは必須項目です。"})} placeholder=" XXX@xxx.com"/>
                        {errors.email && <p>{errors.email.message}</p>}
                    </nav>
                    <nav className={style.inputLabel}>
                        <label htmlFor="password">パスワード</label>
                        <input id="password" type="password" {...register("password", { required: "password"})} placeholder=" XXXX" />
                        {errors.password && <p className={style.error}>{errors.password.message}</p>}
                    </nav>
                    <button type="submit" >ログイン</button>
                </form>
            </section>
        </main>
    );
}