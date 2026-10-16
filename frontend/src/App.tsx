import './App.css'
import Button from "./components/Button";

function App() {
  return (
    <div className="min-h-screen bg-surface p-8 font-sans text-ink">
      <h1 className="text-4xl font-bold text-primary">ReScore</h1>
      <div className="mt-4 flex flex-wrap gap-3">
        <Button>Primary</Button>
      </div>
    </div>
  );
}

export default App;
