import React, { Component } from 'react';
import BankService from '../services/BankService';
import TransactionComponent from './TransactionComponent';
import { Link } from 'react-router-dom';

class TopupComponent extends Component {
    
    constructor(props) {
        super(props)
        let username = "";

        if (localStorage.getItem('username')) {
            username = localStorage.getItem('username');
          } 

        this.state = {topupAmt:'',account:{}, accName:username, isShowOweMsg: false,  transAmt:0}
        this.handleSubmit=this.handleSubmit.bind(this);
        this.handleChange=this.handleChange.bind(this);
    }


handleSubmit(event){
    event.preventDefault();
    const topupAmt=this.state.topupAmt;
    BankService.topupDetails(topupAmt).then((res) => {
        this.setState({ account: res.data});
        this.setState({ isShowOweMsg: res.data.debts && res.data.debts.length > 0 });
        //this.setState({ isShowTransMsg: true });
        let transferAmount =  res.data.transactions != null &&  res.data.transactions.length > 0 ?  res.data.transactions[ res.data.transactions.length - 1].transferAmount : 0;
        this.setState({transferAmt : transferAmount});
    });
}
handleChange(event){
    this.setState({topupAmt:event.target.value});    
}

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                <fieldset>
                    <div className="md-3">
                      <h4>  Hello {this.state.accName}!</h4>
                    </div>
                    <label>
                        <p>
                            Enter the amunt to Topup
                        </p>
                        <input name="topupAmt" value={this.state.topupAmt} onChange={this.handleChange}/>
                    </label>
                </fieldset>
                <button type="Submit">Submit</button>
                <div className="mt-3">
                    {this.state.account.messageDetails}
                    {this.state.isShowOweMsg ? <h4>Transfered {this.state.transferAmt} to {this.state.accountName}.
                    Owing {this.state.account.debts[0].debtAmount} to {this.state.account.debts[0].oweToAccountName} </h4> : null}
                </div>
            </form>
        )
    }
}
export default TopupComponent;