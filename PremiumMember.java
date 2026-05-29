import java.time.LocalDate;


public class PremiumMember extends GymMember
{
    // Premium member specific attributes
    private static final double PREMIUM_PRICE = 5000;
    private String personalTrainer;
    private double paidAmount;
    private double discountAmount;
    
    // Constructor
    public PremiumMember(String memberId, String name, String location, String phone, 
                         String email, String gender, LocalDate dob, 
                         LocalDate membershipStartDate, String referralSource, 
                         String personalTrainer) {
        super(memberId, name, location, phone, email, gender, dob, membershipStartDate, referralSource);
        this.personalTrainer = personalTrainer;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }


    // Method to calculate discount based on attendance
    public void calculateDiscount() {
        
        if (isActiveStatus()) {
            int attendanceCount = getAttendanceCount();
            if (attendanceCount==1 ) {
                discountAmount = 0.15 * PREMIUM_PRICE; // 15% discount
            } else if (attendanceCount > 10) {
                discountAmount = 0.05 * PREMIUM_PRICE; // 5% discount
            } else {
                discountAmount = 0;
            }
            System.out.println("Discount calculated for " + getName() + ": " + discountAmount);
        } else {
            System.out.println("Cannot calculate discount for inactive member: " + getName());
        }
    }
    
    // Method to pay due amount
    public void payDueAmount(double amount) {
        if (isActiveStatus()) {
            if (amount > 0) {
                paidAmount += amount;
                System.out.println("Payment of " + amount + " received from " + getName());
            } else {
                System.out.println("Invalid payment amount.");
            }
        } else {
            System.out.println("Cannot receive payment for inactive member: " + getName());
        }
    }
    
    // Method to revert member
    public void revertMember(String reason) {
        deactivateMembership();
        System.out.println("Premium member " + getName() + " reverted due to: " + reason);
    }
    
    // Method to calculate price
    @Override
    public double calculatePrice() {
        return PREMIUM_PRICE - discountAmount;
    }
    
    // Check if full payment is made
    public boolean isFullPayment() {
        return paidAmount >= calculatePrice();
    }
    
    // Getters
    public String getPersonalTrainer() {
        return personalTrainer;
    }
    
    public double getPaidAmount() {
        return paidAmount;
    }
    
    public double getDiscountAmount() {
        return discountAmount;
    }
    
    public double getRemainingAmount() {
        return calculatePrice() - paidAmount;
    }
    
    // String representation of the object
    @Override
    public String toString() {
        return super.toString() + String.format(" %-10s %-15.2f %-15.2f %-15.2f", 
                personalTrainer, calculatePrice(), discountAmount, paidAmount);
    }
    
    @Override
public String display() {
    return "ID: " + memberId
    + "\nName: " + name + "\nEmail: " + email + "\nPhone:" + phone +
           "\nPlan: Premium\nStart Date: " + membershipStartDate + "\nGender: "+ gender + "\nDOB:" + dob + "\nMembershipStartDate:" 
        + membershipStartDate + "\nReferralSource:" + referralSource + 
           "\nTrainer: " + personalTrainer + "\nDiscount: " + discountAmount + "\nPaid Amount: $" + paidAmount +"%";
}
}