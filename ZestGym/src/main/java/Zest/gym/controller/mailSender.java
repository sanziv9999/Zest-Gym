package Zest.gym.controller;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import Zest.gym.model.Schedule;

public class MailSender {
	
   final String username = "zestgym7@gmail.com";
   final String password = "xvyucjfrqzmqbnhq";
	
	public int sendOtp(String recipientEmail) {

        // Generate 4-digit OTP
        int otp = OtpCodeGenerator.generateUniqueNumber();

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("zestgym7@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject("ZestGym pasword reset otp");

            // Include the OTP in the email body
            message.setText("Your OTP for password reset is: " + otp);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return otp;
    }
	
	public void sendPasswordChangeMessage(String recipientEmail) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("zestgym7@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject("Password Changed Successfully");

            // Include the OTP in the email body
            message.setText("Your password has been changed successfully.");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void sendTrainerScheduleMessage(String recipientEmail,String trainerName, List<Schedule> scheduleList) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("zestgym7@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject("Your Updated Schedule at Zest Gym");

            // Build email body with the schedule details
            StringBuilder emailBody = new StringBuilder();
            emailBody.append("Dear ").append(trainerName).append(",\n\n")
                     .append("Here is your updated schedule at Zest Gym:\n\n");

            for (Schedule schedule : scheduleList) {
                emailBody.append("Activity: ").append(schedule.getActivity()).append("\n")
                         .append("Day: ").append(schedule.getDay()).append("\n")
                         .append("Time Slot: ").append(schedule.getTimeSlot()).append("\n")
                         .append("Trainer: ").append(schedule.getTrainer()).append("\n\n");
            }

            emailBody.append("Please ensure you are available at the scheduled times.\n\n")
                     .append("Best regards,\n")
                     .append("Zest Gym Team");

            // Set email content
            message.setText(emailBody.toString());

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
	
	public void sendEnrollmentEmail(String recipientEmail, String userName, Schedule schedule) {
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	    });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("zestgym7@gmail.com"));
	        message.setRecipients(
	                Message.RecipientType.TO,
	                InternetAddress.parse(recipientEmail)
	        );
	        message.setSubject("Enrollment Confirmation - Zest Gym");

	        // Build the email body
	        StringBuilder emailBody = new StringBuilder();
	        emailBody.append("Dear ").append(userName).append(",\n\n")
	                 .append("Congratulations! You have successfully enrolled in the following activity at Zest Gym:\n\n")
	                 .append("Activity: ").append(schedule.getActivity()).append("\n")
	                 .append("Day: ").append(schedule.getDay()).append("\n")
	                 .append("Time Slot: ").append(schedule.getTimeSlot()).append("\n")
	                 .append("Trainer: ").append(schedule.getTrainer()).append("\n\n")
	                 .append("We look forward to seeing you in your scheduled session!\n\n")
	                 .append("Best regards,\n")
	                 .append("Zest Gym Team");

	        // Set email content
	        message.setText(emailBody.toString());

	        // Send the email
	        Transport.send(message);
	        System.out.println("Enrollment email sent successfully!");

	    } catch (MessagingException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to send email: " + e.getMessage());
	    }
	}
	
	public void sendMembershipBookingMessage(String recipientEmail, String membershipPlan) {
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");


	    Session session = Session.getInstance(props, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	    });

	    try {
	        // Extract membership details
	        String membershipName = extractName(membershipPlan);
	        String membershipPrice = extractPrice(membershipPlan);
	        String membershipDuration = extractDuration(membershipPlan);

	        // Create the email message
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("zestgym7@gmail.com"));
	        message.setRecipients(
	                Message.RecipientType.TO,
	                InternetAddress.parse(recipientEmail)
	        );
	        message.setSubject("Membership Booking Confirmation - Pending Payment");

	        // Email body
	        String emailBody = "Dear Member,\n\n"
	                         + "Thank you for booking your membership at Zest Gym.\n\n"
	                         + "Here are your membership details:\n"
	                         + "Membership Name: " + membershipName + "\n"
	                         + "Price: " + membershipPrice + "\n"
	                         + "Duration: " + membershipDuration + "\n\n"
	                         + "Please note that your membership booking is currently pending. "
	                         + "To confirm your booking, please complete the payment using the QR code attached to this email.\n\n"
	                         + "We look forward to welcoming you to the Zest Gym family.\n\n"
	                         + "Best regards,\n"
	                         + "Zest Gym Team";

	        // Create a multipart message
	        MimeMultipart multipart = new MimeMultipart();

	        // Add text content as the first part
	        MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setText(emailBody);
	        multipart.addBodyPart(textPart);

	        // Add the QR code image as the second part
	        MimeBodyPart imagePart = new MimeBodyPart();
	        String qrImagePath = getClass().getClassLoader().getResource("static/assets/QR.jpg").getPath();
	        File qrFile = new File(qrImagePath);
	        if (qrFile.exists()) {
	            imagePart.attachFile(qrFile);
	            imagePart.setContentID("<qrImage>"); // Optional for inline use
	            imagePart.setDisposition(MimeBodyPart.ATTACHMENT); // Mark as an attachment
	            multipart.addBodyPart(imagePart);
	        } else {
	            throw new RuntimeException("QR code image file not found at path: " + qrImagePath);
	        }

	        // Set the content of the message
	        message.setContent(multipart);

	        // Send the email
	        Transport.send(message);

	        System.out.println("Membership booking email sent successfully with QR code!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to send email: " + e.getMessage());
	    }
	}

	private String extractName(String membershipPlan) {
	    int endIndex = membershipPlan.indexOf('-');
	    if (endIndex > 0) {
	        return membershipPlan.substring(0, endIndex).trim(); // Extract name before the first '-'
	    }
	    return membershipPlan.trim(); // Return the whole string if no '-' is found
	}

	private String extractPrice(String membershipPlan) {
	    int startIndex = membershipPlan.lastIndexOf('$') + 1;
	    int endIndex = membershipPlan.indexOf('-', startIndex);
	    if (startIndex < endIndex && startIndex >= 0) {
	        return membershipPlan.substring(startIndex, endIndex).trim(); // Extract price between '$' and '-'
	    }
	    return ""; // Return empty string if extraction fails
	}

	private String extractDuration(String membershipPlan) {
	    int startIndex = membershipPlan.lastIndexOf('-') + 1;
	    if (startIndex > 0 && startIndex < membershipPlan.length()) {
	        return membershipPlan.substring(startIndex).trim(); // Extract duration after the last '-'
	    }
	    return ""; // Return empty string if extraction fails
	}

}
