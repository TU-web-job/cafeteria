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
      <h1>Top </h1>
    </main>
  );
}
