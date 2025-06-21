import { BrowserRouter, Route, Routes } from 'react-router-dom';

//
import AdviserSidebar from '../../shared/components/AdviserSidebar';
import AdvRep from '../adviser/AdvRep.jsx'
import ProfileAdviser from '../adviser/ProfileAdviser'
import Student from '../adviser/Student'
import { Error } from '../../shared/plugins/Error';

const AsesorRoutes = () => {
    return (
        <AdviserSidebar>
        <Routes>
          <Route path="/" element={<ProfileAdviser/>} />
          <Route path="/profile" element={<ProfileAdviser/>} />
          <Route path="/student" element={<Student />} />
          <Route path="/depRep" element={<AdvRep />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </AdviserSidebar>
    )
}

export default AsesorRoutes;