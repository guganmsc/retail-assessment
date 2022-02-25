import React, { Component } from 'react'
import BankService from '../services/BankService';
import TransactionComponent from './TransactionComponent';
import { Link } from 'react-router-dom';

class TransferComponent extends Component {
    constructor(props) {
        let username = '';
        super(props)
        if (localStorage.getItem('username')) {
            username = localStorage.getItem('username');
          } 

        this.state = {
            amountTransfer: '',
            accountName:'',
            loginName:username,
            account:{},
            isShowOweMsg: false,
            transAmt:0

        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.changeAccountName=this.changeAccountName.bind(this);
        this.changeAmtToTransfer=this.changeAmtToTransfer.bind(this);
    }


    handleSubmit(event){
        event.preventDefault();
        const amountToTransfer = this.state.amountTransfer;
        const account = {
            accountName:this.state.accountName
        };
        BankService.transferDetails(account.accountName,amountToTransfer).then((res) => {
            this.setState({ account: res.data});
            this.setState({ isShowOweMsg: res.data.debts && res.data.debts.length > 0 });
            this.setState({ isShowTransMsg: true });
            let transferAmount =  res.data.transactions != null &&  res.data.transactions.length > 0 ?  res.data.transactions[ res.data.transactions.length - 1].transferAmount : 0;
            this.setState({transferAmt : transferAmount});
            
        });
    }
    handleChange(event){
        this.setState({accountName:event.target.value});    
    }

    changeAccountName(event){
        this.setState({accountName: event.target.value});
    }
    changeAmtToTransfer(event){
        this.setState({amountTransfer: event.target.value});
    }
    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="mt-3">
                   <h4> Hello {this.state.loginName}!. </h4>
                </div>
                <fieldset>
                    <label>
                         <p>
                             Enter The Amount to be transferred
                        </p>
                         <input type="text" name="amountTransfer" value={this.state.amountTransfer}  onChange={this.changeAmtToTransfer}/>
                    </label>
                    <br></br>
                    <label>
                         <p>
                             Enter The Account Details
                        </p>
                         <input type="text" name="accountName" value={this.state.accountName} onChange={this.changeAccountName}/>
                    </label>
                </fieldset>
                <button type="Submit">Submit</button>
                <div className="mt-3">
                    <h4>{this.state.account.messageDetails}</h4>
                    {this.state.isShowTransMsg ? <h4>Transfered {this.state.transferAmt} to {this.state.accountName}.</h4>: null}
                    {this.state.isShowOweMsg ? <h4>Owing {this.state.account.debts[0].debtAmount} to {this.state.account.debts[0].oweToAccountName} </h4> : null}
                </div>
            </form>
        )
    }
}

export default TransferComponent;