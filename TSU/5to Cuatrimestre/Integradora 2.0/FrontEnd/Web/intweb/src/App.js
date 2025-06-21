import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './modules/auth/Login';

//
import AdminSidebar from './shared/components/AdminSidebar';
import Admin from './modules/department/pages/Admin.jsx';
import DepRep from './modules/department/pages/DepRep.jsx';
import ProfileDepartment from './modules/department/pages/ProfileDepartment.jsx';
import Responsable from './modules/department/pages/Responsable';
import Consulta from './modules/department/pages/Consulta';
///
import StudentSidebar from './shared/components/StudentSidebar';
import ProfileStudent from './modules/student/ProfileStudent';
import Reporte from './modules/student/Reporte';

///Responsable
import RespSidebar from './shared/components/RespSidebar';
import Adviser  from './modules/responsable/Adviser';
import ProfileResp from './modules/responsable/ProfileResp';
import RespRep from'./modules/responsable/RespRep';


//Asesor
import AdviserSidebar from './shared/components/AdviserSidebar';
import AdvRep from './modules/adviser/AdvRep.jsx'
import ProfileAdviser from './modules/adviser/ProfileAdviser'
import Student from './modules/adviser/Student'
import DepartmentRoutes from './modules/routes/DepartmentRoutes';
import { Error } from './shared/plugins/Error';
import Loading from './shared/plugins/Loading';

import 'bootstrap/dist/css/bootstrap.min.css';


const App = () => {
  return (

    <Routes>
      <Route path='/login' element={<Login/>}/>
      <Route path='/adminDashboard' element={<DepartmentRoutes/>}/>
    </Routes>
    /*<BrowserRouter>
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
    </BrowserRouter>*/
    /*
    <BrowserRouter>
      <StudentSidebar>
        <Routes>
          <Route path="/" element={<ProfileStudent/>} />
          <Route path="/profileStudent" element={<ProfileStudent/>} />
          <Route path="/reporte" element={<Reporte />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </StudentSidebar>
    </BrowserRouter>*/

    /*<BrowserRouter>
      <RespSidebar>
        <Routes>
          <Route path="/" element={<ProfileResp/>} />
          <Route path="/profileResp" element={<ProfileResp/>} />
          <Route path="/asesor" element={<Adviser />} />
          <Route path="/depRep" element={<RespRep />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </RespSidebar>
    </BrowserRouter>
    
    <BrowserRouter>
      <AdviserSidebar>
        <Routes>
          <Route path="/" element={<ProfileAdviser/>} />
          <Route path="/profileResp" element={<ProfileAdviser/>} />
          <Route path="/student" element={<Student />} />
          <Route path="/depRep" element={<AdvRep />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </AdviserSidebar>
    </BrowserRouter>*/
  );
};

export default App;