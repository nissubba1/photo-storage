import {Link} from "react-router-dom";


export default function Navigation() {
    return(
        <header className={"w-full"}>
         <nav className={"flex flex-row justify-between items-center"}>
             <div className={"text-3xl font-bold text-indigo-600 underline"}>
                 <Link to={"/"}>Photo Storage</Link>
             </div>
             <div className={"flex flex-row justify-center gap-10"}>
                 <Link to={"/"}>Home</Link>
                 <Link to={"/signin"}>Sign In</Link>
                 <Link to={"/signup"}>Sign Up</Link>
             </div>
         </nav>
        </header>
    )
}