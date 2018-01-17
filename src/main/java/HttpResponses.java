import org.json.JSONObject;

public class HttpResponses {

    private static String reply(String code, String message) {
        JSONObject reply = new JSONObject();
        reply.put("code", code);
        reply.put("message", message);
        return reply.toString();
    }

    public static String OK_200() {
        return reply("OK", "Operation completed successfully");
    }

    public static String BADREQUEST_400() {
        return reply("BadRequest", "Operation completed successfully");
    }
}
