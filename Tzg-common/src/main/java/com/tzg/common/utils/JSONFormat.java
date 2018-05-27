package com.tzg.common.utils;

public class JSONFormat  
{  
    /** 
     * 单位缩进字符串。 
     */  
    private static String SPACE = "   ";  
      
    /**
     * 返回格式化JSON字符串。  
     * @param json
     * @return
     */
    public static String format(String json) {  
        StringBuffer result = new StringBuffer();  
        int length = json.length();  
        int number = 0;  
        char key = 0;  
          
        for (int i = 0; i < length; i++)  {  //遍历输入字符串。
            key = json.charAt(i);  //1、获取当前字符。   
            if((key == '[') || (key == '{') ) {   //2、如果当前字符是前方括号、前花括号做如下处理：  
                if((i - 1 > 0) && (json.charAt(i - 1) == ':'))  {   //（1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。 
                    result.append('\n');  
                    result.append(indent(number));  
                }  
                result.append(key);  //（2）打印：当前字符。  
                result.append('\n');  //（3）前方括号、前花括号，的后面必须换行。打印：换行。
                number++;   //（4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。  
                result.append(indent(number));  
                continue;    //（5）进行下一次循环。  
            }  
            //3、如果当前字符是后方括号、后花括号做如下处理：  
            if((key == ']') || (key == '}') ){  
                result.append('\n');   //（1）后方括号、后花括号，的前面必须换行。打印：换行。  
                number--;  
                result.append(indent(number));   //（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。  
                result.append(key); //（3）打印：当前字符。  
                if(((i + 1) < length) && (json.charAt(i + 1) != ',')){ //（4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。   
                    result.append('\n');  
                }  
                continue;   //（5）继续下一次循环。  
            }  
            //4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。  
            if((key == ',')) {  
                result.append(key);  
                result.append('\n');  
                result.append(indent(number));  
                continue;  
            }  
            result.append(key);  
        }  
        return result.toString();  
    }  
      
    /** 
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。 
     *  
     * @param number 缩进次数。 
     * @return 指定缩进次数的字符串。 
     */  
    private static String indent(int number)  
    {  
        StringBuffer result = new StringBuffer();  
        for(int i = 0; i < number; i++)  
        {  
            result.append(SPACE);  
        }  
        return result.toString();  
    }  
}  