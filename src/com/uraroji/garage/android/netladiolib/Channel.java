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
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * �ԑg
 */
public class Channel implements Serializable {

	private static final long serialVersionUID = -4184226934079774358L;

	/**
	 * �ԑg�̏ڍד��e��\������T�C�g��URL
	 */
	private URL mSurl;

	/**
	 * �����J�n����
	 */
	private Date mTims;

	/**
	 * �z�M�T�[�o�z�X�g��
	 */
	private String mSrv;

	/**
	 * �z�M�T�[�o�|�[�g�ԍ�
	 */
	private int mPrt;

	/**
	 * �z�M�T�[�o�}�E���g
	 */
	private String mMnt;

	/**
	 * �z�M�t�H�[�}�b�g�̎��
	 */
	private String mType;

	/**
	 * �^�C�g��
	 */
	private String mNam;

	/**
	 * �W������
	 */
	private String mGnl;

	/**
	 * �������e
	 */
	private String mDesc;

	/**
	 * DJ
	 */
	private String mDj;

	/**
	 * ���݂̋Ȗ����
	 */
	private String mSong;

	/**
	 * Web�T�C�g��URL
	 */
	private URL mUrl;

	/**
	 * ���X�i�����s��
	 */
	public transient static final int UNKNOWN_LISTENER_NUM = -1;

	/**
	 * �����X�i��
	 */
	private int mCln = UNKNOWN_LISTENER_NUM;

	/**
	 * �����X�i��
	 */
	private int mClns = UNKNOWN_LISTENER_NUM;

	/**
	 * �ő僊�X�i��
	 */
	private int mMax = UNKNOWN_LISTENER_NUM;

	/**
	 * �r�b�g���[�g���s��
	 */
	public transient static final int UNKNOWN_BITRATE_NUM = -1;

	/**
	 * �r�b�g���[�g�iKbps�j
	 */
	private int mBit = UNKNOWN_BITRATE_NUM;

	/**
	 * �T���v�����O���[�g���s��
	 */
	public transient static final int UNKNOWN_SAMPLING_RATE_NUM = -1;

	/**
	 * �T���v�����O���[�g
	 */
	private int mSmpl = UNKNOWN_SAMPLING_RATE_NUM;

	/**
	 * �`�����l�������s��
	 */
	public transient static final int UNKNOWN_CHANNEL_NUM = -1;

	/**
	 * �`�����l����
	 */
	private int mChs = UNKNOWN_CHANNEL_NUM;

	/**
	 * ����URL�̃L���b�V��
	 * 
	 * ����URL����x���������炻����L���b�V�����邽�߂ɗp��
	 */
	private transient URL mPlayUrlCache;

	/**
	 * ����URL�̃L���b�V�����L����
	 */
	private transient boolean mIsCreatedPlayUrlCache = false;

	/**
	 * �R���X�g���N�^
	 */
	/*package*/ Channel() {
	}

	@Override
	public String toString() {
		return "Channel [mSurl=" + mSurl + ", mTims=" + mTims + ", mSrv="
				+ mSrv + ", mPrt=" + mPrt + ", mMnt=" + mMnt + ", mType="
				+ mType + ", mNam=" + mNam + ", mGnl=" + mGnl + ", mDesc="
				+ mDesc + ", mDj=" + mDj + ", mSong=" + mSong + ", mUrl="
				+ mUrl + ", mCln=" + mCln + ", mClns=" + mClns + ", mMax="
				+ mMax + ", mBit=" + mBit + ", mSmpl=" + mSmpl + ", mChs="
				+ mChs + "]";
	}

	/**
	 * �ԑg�̏ڍד��e��\������T�C�g��URL���擾����
	 * 
	 * @return �ԑg�̏ڍד��e��\������T�C�g��URL�B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final URL getSurl() {
		return mSurl;
	}

	/**
	 * �ԑg�̏ڍד��e��\������T�C�g��URL��ݒ肷��
	 * 
	 * @param surl
	 *            �ԑg�̏ڍד��e��\������T�C�g��URL
	 */
	/*package*/ final void setSurl(URL surl) {
		this.mSurl = surl;
	}

	/**
	 * �ԑg�̏ڍד��e��\������T�C�g��URL��ݒ肷��
	 * 
	 * @param surl
	 *            �ԑg�̏ڍד��e��\������T�C�g��URL
	 * @throws MalformedURLException
	 *             URL���������Ȃ��ꍇ
	 */
	/*package*/ final void setSurl(String surl) throws MalformedURLException {
		this.mSurl = new URL(surl);
	}

	/**
	 * �����J�n����
	 * 
	 * @return �����J�n�����B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final Date getTims() {
		return mTims;
	}

	/**
	 * �T�[�o����擾���������J�n�����̌`��
	 */
	private transient final static SimpleDateFormat OUTPUT_SDF = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	/**
	 * �����J�n�����𕶎���Ŏ擾����
	 * 
	 * @return �����J�n�����̕�����B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public String getTimsString() {
		if (mTims == null) {
			return null;
		}
		return OUTPUT_SDF.format(getTims());
	}

	/**
	 * �����J�n������ݒ肷��
	 * 
	 * @param tims
	 *            �����J�n�����B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	/*package*/ final void setTims(Date tims) {
		this.mTims = tims;
	}

	/**
	 * �T�[�o����擾���������J�n�����̌`��
	 */
	private transient final static SimpleDateFormat INPUT_SDF = new SimpleDateFormat(
			"yy/MM/dd HH:mm:ss");

	/**
	 * �����J�n�����𕶎���Őݒ肷��
	 * 
	 * "yy/MM/dd HH:mm:ss"�̌`���Őݒ肷��K�v������
	 * 
	 * @param tims
	 *            �����J�n�����̕�����
	 * @throws ParseException
	 *             ������̃t�H�[�}�b�g���������Ȃ�
	 */
	/*package*/ final void setTims(String tims) throws ParseException {
		this.mTims = INPUT_SDF.parse(tims);
	}

	/**
	 * �z�M�T�[�o�z�X�g����ݒ肷��
	 * 
	 * @param srv
	 *            �z�M�T�[�o�z�X�g��
	 */
	/*package*/ final void setSrv(String srv) {
		this.mSrv = srv;

		// ����URL�L���b�V�����N���A����
		mPlayUrlCache = null;
		mIsCreatedPlayUrlCache = false;
	}

	/**
	 * �z�M�T�[�o�|�[�g�ԍ���ݒ肷��
	 * 
	 * @param prt
	 *            �z�M�T�[�o�|�[�g�ԍ�
	 */
	/*package*/ final void setPrt(int prt) {
		this.mPrt = prt;

		// ����URL�L���b�V�����N���A����
		mPlayUrlCache = null;
		mIsCreatedPlayUrlCache = false;
	}

	/**
	 * �z�M�T�[�o�}�E���g��ݒ肷��
	 * 
	 * @param mnt
	 *            �z�M�T�[�o�}�E���g
	 */
	/*package*/ final void setMnt(String mnt) {
		this.mMnt = mnt;

		// ����URL�L���b�V�����N���A����
		mPlayUrlCache = null;
		mIsCreatedPlayUrlCache = false;
	}

	/**
	 * �z�M�t�H�[�}�b�g�̎�ނ��擾����
	 * 
	 * @return �z�M�t�H�[�}�b�g�̎�ށB�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final String getType() {
		return mType;
	}

	/**
	 * �z�M�t�H�[�}�b�g�̎�ނ�ݒ肷��
	 * 
	 * @param type
	 *            �z�M�t�H�[�}�b�g�̎��
	 */
	/*package*/ final void setType(String type) {
		this.mType = type;
	}

	/**
	 * �^�C�g�����擾����
	 * 
	 * @return �^�C�g���B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final String getNam() {
		return mNam;
	}

	/**
	 * �^�C�g����ݒ肷��
	 * 
	 * @param nam
	 *            �^�C�g��
	 */
	/*package*/ final void setNam(String nam) {
		this.mNam = nam;

		// isMatch���ʂ�ێ�����L���b�V�����폜
		mIsMatchCache.clear();
	}

	/**
	 * �W���������擾����
	 * 
	 * @return �W�������B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final String getGnl() {
		return mGnl;
	}

	/**
	 * �W��������ݒ肷��
	 * 
	 * @param gnl
	 *            �W������
	 */
	/*package*/ final void setGnl(String gnl) {
		this.mGnl = gnl;

		// isMatch���ʂ�ێ�����L���b�V�����폜
		mIsMatchCache.clear();
	}

	/**
	 * �������e���擾����
	 * 
	 * @return �������e�B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final String getDesc() {
		return mDesc;
	}

	/**
	 * �������e��ݒ肷��
	 * 
	 * @param desc
	 *            �������e
	 */
	/*package*/ final void setDesc(String desc) {
		this.mDesc = desc;

		// isMatch���ʂ�ێ�����L���b�V�����폜
		mIsMatchCache.clear();
	}

	/**
	 * DJ���擾����
	 * 
	 * @return DJ�B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final String getDj() {
		return mDj;
	}

	/**
	 * DJ��ݒ肷��
	 * 
	 * @param dj
	 *            DJ
	 */
	/*package*/ final void setDj(String dj) {
		this.mDj = dj;

		// isMatch���ʂ�ێ�����L���b�V�����폜
		mIsMatchCache.clear();
	}

	/**
	 * ���݂̋Ȗ������擾����
	 * 
	 * @return ���݂̋Ȗ����B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final String getSong() {
		return mSong;
	}

	/**
	 * ���݂̋Ȗ�����ݒ肷��
	 * 
	 * @param song
	 *            ���݂̋Ȗ����
	 */
	/*package*/ final void setSong(String song) {
		this.mSong = song;
	}

	/**
	 * Web�T�C�g��URL���擾����
	 * 
	 * @return Web�T�C�g��URL�B�ݒ肳��Ă��Ȃ��ꍇ��null�B
	 */
	public final URL getUrl() {
		return mUrl;
	}

	/**
	 * Web�T�C�g��URL��ݒ肷��
	 * 
	 * @param url
	 *            Web�T�C�g��URL
	 */
	/*package*/ final void setUrl(URL url) {
		this.mUrl = url;
	}

	/**
	 * Web�T�C�g��URL�𕶎���Őݒ肷��
	 * 
	 * @param url
	 *            Web�T�C�g��URL
	 * @throws MalformedURLException
	 *             URL���������Ȃ��ꍇ
	 */
	/*package*/ final void setUrl(String url) throws MalformedURLException {
		this.mUrl = new URL(url);
	}

	/**
	 * �����X�i�����擾����
	 * 
	 * @return �����X�i���B�ݒ肳��Ă��Ȃ��ꍇ��UNKNOWN_LISTENER_NUM�B
	 * 
	 * @see Channel#UNKNOWN_LISTENER_NUM
	 */
	public final int getCln() {
		return mCln;
	}

	/**
	 * �����X�i����ݒ肷��
	 * 
	 * @param cln
	 *            �����X�i���B0������ݒ肵���ꍇ�AUNKNOWN_LISTENER_NUM�ƂȂ�B
	 */
	/*package*/ final void setCln(int cln) {
		this.mCln = (cln >= 0) ? cln : UNKNOWN_LISTENER_NUM;
	}

	/**
	 * �����X�i�����擾����
	 * 
	 * @return �����X�i���B�ݒ肳��Ă��Ȃ��ꍇ��UNKNOWN_LISTENER_NUM�B
	 * 
	 * @see Channel#UNKNOWN_LISTENER_NUM
	 */
	public final int getClns() {
		return mClns;
	}

	/**
	 * �����X�i����ݒ肷��
	 * 
	 * @param clns
	 *            �����X�i���B0������ݒ肵���ꍇ�AUNKNOWN_LISTENER_NUM�ƂȂ�B
	 */
	/*package*/ final void setClns(int clns) {
		this.mClns = (clns >= 0) ? clns : UNKNOWN_LISTENER_NUM;
	}

	/**
	 * �ő僊�X�i�����擾����
	 * 
	 * @return �ő僊�X�i���B�ݒ肳��Ă��Ȃ��ꍇ��UNKNOWN_LISTENER_NUM�B
	 * 
	 * @see Channel#UNKNOWN_LISTENER_NUM
	 */
	public final int getMax() {
		return mMax;
	}

	/**
	 * �ő僊�X�i����ݒ肷��
	 * 
	 * @param max
	 *            �ő僊�X�i���B0������ݒ肵���ꍇ�AUNKNOWN_LISTENER_NUM�ƂȂ�B
	 */
	/*package*/ final void setMax(int max) {
		this.mMax = (max >= 0) ? max : UNKNOWN_LISTENER_NUM;
	}

	/**
	 * �r�b�g���[�g�iKbps�j���擾����
	 * 
	 * @return �r�b�g���[�g�iKbps�j�B�ݒ肳��Ă��Ȃ��ꍇ��UNKNOWN_BITRATE_NUM�B
	 * 
	 * @see Channel#UNKNOWN_BITRATE_NUM
	 */
	public final int getBit() {
		return mBit;
	}

	/**
	 * �r�b�g���[�g�iKbps�j��ݒ肷��
	 * 
	 * @param bit
	 *            �r�b�g���[�g�iKbps�j�B0������ݒ肵���ꍇ�AUNKNOWN_BITRATE_NUM�ƂȂ�B
	 */
	/*package*/ final void setBit(int bit) {
		this.mBit = (bit >= 0) ? bit : UNKNOWN_BITRATE_NUM;
	}

	/**
	 * �T���v�����O���[�g���擾����
	 * 
	 * @return �T���v�����O���[�g�B�ݒ肳��Ă��Ȃ��ꍇ��UNKNOWN_SAMPLING_RATE_NUM�B
	 * 
	 * @see Channel#UNKNOWN_SAMPLING_RATE_NUM
	 */
	public final int getSmpl() {
		return mSmpl;
	}

	/**
	 * �T���v�����O���[�g��ݒ肷��
	 * 
	 * @param smpl
	 *            �T���v�����O���[�g�B0������ݒ肵���ꍇ�AUNKNOWN_SAMPLING_RATE_NUM�ƂȂ�B
	 */
	/*package*/ final void setSmpl(int smpl) {
		this.mSmpl = (smpl >= 0) ? smpl : UNKNOWN_CHANNEL_NUM;
	}

	/**
	 * �`�����l�������擾����
	 * 
	 * @return �`�����l�����B�ݒ肳��Ă��Ȃ��ꍇ��UNKNOWN_CHANNEL_NUM�B
	 * 
	 * @see Channel#UNKNOWN_SAMPLING_RATE_NUM
	 */
	public final int getChs() {
		return mChs;
	}

	/**
	 * �`�����l�����𕶎���Ŏ擾����
	 * 
	 * @return �`�����l�����̕�����
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
	 * �`�����l������ݒ肷��
	 * 
	 * @param chs
	 *            �`�����l�����B0������ݒ肵���ꍇ�AUNKNOWN_CHANNEL_NUM�ƂȂ�B
	 */
	/*package*/ final void setChs(int chs) {
		this.mChs = (chs >= 0) ? chs : UNKNOWN_CHANNEL_NUM;
	}

	/**
	 * �ԑg�̕���URL���擾����
	 * 
	 * �ԑg�̕���URL��mSrv�AmPrt�AmMnt�̏�������ɐ�������B ����炪�������ݒ肳��Ă��Ȃ��ꍇ��null���Ԃ�̂Œ��ӁB
	 * 
	 * @return �ԑg�̕���URL�B�ԑg�̕���URL�����݂��Ȃ��ꍇ��null�B
	 */
	public URL getPlayUrl() {
		if (mIsCreatedPlayUrlCache == false) {
			mPlayUrlCache = createPlayUrl(mSrv, mPrt, mMnt);
			mIsCreatedPlayUrlCache = true;
		}
		return mPlayUrlCache;
	}

	/**
	 * �z�M�T�[�o�z�X�g���E�|�[�g�ԍ��E�}�E���g����Đ�URL�𐶐�����
	 * 
	 * @param srv �z�M�T�[�o�z�X�g��
	 * @param prt �z�M�T�[�o�|�[�g�ԍ�
	 * @param mnt �z�M�T�[�o�}�E���g
	 * @return �Đ�URL�B�z�M�T�[�o�z�X�g���E�|�[�g�ԍ��E�}�E���g�̂����ꂩ���s���ȏꍇ��null�B
	 */
	public static URL createPlayUrl(String srv, int prt, String mnt) {
		if (srv == null || srv.length() == 0 || prt < 0 || mnt == null
				|| mnt.length() == 0) {
			return null;
		}
		
		try {
			return new URL("http://" + srv + ":" + String.valueOf(prt) + mnt);
		} catch (MalformedURLException e) {
			// ����URL�������ł��Ȃ��ꍇ
			return null;
		}
	}
	
	/**
	 * isMatch�̌������ʂ��L���b�V�����Ă������߂̃}�b�v
	 * 
	 * ������������Ȃ��ꍇ��AChannel���̏��������������ꍇ�ɂ�null�ɂȂ�B
	 */
	private transient SoftReference<HashMap<String, Boolean>> mIsMatchCache = new SoftReference<HashMap<String, Boolean>>(
			new HashMap<String, Boolean>());

	/**
	 * ���̔ԑg���t�B���^�����O�P��ɍ��v���邩���擾����
	 * 
	 * @param searchWord
	 *            �t�B���^�����O�P��B �����Ŏw�肵����������X�y�[�X�ɕ����������������ꂼ��̕�����ɑ΂��āA
	 *            ���ׂĂɍ��v����ԑg�̂�true��Ԃ��iAND�����j�B
	 *            �t�B���^�����O�P����w�肵�Ȃ��ꍇ�͋��������null���w�肷�邱�ƁB
	 * @return true�̏ꍇ�̓t�B���^�����O�P��ɍ��v����ԑg�ł���B����ȊO��false�B
	 */
	/*package*/ boolean isMatch(String searchWord) {
		// �t�B���^�����O�P�ꂪ�w�肳��Ă��Ȃ��ꍇ�͖������ɍ��v����
		if (searchWord == null || searchWord.length() == 0) {
			return true;
		}

		// �L���b�V���Ɍ������ʂ�����΂�����Q�Ƃ���
		{
			HashMap<String, Boolean> isMatchCacheRef = mIsMatchCache.get();
			if (isMatchCacheRef != null) {
				Boolean r = isMatchCacheRef.get(searchWord);
				if (r != null) {
					return r.booleanValue();
				}
			}
		}

		// ��������󔒕����ŕ�������
		String[] words = searchWord.split(" |\\t|�@");

		boolean result = false;

		if (mNam != null && mNam.length() != 0) {
			boolean misMatch = false;
			for (String word : words) {
				if (mNam.toLowerCase().indexOf(word.toLowerCase()) == -1) {
					misMatch = true;
					break;
				}
			}
			result = !misMatch;
		}

		if (result == false && mGnl != null && mGnl.length() != 0) {
			boolean misMatch = false;
			for (String word : words) {
				if (mGnl.toLowerCase().indexOf(word.toLowerCase()) == -1) {
					misMatch = true;
					break;
				}
			}
			result = !misMatch;
		}

		if (result == false && mDesc != null && mDesc.length() != 0) {
			boolean misMatch = false;
			for (String word : words) {
				if (mDesc.toLowerCase().indexOf(word.toLowerCase()) == -1) {
					misMatch = true;
					break;
				}
			}
			result = !misMatch;
		}

		if (result == false && mDj != null && mDj.length() != 0) {
			boolean misMatch = false;
			for (String word : words) {
				if (mDj.toLowerCase().indexOf(word.toLowerCase()) == -1) {
					misMatch = true;
					break;
				}
			}
			result = !misMatch;
		}

		// �L���b�V���Ɍ������ʂ�ۑ�����
		{
			if (mIsMatchCache == null || mIsMatchCache.get() == null) {
				mIsMatchCache = new SoftReference<HashMap<String, Boolean>>(
						new HashMap<String, Boolean>());
			}
			
			HashMap<String, Boolean> isMatchCacheRef = mIsMatchCache.get();
			if (isMatchCacheRef != null) {
				isMatchCacheRef.put(searchWord, new Boolean(result));
			}
		}
		
		return result;
	}
}
