package com.boco.routesample.utils;



import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES { 
	private static String ivParameter = "0392039203920300";
	
    private static String KEY = "!BM2P@w1sxO*(-8L";
    /** 
      * 加密 
     *  
      * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
      */   
     public static String encrypt(String content) {   
             try {      
            	 
            	
//                     KeyGenerator kgen = KeyGenerator.getInstance("AES");   
//                     kgen.init(128, new SecureRandom(password.getBytes()));   
//                     SecretKey secretKey = kgen.generateKey();   
//                     byte[] enCodeFormat = secretKey.getEncoded();   
//                     SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");   
            	 byte[] raw = KEY.getBytes();
         		SecretKeySpec key = new SecretKeySpec(raw, "AES");
                     byte[] byteContent = content.getBytes("utf-8");   
                     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                     IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                     cipher.init(Cipher.ENCRYPT_MODE, key,iv);// 初始化   
                     byte[] result = cipher.doFinal(byteContent);   
                    String s= parseByte2HexStr(result);  
                     return new String(Base64.encodeBase64(s.getBytes("utf-8")),"utf-8");// 加密
             } catch (NoSuchAlgorithmException e) {   
                     e.printStackTrace();   
             } catch (NoSuchPaddingException e) {   
                     e.printStackTrace();   
             } catch (InvalidKeyException e) {   
                     e.printStackTrace();   
             } catch (UnsupportedEncodingException e) {   
                     e.printStackTrace();   
             } catch (IllegalBlockSizeException e) {   
                     e.printStackTrace();   
             } catch (BadPaddingException e) {   
                     e.printStackTrace();   
             }   
               catch(Exception ex)
             {
            	 ex.printStackTrace();
             }
             return null;   
     }   
       
     
     
     public static byte[] encrypt(byte[] byteContent, String password) {   
         try {              
//                 KeyGenerator kgen = KeyGenerator.getInstance("AES");   
//                 kgen.init(128, new SecureRandom(password.getBytes()));   
//                 SecretKey secretKey = kgen.generateKey();   
//                 byte[] enCodeFormat = secretKey.getEncoded();   
//                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");   
        	 byte[] raw = password.getBytes();
      		SecretKeySpec key = new SecretKeySpec(raw, "AES");
                 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                 IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                 cipher.init(Cipher.ENCRYPT_MODE, key,iv);// 初始化   
                 
                 
                 
                 byte[] result = cipher.doFinal(byteContent);   
                String s= parseByte2HexStr(result); 
                 return Base64.encodeBase64(s.getBytes("utf-8"));// 加密   
                 
                 //return Base64.encodeBase64(result);
         } catch (NoSuchAlgorithmException e) {   
                 e.printStackTrace();   
         } catch (NoSuchPaddingException e) {   
                 e.printStackTrace();   
         } catch (InvalidKeyException e) {   
                 e.printStackTrace();   
         }
         catch (IllegalBlockSizeException e) {   
                 e.printStackTrace();   
         } catch (BadPaddingException e) {   
                 e.printStackTrace();   
         }   
         catch(Exception ex)
         {
        	 ex.printStackTrace();
         }
         return null;   
 }   
     /**解密 
      * @param content  待解密内容 
      * @param password 解密密钥 
      * @return 
       */   
      public static String decrypt(String content) {   
              try {   
            	  
            	  
            	  byte[] str=Base64.decodeBase64(content.getBytes("utf-8"));
            	  String buf= new String(str,"utf-8");
            	  System.out.println(buf);
            	  
            	   str= parseHexStr2Byte(buf);
            	  
//                       KeyGenerator kgen = KeyGenerator.getInstance("AES");   
//                       kgen.init(128, new SecureRandom(password.getBytes()));   
//                       SecretKey secretKey = kgen.generateKey();   
//                       byte[] enCodeFormat = secretKey.getEncoded();   
//                       SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");        
            	   byte[] raw = KEY.getBytes("UTF-8");
            		SecretKeySpec key = new SecretKeySpec(raw, "AES");
                       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                       IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                      cipher.init(Cipher.DECRYPT_MODE, key,iv);// 初始化   
                      byte[] result = cipher.doFinal(str);   
                      return new String(result,"utf-8"); // 加密   
              } catch (NoSuchAlgorithmException e) {   
                      e.printStackTrace();   
              } catch (NoSuchPaddingException e) {   
                      e.printStackTrace();   
              } catch (InvalidKeyException e) {   
                      e.printStackTrace();   
              } catch (IllegalBlockSizeException e) {   
                      e.printStackTrace();   
              } catch (BadPaddingException e) {   
                      e.printStackTrace();   
              }  catch(Exception e){
            	  e.printStackTrace(); 
              }
              
              return null;   
      }  
        
      
      
      public static byte[] decrypt(byte[] buf) {   
          try {   
        	  
        	  
        	  byte[] str=Base64.decodeBase64(buf);
        	  //String buf= new String(str,"utf-8");
        	  
        byte[] str1= parseHexStr2Byte(new String(str,"utf-8"));
        	  
//                   KeyGenerator kgen = KeyGenerator.getInstance("AES");   
//                   kgen.init(128, new SecureRandom(password.getBytes()));   
//                   SecretKey secretKey = kgen.generateKey();   
//                   byte[] enCodeFormat = secretKey.getEncoded();   
//                   SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");     
        byte[] raw = KEY.getBytes();
 		SecretKeySpec key = new SecretKeySpec(raw, "AES");
                   Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                   
                   IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                   cipher.init(Cipher.DECRYPT_MODE, key,iv);// 初始化
                     
                  byte[] result = cipher.doFinal(str1);   
                  return result; // 加密   
          } catch (NoSuchAlgorithmException e) {   
                  e.printStackTrace();   
          } catch (NoSuchPaddingException e) {   
                  e.printStackTrace();   
          } catch (InvalidKeyException e) {   
                  e.printStackTrace();   
          } catch (IllegalBlockSizeException e) {   
                  e.printStackTrace();   
          } catch (BadPaddingException e) {   
                  e.printStackTrace();   
          }  catch(Exception e){
        	  e.printStackTrace(); 
          }
 
          
          return null;   
  }  
      
      
      public static String parseByte2HexStr(byte buf[]) {   
          StringBuffer sb = new StringBuffer();   
          for (int i = 0; i < buf.length; i++) {   
                  String hex = Integer.toHexString(buf[i] & 0xFF);   
                  if (hex.length() == 1) {   
                          hex = '0' + hex;   
                  }   
                //  sb.append("");
            sb.append(hex.toUpperCase());   
          }   
          return sb.toString();   
  }   
      /**将二进制转换成16进制 
       * @param buf 
        * @return 
        */   

         
       /**将16进制转换为二进制 
        * @param hexStr 
         * @return 
         */   
        public static byte[] parseHexStr2Byte(String hexStr) {   
                if (hexStr.length() < 1)   
                        return null;   
                byte[] result = new byte[hexStr.length()/2];   
                for (int i = 0;i< hexStr.length()/2; i++) {   
                        int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);   
                        int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);   
                        result[i] = (byte) (high * 16 + low);   
                }   
                return result;   
        }   
          
        /** 
           * 加密 
          * 
           * @param content 需要加密的内容 
          * @param password  加密密码 
          * @return 
           */   
          public static byte[] encrypt2(String content) {   
                  try {   
                          SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "AES");   
                          Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");   
                          byte[] byteContent = content.getBytes("utf-8");   
                          cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
                          byte[] result = cipher.doFinal(byteContent);   
                          return result; // 加密   
                  } catch (NoSuchAlgorithmException e) {   
                          e.printStackTrace();   
                  } catch (NoSuchPaddingException e) {   
                          e.printStackTrace();   
                  } catch (InvalidKeyException e) {   
                          e.printStackTrace();   
                  } catch (UnsupportedEncodingException e) {   
                          e.printStackTrace();   
                  } catch (IllegalBlockSizeException e) {   
                          e.printStackTrace();   
                  } catch (BadPaddingException e) {   
                          e.printStackTrace();   
                  }   
                  return null;   
          }   
          

        
      public static void main(String[] args) throws UnsupportedEncodingException {  
		String content = "{ID:\"123\",USERNAME:\"TEST\"}";
		// 加密
		System.out.println("加密前：" + content);
     
        String str = encrypt(content);
		// 解密
		String sourcestr = decrypt(
				str);
		System.out.println("解密后：" + sourcestr);
    }  
}  