import style from "./index.module.css";
import {makeStyle} from "../../utils/CSSUtils";
import {Layout, Menu} from "antd";
import {Link, useLocation, Outlet} from "react-router-dom";
import {getToken} from "../../utils";


const {Header, Content, Footer} = Layout;
const s = makeStyle(style)


function HeadBar(){
    const {pathname} = useLocation()
    const token = getToken()


    return(
        <Layout>
            <Header className={s("header-box")}>
                <div className={s("header-content")}>
                    <img style={{height:"6vmin"}} src={require("../.././assert/black-logo.png")}/>
                    <div className={s("right")}>
                        <Menu mode="horizontal"
                              theme={"light"}
                              defaultSelectedKeys={pathname}
                              selectedKeys={pathname}
                              style={{
                                  // height: '100%',
                                  width: "48vw",
                                  borderBottom: 0,
                                  backgroundColor: 'transparent',
                                  display: "flex",
                                  justifyContent: "flex-end"
                              }}
                            // inlineCollapsed={true}
                        >
                            <Menu.Item key="/home">
                                {/*<div>首页</div>*/}
                                <Link to='/' className={s("menu-item")}>首页</Link>
                            </Menu.Item>
                            {
                                token &&
                                <>
                                    <Menu.Item key="/quantify">
                                        <Link to='/quantify' className={s("menu-item")}>量化交易</Link>
                                    </Menu.Item>
                                    <Menu.Item key="/extraction">
                                        <Link to='/extraction' className={s("menu-item")}>信息抽取</Link>
                                    </Menu.Item>
                                </>
                            }
                            <Menu.Item key="/introduce">
                                <Link to='/introduce' className={s("menu-item")}>平台介绍</Link>
                            </Menu.Item>
                            <Menu.Item key="/about">
                                <Link to='/about' className={s("menu-item")}>关于我们</Link>
                            </Menu.Item>
                        </Menu>
                        <div className={s("login-button-box")}>
                            {
                                !token
                                    ?
                                    <Link to={'/login'} className={s("login-button")}>登录</Link>
                                    :
                                    <div className={s("avar-box")}>
                                        <img src={require("../../assert/avar.png")}/>
                                    </div>
                            }
                        </div>
                    </div>
                </div>
            </Header>
            <Content>
                <Outlet/>
            </Content>
        </Layout>
    )
}

export default HeadBar