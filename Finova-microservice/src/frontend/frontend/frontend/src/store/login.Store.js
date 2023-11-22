// login module
import {makeAutoObservable} from 'mobx'
import {http, setToken, getToken, removeToken} from '../utils'
import {ip} from "../../src/constIp"

class LoginStore {
    token = getToken() || ''

    constructor() {
        // 响应式
        makeAutoObservable(this)
    }

    getToken = async ({username, password}, loginWay, {phoneNumber, verifyCode}) => {
        // 调用登录接口
        // const res = await http.post(`http://${ip}:8002/users/${loginWay ? 'login' : 'sms/login'}`, loginWay ? {
        const res = await http.post(`http://${ip}:8002/users/${loginWay ? 'login' : 'sms/login'}`, loginWay ? {
            username, password
        } : {phoneNumber, verifyCode})
        // 存入token
        this.token = res.data.data
        // 存入ls
        setToken(this.token)
        return res
    }
    // 退出登录
    loginOut = () => {
        this.token = ''
        removeToken()
    }
}

export default LoginStore