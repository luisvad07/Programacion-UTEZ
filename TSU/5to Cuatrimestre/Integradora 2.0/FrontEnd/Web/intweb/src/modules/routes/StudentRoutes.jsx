import React from 'react'
import ProfileStudent from '../student/ProfileStudent';
import Reporte from '../student/Reporte';
import { Error } from '../../shared/plugins/Error';
import StudentSidebar from '../../shared/components/StudentSidebar';
import { Route, Routes } from 'react-router-dom';

export const StudentRoutes = () => {
  return (
    <StudentSidebar>
        <Routes>
          <Route path="/" element={<ProfileStudent/>} />
          <Route path="/profile" element={<ProfileStudent/>} />
          <Route path="/reporte" element={<Reporte />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </StudentSidebar>
  )
}

export default StudentRoutes;