package kamel.unoengine.concretion.comment;

public enum Comments {
    UNO("Uno"),
    PASS("Pass"),
    DRAW("Draw");

    private String commentText;

    Comments(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }
}
