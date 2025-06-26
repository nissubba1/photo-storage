import { Link} from "react-router-dom";

function Hero() {
    return(
        <section className={"flex flex-col justify-between items-center gap-10"}>
        <div className={"flex flex-col justify-between gap-5 items-center"}>
            <h1 className={"text-5xl"}>Cloud photo storage that is</h1>
            <div className={"text-3xl"}>
                <ol className={"list-decimal"}>
                    <li>Secure</li>
                    <li>Fast</li>
                    <li>Reliable</li>
                </ol>
            </div>
        </div>
            <div className={"flex flex-row gap-5"}>
                <Link to={"/signin"} className={"border rounded-sm pt-2 pb-2 pr-5 pl-5 cursor-pointer hover:border-amber-600"}>Sign In</Link>
                <Link to={"/signup"} className={"border rounded-sm pt-2 pb-2 pr-5 pl-5 cursor-pointer hover:border-amber-600"}>Sign Up</Link>
            </div>
        </section>
    )
}

export default function Home() {
    return(
        <section>
        <Hero />
        </section>
    )
}