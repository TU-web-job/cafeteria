"use client"

import { useState, useEffect } from "react";

export default function Home() {
  const [msg, setMsg] = useState("loding..");
  useEffect(() => {
    fetch("/api/top")
      .then(r => r.text())
      .then(setMsg)
      .catch(() => setMsg("fetch Error"));
  },[]);
  return (
    <main>
      <h1>Cafeteria Page</h1>
      <section>
        <h2>会員登録・ログイン</h2>
        <nav>
          <a href="/user/signin">Signup</a>
        </nav>
      </section>
      <section>
        <h2>Menu List</h2>
        <nav>
          <p></p>
          <a href="/menu">MenuList</a>
        </nav>
      </section>
      <section>
        <h2>Order</h2>
        <nav>
          <a href="/order">Order</a>
        </nav>
      </section>
      <section>
        <h2>Shop Info</h2>
        <nav>
          <p>Address : Tokyo </p>
          <p>Tel : 000-xxxx-1234</p>
          <p>E-Mail : cafeteria@sample.co.jp</p>
        </nav>
      </section>
      <section>
        <h2>Recruit</h2>
        <nav>
          <a href="/recruit">Recruit</a>
        </nav>
      </section>
    </main>
  );
}
