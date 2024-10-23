import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import EnterAccount from './components/enterAccount';
import CreateAccount from './components/createAccount';
import LoginAccount from './components/loginAccount';
import Home from './components/home';

function App() {
  return (
    <Router>
      <div className='container'>
        <Routes>
          <Route path='/' element={<EnterAccount/>} />
          <Route path='/create' element={<CreateAccount/>} />
          <Route path='/login' element={<LoginAccount/>} />
          <Route path='/home' element={<Home/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App
