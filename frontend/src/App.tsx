import './App.css'
import { BrowserRouter, Route, Routes } from "react-router";
import Header from "./components/Header";
import HomePage from "./pages/HomePage";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-surface font-sans text-ink">
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          {/* Unknown URL */}
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
