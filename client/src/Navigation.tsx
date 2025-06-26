import {Link} from "react-router-dom";


export default function Navigation() {
    return(
        <header>
         <nav>
             <div>
                 <Link to={"/"}>Photo Storage</Link>
             </div>
             <div>
                 <Link to={"/"}>Home</Link>
                 <Link to={"/Login"}>Login</Link>
                 <Link to={"/logout"}>Sign Out</Link>
             </div>
         </nav>
        </header>
    )
}