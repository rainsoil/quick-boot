import axios from "axios";

export function getSelectData() {
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/dict/sex");

}
