import axios from 'axios';
import qs from 'query-string';

const BANK_API_BASE_URL = "http://localhost:8080";

class BankService{
    loginUser(account){
        return axios.post(BANK_API_BASE_URL + '/login',account);
    }

    topupDetails(amount){
        return axios.post(BANK_API_BASE_URL + '/topup/' + amount);
    }

    transferDetails(account,amount){
        //const params = qs.parseUrl('?amount='+amount+'&accountName='+ account);
        return axios.post(BANK_API_BASE_URL + '/pay/?amount='+amount+'&accountName='+ account);
    }
}

export default new BankService();
