
package JavaMail;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService implements IJavaMail {
	String to ="21130553@st.hcmuaf.edu.vn";
    @Override
    public boolean send(String subject, String messageContent) {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailProperty.HOST_NAME); // smtp.gmail.com
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Kích hoạt STARTTLS
        props.put("mail.smtp.port", EmailProperty.TSL_PORT); // Cổng 587
        props.put("mail.smtp.ssl.trust", EmailProperty.HOST_NAME); // Chứng thực SSL

        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("javax.net.ssl.SSLContext", "TLSv1.2");

        // get Session
        Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication()  {
                return new javax.mail.PasswordAuthentication(EmailProperty.APP_EMAIL,EmailProperty.APP_PASSWORD);
            }

        });


        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            ((MimeMessage) message).setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(subject);

            message.setText(messageContent);
            // send message
            Transport.send(message);
            return true;
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String to ="21130463@st.hcmuaf.edu.vn";
        String subject="Đăng ký nhận thư thành công";
        String message = "Cảm ơn bạn đã đăng ký nhận thư";
        IJavaMail emailService = new EmailService();
        emailService.send(subject,message);
    }
}


