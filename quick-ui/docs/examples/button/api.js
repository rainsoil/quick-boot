import axios from "axios";

export function apiClick(status) {
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/crud/click?status=" + status);

}
export function apiClick2(status) {
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/crud/click11?status=" + status);

}
