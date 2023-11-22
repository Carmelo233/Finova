import {HashRouter as HistoryRouter, Routes, Route, HashRouter} from 'react-router-dom'
import {history} from './utils'
import './App.css';
import Login from "./pages/Login";
import Home from "./pages/Home";
import Introduce from "./pages/Introduce";
import About from "./pages/About";
import Register from "./pages/Register";
import Quantify from "./pages/Quantify";
import Extraction from "./pages/Extraction";
import HeadBar from "./pages/HeadBar";
import {AuthComponent} from "./compenents/AuthComponent";

function App() {
    document.title = "Finova"

    return (
        <div className={"App"}>
            <HashRouter history={history}>
                <Routes>
                    <Route path={'/'} element={<HeadBar/>}>
                        <Route path='' element={<Home/>}></Route>
                        <Route path='introduce' element={<Introduce/>}/>
                        <Route path='about' element={<About/>}/>
                        <Route path='login' element={<Login/>}/>
                        <Route path='register' element={<Register/>}/>
                        <Route path='quantify' element={
                            //鉴权路由
                            <AuthComponent>
                                <Quantify/>
                            </AuthComponent>
                        }/>
                        <Route path='extraction' element={
                            //鉴权路由
                            <AuthComponent>
                                <Extraction/>
                            </AuthComponent>
                        }/>
                    </Route>
                    {/*<Route path='/' element={<Home/>}></Route>*/}

                    {/*<Route path='/quantify' element={<Quantify/>}/>*/}

                </Routes>
            </HashRouter>
        </div>


    )
}

export default App;
