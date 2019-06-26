package com.bawei.config;

/**
 * Created by yazhou on 2019/6/23.
 */
public class AlipayConfig {

    // 商户appid
    public static String APPID = "2016101100657259";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCbokhMxMILMbaw3Qkvnfec9U+kK0n8RQax/MUbM1uekABVeKPZxjcLhXngMGFF5ExdV/X/Q/UibOPyEUuQ4Y9cIz1UspKDy4SYttxSjGOdmKNvb7RVow1qWPZP30e5jcmEbU/99k7qpwfhK0kqWRwP1iLVweUI5PH2mdu6d3q1cxSuIWg0/zhoxDo527CbKI9wVU5OBLhhPpeFhFNPP3n0tUuj0vwgihipD4C1GltDwrVZHPCtfMfzijxBGVzGX63K0YEKaDtl1ZpO0mw2gzL5oU032Lr35AdrT+OsrykTjX+HaC4k2R28xNiviYGtDMNaFGaqgpb7adyqzEcLN2T7AgMBAAECggEAZDM9PT/IdpCRQJlumpunmgROPDIqtiJyxxEojfSo3hN0Q5MiWslFE0PiTbaYSflMOSSymqBUKM/WijkcX7D5Lab6i5dUwUm8mt3QR81fLU3im5Jdlm0d7AROwOnOMebZgUsnnyh/cpALgKrvtjYc3Q1O7DSjkvBj4BL65riZ5kupxv66TclF86G2j3rnte0e225hL00FrcUt5Fke4oChd1OJOFCHN6+rI4As5g4zpQFFg4RAA7MPFvAUwcus8f6uPad4TA4BOuKBxreeMrM7eNENddm/2xd05LvXkRHmTWmCXxXYuMl890ZnosBxpksVAgc9O7GjJHyr7c19Kx3H8QKBgQDVJhjWC4iC3FAcP7J2RErRVdcSPKcHbyi7MyzjrEzpmioUuGImdCvkF7VqgAunosCtqODpiUdBG0dZDHwhlBP+xJXelAdVpRqIDoyX+Jyc5xs1fqO+aPIT1/4Ke+f6KLTfFN2d4OQpRPi0YdTwtEf7K1LWMrTWT02r4LLbgCQAHwKBgQC67CCW172sQqRTdWCT8m/Gkemh+YVEi0PjeMDEfq4/S8UnMcLj92PIPJW/0iSeRvPqH8mm0ZRKtOHf59x+4mFtWc3zqzv28SL2VZ7zbOOAxrFNOxYnnkyh0x46CsCDXXHiyGD9/kR/Y+/rs5j+k9q2joT+ZnVWC90vpVwa4c6PpQKBgQCp5i8f1pueq5wTmFZZXQBCMmk3uS7uhxVxek5KwFmiTudCEuLxgeJGcvuBg8VGfz2UbyuwG2O3qtXJTmFXpp/1kJ9hhp7+DpfCYc6BiILVsoWGs4YzzZVAageuNavoRDBALXKlWNClcLvJlL3Wi+1gcN0brdllhlsEKRyISHDpwwKBgQCjO3cOIyKY3q1/0F3hdPJVdrCO6UaO4AsOBzJXpcTms3Vcz+DAkRaLExU2qP2fVpjEmLPRQO39+03Q6Tqtd2XbR3RJ2PwJnvSIJ6ciLrHRkDHfHr0vJdcH7kRULXWGgkDk6VWCGNyaXpxyuajekCGMEJNlUfYfiUbDx+TR+OUYnQKBgQCWNM62sL7kGCa1oYHsyS+vmPEUWYofY3Ma7dP/tEqlS14Tn7e8HBgfRVh/RSH9GLVQMKp6Q1yPeC7yplt4KOqslQg8hM7JzX8nBd+hGxY+jx+fMu4Mk3cOLe1Q38SJPIlHLbYNC5u5eG/1/LRtaIRZxiCcA5of6Gy5x43o/lT/9g==";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = " http://iehk9z.natappfree.cc/payservice/callback/async_callback";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http:/www.p2p.cn:30000/payservice/callback/sync_callback";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAngAgY0qwXZ3DBqrUaoI/DhToA0WoB12/gDk9g+q7jStADaLbfrKEOSHtNthN2tP7TvN3MnUjK6AKhfHafLWSZOLXOLW59Rpx8ffysXbuxS8+gTiuMTpPXY1notymDbuecIhdtnw0XfZ1LuM4ATeMVaQ65Pk79BCV3DlRL3KA0rVF99zkw9ButDREYn3XZmndpn0P92LQ6QUvqik2FDxtcxvwJlkASKXe7lsDiliH8HxrA1bHCjjhI7bHjJV0GF4BjSrps+gD5KiOXmbGsXOT01rrPMoE+btdCzFQQTnp//pILeqod0lzg82YvvFB3K9qlPxfMKKlSpRqfeo2UJIdsQIDAQAB";
    // 日志记录目录
    public static String log_path = "D:/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
