import {Form, Input, Checkbox, Button, message, Layout, Menu} from "antd";
import {LockOutlined, UserOutlined, MobileOutlined} from '@ant-design/icons';
import {useStore} from "../../store";
import style from "./index.module.css";
// import "./index.module.css"
import {getToken} from "../../utils";

import {ip} from "../../constIp"

import {makeStyle} from "../../utils/CSSUtils";


// import "../.././assert/black-logo.png";
import {http} from "../../utils";
import {useNavigate, useLocation, Link} from "react-router-dom";
import {useState, useEffect} from "react";

const {Header, Content, Footer} = Layout;
const s = makeStyle(style)

function Login() {
    const {loginStore} = useStore()
    const navigate = useNavigate()
    const {pathname} = useLocation()
    const [loginWay, setLoginWay] = useState(true)//true代表用户名密码登录，false代表手机验证码登录
    const [phoneNum, setPhoneNum] = useState("")
    const [countdown, setCountdown] = useState(0)
    const [countdownEndTime, setCountdownEndTime] = useState(0)
    const token = getToken()

    const [form] = Form.useForm();

    async function onFinish(values) {
        console.log(values, loginStore)
        // values：放置的是所有表单项中用户输入的内容
        // todo:登录
        const {username, password} = values
        const {phoneNumber, verifyCode} = values

        const res = await loginStore.getToken({username, password}, loginWay, {phoneNumber, verifyCode})
        console.log(1111,res)
        if (res.data.code == 200) {
            // 跳转首页
            navigate('/', {replace: true})
            // 提示用户
            message.success('登录成功')
        } else {
            message.error(res.data.msg)
        }
    }

    async function sendSms(phoneNumber) {
        const res = await http.post(`http://${ip}:8002/users/sms/send`, {
            phoneNumber
        })
        // 发送成功后设置倒计时和倒计时结束时间
        setCountdown(30)
        setCountdownEndTime(Date.now() + 30000)
    }

    useEffect(() => {
        if (countdown > 0) {
            const timerId = setInterval(() => {
                const remainingTime = countdownEndTime - Date.now()
                if (remainingTime > 0) {
                    setCountdown(Math.ceil(remainingTime / 1000))
                } else {
                    clearInterval(timerId)
                    setCountdown(0)
                }
            }, 1000)
            return () => clearInterval(timerId)
        }
    }, [countdown, countdownEndTime])


    return (
        <Content className={s("middle-box")}>
            <div className={s("middle-content")}>
                <div className={s("left-box")}>
                    <div className={s("back-img")}>
                        <img src={require("../.././assert/login-photo.png")}/>
                        <div className={s("cover-div")}></div>
                    </div>
                    <div className={s("text-box")}>
                        <div className={s("text1")}>欢迎来到</div>
                        <img src={require("../../assert/white-logo.png")}/>
                        <div className={s("text2")}>运筹帷幄之中，决胜千里之外。</div>
                    </div>
                </div>
                <div className={s("login-box")}>
                    <div className={s("login-logo")}>Log in</div>
                    <Form
                        name="normal_login"
                        className={s("login-form")}
                        // initialValues={{}}
                        onFinish={onFinish}
                        validateTrigger={['onBlur', 'onChange']}
                        form={form}
                    >
                        <Form.Item
                            name={loginWay ? "username" : "phoneNumber"}
                            rules={loginWay ?
                                [{required: true, message: "请输入用户名"},] :
                                [
                                    {
                                        required: true,
                                        message: "请输入手机号"
                                    },
                                    {
                                        pattern: /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/,
                                        message: '请输入正确的手机号',
                                        validateTrigger: 'onBlur'
                                    }
                                ]
                            }
                            className={s("form-item")}
                        >
                            {loginWay ?
                                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="用户名"
                                       size={"large"}/> :
                                <Input prefix={<MobileOutlined className="site-form-item-icon"/>} placeholder="手机号"
                                       size={"large"}
                                       onChange={(e) => {
                                           if (/^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/.test(e.target.value)) {
                                               setPhoneNum(e.target.value);
                                           }
                                       }}/>}
                        </Form.Item>

                        <Form.Item
                            name={loginWay ? "password" : "verifyCode"}
                            rules={loginWay ? [
                                    {
                                        required: true,
                                        message: '请输入密码',
                                    }
                                ] :
                                [
                                    {
                                        required: true,
                                        message: '请输入验证码',
                                    }
                                ]
                            }
                            className={s("form-item")}
                        >
                            {
                                loginWay ?
                                    <Input.Password
                                        prefix={<LockOutlined className="site-form-item-icon"/>}
                                        type="password"
                                        placeholder={"密码"}
                                        size={"large"}
                                    /> :
                                    <Input
                                        prefix={<LockOutlined className="site-form-item-icon"/>}
                                        placeholder={"验证码"}
                                        size={"large"}
                                        suffix={countdown > 0 ? `${countdown}秒后重新发送` : (
                                            <div onClick={() => {
                                                console.log(phoneNum)
                                                sendSms(phoneNum)
                                            }} style={{fontSize: "10px", cursor: "pointer"}}>
                                                发送验证码
                                            </div>
                                        )}
                                    />
                            }
                        </Form.Item>
                        <Form.Item name="remember" valuePropName="checked" className={s("form-item")}>
                            <div style={{display: "flex", flexDirection: "row", justifyContent: "space-between"}}>
                                <div><Checkbox>记住密码</Checkbox></div>
                                {/*后面这里得改成三目运算符判断用什么方式登录*/}
                                <div className={s("login-way")} onClick={() => {
                                    let flag = loginWay ? false : true
                                    form.resetFields(); // 清空表单
                                    setLoginWay(flag)
                                    setPhoneNum("")
                                }}>{loginWay ? "手机登录" : "用户名登陆"}</div>
                            </div>
                        </Form.Item>

                        <Form.Item name="login-button" className={s("form-item")}>
                            <div style={{display: "flex", flexDirection: "column"}}>
                                <Button htmlType="submit" className={s("login-form-button")}>
                                    登录 >
                                </Button>
                                {/*<a href="">未注册？立即注册!</a>*/}
                            </div>
                        </Form.Item>
                    </Form>
                    <div className={s("register-box")}>还没有自己的账户？<Link to="/register"
                                                                      className={s("register-button")}>立即注册</Link>
                    </div>
                </div>
            </div>
        </Content>
    )
}

export default Login
