import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/loan/');
}

const create = data => {
    return httpClient.post("/api/loan/", data);
}

const get = id =>  {
    return httpClient.get(`/api/loan/${id}`);
}

const update = data => {
    return httpClient.put('/api/loan/', data);
}

const remove = id => {
    return httpClient.delete(`/api/loan/${id}`);
}

const status = status => {
    return httpClient.get(`/api/loan/status/${status}`);
}

const type = type => {
    return httpClient.get(`/api/loan/type/${type}`);
}


const updateExecutive = (data, years, balance) =>{
    return httpClient.put('/api/loan/executive', data, {params:{years,balance}});
}

const simulateLoan = (loanData) => {
    return httpClient.post("/api/loan/simulate", loanData);
}


export default { getAll, create, get, update, remove, status, type, updateExecutive, simulateLoan };
