import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import EnterAccount from './components/enterAccount';
import CreateAccount from './components/createAccount';

function App() {
  return (
    <Router>
      <div className='container'>
        <Routes>
          <Route path='/' element={<EnterAccount/>} />
          <Route path='/create' element={<CreateAccount/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App
