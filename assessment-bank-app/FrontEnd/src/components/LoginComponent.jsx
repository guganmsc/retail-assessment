import React, { Component } from 'react';
import BankService from '../services/BankService';


import TransactionComponent from './TransactionComponent';
import { Redirect } from 'react-router-dom';

class LoginComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {accountName:'',account:{}, isShowWelcome:false}
     
        this.handleSubmit=this.handleSubmit.bind(this);
        this.handleChange=this.handleChange.bind(this);
    }
    renderRedirect = () => {
        if (this.state.isShowWelcome) {
          return <Redirect to={{
            pathname: '/transact',
            state: { account: this.state.account }
        }}  />
        }
      }
    handleSubmit(event){
        event.preventDefault();
        const account = {
            accountName:this.state.accountName,
            message: this.state.messageDetails
        };

        BankService.loginUser(account).then((res) => {
            this.setState({ account: res.data});
            this.setState({ isShowWelcome: true});
            localStorage.setItem('username', account.accountName);
           console.log("data" + res.data.messageDetails);
        });
    }

    handleChange(event){
        this.setState({accountName:event.target.value});    
    }

      render() {
        return (
            <form className="container" onSubmit={this.handleSubmit}>
                <fieldset>
                    <div className="card">
                        <div className="mt-3">
                            <div className="d-grid">
                                <label>
                                    <p>
                                        Enter your UserID 
                                    </p>
                                    <input name="accountName" value={this.state.accountName} onChange={this.handleChange}/>
                                </label>
                            </div>
                        </div>
                        <div className="mt-3">
                            <div className="d-grid">
                                <button className="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </div>
                    {this.renderRedirect()}
                </fieldset>
            </form>
        )
    }
}

export default LoginComponent;

