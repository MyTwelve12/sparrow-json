package com.sparrowjson.util;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/6
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/6       hll    新增              1001
 ********************************************************************/
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;

public class ChineseToPinyinUtil {
    // 匹配中英文标点符号
    public static String regex = "[\\p{P}\\p{IsPunctuation}，。！？；：（）《》“”‘’]";
    public static String toPinyin(String args) {
        String toPinyin = convertToPinyin(args);
        if (!StringUtils.isBlank(toPinyin)){
            toPinyin = toPinyin.replaceAll(regex,"").replaceAll(" ","");
        }
        return toPinyin;
    }

    /**
     * 获取拼音
     * @param chineseText
     * @return
     */
    public static String convertToPinyin(String chineseText) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);      // 小写拼音
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   // 不带音调
        format.setVCharType(HanyuPinyinVCharType.WITH_V);       // 使用 "v" 表示 "ü"

        StringBuilder pinyinText = new StringBuilder();

        for (char c : chineseText.toCharArray()) {
            try {
                // 检查是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    // 获取汉字的拼音
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyinArray != null) {
                        pinyinText.append(pinyinArray[0]).append(" ");
                    }
                } else {
                    // 非汉字字符直接添加
                    pinyinText.append(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pinyinText.toString().trim();
    }


}

