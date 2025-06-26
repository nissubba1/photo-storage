import {type NavigateFunction, useNavigate} from "react-router-dom";
import * as React from "react";
import {useState} from "react";
import type {LoginRequest} from "../types/types.ts";

export default function SignIn() {
    const [formData, setFormData] = useState<LoginRequest>({
        username: "",
        password: ""
    });
    const navigate: NavigateFunction = useNavigate();
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [event.target.name]: event.target.value,
        })
    }

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        try {
            console.log(formData);
            const response: Response = await fetch("http://localhost:8080/api/user/login", {
                credentials: "include",
                method: "POST",
                headers: {
                    "Content-Type": "Application/json",
                },
                body: JSON.stringify(formData)
            })

            if (!response.ok) {
                throw new Error("Login Failed");
            }

            const data = await response.json();
            console.log(data);
            navigate(`/user/login/profile/username?username=${encodeURIComponent(data.username)}`);

        } catch (error) {
            console.log(error)
        }

    }
    return (
        <section className={"p-10 rounded-md bg-white"}>
            <form action=""
                  className={"flex flex-col justify-center items-center gap-10 text-black"}
                  method={"POST"}
                  onSubmit={handleSubmit}
            >
                <fieldset className={"flex flex-col gap-3"}>
                    <div className={"flex flex-col"}>
                        <label htmlFor="username">Username</label>
                        <input name={"username"} type="text" id={"username"} className={"border rounded-sm"}
                               value={formData.username}
                               onChange={handleChange}
                        required/>
                    </div>
                    <div className={"flex flex-col"}>
                        <label htmlFor="password">Password</label>
                        <input name={"password"} type="password" id={"password"} className={"border rounded-sm"}
                               value={formData.password}
                               onChange={handleChange}
                        required/>
                    </div>
                </fieldset>
                <button className={"border rounded-md w-full pt-1.5 pb-1.5 hover:bg-amber-600 cursor-pointer"}>
                    Login
                </button>
            </form>
        </section>
    )
}