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
import javax.mail.internet.MimeMessage;

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


}
