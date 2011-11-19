/* 
 * Copyright (c) 2011 Y.Hirano
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.uraroji.garage.android.netladiolib;

/**
 * サーバ情報
 */
public class Server {

    /**
     * サーバ名
     */
    private ServerName mServerName;

    /**
     * 混雑度エラー（サーバに接続不能）
     */
    public static final int ERROR_CONGESTION = 0;

    /**
     * 混雑度
     */
    private int mCongestionDegree = ERROR_CONGESTION;

    /**
     * 不明なソースコネクション数
     */
    public static final int UNKNOWN_SOURCE = -1;

    /**
     * ソースコネクション数(番組数)
     */
    private int mSource = UNKNOWN_SOURCE;

    /**
     * 不明なクライアントコネクション数
     */
    public static final int UNKNOWN_CLIENT = -1;

    /**
     * クライアントコネクション数(リスナー数)
     */
    private int mClient = UNKNOWN_CLIENT;

    /**
     * 不明な転送データ量
     */
    public static final int UNKNOWN_TRANSFER = -1;

    /**
     * 転送データ量(Out側) kbps
     */
    private int mTransfer = UNKNOWN_TRANSFER;

    /**
     * コンストラクタ
     */
    /* package */Server() {
    }

    @Override
    public String toString() {
        return "Server [mServerName=" + mServerName + ", mCongestionDegree="
                + mCongestionDegree + ", mSource=" + mSource + ", mClient="
                + mClient + ", mTransfer=" + mTransfer + "]";
    }

    /**
     * サーバ名を取得する
     * 
     * @return サーバ名
     */
    public final ServerName getServerName() {
        return mServerName;
    }

    /**
     * サーバ名を設定する
     * 
     * @param mServerName サーバ名
     */
    /* package */final void setServerName(String serverName) {
        this.mServerName = new ServerName(serverName);
    }

    /**
     * 混雑度を取得する
     * 
     * @return 混雑度
     */
    public final int getCongestionDegree() {
        return mCongestionDegree;
    }

    /**
     * 混雑度を設定する
     * 
     * @param congestionDegree 混雑度
     */
    /* package */final void setCongestionDegree(int congestionDegree) {
        this.mCongestionDegree = congestionDegree;
    }

    /**
     * ソースコネクション数(番組数)を取得する
     * 
     * @return ソースコネクション数(番組数)
     */
    public final int getSource() {
        return mSource;
    }

    /**
     * ソースコネクション数(番組数)を設定する
     * 
     * @param source ソースコネクション数(番組数)
     */
    /* package */final void setSource(int source) {
        this.mSource = source;
    }

    /**
     * クライアントコネクション数(リスナー数)を取得する
     * 
     * @return クライアントコネクション数(リスナー数)
     */
    public final int getClient() {
        return mClient;
    }

    /**
     * クライアントコネクション数(リスナー数)を設定する
     * 
     * @param client クライアントコネクション数(リスナー数)
     */
    /* package */final void setClient(int client) {
        this.mClient = client;
    }

    /**
     * 転送データ量(Out側)を取得する
     * 
     * @return 転送データ量(Out側)
     */
    public final int getTransfer() {
        return mTransfer;
    }

    /**
     * 転送データ量(Out側)を設定する
     * 
     * @param transfer 転送データ量(Out側)
     */
    /* package */final void setTransfer(int transfer) {
        this.mTransfer = transfer;
    }

    /**
     * 接続可能なサーバかを取得する
     * 
     * @return 接続可能ならtrue、それ以外ならfalse
     */
    public boolean isConnectable() {
        return mServerName != null && mCongestionDegree != ERROR_CONGESTION;
    }
}
