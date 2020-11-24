package com.example.l_cure;

public class Hangul {

    // ㄱ             ㄲ            ㄴ               ㄷ            ㄸ             ㄹ
    // ㅁ             ㅂ            ㅃ               ㅅ            ㅆ             ㅇ
    // ㅈ             ㅉ           ㅊ                ㅋ            ㅌ              ㅍ      ㅎ
    final static char[] ChoSung = {0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139,
            0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147,
            0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};

    final static String[] ChoSungEng = {"r", "R", "s", "e", "E", "f",
            "a", "q", "Q", "t", "T", "d",
            "w", "W", "c", "z", "x", "v", "g"};

    // ㅏ            ㅐ             ㅑ             ㅒ            ㅓ             ㅔ
    // ㅕ            ㅖ              ㅗ           ㅘ            ㅙ              ㅚ
    // ㅛ            ㅜ              ㅝ           ㅞ             ㅟ             ㅠ
    // ㅡ           ㅢ              ㅣ
    final static char[] JwungSung = {0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154,
            0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
            0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160,
            0x3161, 0x3162, 0x3163};

    final static String[] JwungSungEng = {"k", "o", "i", "O", "j", "p",
            "u", "P", "h", "hk", "ho", "hl",
            "y", "n", "nj", "np", "nl", "b",
            "m", "ml", "l"};

    //         ㄱ            ㄲ             ㄳ            ㄴ              ㄵ
    // ㄶ             ㄷ            ㄹ             ㄺ            ㄻ              ㄼ
    // ㄽ             ㄾ            ㄿ              ㅀ            ㅁ             ㅂ
    // ㅄ            ㅅ             ㅆ             ㅇ            ㅈ             ㅊ
    // ㅋ            ㅌ            ㅍ              ㅎ
    final static char[] JongSung = {0, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135,
            0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
            0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142,
            0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a,
            0x314b, 0x314c, 0x314d, 0x314e};

    final static String[] JongSungEng = {"", "r", "R", "rt", "s", "sw",
            "sg", "e", "f", "fr", "fa", "fq",
            "ft", "fx", "fv", "fg", "a", "q",
            "qt", "t", "T", "d", "w", "c",
            "z", "x", "v", "g"};


    public static String hangulToJaso(String s) {

        int a, b, c; // 자소 버퍼: 초성/중성/종성 순
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch >= 0xAC00 && ch <= 0xD7A3) { // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
                c = ch - 0xAC00;
                a = c / (21 * 28);
                c = c % (21 * 28);
                b = c / 28;
                c = c % 28;

                result = result + ChoSung[a] + JwungSung[b];
                if (c != 0) result = result + JongSung[c]; // c가 0이 아니면, 즉 받침이 있으면
            } else {
                result = result + ch;
            }
        }
        return result;
    }
}