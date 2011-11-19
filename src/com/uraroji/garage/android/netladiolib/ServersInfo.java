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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * サーバ情報一覧
 */
public class ServersInfo {

    /**
     * ねとらじのサーバ情報のURL
     */
    private static final String NETLADIO_SERVER_DAT_URL = "http://yp.ladio.net/stats/server.dat";

    /**
     * サーバ情報の作成時刻
     */
    private Date mDate;

    /**
     * 不明な全ソースコネクション数
     */
    public static final int UNKNOWN_SOURCE = -1;

    /**
     * 全ソースコネクション数(番組数)
     */
    private int mSource = UNKNOWN_SOURCE;

    /**
     * 不明な全クライアントコネクション数
     */
    public static final int UNKNOWN_CLIENT = -1;

    /**
     * 全クライアントコネクション数(リスナー数)
     */
    private int mClient = UNKNOWN_CLIENT;

    /**
     * 不明な全転送データ量
     */
    public static final int UNKNOWN_TRANSFER = -1;

    /**
     * 全転送データ量(Out側) kbps
     */
    private int mTransfer = UNKNOWN_TRANSFER;

    /**
     * ストリーミングサーバ接続パスワード
     */
    private String mPassword;

    /**
     * サーバ情報リスト
     */
    private ArrayList<Server> mServerList = new ArrayList<Server>();

    /**
     * コンストラクタ
     */
    public ServersInfo() {
    }

    /**
     * サーバ情報の作成時刻を取得する
     * 
     * @return サーバ情報の作成時刻
     */
    public final Date getDate() {
        return mDate;
    }

    /**
     * 全ソースコネクション数(番組数)を取得する
     * 
     * @return 全ソースコネクション数(番組数)
     */
    public final int getSource() {
        return mSource;
    }

    /**
     * 全クライアントコネクション数(リスナー数)を取得する
     * 
     * @return 全クライアントコネクション数(リスナー数)
     */
    public final int getClient() {
        return mClient;
    }

    /**
     * 全転送データ量(Out側)を取得する
     * 
     * @return 全転送データ量(Out側)
     */
    public final int getTransfer() {
        return mTransfer;
    }

    /**
     * ストリーミングサーバ接続パスワードを取得する
     * 
     * @return ストリーミングサーバ接続パスワード
     */
    public final String getPassword() {
        return mPassword;
    }

    /**
     * サーバ情報リストを取得する
     * 
     * @return サーバ情報リスト
     */
    public final Server[] getServers() {
        synchronized (this) {
            return mServerList.toArray(new Server[mServerList.size()]);
        }
    }

    /**
     * 指定したアドレスのサーバを取得する
     * 
     * @param server サーバアドレス
     * @return サーバ
     */
    public final Server getServer(String server)
    {
        if (server == null || server.length() == 0) {
            return null;
        }

        Server result = null;

        synchronized (this) {
            for (Server s : mServerList) {
                if (s != null && s.isConnectable()) {
                    if (s.getServerName().asString().equals(server)) {
                        result = s;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 空いているサーバを取得する
     * 
     * @return 空いているサーバ
     */
    public final Server getVacantServer() {
        Server result = null;

        synchronized (this) {
            for (Server s : mServerList) {
                if (s != null && s.isConnectable()) {
                    if (result == null
                            || result.getCongestionDegree() > s
                                    .getCongestionDegree()) {
                        result = s;
                    }
                }
            }
        }

        return result;
    }

    /**
     * サーバから取得される時刻の形式
     */
    private final static SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * サーバの一覧をインターネットから取得する インターネットからねとらじサーバ一覧を取得し、 ServerInfoクラスが保持する番組を更新する
     * 
     * @throws IOException 接続に失敗したと思われる場合
     */
    public void fetchServerInfo() throws IOException {
        synchronized (this) {
            // 保持しているサーバをいったんクリアする
            mServerList.clear();

            HttpURLConnection httpConnection = null;
            InputStream st = null;
            InputStreamReader sr = null;
            BufferedReader br = null;

            try {
                // ネットワークからサーバ情報を取得する
                final URL url = new URL(NETLADIO_SERVER_DAT_URL);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                st = httpConnection.getInputStream();
                sr = new InputStreamReader(st, "Shift_JIS");
                br = new BufferedReader(sr);

                // 解析
                String line;
                boolean header = true; // ヘッダか
                while ((line = br.readLine()) != null) {
                    if (line.length() == 0) {
                        // 空行があったらヘッダ終了
                        header = false;
                    } else if (header) {
                        String[] sDate = line.split("Date: ");
                        if (sDate.length >= 2) {
                            try {
                                mDate = SDF.parse(sDate[1]);
                            } catch (ParseException e) {
                                ;
                            }
                            continue;
                        }

                        String[] sSource = line.split("Source: ");
                        if (sSource.length >= 2) {
                            try {
                                mSource = Integer.parseInt(sSource[1]);
                            } catch (NumberFormatException e) {
                                ;
                            }
                            continue;
                        }

                        String[] sClient = line.split("Client: ");
                        if (sClient.length >= 2) {
                            try {
                                mClient = Integer.parseInt(sClient[1]);
                            } catch (NumberFormatException e) {
                                ;
                            }
                            continue;
                        }

                        String[] sTransfer = line.split("Transfer: ");
                        if (sTransfer.length >= 2) {
                            try {
                                mTransfer = Integer.parseInt(sTransfer[1].replace("kbps", ""));
                            } catch (NumberFormatException e) {
                                ;
                            }
                            continue;
                        }

                        String[] sPassword = line.split("Password: ");
                        if (sPassword.length >= 2) {
                            mPassword = sPassword[1];
                            continue;
                        }
                    } else {
                        Server server = new Server();
                        String[] serverTokens = line.split("\t");
                        if (serverTokens.length >= 5) {
                            server.setServerName(serverTokens[0]);

                            try {
                                server.setCongestionDegree(Integer.parseInt(serverTokens[1]));
                            } catch (NumberFormatException e) {
                                ;
                            }

                            try {
                                server.setSource(Integer.parseInt(serverTokens[2]));
                            } catch (NumberFormatException e) {
                                ;
                            }

                            try {
                                server.setClient(Integer.parseInt(serverTokens[3]));
                            } catch (NumberFormatException e) {
                                ;
                            }

                            try {
                                server.setTransfer(Integer.parseInt(serverTokens[4]));
                            } catch (NumberFormatException e) {
                                ;
                            }

                            mServerList.add(server);
                        }
                    }
                }
            } finally {
                if (br != null) {
                    br.close();
                }
                if (sr != null) {
                    sr.close();
                }
                if (st != null) {
                    st.close();
                }
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
            }
        }
    }
}
