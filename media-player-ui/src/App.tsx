import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import QaDashboard from './pages/Qa/QaDashboard';

import TokenManager from './components/tokens/TokenManager'
import UserTable from "./components/Users/UserTable";
import SeriesDashBoard from "./pages/ContentDashboard/SeriesDashBoard";
import PageHeader from "./components/util/PageHeader";
import MediaPlayer from "./components/media/MediaPlayer";

function App() {
  return (
      <Router>
          <PageHeader />
          <Routes>
              <Route path="/" element={<TokenManager />} />
              <Route path="/qa-dashboard" element={<QaDashboard />} />
              <Route path="/admin/user" element={<UserTable />}/>
              <Route path="/series" element={<SeriesDashBoard />}/>
              <Route path="/player/:seriesId" element={<MediaPlayer/>}/>
          </Routes>
      </Router>
  );
}

export default App;
