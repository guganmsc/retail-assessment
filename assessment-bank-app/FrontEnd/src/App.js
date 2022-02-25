import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch, Redirect} from 'react-router-dom'
import TopupComponent from './components/TopupComponent';
import TransferComponent from './components/TransferComponent';
import LoginComponent from './components/LoginComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import TransactionComponent from './components/TransactionComponent';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                    <Route exact path="/"><LoginComponent /></Route>
                    <Route path="/home"><Redirect to="/" /></Route>
                    <Route path = "/login"><LoginComponent/></Route>
                    <Route path = "/transfer"><TransferComponent/></Route>
                    <Route path = "/topup" ><TopupComponent/></Route>
                    <Route path = "/transact" render={(props) => <TransactionComponent {...props}/>}></Route>
                
                    </Switch>
                </div>
                <div></div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;