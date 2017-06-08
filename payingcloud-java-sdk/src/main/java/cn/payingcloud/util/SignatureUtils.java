package cn.payingcloud.util;

import cn.payingcloud.PayingCloud;
import cn.payingcloud.net.PcClientException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;

/**
 * @author YQ.Huang
 */
public class SignatureUtils {

    public static String buildQuery(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            Object value = params.get(key);
            builder.append(key).append('=').append(value).append('&');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String sign(String key, String verb, String resource, String body, String timestamp) {
        return HmacUtils.hmacSha1Hex(key, verb + "\n" +
                resource + "\n" +
                body + "\n" +
                timestamp + "\n");
    }

    public static boolean verify(String content, String sign) throws SignatureException {
        return sha1Verify(content, sign, "UTF-8");
    }

    public static boolean sha1Verify(String content, String sign, String charset) throws SignatureException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(PayingCloud.DEFAULT_PUBLIC_KEY);
            PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(key);
            signature.update(content.getBytes(charset));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (NoSuchAlgorithmException e) {
            throw new PcClientException(e);
        } catch (UnsupportedEncodingException e) {
            throw new PcClientException(e);
        } catch (InvalidKeyException e) {
            throw new PcClientException(e);
        } catch (InvalidKeySpecException e) {
            throw new PcClientException(e);
        } catch (SignatureException e) {
            throw new PcClientException(e);
        }
    }
}
