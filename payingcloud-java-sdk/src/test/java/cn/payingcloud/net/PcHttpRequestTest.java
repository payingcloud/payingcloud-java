package cn.payingcloud.net;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YQ.Huang
 */
public class PcHttpRequestTest {
    @Test
    public void buildQuery() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("k1", "v1");
        params.put("k3", "v3");
        params.put("kn", "vn");
        params.put("k2", "v2");

//        String query = PcHttpRequest.buildQuery(params);
//
//        Assert.assertEquals("k1=v1&k2=v2&k3=v3&kn=vn", query);
    }

    @Test
    public void buildQuery_withEmptyParams() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();

//        String query = PcHttpRequest.buildQuery(params);
//
//        Assert.assertNull(query);
    }

    @Test
    public void buildQuery_withNullParams() throws Exception {
//        String query = PcHttpRequest.buildQuery(null);
//
//        Assert.assertNull(query);
    }

}