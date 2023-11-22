import {Form, Input, Checkbox, Button, message, Layout, Menu} from "antd"
import {LockOutlined, UserOutlined, MobileOutlined, RightOutlined} from '@ant-design/icons'
import {useStore} from "../../store"
import style from "./index.module.css"
// import "./index.module.css"
import {makeStyle} from "../../utils/CSSUtils";
import {useNavigate, useLocation, Link} from "react-router-dom"
import {useState, useEffect} from "react"
import {getToken, http} from "../../utils";
import {ip} from "../../constIp";

const {Header, Content, Footer} = Layout
const s = makeStyle(style)

function Register() {
    {
        const {pathname} = useLocation()
        const [form] = Form.useForm()
        const navigate = useNavigate()
        const {registerStore} = useStore()

        const token = getToken()

        const [canUse, setCanUse] = useState(true)
        const [password, setPassword] = useState("")
        const [confirmPassword, setConfirmPassword] = useState("")
        const [passwordMismatch, setPasswordMismatch] = useState(false)

        function handleConfirmPasswordChange(event) {
            const value = event.target.value
            setConfirmPassword(value)
            setPasswordMismatch(value !== password)
            setCanUse(true)
        }

        function handleRegisterClick() {
            if (passwordMismatch) {
                alert("两次输入的密码不一致！")
                setCanUse(false)
                setTimeout(() => {
                    setCanUse(true)
                }, 100) // 100毫秒后执行setCanUse(true),这样就使得按钮失灵后立马变回原来的样子
                return
            }

        }

        async function onFinish(values) {
            console.log(values, registerStore)
            // values：放置的是所有表单项中用户输入的内容
            const {username, password} = values
            const res = await http.post(`http://${ip}:8002/users/register`, {username, password})

            console.log(res)
            if (res.data.code == 200) {
                navigate('/login', {replace: true})
                message.success('注册成功')
            } else {
                message.error(res.data.msg)
            }
            // try {
            // } catch (e) {
            //     console.log(e)
            //     message.success('注册失败')
            //
            // }
            // 跳转首页
        }

        return (

            <Content className={s("register-box")}>
                <div className={s("back-img")}>
                    <img src={require("../../assert/login-photo.png")}/>
                    <div className={s("cover-div")}></div>
                </div>

                <div className={s("register-content")}>
                    <div className={s("register-logo")}>
                        <img className={s("white-logo")} src={require("../../assert/white-logo.png")}/>
                        <div className={s("text1")} style={{textAlign: 'right'}}>

                            Having the situation well in hand
                        </div>
                    </div>
                    <Form
                        name="normal_register"//需要修改
                        className={s("register-form")}
                        form={form}
                        validateTrigger={['onBlur', 'onChange']}
                        onFinish={onFinish}
                    >
                        <Form.Item
                            name="username"
                            rules={[{required: true, message: 'Please input your username!'}]}
                        >
                            {/* suffix */}
                            <Input
                                suffix={<UserOutlined className="site-form-item-icon"/>}
                                placeholder={"用户名Username"}
                                className={s("register-input")}
                                size={"large"}
                                bordered={false}
                                style={{
                                    border: 'none',
                                    borderBottom: '1px solid #f0f0f0',
                                    borderRadius: 0,
                                    color: 'white',
                                    placeholder: {
                                        color: 'white',
                                        filter: 'brightness(200%) saturate(100%) hue-rotate(0deg)',
                                    }
                                }}
                                onChange={(event) => {

                                    setCanUse(true)

                                }}
                            />
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[{required: true, message: 'Please input your password!'}]}
                        >

                            <Input
                                suffix={<LockOutlined className="site-form-item-icon"/>}
                                className={s("register-input")}
                                placeholder={"密码Password"}
                                bordered={false}
                                style={{
                                    border: 'none',
                                    borderBottom: '1px solid #f0f0f0',
                                    borderRadius: 0,
                                    color: 'white'
                                }}
                                placeholderStyle={{color: '#fff'}}
                                size={"large"}
                                // 再次修改部分
                                type="password"
                                value={password}
                                onChange={(event) => {
                                    setPassword(event.target.value)
                                    setPasswordMismatch(event.target.value !== password)
                                    setCanUse(true)

                                }}


                            />

                        </Form.Item>

                        <Form.Item

                            name="rePassword"
                            rules={[{required: true, message: 'Please confirm the password again!'}]}
                        >
                            <Input
                                suffix={<LockOutlined className="site-form-item-icon"/>}
                                className={s("register-input")}
                                size={"large"}
                                placeholder={"再次确认密码Password"} bordered={false} style={{
                                border: 'none',
                                borderBottom: '1px solid #f0f0f0',
                                borderRadius: 0,
                                color: 'white'
                            }}
                                //开始修改部分
                                type="password"
                                value={confirmPassword}
                                onChange={handleConfirmPasswordChange}

                            />

                        </Form.Item>
                        <Form.Item name="register-button" className={s("form-item")}>
                            <div style={{display: "flex", flexDirection: "column"}}>


                                <Button onClick={handleRegisterClick} htmlType="submit"
                                        className={s("register-form-button")} disabled={!canUse}
                                >
                                    立即注册
                                    {<RightOutlined/>}
                                </Button>


                                {passwordMismatch && (
                                    <div style={{color: "red"}}>两次输入的密码不一致！</div>
                                )}

                            </div>
                        </Form.Item>
                    </Form>
                </div>
            </Content>

        )
    }
}

export default Register