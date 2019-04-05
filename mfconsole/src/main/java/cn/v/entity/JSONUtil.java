package cn.v.entity;/**
 * Created by VLoye on 2019/4/5.
 */

/**
 * @author V
 * @Classname JSONUtil
 * @Description
 **/
public class JSONUtil {

    public static final String SEPARATOR = "    ";

    public static String toJsonFormat(String str) {
        int level = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
//            sb.append(c);
            switch (c) {
                case '{':
                case '[' :
                    sb.append(c);
                    level++;
                    newLine(sb, level);
                    break;
                case ']':
                case '}':
                    level--;
                    newLine(sb, level);
                    sb.append(c);
                    break;
                case ',':
                    sb.append(c);
                    newLine(sb,level);
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void newLine(StringBuilder sb, int level) {
        sb.append("\n");
        for (int i = 0; i < level; i++)
            sb.append(SEPARATOR);
    }
}
