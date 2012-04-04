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
 * サーバ名
 */
public class ServerName {

    /**
     * サーバ名
     */
    private String mName;

    /**
     * 不明なポート番号
     */
    public static final int UNKNOWN_PORT = -1;

    /**
     * ポート番号
     */
    private int mPort = UNKNOWN_PORT;

    /**
     * コンストラクタ
     * 
     * @param serverName サーバ名。xxx.com:8080のようなサーバ名とIPが組み合った文字列を指定すること。
     * @throws IllegalArgumentExceptionØ xxx.com:8080の形式に沿っていないサーバ名が指定された。
     */
    /*package*/ ServerName(String serverName) {
        String[] token = serverName.split(":");
        if (token.length >= 2) {
            this.mName = token[0];
            try {
                this.mPort = Integer.valueOf(token[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "ServerName [mName=" + mName + ", mPort=" + mPort + "]";
    }

    /**
     * サーバ名を取得する
     * 
     * @return サーバ名
     */
    public final String getName() {
        return mName;
    }

    /**
     * ポート番号を取得する
     * 
     * @return ポート番号
     */
    public final int getPort() {
        return mPort;
    }

    /**
     * サーバ名を文字列として返す。<br />
     * xxx.com:8080の形式に沿う。
     * 
     * @return 文字列としてのサーバ名
     */
    /*package*/ String asString() {
        return mName + ":" + String.valueOf(mPort);
    }
}
