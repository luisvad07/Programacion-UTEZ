import { Outlet } from "react-router-dom";
import UserNavbar from "./UserNavbar";
import NavbarAdmin from "./NavbarAdmin";

const AppLayout = ({ option }) => {
    return <div style={{
        padding: '8vh 0px 0px 0px',
        overflow: "hidden",
        backgroundColor: "#e6e6e6"
    }}>
        {
            option == 1 ? (
                <>
                <NavbarAdmin/>
                <Outlet/>
                </>
            ) : (
                option == 2 ? (
                    <>
                    <UserNavbar />
                    <Outlet/></>
                ) : (
                    option == 3 ? (
                        <>
                            <UserNavbar />
                            <Outlet/>
                        </>
                    ) : (
                        option == 4 && (
                            <>
                            <Outlet/>
                        </>
                        )
                    )
                )
            )}
        
    </div>;
};

export default AppLayout;