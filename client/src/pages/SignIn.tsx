export default function SignIn() {
    return (
        <section className={"p-10 rounded-md bg-white"}>
            <form action=""
                  className={"flex flex-col justify-center items-center gap-10"}
            >
                <fieldset className={"flex flex-col gap-3"}>
                    <div className={"flex flex-col"}>
                        <label htmlFor="username">Username</label>
                        <input type="text" id={"username"} className={"border rounded-sm"}/>
                    </div>
                    <div className={"flex flex-col"}>
                        <label htmlFor="password">Password</label>
                        <input type="password" id={"password"} className={"border rounded-sm"}/>
                    </div>
                </fieldset>
                <button className={"border rounded-md w-full pt-1.5 pb-1.5 hover:bg-amber-600 cursor-pointer"}>
                    Login
                </button>
            </form>
        </section>
    )
}