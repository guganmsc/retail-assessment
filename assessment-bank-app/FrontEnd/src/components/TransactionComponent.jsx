import React, { Component } from 'react'
import { Link, useLocation } from "react-router-dom";
import TopupComponent from './TopupComponent';
import TransferComponent from './TransferComponent';

class TransactionComponent extends Component {
    constructor(props) {
        super(props)

        let accountData = this.props.location.state.account;

        
       
        //this.setState({ isShowTransMsg: true });
        let transferAmount =  accountData.transactions != null &&  accountData.transactions.length > 0 ?  accountData.transactions[accountData.transactions.length - 1].transferAmount : 0;
        this.state = {isShowOweMsg: false, transferAmt:transferAmount, isShowOweMsg: accountData.debts && accountData.debts.length > 0}
        this.state = {accountInfo:{accountData}};
     
    }

    render() {
        return (
            <div>
                <div><h1>Hello {this.props.location.state.account.accountName}!</h1>
                        {this.props.location.state.account.messageDetails}

                        {this.state.isShowOweMsg ? <h4> Transfered Amount : {this.state.transferAmt} <br></br>
                    Owing {this.state.account.debts && this.state.account.debts[0].debtAmount} to {this.state.account.debts && this.state.account.debts[0].oweToAccountName} </h4> : null}

                </div>
                <div className="mt-3">
                    {/* <Link to="/topup" className="text-secondary">Topup</Link> */}
                    
                </div>
                <div className="mt-3">
                    {/* <Link to="/transfer" className="text-secondary">Transfer</Link> */}
                   
                </div>
            </div>
        )
    }
}

export default TransactionComponent;