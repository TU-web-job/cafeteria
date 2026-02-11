"use client"

import { useState, useEffect } from "react";
import t from "./page.module.css";

export default function Home() {
  const [msg, setMsg] = useState("loding..");
  useEffect(() => {
    fetch("/api/top")
      .then(r => r.text())
      .then(setMsg)
      .catch(() => setMsg("fetch Error"));
  },[]);

  return (
    <main className={t.main}>
      <h1>Cafeteria Page</h1>
      <section className={t.infoContainer}>
        <h2>会員登録・ログイン</h2>
        <nav className={t.infoContents}>
          <label>登録・ログインは<a href="/user/signin">ここから</a>飛ぶ🚀</label>
        </nav>
      </section>
      <section className={t.infoContainer}>
        <h2>Menu List</h2>
        <nav className={t.infoContents}>
          <p></p>
          <label>メニュー一覧は<a href="/menu">ここから</a>飛ぶ🚀</label>
        </nav>
      </section>
      <section className={t.infoContainer}>
        <h2>Order</h2>
        <nav className={t.infoContents}>
          <label>注文は<a href="/order">ここから</a>飛ぶ🚀</label>
        </nav>
      </section>
      <section className={t.infoContainer}>
        <h2>Shop Info</h2>
        <nav className={t.infoContents}>
          <p><span>Address</span> : Tokyo </p>
          <p><span>Tel</span> : 000-xxxx-1234</p>
          <p><span>E-Mail</span> : cafeteria@sample.co.jp</p>
        </nav>
      </section>
      <section className={t.infoContainer}>
        <h2>Recruit</h2>
        <nav className={t.infoContents}>
          <label>求人情報は<a href="/recruit">ここから</a>飛ぶ🚀</label>
        </nav>
      </section>
    </main>
  );
}
