import { useContext } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Container } from 'react-bootstrap';
import { AuthContext } from '../../modules/auth/authContext';
import { LoginScreen } from '../../modules/auth/LoginScreen';
import { PublicNavbar } from '../components/PublicNavbar'
import AdminNavbar from './AdminNavbar';
import { CategoryScreen } from '../../modules/category/CategoryScreen';
import { SubcategoryScreen } from '../../modules/subcategory/SubcategoryScreen';
import { ProductScreen } from '../../modules/product/ProductScreen';

export const AppRouter = () => {
  const { user } = useContext(AuthContext);
  return (
    <Router>
      <Routes>
        <Route path="/auth" element={<LoginScreen />} />
        <Route
          path="/*"
          element={
            user.isLogged ? (
              <>
                <AdminNavbar/>
                <Container style={{ marginTop: '20px' }}>
                  <Routes>
                    <Route path="products" element={<ProductScreen/>} />
                    <Route path="category" element={<CategoryScreen/>} />
                    <Route path="subcategory" element={<SubcategoryScreen/>} />
                    <Route index element={<>INDEX</>} />
                    <Route path="*" element={<>404</>} />
                  </Routes>
                </Container>
              </>
            ) : (
              <>
                <PublicNavbar/>
                <Container style={{ marginTop: '20px' }}>
                  <Routes>
                    <Route path="more-info/:id" element={<>MOREINFO</>} />
                    <Route path="contact" element={<>Contact</>} />
                    <Route index element={<>INDEX</>} />
                    <Route path="*" element={<>404</>} />
                  </Routes>
                </Container>
              </>
            )
          }
        />
        <Route path="*" element={<>404</>} />
      </Routes>
    </Router>
  );
};
