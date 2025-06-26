import SignIn from "./pages/SignIn.tsx"
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Profile from "./pages/Profile.tsx"
import Navigation from "./Navigation.tsx";
import Home from "./pages/Home.tsx";
import SignUp from "./pages/SignUp.tsx";
export default function App() {
    return (
        <Router>
            <Navigation />
            <main className={"container w-full flex flex-row justify-center items-center pt-10"}>
                <Routes>
                    <Route path={"/"} element={<Home />} />
                    <Route path={"/signin"} element={<SignIn />} />
                    <Route path={"/signup"} element={<SignUp />} />
                    <Route path={"/user/login/profile/:username"} element={<Profile />} />
                </Routes>
            </main>
        </Router>
    )
}