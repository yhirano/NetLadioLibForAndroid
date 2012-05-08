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

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 番組
 */
public class Channel implements Serializable {

    private static final long serialVersionUID = -4184226934079774358L;

    /**
     * 番組の詳細内容を表示するサイトのURL
     */
    private URL mSurl;

    /**
     * 放送開始時刻
     */
    private Date mTims;

    /**
     * 配信サーバホスト名
     */
    private String mSrv;

    /**
     * 配信サーバポート番号
     */
    private int mPrt;

    /**
     * 配信サーバマウント
     */
    private String mMnt;

    /**
     * 配信フォーマットの種類
     */
    private String mType;

    /**
     * タイトル
     */
    private String mNam;

    /**
     * ジャンル
     */
    private String mGnl;

    /**
     * 放送内容
     */
    private String mDesc;

    /**
     * DJ
     */
    private String mDj;

    /**
     * 現在の曲名情報
     */
    private String mSong;

    /**
     * WebサイトのURL
     */
    private URL mUrl;

    /**
     * リスナ数が不明
     */
    public transient static final int UNKNOWN_LISTENER_NUM = -1;

    /**
     * 現リスナ数
     */
    private int mCln = UNKNOWN_LISTENER_NUM;

    /**
     * 総リスナ数
     */
    private int mClns = UNKNOWN_LISTENER_NUM;

    /**
     * 最大リスナ数
     */
    private int mMax = UNKNOWN_LISTENER_NUM;

    /**
     * ビットレートが不明
     */
    public transient static final int UNKNOWN_BITRATE_NUM = -1;

    /**
     * ビットレート（Kbps）
     */
    private int mBit = UNKNOWN_BITRATE_NUM;

    /**
     * サンプリングレートが不明
     */
    public transient static final int UNKNOWN_SAMPLING_RATE_NUM = -1;

    /**
     * サンプリングレート
     */
    private int mSmpl = UNKNOWN_SAMPLING_RATE_NUM;

    /**
     * チャンネル数が不明
     */
    public transient static final int UNKNOWN_CHANNEL_NUM = -1;

    /**
     * チャンネル数
     */
    private int mChs = UNKNOWN_CHANNEL_NUM;

    /**
     * コンストラクタ
     */
    /*package*/ Channel() {
    }

    @Override
    public String toString() {
        return "Channel [mSurl=" + mSurl + ", mTims=" + mTims + ", mSrv=" + mSrv + ", mPrt=" + mPrt + ", mMnt=" + mMnt
                + ", mType=" + mType + ", mNam=" + mNam + ", mGnl=" + mGnl + ", mDesc=" + mDesc + ", mDj=" + mDj
                + ", mSong=" + mSong + ", mUrl=" + mUrl + ", mCln=" + mCln + ", mClns=" + mClns + ", mMax=" + mMax
                + ", mBit=" + mBit + ", mSmpl=" + mSmpl + ", mChs=" + mChs + "]";
    }

    /**
     * 番組の詳細内容を表示するサイトのURLを取得する
     * 
     * @return 番組の詳細内容を表示するサイトのURL。設定されていない場合はnull。
     */
    public final URL getSurl() {
        return mSurl;
    }

    /**
     * 番組の詳細内容を表示するサイトのURLを設定する
     * 
     * @param surl 番組の詳細内容を表示するサイトのURL
     */
    /*package*/ final void setSurl(URL surl) {
        this.mSurl = surl;
    }

    /**
     * 番組の詳細内容を表示するサイトのURLを設定する
     * 
     * @param surl 番組の詳細内容を表示するサイトのURL
     * @throws MalformedURLException URLが正しくない場合
     */
    /*package*/ final void setSurl(String surl) throws MalformedURLException {
        this.mSurl = new URL(surl);
    }

    /**
     * 放送開始時刻
     * 
     * @return 放送開始時刻。設定されていない場合はnull。
     */
    public final Date getTims() {
        return mTims;
    }

    /**
     * サーバから取得される放送開始時刻の形式
     */
    private transient final static SimpleDateFormat OUTPUT_SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 放送開始時刻を文字列で取得する
     * 
     * @return 放送開始時刻の文字列。設定されていない場合はnull。
     */
    public String getTimsString() {
        if (mTims == null) {
            return null;
        }
        return OUTPUT_SDF.format(getTims());
    }

    /**
     * 放送開始時刻を設定する
     * 
     * @param tims 放送開始時刻。設定されていない場合はnull。
     */
    /*package*/ final void setTims(Date tims) {
        this.mTims = tims;
    }

    /**
     * サーバから取得される放送開始時刻の形式
     */
    private transient final static SimpleDateFormat INPUT_SDF = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

    /**
     * 放送開始時刻を文字列で設定する "yy/MM/dd HH:mm:ss"の形式で設定する必要がある
     * 
     * @param tims 放送開始時刻の文字列
     * @throws ParseException 文字列のフォーマットが正しくない
     */
    /*package*/ final void setTims(String tims) throws ParseException {
        this.mTims = INPUT_SDF.parse(tims);
    }

    /**
     * 配信サーバホスト名を設定する
     * 
     * @param srv 配信サーバホスト名
     */
    /*package*/ final void setSrv(String srv) {
        this.mSrv = srv;
    }

    /**
     * 配信サーバポート番号を設定する
     * 
     * @param prt 配信サーバポート番号
     */
    /*package*/ final void setPrt(int prt) {
        this.mPrt = prt;
    }

    /**
     * 配信サーバマウントを設定する
     * 
     * @param mnt 配信サーバマウント
     */
    /*package*/ final void setMnt(String mnt) {
        this.mMnt = mnt;
    }

    /**
     * 配信フォーマットの種類を取得する
     * 
     * @return 配信フォーマットの種類。設定されていない場合はnull。
     */
    public final String getType() {
        return mType;
    }

    /**
     * 配信フォーマットの種類を設定する
     * 
     * @param type 配信フォーマットの種類
     */
    /*package*/ final void setType(String type) {
        this.mType = type;
    }

    /**
     * タイトルを取得する
     * 
     * @return タイトル。設定されていない場合はnull。
     */
    public final String getNam() {
        return mNam;
    }

    /**
     * タイトルを設定する
     * 
     * @param nam タイトル
     */
    /*package*/ final void setNam(String nam) {
        this.mNam = nam;
    }

    /**
     * ジャンルを取得する
     * 
     * @return ジャンル。設定されていない場合はnull。
     */
    public final String getGnl() {
        return mGnl;
    }

    /**
     * ジャンルを設定する
     * 
     * @param gnl ジャンル
     */
    /*package*/ final void setGnl(String gnl) {
        this.mGnl = gnl;
    }

    /**
     * 放送内容を取得する
     * 
     * @return 放送内容。設定されていない場合はnull。
     */
    public final String getDesc() {
        return mDesc;
    }

    /**
     * 放送内容を設定する
     * 
     * @param desc 放送内容
     */
    /*package*/ final void setDesc(String desc) {
        this.mDesc = desc;
    }

    /**
     * DJを取得する
     * 
     * @return DJ。設定されていない場合はnull。
     */
    public final String getDj() {
        return mDj;
    }

    /**
     * DJを設定する
     * 
     * @param dj DJ
     */
    /*package*/ final void setDj(String dj) {
        this.mDj = dj;
    }

    /**
     * 現在の曲名情報を取得する
     * 
     * @return 現在の曲名情報。設定されていない場合はnull。
     */
    public final String getSong() {
        return mSong;
    }

    /**
     * 現在の曲名情報を設定する
     * 
     * @param song 現在の曲名情報
     */
    /*package*/ final void setSong(String song) {
        this.mSong = song;
    }

    /**
     * WebサイトのURLを取得する
     * 
     * @return WebサイトのURL。設定されていない場合はnull。
     */
    public final URL getUrl() {
        return mUrl;
    }

    /**
     * WebサイトのURLを設定する
     * 
     * @param url WebサイトのURL
     */
    /*package*/ final void setUrl(URL url) {
        this.mUrl = url;
    }

    /**
     * WebサイトのURLを文字列で設定する
     * 
     * @param url WebサイトのURL
     * @throws MalformedURLException URLが正しくない場合
     */
    /*package*/ final void setUrl(String url) throws MalformedURLException {
        this.mUrl = new URL(url);
    }

    /**
     * 現リスナ数を取得する
     * 
     * @return 現リスナ数。設定されていない場合はUNKNOWN_LISTENER_NUM。
     * @see Channel#UNKNOWN_LISTENER_NUM
     */
    public final int getCln() {
        return mCln;
    }

    /**
     * 現リスナ数を設定する
     * 
     * @param cln 現リスナ数。0未満を設定した場合、UNKNOWN_LISTENER_NUMとなる。
     */
    /*package*/ final void setCln(int cln) {
        this.mCln = (cln >= 0) ? cln : UNKNOWN_LISTENER_NUM;
    }

    /**
     * 総リスナ数を取得する
     * 
     * @return 総リスナ数。設定されていない場合はUNKNOWN_LISTENER_NUM。
     * @see Channel#UNKNOWN_LISTENER_NUM
     */
    public final int getClns() {
        return mClns;
    }

    /**
     * 総リスナ数を設定する
     * 
     * @param clns 総リスナ数。0未満を設定した場合、UNKNOWN_LISTENER_NUMとなる。
     */
    /*package*/ final void setClns(int clns) {
        this.mClns = (clns >= 0) ? clns : UNKNOWN_LISTENER_NUM;
    }

    /**
     * 最大リスナ数を取得する
     * 
     * @return 最大リスナ数。設定されていない場合はUNKNOWN_LISTENER_NUM。
     * @see Channel#UNKNOWN_LISTENER_NUM
     */
    public final int getMax() {
        return mMax;
    }

    /**
     * 最大リスナ数を設定する
     * 
     * @param max 最大リスナ数。0未満を設定した場合、UNKNOWN_LISTENER_NUMとなる。
     */
    /*package*/ final void setMax(int max) {
        this.mMax = (max >= 0) ? max : UNKNOWN_LISTENER_NUM;
    }

    /**
     * ビットレート（Kbps）を取得する
     * 
     * @return ビットレート（Kbps）。設定されていない場合はUNKNOWN_BITRATE_NUM。
     * @see Channel#UNKNOWN_BITRATE_NUM
     */
    public final int getBit() {
        return mBit;
    }

    /**
     * ビットレート（Kbps）を設定する
     * 
     * @param bit ビットレート（Kbps）。0未満を設定した場合、UNKNOWN_BITRATE_NUMとなる。
     */
    /*package*/ final void setBit(int bit) {
        this.mBit = (bit >= 0) ? bit : UNKNOWN_BITRATE_NUM;
    }

    /**
     * サンプリングレートを取得する
     * 
     * @return サンプリングレート。設定されていない場合はUNKNOWN_SAMPLING_RATE_NUM。
     * @see Channel#UNKNOWN_SAMPLING_RATE_NUM
     */
    public final int getSmpl() {
        return mSmpl;
    }

    /**
     * サンプリングレートを設定する
     * 
     * @param smpl サンプリングレート。0未満を設定した場合、UNKNOWN_SAMPLING_RATE_NUMとなる。
     */
    /*package*/ final void setSmpl(int smpl) {
        this.mSmpl = (smpl >= 0) ? smpl : UNKNOWN_CHANNEL_NUM;
    }

    /**
     * チャンネル数を取得する
     * 
     * @return チャンネル数。設定されていない場合はUNKNOWN_CHANNEL_NUM。
     * @see Channel#UNKNOWN_SAMPLING_RATE_NUM
     */
    public final int getChs() {
        return mChs;
    }

    /**
     * チャンネル数を文字列で取得する
     * 
     * @return チャンネル数の文字列
     */
    public String getChsString() {
        switch (mChs) {
            case UNKNOWN_CHANNEL_NUM:
                return "Unknown";
            case 1:
                return "Mono";
            case 2:
                return "Stereo";
            default:
                return String.valueOf(mChs);
        }
    }

    /**
     * チャンネル数を設定する
     * 
     * @param chs チャンネル数。0未満を設定した場合、UNKNOWN_CHANNEL_NUMとなる。
     */
    /*package*/ final void setChs(int chs) {
        this.mChs = (chs >= 0) ? chs : UNKNOWN_CHANNEL_NUM;
    }

    /**
     * 番組の放送URLを取得する 番組の放送URLはmSrv、mPrt、mMntの上方を元に生成する。
     * これらが正しく設定されていない場合はnullが返るので注意。
     * 
     * @return 番組の放送URL。番組の放送URLが存在しない場合はnull。
     */
    public URL getPlayUrl() {
        return createPlayUrl(mSrv, mPrt, mMnt);
    }

    /**
     * 配信サーバホスト名・ポート番号・マウントから再生URLを生成する
     * 
     * @param srv 配信サーバホスト名
     * @param prt 配信サーバポート番号
     * @param mnt 配信サーバマウント
     * @return 再生URL。配信サーバホスト名・ポート番号・マウントのいずれかが不正な場合はnull。
     */
    public static URL createPlayUrl(String srv, int prt, String mnt) {
        if (srv == null || srv.length() == 0 || prt < 0 || mnt == null || mnt.length() == 0) {
            return null;
        }

        try {
            return new URL(String.format("http://%s:%d%s", srv, prt, mnt));
        } catch (MalformedURLException e) {
            // 放送URLが生成できない場合
            return null;
        }
    }

    /**
     * この番組がフィルタリング単語に合致するかを取得する
     * 
     * @param searchWord フィルタリング単語。
     *            フィルタリング単語を指定しない場合は空も時価かnullを指定すること。
     * @return trueの場合はフィルタリング単語に合致する番組である。それ以外はfalse。
     */
    /*package*/ boolean isMatch(String[] searchWords) {
        // フィルタリング単語が指定されていない場合は無条件に合致する
        if (searchWords == null || searchWords.length == 0) {
            return true;
        }

        /*
         * 検索単語を辞書に登録する
         * 辞書は Key:検索単語 Value:マッチしたか
         */
        HashMap<String, Boolean> searchMap = new HashMap<String, Boolean>(searchWords.length);
        for (String searchWord : searchWords) {
            if (searchWord != null && searchWord.length() != 0) {
                searchMap.put(searchWord, Boolean.FALSE);
            }
        }
        
        // 検索対象文字列を配列にする
        ArrayList<String> searchedWords = new ArrayList<String>(4);
        if (mNam != null && mNam.length() != 0) {
            searchedWords.add(mNam);
        }
        if (mGnl != null && mGnl.length() != 0) {
            searchedWords.add(mGnl);
        }
        if (mDesc != null && mDesc.length() != 0) {
            searchedWords.add(mDesc);
        }
        if (mDj != null && mDj.length() != 0) {
            searchedWords.add(mDj);
        }

        // 検索文字と検索対象文字を比較する
        for (String searchedWord : searchedWords) {
            for (String searchWord : searchMap.keySet()) {
                // 見つかった場合は検索辞書の該当単語にYESを代入
                if (searchedWord.toLowerCase().indexOf(searchWord.toLowerCase()) >= 0) {
                    searchMap.put(searchWord, Boolean.TRUE);
                }
            }
        }

        // すべての検索単語がマッチした場合にのみYESを返す
        for (Boolean match : searchMap.values()) {
            if (match.equals(Boolean.FALSE)) {
                return false;
            }
        }
        return true;
    }
}
