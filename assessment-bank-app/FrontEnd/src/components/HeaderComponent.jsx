import React, { Component } from 'react'
import { Link } from "react-router-dom";

class HeaderComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
                 
        }
    }

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div><a href="https://www.ocbc.com/group/gateway.page" className="navbar-brand">Retail Bank Web App </a></div>
                    <div className="mt-3 ml-5">
                        <Link to="/topup" className="text-primary">Topup</Link>
                    </div>
                    <div className="mt-3 ml-5">
                        <Link to="/transfer" className="text-primary">Transfer</Link>
                    </div>
                    </nav>
                </header>
            </div>
        )
    }
}

export default HeaderComponent;