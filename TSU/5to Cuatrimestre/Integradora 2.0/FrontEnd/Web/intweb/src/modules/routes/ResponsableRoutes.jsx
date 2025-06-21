import { BrowserRouter, Route, Routes } from 'react-router-dom';

//
import RespSidebar from '../../shared/components/RespSidebar';
import Adviser  from '../responsable/Adviser';
import ProfileResp from '../responsable/ProfileResp';
import RespRep from'../responsable/RespRep';
import { Error } from '../../shared/plugins/Error';


const ResponsableRoutes = () => {
    return (
        <RespSidebar>
        <Routes>
          <Route path="/" element={<ProfileResp/>} />
          <Route path="/profileResp" element={<ProfileResp/>} />
          <Route path="/asesor" element={<Adviser />} />
          <Route path="/depRep" element={<RespRep />} />
          <Route path="/*" element={<Error/>} />
        </Routes>
      </RespSidebar>
    )
}

export default ResponsableRoutes;