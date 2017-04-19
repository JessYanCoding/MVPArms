package com.jess.arms.common.assist;

import android.content.Intent;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 辅助判断
 *
 * @author lujianzhao
 * @date 2013-6-10下午5:50:57
 */
public class Check {

	public static boolean isEmpty(CharSequence str) {
		return isNull(str) || str.length() == 0;
	}

	public static boolean isEmpty(Object[] os) {
		return isNull(os) || os.length == 0;
	}

	public static boolean isEmpty(Collection<?> l) {
		return isNull(l) || l.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> m) {
		return isNull(m) || m.isEmpty();
	}


	private static boolean isNull(Object o) {
		return o == null;
	}

	/**
	 * 是否是中国大陆加港澳台手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean isMobilePhoneNumber(String  mobile) {
		return Pattern.matches("^[1][3-8]\\d{9}$|^([6|9])\\d{7}$|^[0][9]\\d{8}$|^[6]([8|6])\\d{5}$", mobile);
	}

	/**
	 * 校验身份证
	 * @param idCard
	 * @return
     */
	public static boolean isIdCard(String  idCard) {
		return Pattern.matches("^\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x)$", idCard);
	}

	/**
	 * 校验护照
	 * @param passport
	 * @return
     */
	public static boolean isPassport(String  passport) {
		return Pattern.matches("^[a-zA-Z]{5,17}$|^[a-zA-Z0-9]{5,17}$", passport);
	}

	/**
	 * 校验港澳通行证验证
	 * @param passVerification
	 * @return
     */
	public static boolean isHKMacaopPassVerification(String  passVerification) {
		return Pattern.matches("^[HMhm]{1}([0-9]{10}|[0-9]{8})$", passVerification);
	}

	/**
	 * 校验台湾通行证验证
	 * @param passVerification
	 * @return
     */
	public static boolean isTaiwanPassVerification(String  passVerification) {
		return Pattern.matches("^[0-9]{8}$|^[0-9]{10}$", passVerification);
	}

	/**
	 * 判断intent和它的bundle是否为空
	 *
	 * @param intent
	 * @return
	 */
	public static boolean isBundleEmpty(Intent intent) {
		return (intent == null) || (intent.getExtras() == null);
	}
}
