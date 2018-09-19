package in.techfantasy.hope;
public class cardRequestItem {
    private String RequesteeName;
    private String RequesteeContact;
    private String DistanceFromMe;

    public cardRequestItem(){

    }

    public cardRequestItem(String requesteeName, String requesteeContact, String distanceFromMe) {
        RequesteeName = requesteeName;
        RequesteeContact = requesteeContact;
        DistanceFromMe = distanceFromMe;
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
