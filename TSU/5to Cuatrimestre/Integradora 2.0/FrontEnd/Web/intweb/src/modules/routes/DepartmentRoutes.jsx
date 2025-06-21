import { BrowserRouter, Route, Routes } from 'react-router-dom';

//
import AdminSidebar from '../../shared/components/AdminSidebar';
import Admin from '../department/pages/Admin.jsx';
import DepRep from '../department/pages/DepRep.jsx';
import ProfileDepartment from '../department/pages/ProfileDepartment.jsx';
import Responsable from '../department/pages/Responsable';
import Consulta from '../department/pages/Consulta';
import { Error } from '../../shared/plugins/Error';


const DepartmentRoutes = () => {
    return (
      <AdminSidebar>
        <Routes>
          <Route path="/" element={<ProfileDepartment/>} />
          <Route path="/profile" element={<ProfileDepartment/>} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/depRep" element={<DepRep />} />
          <Route path="/responsable" element={<Responsable />} />
          <Route path="/consulta" element={<Consulta />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </AdminSidebar>
    )
    
}

export default DepartmentRoutes;