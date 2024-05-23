package com.example.terry;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Java library that converts Korean into Roman characters.
 * It is implemented based on the National Korean Language Romanization and can be covered a lot,
 * but it is not perfect because it is difficult to implement 100% if there is no word dictionary data due to the nature of Korean.
 */
public class KoreanRomanizer {
	private static final Pattern doubleSurnames = Pattern.compile("^(강전|남궁|독고|동방|등정|망절|무본|사공|서문|선우|소봉|어금|장곡|제갈|황목|황보)(.{1,10})$");

	private static final Map<String, String[]> typicalSurnameRules = new HashMap<String, String[]>() {
		{
			put("가", new String[] {"Ka", "Ga"});
			put("간", new String[] {"Kan", "Gan"});
			put("갈", new String[] {"Kal", "Gal"});
			put("감", new String[] {"Kam", "Gam"});
			put("강", new String[] {"Kang"});
			put("강전", new String[] {"Kangjun", "Gangjun"});
			put("견", new String[] {"Kyun", "Gyun"});
			put("경", new String[] {"Kyung", "Gyung"});
			put("계", new String[] {"Kye", "Gye"});
			put("고", new String[] {"Ko", "Go"});
			put("공", new String[] {"Kong", "Gong"});
			put("곽", new String[] {"Kwak", "Gwak"});
			put("구", new String[] {"Koo", "Goo"});
			put("국", new String[] {"Kook", "Gook"});
			put("군", new String[] {"Kun", "Gun"});
			put("궁", new String[] {"Koong", "Goong"});
			put("궉", new String[] {"Kwok", "Gwok"});
			put("권", new String[] {"Kwon", "Gwon"});
			put("근", new String[] {"Keun", "Geun"});
			put("금", new String[] {"Keum", "Geum"});
			put("기", new String[] {"Ki", "Gi"});
			put("길", new String[] {"Kil", "Gil"});
			put("김", new String[] {"Kim", "Gim"});
			put("남궁", new String[] {"Namgoong", "NamGung", "NamGKoong", "NamKung"});
			put("노", new String[] {"Noh", "Roh"});
			put("독고", new String[] {"DokGo", "Dokko"});
			put("두", new String[] {"Doo"});
			put("등정", new String[] {"DeungJeong", "DungJeoung"});
			put("란", new String[] {"Lan", "Ran"});
			put("뢰", new String[] {"Loi"});
			put("루", new String[] {"Lu"});
			put("망절", new String[] {"Mangjul"});
			put("명", new String[] {"Myung", "Myoung"});
			put("무본", new String[] {"Moobon", "Mubon"});
			put("문", new String[] {"Moon", "Mun"});
			put("박", new String[] {"Park", "Pak", "Bak"});
			put("변", new String[] {"Byun", "Byeon"});
			put("부", new String[] {"Boo"});
			put("사공", new String[] {"Sagong", "SaKong"});
			put("서", new String[] {"Seo", "Suh"});
			put("서문", new String[] {"Seomoon", "Seomun", "Suhmun", "Suhmoon"});
			put("선", new String[] {"Sun", "Seon"});
			put("선우", new String[] {"Sunwoo", "Sunwu", "Seonwoo", "Seunwu"});
			put("성", new String[] {"Sung", "Seong"});
			put("소봉", new String[] {"Sobong", "Sopong"});
			put("순", new String[] {"Soon", "Sun"});
			put("신", new String[] {"Shin"});
			put("심", new String[] {"Shim", "Sim"});
			put("아", new String[] {"Ah", "A"});
			put("어금", new String[] {"Eokum", "Eogum", "Eogeum"});
			put("오", new String[] {"Oh", "O"});
			put("우", new String[] {"Woo"});
			put("운", new String[] {"Woon", "Wun"});
			put("유", new String[] {"Yoo", "Yu"});
			put("윤", new String[] {"Yoon", "Yun"});
			put("이", new String[] {"Lee", "Yi", "I"});
			put("임", new String[] {"Lim", "Im"});
			put("장곡", new String[] {"Janggok", "Jangkok"});
			put("정", new String[] {"Jung", "Jeong"});
			put("제갈", new String[] {"Jegal", "Jekal"});
			put("조", new String[] {"Cho", "Jo"});
			put("주", new String[] {"Joo", "Ju", "Choo"});
			put("준", new String[] {"June", "Jun", "Joon"});
			put("즙", new String[] {"Chup", "Jeup"});
			put("최", new String[] {"Choi"});
			put("편", new String[] {"Pyun", "Pyeon"});
			put("평", new String[] {"Pyung", "Pyeong"});
			put("풍", new String[] {"Poong", "Pung"});
			put("현", new String[] {"Hyun", "Hyoen"});
			put("형", new String[] {"Hyung", "Heyoung"});
			put("황", new String[] {"Hwang"});
			put("황목", new String[] {"Hwangmok"});
			put("황보", new String[] {"Hwangbo", "Hwangpo"});
			put("흥", new String[] {"Hong", "Heoung"});

		}
	};

	/**
	 * Romanize the surname.
	 *
	 * @param fullname the name string to normalize.
	 * @return the list of normalized names.
	 */
	public static List<String> romanizeSurName(String fullname) {
		Objects.requireNonNull(fullname, "String should not be null.");

		final Matcher matcher = doubleSurnames.matcher(fullname);

		LinkedList<String> fullNames = new LinkedList<>();

		if (matcher.find()) {
			final String surname = matcher.group(1);
			final List<String> givenNames = romanizeGivenName(matcher.group(2));

			final String[] surnames = typicalSurnameRules.get(surname);
			if (surnames == null) {
				for (final String givenTemp : givenNames) {
					fullNames.add(givenTemp);
				}
			} else  {
				for (final String surTemp : surnames)  {
					for (final String givenTemp : givenNames) {
						fullNames.add(surTemp.toUpperCase() + " " + givenTemp);
					}
				}
			}
		}

		final List<String> surnamesTemp;

		final String[] surnames = typicalSurnameRules.get(fullname.substring(0, 1));
		if (surnames == null) {
			surnamesTemp = romanizeGivenName(fullname.substring(0, 1));
		} else {
			surnamesTemp = new ArrayList<>();
		}
		final List<String> givenNames = romanizeGivenName(fullname.substring(1));

		if (surnamesTemp != null) {
			for (final String surnameTemp : surnamesTemp) {
				for (final String givenTemp : givenNames) {
					fullNames.add(surnameTemp.toUpperCase() + " " + givenTemp);
				}
			}
		}

		if (surnames != null) {
			for (final String surTemp : surnames)  {
				for (final String givenTemp : givenNames) {
					fullNames.add(surTemp.toUpperCase() + " " + givenTemp);
				}
			}
		} else if(fullNames.isEmpty()){
			for (final String fullTemp : romanizeGivenName(fullname)) {
				fullNames.add(fullTemp);
			}
		}



		return fullNames;
	}

	/**
	 * Romanize the given name.
	 *
	 * @param givenName the given name to romanize.
	 * @return the romanized given name.
	 */

	private static List<String> romanizeGivenName(String givenName) {
		final List<String> buffer = new ArrayList<>();

		KoreanCharacter currentCharacter = null;
		KoreanCharacter nextCharacter = null;

		for (int i = 0; i < givenName.length(); i++) {
			final KoreanCharacter prevCharacter = currentCharacter;
			currentCharacter = (nextCharacter == null) ? new KoreanCharacter(givenName.charAt(i)) : nextCharacter;
			nextCharacter = (i < givenName.length() - 1) ? new KoreanCharacter(givenName.charAt(i + 1)) : null;

			// Assuming KoreanCharacter has a method getRomanizedString
			final List<String> oneCharName = currentCharacter.getRomanizedString(prevCharacter, nextCharacter);
			List<String> newBuffer = new ArrayList<>();

			for (final String temp : oneCharName) {
				if (buffer.isEmpty()) {
					newBuffer.add(temp.toUpperCase());
				} else {
					for (final String existing : buffer) {
						newBuffer.add(existing + temp.toUpperCase());
					}
				}
			}
			buffer.clear();
			buffer.addAll(newBuffer);
		}

		return buffer;
	}
}
