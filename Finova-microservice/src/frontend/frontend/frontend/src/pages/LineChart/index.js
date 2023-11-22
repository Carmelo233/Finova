import {useCallback, useEffect, useState, useRef} from "react";
import * as echarts from "echarts";

export function LineChart(props) {
    const chartRef = useRef(null);

    const {markLineData, rawData} = props

    useEffect(() => {
        const chart = echarts.init(chartRef.current);

        const datasetWithFilters = [];
        const seriesList = [];
        const lineTypes = [
            'open',
            'high',
            'low',
            'close'
        ];

        console.log("markLineData", markLineData)
        echarts.util.each(lineTypes, function (lineType) {
            let datasetId = 'dataset_' + lineType;
            datasetWithFilters.push({
                id: datasetId,
                fromDatasetId: 'dataset_raw',
                transform: {
                    type: 'filter',
                    config: {
                        and: [
                            // {dimension: 'Trade_date', gte: 20010827},
                            {dimension: 'Type', '=': lineType}
                        ]
                    }
                }
            });

            seriesList.push({
                type: 'line',
                datasetId: datasetId,
                showSymbol: false,
                name: lineType,
                labelLayout: {
                    moveOverlap: 'shiftY'
                },
                emphasis: {
                    focus: 'series'
                },
                encode: {
                    x: 'Trade_date',
                    y: 'Price',
                    label: ['Type', 'Price'],
                    itemName: 'Trade_date',
                    tooltip: ['Price']
                },
                markLine: {
                    symbol: '',
                    label: {
                        show: false,
                        symbol: ''
                    },
                    data: markLineData,

                    animationDelay: function (idx) {
                        console.log(markLineData[idx].xAxis, 5000 / (rawData.length / 4), markLineData[idx].xAxis * (5000 / (rawData.length / 4)))
                        return markLineData[idx].xAxis * (5000 / (rawData.length / 4))
                    },
                    emphasis: {
                        label: {
                            show: true, // 鼠标悬停时显示标签
                            position: 'end',
                            formatter: function (params) {
                                return params.value.label;
                            }
                        }
                    },
                    animationDuration: 100
                }

            })
            ;
        });

        const option = {
            animation: true,
            animationDuration: 5000,
            dataset: [
                {
                    id: 'dataset_raw',
                    source: rawData
                },
                ...datasetWithFilters
            ],
            // title: {
            //     text: 'Income of Germany and France since 1950'
            // },
            tooltip: {
                order: 'valueDesc',
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                nameLocation: 'middle',
                // data:[20010903,20010904,20010905,20010906,20010907]
            },
            yAxis: {
                name: 'Price',
                // min: 10
            },
            grid: {
                right: 140
            },
            series: seriesList
        };

        // chart.setOption(option, false, {animationDelay: 1});
        // chart.setOption(option, true);
        chart.setOption(option, true);


    }, [rawData, markLineData])

    return <div ref={chartRef} style={{width: "100%", height: "400px"}} key={rawData[1] + markLineData[1]}/>
}