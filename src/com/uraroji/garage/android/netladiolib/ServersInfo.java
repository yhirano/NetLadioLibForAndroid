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
 * �T�[�o���ꗗ
 */
public class ServersInfo {

	/**
	 * �˂Ƃ炶�̃T�[�o����URL
	 */
	private static final String NETLADIO_SERVER_DAT_URL = "http://yp.ladio.net/stats/server.dat";

	/**
	 * �T�[�o���̍쐬����
	 */
	private Date mDate;

	/**
	 * �s���ȑS�\�[�X�R�l�N�V������
	 */
	public static final int UNKNOWN_SOURCE = -1;

	/**
	 * �S�\�[�X�R�l�N�V������(�ԑg��)
	 */
	private int mSource = UNKNOWN_SOURCE;

	/**
	 * �s���ȑS�N���C�A���g�R�l�N�V������
	 */
	public static final int UNKNOWN_CLIENT = -1;

	/**
	 * �S�N���C�A���g�R�l�N�V������(���X�i�[��)
	 */
	private int mClient = UNKNOWN_CLIENT;

	/**
	 * �s���ȑS�]���f�[�^��
	 */
	public static final int UNKNOWN_TRANSFER = -1;

	/**
	 * �S�]���f�[�^��(Out��) kbps
	 */
	private int mTransfer = UNKNOWN_TRANSFER;

	/**
	 * �X�g���[�~���O�T�[�o�ڑ��p�X���[�h
	 */
	private String mPassword;

	/**
	 * �T�[�o��񃊃X�g
	 */
	private ArrayList<Server> mServerList = new ArrayList<Server>();
	
	/**
	 * �R���X�g���N�^
	 */
	public ServersInfo() {
	}

	/**
	 * �T�[�o���̍쐬�������擾����
	 * 
	 * @return �T�[�o���̍쐬����
	 */
	public final Date getDate() {
		return mDate;
	}

	/**
	 * �S�\�[�X�R�l�N�V������(�ԑg��)���擾����
	 * 
	 * @return �S�\�[�X�R�l�N�V������(�ԑg��)
	 */
	public final int getSource() {
		return mSource;
	}

	/**
	 * �S�N���C�A���g�R�l�N�V������(���X�i�[��)���擾����
	 * 
	 * @return �S�N���C�A���g�R�l�N�V������(���X�i�[��)
	 */
	public final int getClient() {
		return mClient;
	}

	/**
	 * �S�]���f�[�^��(Out��)���擾����
	 * 
	 * @return �S�]���f�[�^��(Out��)
	 */
	public final int getTransfer() {
		return mTransfer;
	}

	/**
	 * �X�g���[�~���O�T�[�o�ڑ��p�X���[�h���擾����
	 * 
	 * @return �X�g���[�~���O�T�[�o�ڑ��p�X���[�h
	 */
	public final String getPassword() {
		return mPassword;
	}

	/**
	 * �T�[�o��񃊃X�g���擾����
	 * 
	 * @return �T�[�o��񃊃X�g
	 */
	public final Server[] getServers() {
		synchronized (this) {
			return mServerList.toArray(new Server[mServerList.size()]);
		}
	}
	
	/**
	 * �w�肵���A�h���X�̃T�[�o���擾����
	 * 
	 * @param server
	 *            �T�[�o�A�h���X
	 * @return �T�[�o
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
	 * �󂢂Ă���T�[�o���擾����
	 * 
	 * @return �󂢂Ă���T�[�o
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
	 * �T�[�o����擾����鎞���̌`��
	 */
	private final static SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * �T�[�o�̈ꗗ���C���^�[�l�b�g����擾����
	 * 
	 * �C���^�[�l�b�g����˂Ƃ炶�T�[�o�ꗗ���擾���A ServerInfo�N���X���ێ�����ԑg���X�V����
	 * 
	 * @throws IOException
	 *             �ڑ��Ɏ��s�����Ǝv����ꍇ
	 */
	public void fetchServerInfo() throws IOException {
		synchronized (this) {
			// �ێ����Ă���T�[�o����������N���A����
			mServerList.clear();

			HttpURLConnection httpConnection = null;
			InputStream st = null;
			InputStreamReader sr = null;
			BufferedReader br = null;

			try {
				// �l�b�g���[�N����T�[�o�����擾����
				final URL url = new URL(NETLADIO_SERVER_DAT_URL);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();
				st = httpConnection.getInputStream();
				sr = new InputStreamReader(st, "Shift_JIS");
				br = new BufferedReader(sr);

				// ���
				String line;
				boolean header = true; // �w�b�_��
				while ((line = br.readLine()) != null) {
					if (line.length() == 0) {
						// ��s����������w�b�_�I��
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
