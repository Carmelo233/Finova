import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os
import arr2json



def bollinger_bands(data, window_size, num_std):
    # 计算移动平均线和标准差
    rolling_mean = data['close'].rolling(window_size).mean()
    rolling_std = data['close'].rolling(window_size).std()

    # 计算上下布林带
    upper_band = rolling_mean + (rolling_std * num_std)
    lower_band = rolling_mean - (rolling_std * num_std)

    # 返回上下布林带和移动平均线
    return rolling_mean, upper_band, lower_band

# 计算布林带
def cal(share):
    ## 读取CSV文件
    # share_code = "600519.SH"
    # data_file = share_code + ".csv"
    # df = pd.read_csv(os.path.join(os.path.dirname(os.getcwd()), "share_data", data_file))
    df = pd.read_csv(os.path.join(os.path.dirname(os.getcwd()), "share_data", share))
    data = df
    # print(os.path.join(os.path.dirname(os.getcwd()), "share_data", share))

    ## 模型代码
    window_size = 20
    num_std = 2
    rolling_mean, upper_band, lower_band = bollinger_bands(data, window_size, num_std)

    # 绘制收盘价、移动平均线和布林带
    # plt.plot(data['trade_date'], data['close'], label='Close')
    # plt.plot(data['trade_date'], rolling_mean, label='Moving Average')
    # plt.plot(data['trade_date'], upper_band, label='Upper Band')
    # plt.plot(data['trade_date'], lower_band, label='Lower Band')
    # plt.title('Bollinger Bands')
    # plt.legend()
    # plt.show()

    # 初始化变量
    position = None
    buy = []
    sell = []

    # 根据动量策略进行交易
    for i in range(window_size, len(data)):
        if data['close'][i] > upper_band[i-1]: # 收盘价突破上布林带
            if position != True: # 如果没有持有股票，则买入
                position = True
                buy.append(data['trade_date'][i])
                # print('Buying at ' + str(data['trade_date'][i]))
        elif data['close'][i] < lower_band[i-1]: # 收盘价跌破下布林带
            if position != False: # 如果持有股票，则卖出
                position = False
                sell.append(data['trade_date'][i])
                # print('Selling at ' + str(data['trade_date'][i]))
        else:
            pass # 不做任何操作

    # 打印买入和卖出日期
    # print('Buy Dates:', buy)
    # print('Sell Dates:', sell)
    return [buy,sell]


    # sr1 = pd.Series(1,index=buy)
    # sr2 = pd.Series(0,index=sell)
    # # sr = sr1.append(sr2).sort_index()
    # sr = pd.concat([sr1, sr2], axis=0).sort_index()
    # print(share_code+'布林带模型策略（从发行股票第一天开始）')
    # first_money = 100000
    # print('本金:',first_money)
    # money = first_money
    # hold = 0
    # for i in range(len(sr)):
    #     print(sr.index[i])
    #     print(money)
    #     print(hold)
    #     # p = df['open'][sr.index[i]]
    #     p = df.loc[df['trade_date'] == sr.index[i], 'open'].values[0]
    #     if sr.iloc[i]== 1:
    #         buy = (money // (100*p))
    #         money -= buy*100*p
    #         hold += buy*100
    #     else:
    #         money += hold*p
    #         hold = 0
    #         print(f'第{i//2+1}次交易后的收益：',money-first_money)
    # p = df.iloc[-1]["close"]
    # # p = df['close'][-1]
    # last_money = p*hold +money
    #
    #
    # print('总收益:',last_money-first_money)