import { LoginScreen } from "../../modules/auth/LoginScreen";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import { Container } from "react-bootstrap";
import { PublicNavbar } from "./PublicNavbar";
import AppLayout from "./AppLayout";
import Users from "../../modules/super/Users";
import UserHome from "../../modules/user/UserHome";
import PlatformScreen from "../../modules/platform/PlatformScreen";
import { AuthContext } from "../../modules/auth/authContext";
import { useContext, useEffect } from "react";
import ProductScreen from "../../modules/product/ProductScreen";
import ItemScreen from "../../modules/item/ItemScreen";
import ProfileUser from "../../modules/profile/ProfileUser";
import CashierDashboard from "../../modules/cashier/CashierDashboard";
import UsersScreen from "../../modules/users/UsersScreen";
import LogScreen from "../../modules/log/LogScreen";

export const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path="/auth" element={<LoginScreen/>}/>
        <Route path="/*" element={<PrivateRoutes />} />
      </Routes>
    </Router>
  );
};

const PrivateRoutes = () => {
  const { user } = useContext(AuthContext);
  const { dispatch } = useContext(AuthContext);
  const navigation = useNavigate();

  useEffect(() => {
    if (!user.isLogged) {
      navigation("/auth", { replace: true });
    }
  }, [user, dispatch, navigation]);

  if (user.data.role === 1) {
    return (
      <>
        <Routes>
          <Route path="/" element={<AppLayout option={1} />}>
            <Route element={<Users option={true} />} />
            <Route path="/plataforma" element={<PlatformScreen />} />
            <Route path="/producto" element={<ProductScreen />} />
            <Route path="/item" element={<ItemScreen />} />
            <Route path="/perfil" element={<ProfileUser/>} />
            <Route path="/usuarios" element={<UsersScreen/>}/>
            <Route path="/log" element={<LogScreen/>}/>
            <Route index element={<PlatformScreen />} />
          </Route>
        </Routes>
      </>
    )
  }
  if (user.data.role === 2) {
    return (
      <>
        <Routes>
          <Route path="/" element={<AppLayout option={2} />}>
            <Route element={<Users option={false} />} />
            <Route index element={<CashierDashboard/>}/>
            <Route path="*" element={<>SUPER</>} />
          </Route>
        </Routes>
      </>

    )
  }
  if (user.data.role === 3) {
    return (
      <>
        <Routes>
          <Route path="/" element={<AppLayout option={3} />}>
            <Route index element={<UserHome />} />
            <Route path="/perfil" element={<ProfileUser/>} />
            <Route path="*" element={<>USER</>} />
          </Route>
        </Routes>
      </>

    )
  }

}