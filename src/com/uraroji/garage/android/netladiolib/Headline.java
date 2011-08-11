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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ヘッドライン
 */
public class Headline {

	/**
	 * ねとらじのヘッドラインのURL DAT v2
	 */
	private static final String NETLADIO_HEADLINE_DAT_V2_URL = "http://yp.ladio.livedoor.jp/stats/list.v2.dat";

	/**
	 * ヘッドラインのソートの種類 NEWLY
	 */
	public static final int SORT_TYPE_NEWLY = 0;

	/**
	 * ヘッドラインのソートの種類 LISTENERS
	 */
	public static final int SORT_TYPE_LISTENERS = 1;
	
	/**
	 * ヘッドラインのソートの種類 TITLE
	 */
	public static final int SORT_TYPE_TITLE = 2;
	
	/**
	 * ヘッドラインのソートの種類 DJ
	 */
	public static final int SORT_TYPE_DJ = 3;

	/**
	 * 番組リスト
	 */
	private ArrayList<Channel> mChannelList = new ArrayList<Channel>();

	/**
	 * コンストラクタ
	 * 
	 * @param context
	 *            コンテキスト
	 */
	public Headline() {
	}

	/**
	 * 一致する再生URLを持つ番組を取得する
	 * 
	 * @param playUrl
	 *            再生URL
	 * @return Channel。見つからない場合はnull。
	 */
	public Channel getChannel(String playUrl) {
		if (playUrl == null || playUrl.length() == 0) {
			return null;
		}

		synchronized (this) {
			for (Channel c : mChannelList) {
				if (c.getPlayUrl() != null
						&& c.getPlayUrl().toString().equals(playUrl)) {
					return c;
				}
			}
		}

		return null;
	}

	/**
	 * 一致する再生URLを持つ番組を取得する
	 * 
	 * @param playUrl
	 *            再生URL
	 * @return Channel。見つからない場合はnull。
	 */
	public Channel getChannel(URL playUrl) {
		if (playUrl == null) {
			return null;
		}
		
		return getChannel(playUrl.toString());
	}

	private static final Pattern surlPattern = Pattern.compile("^SURL=(.*)");
	private static final Pattern timsPattern = Pattern.compile("^TIMS=(.*)");
	private static final Pattern srvPattern = Pattern.compile("^SRV=(.*)");
	private static final Pattern prtPattern = Pattern.compile("^PRT=(.*)");
	private static final Pattern mntPattern = Pattern.compile("^MNT=(.*)");
	private static final Pattern typePattern = Pattern.compile("^TYPE=(.*)");
	private static final Pattern namPattern = Pattern.compile("^NAM=(.*)");
	private static final Pattern gnlPattern = Pattern.compile("^GNL=(.*)");
	private static final Pattern descPattern = Pattern.compile("^DESC=(.*)");
	private static final Pattern djPattern = Pattern.compile("^DJ=(.*)");
	private static final Pattern songPattern = Pattern.compile("^SONG=(.*)");
	private static final Pattern urlPattern = Pattern.compile("^URL=(.*)");
	private static final Pattern clnPattern = Pattern.compile("^CLN=(\\d+)");
	private static final Pattern clnsPattern = Pattern.compile("^CLNS=(\\d+)");
	private static final Pattern maxPattern = Pattern.compile("^MAX=(\\d+)");
	private static final Pattern bitPattern = Pattern.compile("^BIT=(\\d+)");
	private static final Pattern smplPattern = Pattern.compile("^SMPL=(\\d+)");
	private static final Pattern chsPattern = Pattern.compile("^CHS=(\\d+)");

	/**
	 * 番組の一覧をインターネットから取得する
	 * 
	 * インターネットからねとらじ番組一覧を取得し、 Headlineクラスが保持する番組を更新する
	 * 
	 * @throws IOException
	 *             接続に失敗したと思われる場合
	 */
	public void fecthHeadline() throws IOException {
		synchronized (this) {
			// 保持している番組をいったんクリアする
			mChannelList.clear();
			
			HttpURLConnection httpConnection = null;
			InputStream st = null;
			InputStreamReader sr = null;
			BufferedReader br = null;

			try {
				// ネットワークからヘッドライン情報を取得する
				final URL url = new URL(NETLADIO_HEADLINE_DAT_V2_URL);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();
				st = httpConnection.getInputStream();
				sr = new InputStreamReader(st, "Shift_JIS");
				br = new BufferedReader(sr);

				Channel channel = null;

				// 解析
				String line;
				while ((line = br.readLine()) != null) {

					Matcher surlMatcher = surlPattern.matcher(line);
					if (surlMatcher.matches()) {
						try {
							if (surlMatcher.groupCount() >= 1
									&& surlMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setSurl(surlMatcher.group(1));
							}

							continue;
						} catch (MalformedURLException e) {
							;
						}
					}

					Matcher timsMatcher = timsPattern.matcher(line);
					if (timsMatcher.matches()) {
						try {
							if (timsMatcher.groupCount() >= 1
									&& timsMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setTims(timsMatcher.group(1));
							}

							continue;
						} catch (ParseException e) {
							;
						}
					}

					Matcher srvMatcher = srvPattern.matcher(line);
					if (srvMatcher.matches()) {
						if (srvMatcher.groupCount() >= 1
								&& srvMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setSrv(srvMatcher.group(1));
						}

						continue;
					}

					Matcher prtMatcher = prtPattern.matcher(line);
					if (prtMatcher.matches()) {
						try {
							if (prtMatcher.groupCount() >= 1
									&& prtMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setPrt(Integer.valueOf(prtMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					Matcher mntMatcher = mntPattern.matcher(line);
					if (mntMatcher.matches()) {
						if (mntMatcher.groupCount() >= 1
								&& mntMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setMnt(mntMatcher.group(1));
						}

						continue;
					}

					Matcher typeMatcher = typePattern.matcher(line);
					if (typeMatcher.matches()) {
						if (typeMatcher.groupCount() >= 1
								&& typeMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setType(typeMatcher.group(1));
						}

						continue;
					}

					Matcher namMatcher = namPattern.matcher(line);
					if (namMatcher.matches()) {
						if (namMatcher.groupCount() >= 1
								&& namMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setNam(namMatcher.group(1));
						}

						continue;
					}

					Matcher gnlMatcher = gnlPattern.matcher(line);
					if (gnlMatcher.matches()) {
						if (gnlMatcher.groupCount() >= 1
								&& gnlMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setGnl(gnlMatcher.group(1));
						}

						continue;
					}

					Matcher descMatcher = descPattern.matcher(line);
					if (descMatcher.matches()) {
						if (descMatcher.groupCount() >= 1
								&& descMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setDesc(descMatcher.group(1));
						}

						continue;
					}

					Matcher djMatcher = djPattern.matcher(line);
					if (djMatcher.matches()) {
						if (djMatcher.groupCount() >= 1
								&& djMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setDj(djMatcher.group(1));
						}

						continue;
					}

					Matcher songMatcher = songPattern.matcher(line);
					if (songMatcher.matches()) {
						if (songMatcher.groupCount() >= 1
								&& songMatcher.group(1).length() != 0) {
							if (channel == null) {
								channel = new Channel();
							}
							channel.setSong(songMatcher.group(1));
						}

						continue;
					}

					Matcher urlMatcher = urlPattern.matcher(line);
					if (urlMatcher.matches()) {
						try {
							if (urlMatcher.groupCount() >= 1
									&& urlMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setUrl(urlMatcher.group(1));
							}

							continue;
						} catch (MalformedURLException e) {
							;
						}
					}

					Matcher clnMatcher = clnPattern.matcher(line);
					if (clnMatcher.matches()) {
						try {
							if (clnMatcher.groupCount() >= 1
									&& clnMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setCln(Integer.valueOf(clnMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					Matcher clnsMatcher = clnsPattern.matcher(line);
					if (clnsMatcher.matches()) {
						try {
							if (clnsMatcher.groupCount() >= 1
									&& clnsMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setClns(Integer.valueOf(clnsMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					Matcher maxMatcher = maxPattern.matcher(line);
					if (maxMatcher.matches()) {
						try {
							if (maxMatcher.groupCount() >= 1
									&& maxMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setMax(Integer.valueOf(maxMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					Matcher bitMatcher = bitPattern.matcher(line);
					if (bitMatcher.matches()) {
						try {
							if (bitMatcher.groupCount() >= 1
									&& bitMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setBit(Integer.valueOf(bitMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					Matcher smplMatcher = smplPattern.matcher(line);
					if (smplMatcher.matches()) {
						try {
							if (smplMatcher.groupCount() >= 1
									&& smplMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setSmpl(Integer.valueOf(smplMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					Matcher chsMatcher = chsPattern.matcher(line);
					if (chsMatcher.matches()) {
						try {
							if (chsMatcher.groupCount() >= 1
									&& chsMatcher.group(1).length() != 0) {
								if (channel == null) {
									channel = new Channel();
								}
								channel.setChs(Integer.valueOf(chsMatcher
										.group(1)));
							}

							continue;
						} catch (NumberFormatException e) {
							;
						}
					}

					if (line.length() == 0 && channel != null) {
						mChannelList.add(channel);
						channel = null;
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

	/**
	 * 番組リストを取得する
	 * 
	 * @return 番組リスト
	 */
	public Channel[] getChannels() {
		return getChannels(SORT_TYPE_NEWLY);
	}

	/**
	 * 番組リストを取得する
	 * 
	 * @param sortType
	 *            ソート種類。ここで指定したソート種類に従って返り値の番組がソートされる。
	 * @return 番組リスト
	 */
	public Channel[] getChannels(int sortType) {
		return getChannels(sortType, null);
	}

	/**
	 * 番組リストを取得する
	 * 
	 * @param searchWord
	 *            フィルタリング単語。ここで指定した文字列をスペースに分かち書きしたそれぞれの文字列に対して、
	 *            すべてに合致する番組のみtrueを返す（AND検索）。
	 *            フィルタリング単語を指定しない場合は空も時価かnullを指定すること。
	 * @return 番組リスト
	 */
	public Channel[] getChannels(String searchWord) {
		return getChannels(SORT_TYPE_NEWLY, searchWord);
	}

	/**
	 * 番組リストを取得する
	 * 
	 * @param sortType
	 *            ソート種類。ここで指定したソート種類に従って返り値の番組がソートされる。
	 * @param searchWord
	 *            フィルタリング単語。ここで指定した文字列をスペースに分かち書きしたそれぞれの文字列に対して、
	 *            すべてに合致する番組のみtrueを返す（AND検索）。
	 *            フィルタリング単語を指定しない場合は空も時価かnullを指定すること。
	 * @return 番組リスト
	 */
	public Channel[] getChannels(int sortType, String searchWord) {
		ArrayList<Channel> list = null;

		synchronized (this) {
			list = new ArrayList<Channel>(mChannelList);
		}

		// フィルタリング
		if (searchWord != null && searchWord.length() != 0) {
			ArrayList<Channel> removeList = new ArrayList<Channel>();
			for (Channel channel : list) {
				if (channel.isMatch(searchWord) == false) {
					removeList.add(channel);
				}
			}
			if (removeList.size() != 0) {
				for (Channel removeCannel : removeList) {
					list.remove(removeCannel);
				}
			}
		}
		
		// ソート
		switch (sortType) {
		case SORT_TYPE_LISTENERS:
			Collections.sort(list, channelComparatorListeners);
			break;
		case SORT_TYPE_TITLE:
			Collections.sort(list, channelComparatorTitle);
			break;
		case SORT_TYPE_DJ:
			Collections.sort(list, channelComparatorDj);
			break;
		case SORT_TYPE_NEWLY:
			// Collections.sort(list, channelComparatorNewly);
			break;
		default:
			break;
		}

		return list.toArray(new Channel[list.size()]);
	}

	/**
	 * 保持している番組リストをクリアする
	 */
	public void clearChannels() {
		synchronized (this) {
			mChannelList.clear();
		}
	}
	
	private static final ChannelComparator channelComparatorListeners = new ChannelComparator(SORT_TYPE_LISTENERS);
	private static final ChannelComparator channelComparatorTitle = new ChannelComparator(SORT_TYPE_TITLE);
	private static final ChannelComparator channelComparatorDj = new ChannelComparator(SORT_TYPE_DJ);
	// private static final ChannelComparator channelComparatorNewly = new ChannelComparator(SORT_TYPE_NEWLY);
	
	/**
	 * 番組の比較
	 */
	private static class ChannelComparator implements Comparator<Channel> {
		/**
		 * ソート種類
		 */
		private int mSortType;

		/**
		 * コンストラクタ
		 * 
		 * @param sortType
		 *            ソート種類
		 */
		public ChannelComparator(int sortType) {
			this.mSortType = sortType;
		}

		@Override
		public int compare(Channel object1, Channel object2) {
			switch (mSortType) {
			case SORT_TYPE_LISTENERS:
				// 多い方が前に来る
				if (object1.getCln() < object2.getCln()) {
					return 1;
				} else if (object1.getCln() > object2.getCln()) {
					return -1;
				} else if (object1.getCln() == object2.getCln()) {
					return 0;
				}
			case SORT_TYPE_TITLE:
				if (object1.getNam() != null && object2.getNam() == null) {
					return -1;
				} else if (object1.getNam() == null && object2.getNam() != null) {
					return 1;
				} else if (object1.getNam() == null && object2.getNam() == null) {
					return 0;
				} else {
					return object1.getNam().compareTo(object2.getNam());
				}
			case SORT_TYPE_DJ:
				if (object1.getDj() != null && object2.getDj() == null) {
					return -1;
				} else if (object1.getDj() == null && object2.getDj() != null) {
					return 1;
				} else if (object1.getDj() == null && object2.getDj() == null) {
					return 0;
				} else {
					return object1.getDj().compareTo(object2.getDj());
				}
			case SORT_TYPE_NEWLY:
				if (object1.getTims() != null && object2.getTims() == null) {
					return -1;
				} else if (object1.getTims() == null
						&& object2.getTims() != null) {
					return 1;
				} else if (object1.getTims() == null
						&& object2.getTims() == null) {
					return 0;
				} else {
					// 新しい方が前に来る
					return object1.getTims().compareTo(object2.getTims()) * -1;
				}
			default:
				throw new IllegalStateException("Unknown sort type specified.");
			}
		}
	}
}
