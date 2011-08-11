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

import java.security.InvalidParameterException;

/**
 * �T�[�o��
 */
public class ServerName {

	/**
	 * �T�[�o��
	 */
	private String mName;

	/**
	 * �s���ȃ|�[�g�ԍ�
	 */
	public static final int UNKNOWN_PORT = -1;

	/**
	 * �|�[�g�ԍ�
	 */
	private int mPort = UNKNOWN_PORT;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param serverName
	 *            �T�[�o���Bxxx.com:8080�̂悤�ȃT�[�o����IP���g�ݍ�������������w�肷�邱�ƁB
	 * @throws InvalidParameterException
	 *             xxx.com:8080�̌`���ɉ����Ă��Ȃ��T�[�o�����w�肳�ꂽ�B
	 */
	/*package*/ ServerName(String serverName) {
		String[] token = serverName.split(":");
		if (token.length >= 2) {
			this.mName = token[0];
			try {
				this.mPort = Integer.valueOf(token[1]);
			} catch (NumberFormatException e) {
				throw new InvalidParameterException();
			}
		} else {
			throw new InvalidParameterException();
		}
	}

	@Override
	public String toString() {
		return "ServerName [mName=" + mName + ", mPort=" + mPort + "]";
	}

	/**
	 * �T�[�o�����擾����
	 * 
	 * @return �T�[�o��
	 */
	public final String getName() {
		return mName;
	}

	/**
	 * �|�[�g�ԍ����擾����
	 * 
	 * @return �|�[�g�ԍ�
	 */
	public final int getPort() {
		return mPort;
	}
	
	/**
	 * �T�[�o���𕶎���Ƃ��ĕԂ��B<br />
	 * xxx.com:8080�̌`���ɉ����B
	 * 
	 * @return ������Ƃ��ẴT�[�o��
	 */
	/*package*/ String asString() {
		return mName + ":" + String.valueOf(mPort);
	}
}
