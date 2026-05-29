import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Write a description of class GymMember here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class GymMember
{
    // Common attributes for all gym members
    protected String memberId;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected LocalDate dob;
    protected LocalDate membershipStartDate;
    protected String referralSource;
    protected boolean activeStatus;
    protected ArrayList<LocalDate> attendanceDates;
    protected LocalDate DateTimeFormatter;
    

    // Constructor
    public GymMember(String memberId, String name, String location, String phone,
                     String email, String gender, LocalDate dob,
                     LocalDate membershipStartDate, String referralSource){
        this.memberId = memberId;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.membershipStartDate = membershipStartDate;
        this.referralSource = referralSource;
        this.activeStatus = true; // By default, members are active when added
        this.attendanceDates = new ArrayList<>();
        this.DateTimeFormatter= DateTimeFormatter;
    }

    // Abstract methods to be implemented by child classes
    public abstract double calculatePrice();
    
     public String display() {
        return "MemberID: " + memberId + "\nName: " + name + "\nPhone" + phone + "\nLocation:" + location+ "\nEmail: " + email + "\nGender: "+ gender + "\nDOB:" + dob + "\nMembershipStartDate:" 
        + membershipStartDate + "\nReferralSource:" + referralSource;
    }

    // Method to activate membership
    public void activateMembership() {
        if (!activeStatus) {
            activeStatus = true;
            System.out.println("Membership activated for member: " + name);
        } else {
            System.out.println("Membership is already active for member: " + name);
        }
    }
    
    protected String removalReason;

public void setRemovalReason(String reason) {
    this.removalReason = reason;
}

public String getRemovalReason() {
    return removalReason;
}

public boolean isActiveStatus() {
    return activeStatus;
}

public void setActiveStatus(boolean activeStatus) {
    this.activeStatus = activeStatus;
}

    // Method to deactivate membership
    public void deactivateMembership() {
        if (activeStatus) {
            activeStatus = false;
            System.out.println("Membership deactivated for member: " + name);
        } else {
            System.out.println("Membership is already inactive for member: " + name);
        }
    }

    // Method to mark attendance
    public void markAttendance() {
        if (activeStatus) {
            LocalDate today = LocalDate.now();
            attendanceDates.add(today);
            System.out.println("Attendance marked for " + name + " on " + today);
        } else {
            System.out.println("Cannot mark attendance for inactive member: " + name);
        }
    }

    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public String getReferralSource() {
        return referralSource;
    }


    public ArrayList<LocalDate> getAttendanceDates() {
        return attendanceDates;
    }

    public int getAttendanceCount() {
        return attendanceDates.size();
    }
    
    @Override
public String toString() {
    return "ID: " + memberId + "\nName: " + name + "\nMembership Type: Regular";
}
}
