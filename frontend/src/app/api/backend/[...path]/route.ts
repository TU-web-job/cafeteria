import { NextRequest, NextResponse } from "next/server";

export const dynamic = "force-dynamic";
const BASE = process.env.BACKEND_BASE_URL ?? "http://localhost:8080";

async function proxy(
    req: NextRequest,
    method: "GET" | "POST",
    segments: string[]
){
    const url =
        method === "GET" ? `${BASE}/api/${segments.join("/")}${req.nextUrl.search}`
        : `${BASE}/api/${segments.join("/")}`;

    const headers: Record<string, string> = {
        "content-type": req.headers.get("content-type") ?? "application/json",
        "accept": req.headers.get("accept") ?? "application/json",
        "cookie": req.headers.get("cookie") ?? "",
    };

    const init: RequestInit = { method, headers };
    if(method === "POST"){
        init.body = await req.text();
    }

    const r = await fetch(url, init);

    const resHeaders = new Headers();
    const setCookies = r.headers.get("set-cookie");
    if(setCookies) resHeaders.set("set-cookie", setCookies);

    const ct = r.headers.get("content-type") ?? "";
    const body = ct.includes("application/json") ? await r.text() : await r.text();

    return new NextResponse(body, { status: r.status, headers: resHeaders });
}

export async function GET(req: NextRequest, ctx: { params: Promise<{ path: string[]}> }){
    const { path } = await ctx.params;
    return proxy(req, "GET", path);
}

export async function POST(req: NextRequest, ctx:  { params : Promise<{ path: string[]}>}){
    const { path } = await ctx.params;
    return proxy(req, "POST", path);
}