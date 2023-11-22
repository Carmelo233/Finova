// <<<<<<< HEAD
// import style from "./index.module.css";
// import {makeStyle} from "../../utils/CSSUtils";
// const s = makeStyle(style)
// function About(){
// =======
import { Carousel } from 'antd'
import style from "./index.module.css"
import { makeStyle } from "../../utils/CSSUtils"

const s = makeStyle(style)


const contentStyle = {
    margin: 0,
    height: '80vh',
    color: '#fff',
    lineHeight: '160px',
    textAlign: 'center',
    background: '#364d79',
}
const About = () => {
    const onChange = (currentSlide) => {
        console.log(currentSlide)
        console.log("视口宽度：" + window.innerWidth)
        console.log("视口高度：" + window.innerHeight)

    }
    return (
        <Carousel afterChange={onChange} autoplay>
            <div className={s('box1')} >
                <div className={s("back-box")}>
                    <img className={s("img1")} src={require("../../assert/About_Back1.png")} style={{ height: '89.2vh', width: '100vw', }} />
                    <div className={s("cover-div1")}></div>
                </div>

                <div className={s('box1_sum')}>
                    <div className={s('box1_box1')}></div>
                    <div className={s('box1_box2')}>愿景</div>
                    <div className={s('box1_box3')}>

                        <span style={{ display: 'block' }}>技术铸交易平台， 热血凝金融赋能。 书生一路追梦行， 守护繁荣与平稳。</span>
                        <span style={{ display: 'block' }}>代码虽难终能成， 功夫不负有心人。 晨钟暮鼓勤耕耘， 岁月静好情更深。</span>
                        <span style={{ display: 'block' }}>云端见证繁华景， 数字编织投资网。 晨曦初现心向明， 浩浩长路任逍遥。</span>
                        <span style={{ display: 'block' }}>牢记初心不忘本， 善用智慧扶危济困。 愿尽所能创新意， 砥砺前行不辞劳。</span>
                    </div>

                    <div className={s('box1_box4')}>
                        <span style={{ display: 'block', opacity: 0.05 }}>技术铸交易平台， 热血凝金融赋能。 书生一路追梦行， 守护繁荣与平稳。</span>
                        <span style={{ display: 'block', opacity: 0.1 }}>代码虽难终能成， 功夫不负有心人。 晨钟暮鼓勤耕耘， 岁月静好情更深。</span>
                        <span style={{ display: 'block', opacity: 0.2 }}>云端见证繁华景， 数字编织投资网。 晨曦初现心向明， 浩浩长路任逍遥。</span>
                        <span style={{ display: 'block', opacity: 0.25 }}>牢记初心不忘本， 善用智慧扶危济困。 愿尽所能创新意， 砥砺前行不辞劳。</span></div>

                </div>


                <h3 style={contentStyle}> </h3>
            </div>
            <div className={s('box2')}>
                <div className={s("back-box")}>
                    <img className={s("img2")} src={require("../../assert/About_Back2.png")} style={{ height: '89.2vh', width: '100vw', }} />
                    <div className={s("cover-div2")}></div>
                </div>
                <div className={s('box2_sum')}>
                    <div className={s('box2_box1')}></div>
                    <div className={s('box2_box2')}>团队</div>
                    <div className={s('box2_box3')}>

                        <span style={{ display: 'block' }}>富途封神团队成立于 2023年 ，成员均为在校学生，团队人数由最初的3名核心人员发展成现有的5</span>
                        <span style={{ display: 'block' }}>名成员。团队成员分别来自华南理工大学软件、设计等学院， 都有过学科竞赛等相关经验，综合素质</span>
                        <span style={{ display: 'block' }}> 较高。团队知识结构合理、优势互补，是一支团结协作、充满激情的优秀团队。</span>
                    </div>
                    <div className={s('box2_box4')}>
                        <span style={{ display: 'block', opacity: 0.05 }}>富途封神团队成立于 2023年 ，成员均为在校学生，团队人数由最初的3名核心人员发展成现有的5</span>
                        <span style={{ display: 'block', opacity: 0.1 }}>名成员。团队成员分别来自华南理工大学软件、设计等学院， 都有过学科竞赛等相关经验，综合素质</span>
                        <span style={{ display: 'block', opacity: 0.2 }}> 较高。团队知识结构合理、优势互补，是一支团结协作、充满激情的优秀团队。</span>
                    </div>
                </div>
                <h3 style={contentStyle}>2</h3>
            </div>
            <div className={s('box3')}>
                <div className={s("back-box")}>
                    <img className={s("img3")} src={require("../../assert/About_Back3.png")} style={{ height: '89.2vh', width: '100vw', }} />
                    <div className={s("cover-div3")}></div>
                </div>

                <div className={s('box3_sum')}>
                <div className={s('box3_box1')}></div>
                <div className={s('box3_box2')}>技术</div>
                <div className={s('box3_box3')}>
                    <span style={{ display: 'block' }}> Finova由"Fin"和"nova"两个单词组成，"Fin"代表着金融，而"nova"则代表着新星、新生和新的开始。我</span>
                    <span style={{ display: 'block' }}> 们的平台旨在通过创新的方式，用技术为金融赋能，为金融提供更多的可能。 </span>
                    <span style={{ display: 'block' }}> 我们平台希望能够同时为专业投资者和金融新手赋能，我们为每个模块提供了详细的介绍和详细的解释，</span>
                    <span style={{ display: 'block' }}> 希望能够让金融新手快速入门。我们也提供了多种的量化交易模型，方便专业投资者进行回测和预测。同</span>
                    <span style={{ display: 'block' }}> 时，我们也允许用户上传自己的量化交易模型，我们会为基于用户上传的模型进行回测。同时我们提供了</span>
                    <span style={{ display: 'block' }}> 信息批量提取的功能，希望在这个信息纷繁的金融世界，为用户筑起一道过滤的屏障。</span>


                </div>
                <div className={s('box3_box4')}>
                    <span style={{ display: 'block', opacity: 0.01 }}> Finova由"Fin"和"nova"两个单词组成，"Fin"代表着金融，而"nova"则代表着新星、新生和新的开始。我</span>
                    <span style={{ display: 'block', opacity: 0.01 }}> 们的平台旨在通过创新的方式，用技术为金融赋能，为金融提供更多的可能。 </span>
                    <span style={{ display: 'block', opacity: 0.01 }}> 我们平台希望能够同时为专业投资者和金融新手赋能，我们为每个模块提供了详细的介绍和详细的解释，</span>
                    <span style={{ display: 'block', opacity: 0.1 }}> 希望能够让金融新手快速入门。我们也提供了多种的量化交易模型，方便专业投资者进行回测和预测。同</span>
                    <span style={{ display: 'block', opacity: 0.15 }}> 时，我们也允许用户上传自己的量化交易模型，我们会为基于用户上传的模型进行回测。同时我们提供了</span>
                    <span style={{ display: 'block', opacity: 0.20 }}> 信息批量提取的功能，希望在这个信息纷繁的金融世界，为用户筑起一道过滤的屏障。</span>

                </div>
                </div>


                <h3 style={contentStyle}>3</h3>
            </div>



        </Carousel>
    )
}
export default About