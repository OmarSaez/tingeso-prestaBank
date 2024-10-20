import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Test from './components/Test'

function App() {
  return (
    <Router>
      <div className='container'>
        <Routes>
          <Route path='/' element={<Test/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App
