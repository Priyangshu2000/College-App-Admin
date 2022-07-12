package com.example.collegeapp_admin.Notice;

public class NoticeModel {
    private String heading;
    private String body;
    private String userPic;
    private String noticePic;
    public NoticeModel(String heading, String body, String userPic, String noticePic, String noticeId) {
        this.heading = heading;
        this.body = body;
        this.userPic = userPic;
        this.noticePic = noticePic;
        NoticeId = noticeId;
    }

    public String getNoticePic() {
        return noticePic;
    }

    public void setNoticePic(String noticePic) {
        this.noticePic = noticePic;
    }



    public NoticeModel(String heading, String body, String noticeId, String userPic) {
        this.heading = heading;
        this.body = body;
        NoticeId = noticeId;
        this.userPic = userPic;
    }
    public NoticeModel() {
    }


    public String getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(String noticeId) {
        NoticeId = noticeId;
    }

    private String NoticeId;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }


}
