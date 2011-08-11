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
 * �T�[�o���
 */
public class Server {

	/**
	 * �T�[�o��
	 */
	private ServerName mServerName;

	/**
	 * ���G�x�G���[�i�T�[�o�ɐڑ��s�\�j
	 */
	public static final int ERROR_CONGESTION = 0;

	/**
	 * ���G�x
	 */
	private int mCongestionDegree = ERROR_CONGESTION;

	/**
	 * �s���ȃ\�[�X�R�l�N�V������
	 */
	public static final int UNKNOWN_SOURCE = -1;

	/**
	 * �\�[�X�R�l�N�V������(�ԑg��)
	 */
	private int mSource = UNKNOWN_SOURCE;

	/**
	 * �s���ȃN���C�A���g�R�l�N�V������
	 */
	public static final int UNKNOWN_CLIENT = -1;

	/**
	 * �N���C�A���g�R�l�N�V������(���X�i�[��)
	 */
	private int mClient = UNKNOWN_CLIENT;

	/**
	 * �s���ȓ]���f�[�^��
	 */
	public static final int UNKNOWN_TRANSFER = -1;

	/**
	 * �]���f�[�^��(Out��) kbps
	 */
	private int mTransfer = UNKNOWN_TRANSFER;

	/**
	 * �R���X�g���N�^
	 */
	/*package*/ Server() {
	}
	
	@Override
	public String toString() {
		return "Server [mServerName=" + mServerName + ", mCongestionDegree="
				+ mCongestionDegree + ", mSource=" + mSource + ", mClient="
				+ mClient + ", mTransfer=" + mTransfer + "]";
	}

	/**
	 * �T�[�o�����擾����
	 * 
	 * @return �T�[�o��
	 */
	public final ServerName getServerName() {
		return mServerName;
	}

	/**
	 * �T�[�o����ݒ肷��
	 * 
	 * @param mServerName
	 *            �T�[�o��
	 */
	/*package*/ final void setServerName(String serverName) {
		this.mServerName = new ServerName(serverName);
	}

	/**
	 * ���G�x���擾����
	 * 
	 * @return ���G�x
	 */
	public final int getCongestionDegree() {
		return mCongestionDegree;
	}

	/**
	 * ���G�x��ݒ肷��
	 * 
	 * @param congestionDegree
	 *            ���G�x
	 */
	/*package*/ final void setCongestionDegree(int congestionDegree) {
		this.mCongestionDegree = congestionDegree;
	}

	/**
	 * �\�[�X�R�l�N�V������(�ԑg��)���擾����
	 * 
	 * @return �\�[�X�R�l�N�V������(�ԑg��)
	 */
	public final int getSource() {
		return mSource;
	}

	/**
	 * �\�[�X�R�l�N�V������(�ԑg��)��ݒ肷��
	 * 
	 * @param source
	 *            �\�[�X�R�l�N�V������(�ԑg��)
	 */
	/*package*/ final void setSource(int source) {
		this.mSource = source;
	}

	/**
	 * �N���C�A���g�R�l�N�V������(���X�i�[��)���擾����
	 * 
	 * @return �N���C�A���g�R�l�N�V������(���X�i�[��)
	 */
	public final int getClient() {
		return mClient;
	}

	/**
	 * �N���C�A���g�R�l�N�V������(���X�i�[��)��ݒ肷��
	 * 
	 * @param client
	 *            �N���C�A���g�R�l�N�V������(���X�i�[��)
	 */
	/*package*/ final void setClient(int client) {
		this.mClient = client;
	}

	/**
	 * �]���f�[�^��(Out��)���擾����
	 * 
	 * @return �]���f�[�^��(Out��)
	 */
	public final int getTransfer() {
		return mTransfer;
	}

	/**
	 * �]���f�[�^��(Out��)��ݒ肷��
	 * 
	 * @param transfer
	 *            �]���f�[�^��(Out��)
	 */
	/*package*/ final void setTransfer(int transfer) {
		this.mTransfer = transfer;
	}

	/**
	 * �ڑ��\�ȃT�[�o�����擾����
	 * 
	 * @return �ڑ��\�Ȃ�true�A����ȊO�Ȃ�false
	 */
	public boolean isConnectable() {
		return mServerName != null && mCongestionDegree != ERROR_CONGESTION;
	}
}
