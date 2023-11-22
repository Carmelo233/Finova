import "./index.module.css"
import {Layout, Menu} from "antd";
import {Link, useLocation} from "react-router-dom";
import {getToken} from "../../utils";
import style from "./index.module.css";
import {makeStyle} from "../../utils/CSSUtils";
const s = makeStyle(style)

const {Header, Content, Footer} = Layout;

function Home() {
    const {pathname} = useLocation()
    const token = getToken()

    return (
        <div className={s("content-box")}>
            <div className={s("first-box")}>
                <img className={s("back-img")} src={require("../../assert/homeEarth.png")}/>
                <div className={s("back-cover1")}></div>
                <div className={s("back-cover2")}></div>
                <div className={s("first-content")}>
                    <img className={s("logo")} src={require("../../assert/white-logo.png")}/>
                    <div className={s("signature-box")}>
                        <img className={s("signature")} src={require("../../assert/home-signature.png")}/>
                    </div>

                    <div className={s("text1")}>FINOVA是一款基于web的量化交易与信息抽取平台。</div>
                    <div className={s("text1")}>
                        该平台拥有双核心模块，分别是
                        <span className={s("text2")}>量化交易模块</span>
                        和
                        <span className={s("text2")}>信息抽取模块</span>
                    </div>

                    {
                        !token
                            ?
                            <Link className={s("button")} to={"/login"}>立即加入</Link>
                            :
                            <Link className={s("button")} to={"/introduce"}>平台介绍</Link>
                    }
                </div>
            </div>
            <div className={s("second-box")}>
                <div className={s("division-box")}>
                    <div className="division-line1"></div>
                    <div className={s("division-content")}>
                        <img src={require("../../assert/black-logo.png")} className={s("division-logo")}/>
                        <span className={s("division-text")}>功能</span>
                    </div>
                    <div className="division-line2"></div>

                </div>
                <div className={s("function-text-box")}>
                    <div className={s("back-cover")}>FUNCTION</div>
                    <div className={s("function-text-content")}>
                        <div className={s("function-text")}>FINOVA平台提供多种模块来分析及展示各种金融信息，实现交易、数据与分析结果的可视化。</div>
                        <div className={s("function-text")}>您可以在其中选择最适合您的模型，或是上传您的文件，我们的平台将为您提供量化与分析服务。</div>
                    </div>
                </div>
                <div className={s("four-function-box")}>
                    <img className={s("back-cover")} src={require("../../assert/black-logo.png")}/>
                    <div className={s("four-function-content-box")}>
                        <div className={s("four-function-content")}>
                            <img className={s("function-logo")} src={require("../../assert/code.png")} style={{
                                height: "8vh", width: "9vh"
                            }}/>
                            <div className={s("function-title")}>算法</div>
                            <div className={s("function-text")}>多种可供选择的算法</div>
                        </div>
                        <div className={s("four-function-content")}>
                            <img className={s("function-logo")} src={require("../../assert/chart.png")} style={{
                                height: "8vh", width: "8vh"
                            }}/>
                            <div className={s("function-title")}>量化</div>
                            <div className={s("function-text")}>交易数据可视化</div>
                        </div>
                        <div className={s("four-function-content")}>
                            <img className={s("function-logo")} src={require("../../assert/save.png")} style={{
                                height: "8vh", width: "7vh"
                            }}/>
                            <div className={s("function-title")}>标记</div>
                            <div className={s("function-text")}>收藏每一个关键信息</div>
                        </div>
                        <div className={s("four-function-content")}>
                            <img className={s("function-logo")} src={require("../../assert/key.png")} style={{
                                height: "8vh", width: "11vh"
                            }}/>
                            <div className={s("function-title")}>数据</div>
                            <div className={s("function-text")}>上传文件，提取信息</div>
                        </div>
                    </div>


                </div>
            </div>
            <div className={s("third-box")}>
                <div className={s("division-box")}>
                    <div className="division-line1"></div>
                    <div className={s("division-content")}>
                        <img src={require("../../assert/black-logo.png")} className={s("division-logo")}/>
                        <span className={s("division-text")}>优势</span>
                    </div>
                    <div className="division-line2"></div>

                </div>
                <div className={s("top")}>
                    <div className={s("back-cover")}>WHY</div>
                    <div className={s("text-box")}>
                        <div className={s("text1")}>为何选择</div>
                        <div className={s("text2")}>FINOVA?</div>
                    </div>
                </div>
                <div className={s("bottom")}>
                    <div className={s("left")}>
                        FINOVA平台提供多种模块来分析及展示各种金融信息，实现交易、数据与分析结果的可视化。您可以在其中选择最适合您的模型，或是上传您的文件，我们的平台将为您提供量化与分析服务。
                    </div>
                    <Link className={s("right-box")} to={"/introduce"}>
                        <div className={s("back-cover")}>INTRODUCE</div>
                        <div className={s("button-content")}>
                            <div className={s("button-text")}>平台介绍</div>
                            <img className={s("button-logo")} src={require("../../assert/arrow-right.png")}/>
                        </div>
                    </Link>
                </div>
            </div>
            <div className={s("fourth-box")}>
                <img className={s("back-cover")} src={require("../../assert/back-hall.png")}/>
                <div className={s("thanks-box")}>
                    <div className={s("back-cover-text")}>THANKS</div>
                    <div className={s("thanks-box-text")}>特别鸣谢</div>
                </div>
                <div className={s("logo-box")}>
                    <img src={require("../../assert/school-logo.png")}/>
                    <img src={require("../../assert/futu-logo.png")}/>
                </div>
                <div className={s("bottom-box")}>
                    <div className={s("first-box")}>
                        <div className={s("logos")}>
                            <img src={require("../../assert/white-logo.png")} className={s("logo1")}/>
                            <img src={require("../../assert/home-signature.png")} className={s("logo2")}/>
                        </div>
                        <Link to={`${!token ? "/login" : "/introduce"}`}
                              className={s("join-button")}>{`${!token ? "立即加入" : "了解我们"}`}</Link>
                    </div>
                    <div className={s("bottom-second-box")}>
                        <div className={s("second-content")}>
                            <div className={s("second-content-title")}>关于我们</div>
                            <div className={s("second-content-text")}>愿景</div>
                            <div className={s("second-content-text")}>技术</div>
                            <div className={s("second-content-text")}>团队</div>
                        </div>
                        <div className={s("second-content")}>
                            <div className={s("second-content-title")}>平台介绍</div>
                            <div className={s("second-content-text")}>使用对象</div>
                            <div className={s("second-content-text")}>操作手册</div>
                            <div className={s("second-content-text")}>相关工具</div>
                        </div>
                        <div className={s("second-content")}>
                            <div className={s("second-content-title")}>评价与建议</div>
                            <div className={s("second-content-text")}>评价</div>
                            <div className={s("second-content-text")}>建议</div>
                            <div className={s("second-content-text")}>联系</div>
                        </div>
                    </div>
                    <div className={s("bottom-third-box")}>
                        <img className={s("QRcode")} src={require("../../assert/QRcode.jpg")}/>
                        <div className={s("address-box")}>
                            <div className={s("address-content-title")}>通讯地址</div>
                            <div className={s("address-content")}>地址：中国广东省广州市番禺区广州大学城华南理工大学B7、B8楼</div>
                            <div className={s("address-content")}>邮政编码：510006</div>
                            <div className={s("address-content")}>联系电话：4008-823-823 疯狂星期四V我50</div>

                        </div>
                        <div className={s("department-box")}>
                            <div className={s("department-title")}>相关单位</div>
                            <div className={s("department-content")}>华南理工大学</div>
                            <div className={s("department-content")}>华南理工大学软件学院</div>
                        </div>
                    </div>
                    <div className={s("bottom-fourth-box")}>
                        版权所有 © 2023 | 隐私政策 | blabla | 阿巴阿巴
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home