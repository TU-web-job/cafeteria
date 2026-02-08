
import r from "./recruit.module.css";

type labelList = {
    title: string;
    value: string;
}

const textList: labelList[] = [
    {title: "雇用形態", value: "正社員、アルバイト、パート"},
    {title: "ポジション", value: "副店長(店長候補)、ホールスタッフ、キッチンスタッフ"},
    {title: "勤務地", value: "東京、神奈川、埼玉、大阪"},
    {title: "給料", value: "正社員：25万円〜、アルバイト、パート：1350円〜"},
    {title: "休日", value: "月曜固定、夏季休暇、年末年始、その他"},
    {title: "勤務時間", value: "08:00~22:00"},
    {title: "福利厚生", value: "交通費全額支給、賄いあり、社内割引あり、有給休暇、制服貸与"},
    {title: "募集フロー", value: "書類選考→面接→内定"},
    {title: "お問い合わせ", value: "Email: cafeteria@sample.co.jp、Tel: 000-xxxx-1234"},

]

export default function Recruit() {
    return (
        <main className={r.recruit}>
            <h2 className={r.title}>Recruit</h2>
            <table className={r.rTable}>
            <tbody>
                {textList.map((txt,idx) => (
                    <tr key={idx}>
                        <th>{txt.title}</th>
                        <td>{txt.value}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </main>
    )
}