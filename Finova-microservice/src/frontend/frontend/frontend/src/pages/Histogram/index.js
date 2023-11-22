import style from "./index.module.css";
import {makeStyle} from "../../utils/CSSUtils";
import {useEffect, useRef} from "react";
import * as echarts from 'echarts';

const s = makeStyle(style)

export function Histogram(props) {
    const chartRef = useRef(null);

    const {wordArr, probabilityArr} = props
    useEffect(() => {
        const chart = echarts.init(chartRef.current);

        const option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: wordArr,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '概率',
                    type: 'bar',
                    barWidth: '60%',
                    data: probabilityArr
                }
            ]
        };
        chart.setOption(option);
    }, [])


    return (
        <div ref={chartRef} style={{width: "60vw", height: "60vh"}}></div>
    )
}