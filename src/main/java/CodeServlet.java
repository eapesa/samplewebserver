import org.eclipse.jetty.http.HttpStatus;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class CodeServlet extends HttpServlet {

    private static String appname = "sample";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.addHeader("Content-Type", "application/json");
        try {
            String msisdn = req.getParameter("msisdn");
            if (msisdn.equals("")) {
                res.setStatus(HttpStatus.BAD_REQUEST_400);
                res.getWriter().println(HttpResponses.BADREQUEST_400());
                return;
            }
            String vcode = getCode(msisdn);
            res.addHeader("Content-Type", "application/json");
            res.setStatus(HttpStatus.OK_200);
            res.getWriter().println(HttpResponses.OK_200());
        } catch (NullPointerException e) {
            System.out.println(e.toString());
            res.setStatus(HttpStatus.BAD_REQUEST_400);
            res.getWriter().println(HttpResponses.BADREQUEST_400());
        }

    }

    private String getCode(String msisdn) {
        Jedis conn = CacheSingleton.getInstance().getConn();
        String key = appname + ":vcode:" + msisdn;
        String vcode = conn.get(key);
        if (vcode == null) {
            String newCode = generateCode();
            conn.setex(key, 300, newCode);
            return newCode;
        } else {
            return vcode;
        }
    }

    private String generateCode() {
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
                "abcdefghijklmnopqrstuvwxyz";
        int length = 6;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }
        return sb.toString();
    }

    private void sendVcode(String msisdn, String vcode) {
        String vcmUrl = "https://vcm.voyagerinnovation.com/v2/sms";
        String vcmAppname = "ansible-staging";
        String vcmAuthToken = "ZTI0MDM4NWMwMmVhNDIwYzg2YjUwNmFlZjhlMTk2NmI6NDZhMGQ2MjFjOGFiZDg3MA==";

    }
}
