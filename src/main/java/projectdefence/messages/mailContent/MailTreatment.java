package projectdefence.messages.mailContent;

public class MailTreatment {
    public static String TREATMENT_MAIL = "Good day, you have treatment:<br>" +
            "<br><strong>Content</strong>: %s" +
            "<br><strong>Date added</strong>: " +
            "%s<br><strong>Visits</strong>:" +
            " %s<br><strong>Goal</strong>: %s" +
            "<br><strong>Duration</strong>: %s" +
            "<br><strong>By kinesitherapist</strong>: %s<br>" +
            "<br>You can check " +
            " this treatment at: <a href=\"http://localhost:8080/treatment/check/?username=%s\">Click here<a/>" +
            " <br>If you have any question, don't hesitate to contact us.<br>\n" +
            " mail: motorious.headquarters@gmail.com<br><br>" +
            " Best regards,<br><br> <strong>Motorious</strong> team.";
}
