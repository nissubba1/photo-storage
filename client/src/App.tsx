import SignIn from "./pages/SignIn.tsx"
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Profile from "./pages/Profile.tsx"
export default function App() {
    return (
        <Router>
            <main className={"container w-full flex flex-row justify-center items-center"}>
                <Routes>
                    <Route path={"/"} element={<SignIn />} />
                    <Route path={"/user/login/profile/:username"} element={<Profile />} />
                </Routes>
            </main>
        </Router>
    )
}