package com.huiyu.tech.zhongxing.utils.pinyin;


import com.huiyu.tech.zhongxing.models.ContactsModel;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator2 implements Comparator<ContactsModel> {

	public int compare(ContactsModel o1, ContactsModel o2) {
		if (o1.getSortLetters().equals("☆") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("☆")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
