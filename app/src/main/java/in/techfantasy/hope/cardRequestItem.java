package in.techfantasy.hope;
public class cardRequestItem {
    private String RequesteeName;
    private String RequesteeContact;
    private String DistanceFromMe;
    private String Request;
    private String RequestID;

    public cardRequestItem(){

    }

    public cardRequestItem(String requesteeName, String requesteeContact, String distanceFromMe,String Req,String reqID) {
        RequesteeName = requesteeName;
        RequesteeContact = requesteeContact;
        DistanceFromMe = distanceFromMe;
        Request = Req;
        RequestID = reqID;
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getRequesteeName() {
        return RequesteeName;
    }

    public void setRequesteeName(String requesteeName) {
        RequesteeName = requesteeName;
    }

    public String getRequesteeContact() {
        return RequesteeContact;
    }

    public void setRequesteeContact(String requesteeContact) {
        RequesteeContact = requesteeContact;
    }

    public String getDistanceFromMe() {
        return DistanceFromMe;
    }

    public void setDistanceFromMe(String distanceFromMe) {
        DistanceFromMe = distanceFromMe;
    }
}
