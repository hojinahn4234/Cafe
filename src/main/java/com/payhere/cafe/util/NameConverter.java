package com.payhere.cafe.util;

public class NameConverter {
    private static final int KOR_BASE = 0xAC00;
    private static String[] consonants = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ" , "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

    public static String toConsonant(String str) {
        if (str == null) return null;

        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c >= KOR_BASE) {
                // 한글 첫 글자 (가) 유니코드를 기준으로 몇 번째 글자인지
                c = (char) (c - KOR_BASE);
                // 한글 초성으로만 위치를 판단
                char consonant = (char)(c/28/21);
                // 문자열 추가
                sb.append(consonants[consonant]);
            } else {
                // 한글이 아닌 경우 그대로 문자열 추가
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
