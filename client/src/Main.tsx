import { StrictMode} from "react"
import {createRoot} from "react-dom/client"
// @ts-ignore
import "./styles.css"
import App from "./App.tsx"

const root = createRoot(document.getElementById("root") as HTMLElement);
root.render(
    <StrictMode>
        <div className={"container w-full"}>
            <App />
        </div>

    </StrictMode>
)