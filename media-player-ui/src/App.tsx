import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import QaDashboard from './pages/Qa/QaDashboard';

import TokenManager from './components/tokens/TokenManager'
import UserTable from "./components/Users/UserTable";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/" element={<TokenManager />} />
              <Route path="/qa-dashboard" element={<QaDashboard />} />
              <Route path="/admin/user" element={<UserTable />}/>
          </Routes>
      </Router>
  );
}

export default App;
